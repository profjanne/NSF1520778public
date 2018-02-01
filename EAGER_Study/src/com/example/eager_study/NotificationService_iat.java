package com.example.eager_study;


import java.util.Timer;
import java.util.TimerTask;

import com.example.eager_study_UI.IAT_1;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Vibrator;
import android.view.WindowManager;

public class NotificationService_iat extends Service{
	SQLiteDatabase myDB = null;
	Vibrator v;
	boolean isClicked=false;
	public SharedPreferences settings, location_service;
	public SharedPreferences.Editor edit;
	private String prompt_type=null;
	private double current_lat, current_lon;
	private static double past_lat=0, past_lon=0;
	private int count=0;
	long pattern[]={0,600,200,1000,300,500,400,300};
	Handler mHandler;
	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}
	
	@Override
    public int onStartCommand(Intent intent, int flags, int startId) {
		
		Bundle extras = intent.getExtras();
		location_service = getApplicationContext().getSharedPreferences("location_service",MODE_MULTI_PROCESS);
		v = (Vibrator)getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
		past_lat = location_service.getFloat("lat1", 0);
		past_lon = location_service.getFloat("lon1", 0);
		count=0;
		settings=getSharedPreferences("TEST", MODE_MULTI_PROCESS);
		edit = settings.edit();
		if (extras!=null) {
			prompt_type = extras.getString("prompt_type", "N.A.");
		}
		isClicked=false;
		

        

        v.vibrate(pattern,1);
       

		final AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
    	builder.setTitle("Please Take the IAT test!");

    	builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
    	    public void onClick(DialogInterface dialog, int whichButton) {

    	    	v.cancel();
    	        dialog.dismiss();
    	        if ((prompt_type!=null)&&(prompt_type.equals("home"))){
    	        	edit.putInt("IAT_home", 1);
    	        	edit.commit();
    	        }else if ((prompt_type!=null)&&(prompt_type.equals("work"))){
    	        	edit.putInt("IAT_work", 1);
    	        	edit.commit();
    	        }
    	        
    	        isClicked=true;
    	        Intent mainActivityIntent = new Intent(getApplicationContext(), IAT_1.class);
    	        mainActivityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    	        getApplication().startActivity(mainActivityIntent);
    	    }
    	    });
    	builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
    	    public void onClick(DialogInterface dialog, int whichButton) {
    	        dialog.dismiss();
    	        v.cancel();
    	      
				
    	    }
    	});
    	
    	final AlertDialog alert = builder.create();
    	alert.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
    	alert.setCanceledOnTouchOutside(false);
    	alert.show();
    	
    	final Timer autoStop = new Timer();
        autoStop.schedule(new TimerTask() {
            @Override
            public void run() {
            	try{ 
            		v.cancel();
            		alert.cancel();
            	}catch(Exception e){
            		
            	}
                autoStop.cancel();
                System.out.println("is it clicked: "+isClicked);
            
                };
            
        }, 30000, 60000);
        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message message) {
            	alert.show();
            }
        };
        
        final Timer reminder = new Timer();
        reminder.schedule(new TimerTask() {
            @SuppressWarnings("unused")
			@Override
            public void run() {
            	if (isClicked){
            		reminder.cancel();
            	}else{
            		
            	
            	AlertDialog alert2=null;
            	current_lat = location_service.getFloat("lat1", 0);
        		current_lon = location_service.getFloat("lon1", 0);
        	if (count<=0){
        		if (WithinDistance(current_lat, current_lon, past_lat, past_lon)){
        			v.vibrate(pattern,1);
        			Message message = mHandler.obtainMessage();
        		    message.sendToTarget();
        	    	
        	    	
        		}else{
        			reminder.cancel();
        		}
        	}else{
            		try{ 
                		v.cancel();
                		if (alert2!=null) alert2.cancel();
                	}catch(Exception e){
                		
                	}
            		reminder.cancel();
            	}
        		count++;
            	
            }
            }
                
            
        }, 20*60*1000, 30000); 
    
    	return START_NOT_STICKY;
	}

	private boolean WithinDistance(double lat1, double lon1, double lat2, double lon2){
    	boolean flag=false;
    	double d2r = Math.PI / 180.0;
  	  	double dlon = (lon2 - lon1) * d2r;
  	  	double dlat = (lat2 - lat1) * d2r;
  	  	double a = Math.sin(dlat/2.0)*Math.sin(dlat/2.0) + Math.cos(lat1*d2r) * Math.cos(lat2*d2r) * Math.sin(dlon/2.0)*Math.sin(dlon/2.0);
  	  	double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)) * 3959 * 1609.34;
  	  	if (c<=804.672) flag=true;
  	  	return flag;
  	  	
    }
	

}
