package com.example.eager_study;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimerTask;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

public class GetLocationService extends Service{
	static SharedPreferences settings;
	Handler handler = new Handler();
	private static final String TAG="EAGER_Location"; 
	private String TableName = "location";
	private String[] key_lat = {"location1_lat", "location2_lat", "location3_lat", "location4_lat", "location5_lat", "location6_lat", "locaion7_lat", "location8_lat"};
	private String[] key_lon = {"location1_lon", "location2_lon", "location3_lon", "location4_lon", "location5_lon", "location6_lon", "locaion7_lon", "location8_lon"};
	private SharedPreferences location_service;
    private SharedPreferences.Editor location_edit;
	
	private static Location lastLocation;
	
	private static final String SQLite_NAME = "/data/com.example.eager_study/databases/questions.db";//database
	private File data = Environment.getDataDirectory();
		 

	Intent intent;
    // flag for GPS status
    boolean isGPSEnabled = false;

    private static PendingIntent pi;
    
    private AlarmManager alarmManager;
    // flag for network status
    boolean isNetworkEnabled = false;
 
    // flag for GPS status
    boolean canGetLocation = false;
 
    Location location; // location
    private static double latitude; 
    private static double longitude;
 
    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 0;
    
    //GPS sampling rate
    private static final long UPDATE_RATE=2*1000*60; //2 minute
 
    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 30; //30 sec

    // Declaring a Location Manager
    protected LocationManager locationManager;
    
    private static ArrayList<Double> latArray, lonArray;
    
    private static double radius=200;

    private static boolean leaving = true;
    private static long previous_prompt_time=0;
    
    private static double previous_lat=0, previous_lon=0;

    
    private static double home_lat=0, home_lon=0;
    @SuppressWarnings("unchecked")
	public int onStartCommand(Intent intent1, int flags, int startId)  
    {  
    
        Log.i(TAG, "Service onStart--->");
        alarmManager=(AlarmManager) getSystemService(Context.ALARM_SERVICE);
        pi = PendingIntent.getService(getApplicationContext(), 0,
	            new Intent(getApplicationContext(), NotificationService.class),PendingIntent.FLAG_UPDATE_CURRENT);
        
        location_service = getApplicationContext().getSharedPreferences("location_service",MODE_MULTI_PROCESS);
		double p_home_lat=location_service.getFloat("home_lat", -1000);
		double p_home_lon=location_service.getFloat("home_lon", -1000);
		if ((p_home_lat!=-1000)&&(p_home_lon!=-1000)){
			home_lat = p_home_lat;
			home_lon = p_home_lon;
		}
		location_edit=location_service.edit();
		int size = location_service.getInt("location_size", -1);
		if (size!=-1){
			latArray = new ArrayList<Double>();
			lonArray = new ArrayList<Double>();
			for (int i=0; i<size; i++){
				double kk_lat = location_service.getFloat(key_lat[i], -1000);
				double kk_lon = location_service.getFloat(key_lon[i], -1000);
				
				if ((kk_lat!=-1000)&&(kk_lon!=-1000)){
					latArray.add(kk_lat);
					lonArray.add(kk_lon);
				}
				
				
				
			}
		}
        if (intent1!=null){
        Bundle extras = intent1.getExtras();
        intent=intent1;
        if (extras!=null) {
        	latArray=(ArrayList<Double>) extras.get("usual_places_lat");
        	lonArray=(ArrayList<Double>) extras.get("usual_places_lon");
        	home_lat= (double) extras.getDouble("home_latitude");
        	home_lon= (double) extras.getDouble("home_longitude");
        	radius=(double) extras.getDouble("distance");
        	
        	location_edit.putFloat("home_lat", (float) home_lat);
        	location_edit.putFloat("home_lon", (float) home_lon);
        	location_edit.putInt("location_size", latArray.size());
        	location_edit.commit();
        	
        	for (int i=0; i<latArray.size(); i++){
        		location_edit.putFloat(key_lat[i], (float) latArray.get(i).doubleValue());
        		location_edit.putFloat(key_lon[i], (float) lonArray.get(i).doubleValue());
        		location_edit.commit();
        	}
        	
        }
        }
        
        System.out.println("Service ---> " + Thread.currentThread().getId());
       
        double t_lat=location_service.getFloat("lastLocation_lat", -1000);
		double t_lon=location_service.getFloat("lastLocation_lon", -1000);
		long t_time=location_service.getLong("lastLocation_time", -1000);
		if ((t_lat!=-1000)&&(t_lon!=-1000)&&(t_time!=-1000)){
			lastLocation=new Location("test");
			lastLocation.setLatitude(t_lat);
			lastLocation.setLongitude(t_lon);
			lastLocation.setTime(t_time);
		}
		
		double t_lat1=location_service.getFloat("lastPrompt_lat", -1000);
		double t_lon1=location_service.getFloat("lastPrompt_lon", -1000);
		if ((t_lat1!=-1000)&&(t_lon1!=-1000)){
			location_edit.putFloat("lat1", (float) t_lat1);
			location_edit.putFloat("lon1", (float) t_lon1);
			
		}
		
		double p_lat=location_service.getFloat("previous_lat", 0);
		double p_lon=location_service.getFloat("previous_lon", 0);
		
		previous_lat = p_lat;
		previous_lon = p_lon;
		
		
		String leave=location_service.getString("leaving", "NA");
		if (!leave.equals("NA")){
			if (leave.equals("true"))
				leaving=true;
			if (leave.equals("false"))
				leaving=false;
		}
		
		long pre_time = location_service.getLong("previous_prompt_time", 0);
		
			previous_prompt_time=pre_time;
		
		
		handler.postDelayed(runnable, 100);
		return START_STICKY;  
    }
    
	private Runnable runnable = new Runnable() {
		@Override
		public void run() {
			location = getLocation();
			if (location != null) {
				double lat2, lon2;
				lat2=location.getLatitude();
				lon2=location.getLongitude();
				location_edit.putFloat("lat1", (float) location.getLatitude());
				location_edit.putFloat("lon1", (float) location.getLongitude());
				
				
				
			
				location_edit.putFloat("lastPrompt_lat", (float)lat2);
				location_edit.putFloat("lastPrompt_lon", (float)lon2);
				location_edit.commit();
				

				if(lastLocation!=null){
					System.out.println(location.getTime()
								- lastLocation.getTime());
				}

				if (lastLocation == null
						|| (lastLocation != null && location.getTime()
								- lastLocation.getTime() >= UPDATE_RATE)) {
					lastLocation = location;
					
					
					location_edit.putFloat("lastLocation_lat", (float)lastLocation.getLatitude());
					location_edit.putFloat("lastLocation_lon", (float)lastLocation.getLongitude());
					location_edit.putLong("lastLocation_time", lastLocation.getTime());
					
					location_edit.commit();
					
					
					double lat = location.getLatitude();
					double lng = location.getLongitude();
					SQLiteDatabase myDB = null;
					settings=getSharedPreferences("TEST", MODE_MULTI_PROCESS);
					String idString = settings.getString("UID", "ERROR");
					
					/* Create a Database. */
					try {
						File dbfile = new File(data,SQLite_NAME);
						myDB = SQLiteDatabase
								.openOrCreateDatabase(dbfile, null);

						/* Insert data to a Table */
						myDB.execSQL("INSERT INTO " + TableName
								+ " (time, userid, lat, lon)"
								+ " VALUES (datetime('now','localtime'), '" + idString + "', "
								+ lat + ", " + lng + ");");
						
						System.out.println("inserted?:"+lat+" "+lng);
					} catch (Exception e) {
						Log.e("Error", "Error", e);
					} finally {
						if (myDB != null)
							myDB.close();
					}
					if (WithinDistance(home_lat, home_lon, lat, lng)) {
						leaving = true;
						
						location_edit.putString("leaving", "true");
						location_edit.commit();
					}else{
						if (leaving==true){
							Calendar calendar = Calendar.getInstance();
							long _alarm = 0;
		            	
							_alarm = calendar.getTimeInMillis() + 3600*1000; //3600*1000

							alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm,
									3*3600*1000, pi); //3*3600*1000
							}
						
						location_edit.putString("leaving", "false");
						location_edit.commit();
					}
					if (leaving==true){
						Calendar calendar = Calendar.getInstance();
						long _alarm = 0;
	            	
						_alarm = calendar.getTimeInMillis() + 3600*1000; //3600*1000

						alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm,
								3*3600*1000, pi); //3*3600*1000
					}
					
					for (int i=0; i<latArray.size(); i++){
						int place=-1;
						if (WithinDistance(latArray.get(i), lonArray.get(i), lat, lng))
							place =i;
						if (place!=-1){
							double p_lat=location_service.getFloat("previous_lat", 0);
							double p_lon=location_service.getFloat("previous_lon", 0);
							previous_lat = p_lat;
							previous_lon = p_lon;
							long pre_time2 = location_service.getLong("previous_prompt_time", 0);
							previous_prompt_time=pre_time2;
							if ((previous_prompt_time==0)||((Calendar.getInstance().getTimeInMillis()-previous_prompt_time>3600*1000)
								&&(!WithinDistance(previous_lat, previous_lon, lat, lng)))){
							//start the notificationservice
							Intent notificationIntent = new Intent(getApplicationContext(),NotificationService.class);
			        		notificationIntent.setAction("NotificationService");
			        		
			        		notificationIntent.putExtra("trigeringMethod", "location");
			            	startService(notificationIntent);
			            	// set the alarm
			            	Calendar calendar = Calendar.getInstance();
			            	long _alarm = 0;
			            	
			                _alarm = calendar.getTimeInMillis() + 3*3600*1000;

			        		alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm,
			        				3*3600*1000, pi);
			        		}
						}
							
					}
				}
			}else {
				//Do nothing
			}
			
		
			System.out.println("Runnable ---> " + Thread.currentThread().getId());

			handler.postDelayed(this, UPDATE_RATE);
		}
	};
    
    class task extends TimerTask{

		@Override
		public void run() {
			
			if(locationManager != null){
			locationManager.removeUpdates(locationListener);
			}
		}
		
	}
    
 
    public Location getLocation() {
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
 
            // getting GPS status
            isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);
 
            // getting network status
            isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
 
            if (!isGPSEnabled && !isNetworkEnabled) {
                // no network provider is enabled
            	return null;
            } else {
                this.canGetLocation = true;
                // First get location from Network Provider
                if (isGPSEnabled) {
                    locationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, locationListener);
                    Log.d("GPS", "GPS");
                    if (locationManager != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        System.out.println(sameLocation(lastLocation,location));
                       // System.out.println(location.equals(lastLocation));
                        long curtime= System.currentTimeMillis();
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                            location.setTime(curtime);
                            System.out.println("current Location is "+location.getLatitude()+", "+location.getLongitude());
                            
                            if(lastLocation!=null)
                            	System.out.println("last Location is "+lastLocation.getLatitude()+", "+lastLocation.getLongitude());
                            else
                            	System.out.println("lastLocation is null");
                        }
                        
                    }
                }
               
                if (isNetworkEnabled) {
                	//System.out.println("enter GPS");
                    if (location == null) {
                    	System.out.println("here?");
                        locationManager.requestLocationUpdates(
                                LocationManager.NETWORK_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, locationListener);
                        Log.d("NETWORK", "NETWORK");
                        if (locationManager != null) {
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                            long curtime= System.currentTimeMillis();
                            if (location != null) {
                            	location.setTime(curtime);
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                                System.out.println("current Location is "+location.getLatitude()+", "+location.getLongitude());
                                
                                if(lastLocation!=null)
                                	System.out.println("last Location is "+lastLocation.getLatitude()+", "+lastLocation.getLongitude());
                                else
                                	System.out.println("lastLocation is null");
                            }
                        }
                    }
                }
            }
 
        } catch (Exception e) {
            e.printStackTrace();
            //Toast.makeText(getBaseContext(),"WRONG",Toast.LENGTH_SHORT).show();
            
        }
 
        return location;
    }
     
    /**
     * Stop using GPS listener
     * Calling this function will stop using GPS in your app
     * */
    public void stopUsingGPS(){
//        if(locationManager != null){
//            locationManager.removeUpdates(GetLocationService.this);
//        }       
    }
     
    /**
     * Function to get latitude
     * */
    public double getLatitude(){
        if(location != null){
            latitude = location.getLatitude();
        }
         
        // return latitude
        return latitude;
    }
     
    /**
     * Function to get longitude
     * */
    public double getLongitude(){
        if(location != null){
            longitude = location.getLongitude();
        }
         
        // return longitude
        return longitude;
    }
     
    /**
     * Function to check GPS/wifi enabled
     * @return boolean
     * */
    public boolean canGetLocation() {
        return this.canGetLocation;
    }
    
    LocationListener locationListener = new LocationListener() {
 
 
    @Override
    public void onLocationChanged(Location location) {
    }
 
    @Override
    public void onProviderDisabled(String provider) {
    }
 
    @Override
    public void onProviderEnabled(String provider) {
    }
 
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }
    };
 
    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }
    
    public boolean sameLocation(Location l1, Location l2){
    	if(l1==null&&l2==null)
    		return true;
    	if(l1==null||l2==null)
    		return false;
    	if(l1.getLatitude()==l2.getLatitude()&&l1.getLongitude()==l2.getLongitude())
    		return true;
    	else
    		return false;
    	
    }
    
    private boolean WithinDistance(double lat1, double lon1, double lat2, double lon2){
    	boolean flag=false;
    	double d2r = Math.PI / 180.0;
  	  	double dlon = (lon2 - lon1) * d2r;
  	  	double dlat = (lat2 - lat1) * d2r;
  	  	double a = Math.sin(dlat/2.0)*Math.sin(dlat/2.0) + Math.cos(lat1*d2r) * Math.cos(lat2*d2r) * Math.sin(dlon/2.0)*Math.sin(dlon/2.0);
  	  	double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)) * 3959 * 1609.34;
  	  	if (c<=radius) flag=true;
  	  	return flag;
  	  	
    }
    

 
}