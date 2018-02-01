package com.example.eager_presurvey.UI;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.widget.SearchView;
import com.example.eager_presurvey.R;
import com.example.eager_presurvey.util.PlaceProvider;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class SelectionMap extends SherlockFragmentActivity implements LoaderCallbacks<Cursor>{
	
	GoogleMap mGoogleMap;
	private static SharedPreferences settings;
	public static final String PREFS_NAME = "home_location";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.selectionmap);
		
		settings=getSharedPreferences(PREFS_NAME, MODE_MULTI_PROCESS);
	    double latitude = settings.getFloat("lat", (float)40.500525);
	    double longitude = settings.getFloat("lng", (float)-74.447592);
	    
		SupportMapFragment fragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mGoogleMap = fragment.getMap();

        handleIntent(getIntent());
        CameraUpdate point = CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 15.0f);

     // moves camera to coordinates
        mGoogleMap.moveCamera(point);
        mGoogleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng point) {
            	MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(point);
                markerOptions.title("lat: "+Math.round(point.latitude*10000)/10000.0+" lng: "+Math.round(point.longitude*10000)/10000.0);
                markerOptions.snippet("Tap HERE to select this location!");
                mGoogleMap.addMarker(markerOptions).showInfoWindow();
            }
        });
        mGoogleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
			
			@Override
			public void onInfoWindowClick(Marker marker) {
			        Intent resultIntent = new Intent();
			        double lat = marker.getPosition().latitude;
			        double lng = marker.getPosition().longitude;
			        resultIntent.putExtra("latitude", lat);
			        resultIntent.putExtra("longitude", lng);
			        setResult(Activity.RESULT_OK, resultIntent);
			        finish();
			      
				
			}
		});
	}

	private void handleIntent(Intent intent){
		if (intent.getAction()!=null){
        if(intent.getAction().equals(Intent.ACTION_SEARCH)){
                doSearch(intent.getStringExtra(SearchManager.QUERY));
        }else if(intent.getAction().equals(Intent.ACTION_VIEW)){
                getPlace(intent.getStringExtra(SearchManager.EXTRA_DATA_KEY));
        }
		}
	}

	@Override
	protected void onNewIntent(Intent intent) {
	        super.onNewIntent(intent);
	        setIntent(intent);
	        handleIntent(intent);
	}
	
	private void doSearch(String query){
	        Bundle data = new Bundle();
	        data.putString("query", query);
	        getSupportLoaderManager().restartLoader(0, data, this);
	}
	
	private void getPlace(String query){
	        Bundle data = new Bundle();
	        data.putString("query", query);
	        getSupportLoaderManager().restartLoader(1, data, this);
	}	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//getMenuInflater().inflate(R.menu.main, menu);
		// Inflate the menu; this adds items to the action bar if it is present.
	getSupportMenuInflater().inflate(R.menu.main, menu);
		
		// Get the SearchView and set the searchable configuration
	    SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
	    SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
	    // Assumes current activity is the searchable activity
	    searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

		return true;
	}

	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle query) {
		 CursorLoader cLoader = null;
         if(arg0==0)
                 cLoader = new CursorLoader(getBaseContext(), PlaceProvider.SEARCH_URI, null, null, new String[]{ query.getString("query") }, null);
         else if(arg0==1)
                 cLoader = new CursorLoader(getBaseContext(), PlaceProvider.DETAILS_URI, null, null, new String[]{ query.getString("query") }, null);
         return cLoader;

	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor c) {
		showLocations(c);
		
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {	
	}
	
	private void showLocations(Cursor c){
        MarkerOptions markerOptions = null;
        LatLng position = null;
        mGoogleMap.clear();
        while(c.moveToNext()){
                markerOptions = new MarkerOptions();
                position = new LatLng(Double.parseDouble(c.getString(1)),Double.parseDouble(c.getString(2)));
                markerOptions.position(position);
                markerOptions.title(c.getString(0));
                markerOptions.snippet("Tap HERE to select this location!");
                mGoogleMap.addMarker(markerOptions).showInfoWindow();
        }
        if(position!=null){
                CameraUpdate cameraPosition = CameraUpdateFactory.newLatLng(position);
                mGoogleMap.animateCamera(cameraPosition);
        }
	}
}
