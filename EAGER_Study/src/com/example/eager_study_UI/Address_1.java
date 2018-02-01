package com.example.eager_study_UI;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.eager_study.BaseActivity;
import com.example.eager_study.MainActivity;
import com.example.eager_study.R;
import com.example.eager_study_util.GeocodingLocation;

public class Address_1 extends BaseActivity{
	EditText street_number, city, state, zip;
	TextView latLongTV;
	private int address_count=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.address_1);
		street_number= (EditText) findViewById(R.id.editText1);
		street_number.setHint("Street and number");
		city= (EditText) findViewById(R.id.editText2);
		city.setHint("City");
		state= (EditText) findViewById(R.id.editText3);
		state.setHint("State");
		zip= (EditText) findViewById(R.id.editText4);
		zip.setHint("Zip code");
		latLongTV = (TextView) findViewById(R.id.latLongTV);
		address_count=MainActivity.lat.size();
		
	}
	@Override
	   public boolean onCreateOptionsMenu(Menu menu) {
	      // Inflate the menu; this adds items to the action bar if it is present.
	      getMenuInflater().inflate(R.menu.main, menu);
	      return true;
	   }
	public void next_button(View view){
		Intent adddressIntent = new Intent(this,Address_2.class);
    	startActivity(adddressIntent);
	   }
	
	@Override
	   public void onBackPressed(){
		if (MainActivity.lat.size()>address_count)
			MainActivity.lat.remove(MainActivity.lat.size()-1);
		Intent mainIntent = new Intent(getApplicationContext(),MainActivity.class);
    	startActivity(mainIntent);
	   }
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()) {
            case android.R.id.home:
            	if (MainActivity.lat.size()>address_count)
        			MainActivity.lat.remove(MainActivity.lat.size()-1);
            	Intent mainIntent = new Intent(getApplicationContext(),MainActivity.class);
            	startActivity(mainIntent);
            }
            return true;
    }
	public void back_button(View view){
		if (MainActivity.lat.size()>address_count)
			MainActivity.lat.remove(MainActivity.lat.size()-1);
		Intent mainIntent = new Intent(getApplicationContext(),MainActivity.class);
    	startActivity(mainIntent);
	   }
	@SuppressWarnings("static-access")
	public void lat_lng_button(View view){
		
        String address = street_number.getText().toString()+" "+city.getText().toString()+" "+state.getText().toString()+" "+zip.getText().toString();

        GeocodingLocation locationAddress = new GeocodingLocation();
        locationAddress.getAddressFromLocation(address, getApplicationContext(), new GeocoderHandler(), 1);
	   }
	
	 private class GeocoderHandler extends Handler {
	        @Override
	        public void handleMessage(Message message) {
	            String locationAddress;
	            switch (message.what) {
	                case 1:
	                    Bundle bundle = message.getData();
	                    locationAddress = bundle.getString("address");
	                    break;
	                default:
	                    locationAddress = null;
	            }
	            latLongTV.setText(locationAddress);
	        }
	    }
}
