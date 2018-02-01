package com.example.eager_study;


import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

public class iat_trigger_2_home extends Service{
	private SharedPreferences location_service, settings;
	private static final long UPDATE_RATE=15*1000*60; //15 minute
	private Handler handler = new Handler();
	private double radius = 200;
	private int count=0;
	private double current_lat, current_lon;
	private static double past_lat=0, past_lon=0;
	@Override
    public int onStartCommand(Intent intent, int flags, int startId) {
		Log.i("iat", "I'm in home service");
		past_lat=0; 
		past_lon=0;
		
		location_service = getApplicationContext().getSharedPreferences("location_service",MODE_MULTI_PROCESS);
		settings=getSharedPreferences("TEST", MODE_MULTI_PROCESS);
		double home_lat=location_service.getFloat("home_lat", -1000);
		double home_lon=location_service.getFloat("home_lon", -1000);
		
		int iat_home = settings.getInt("IAT_home", -1);
		if (home_lat==-1000 || home_lon==-1000){
		}
		
		current_lat = location_service.getFloat("lat1", 0);
		current_lon = location_service.getFloat("lon1", 0);
		
		if ((WithinDistance(current_lat, current_lon, home_lat, home_lon))&&(iat_home==0)){
			count=0;
			Log.i("iat_at_home", "I'm at home now!");
			handler.postDelayed(runnable, 100);
		}
		
		return START_NOT_STICKY;
	}
	
	@Override
	public IBinder onBind(Intent arg0) {
		return null;
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
	  
		private Runnable runnable = new Runnable() {
			@Override
			public void run() {
				if (count==0){
					past_lat = current_lat;
					past_lon = current_lon;
					count++;
					handler.postDelayed(this, UPDATE_RATE);
				}else if (count==1){
					current_lat = location_service.getFloat("lat1", 0);
					current_lon = location_service.getFloat("lon1", 0);
					if (WithinDistance(past_lat, past_lon, current_lat, current_lon)){
						Intent notificationIntent = new Intent(getApplicationContext(),NotificationService_iat.class);
						notificationIntent.setAction("NotificationService_iat");
		        		notificationIntent.putExtra("prompt_type", "home");
						startService(notificationIntent);
					}
				}
				
				
			}
		};
	    
}
