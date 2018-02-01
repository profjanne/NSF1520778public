package com.example.eager_study;



import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import com.example.eager_study_UI.EMA_formal_1;


import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Vibrator;
import android.view.WindowManager;

public class NotificationService extends Service{
	SQLiteDatabase myDB = null;

	boolean isClicked=false;
	public SharedPreferences location_service;
	public SharedPreferences.Editor location_edit;
	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}
	
	@Override
    public int onStartCommand(Intent intent, int flags, int startId) {
		
		Bundle extras = intent.getExtras();
	
		location_service = getApplicationContext().getSharedPreferences("location_service",MODE_MULTI_PROCESS);
		location_edit=location_service.edit();
		double p_lat=location_service.getFloat("previous_lat", 0);
		double p_lon=location_service.getFloat("previous_lon", 0);
		double lat1 = location_service.getFloat("lat1", 0);
		double lon1 = location_service.getFloat("lon1", 0);
		if ((extras==null)||(extras.getString("trigeringMethod")==null)||(extras.getString("trigeringMethod")!=null && !extras.getString("trigeringMethod").equals("location"))){
			if ((p_lat!=0)&&(p_lon!=0)&&(WithinDistance(p_lat, p_lon, lat1, lon1))){
				return START_NOT_STICKY;
			}
		}
		
		Calendar now = Calendar.getInstance();

		location_edit.putLong("previous_prompt_time", now.getTimeInMillis());
		location_edit.putFloat("previous_lat", (float) lat1);
		location_edit.putFloat("previous_lon", (float) lon1);
		location_edit.commit();
		
		
		isClicked=false;
		final Vibrator v = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);

        
      //vibrate  in a pattern
        long pattern[]={0,600,200,1000,300,500,400,300};
       v.vibrate(pattern,1);
       
       
		

    	

		AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
    	builder.setTitle("Please Take the Survey!");

    	builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
    	    public void onClick(DialogInterface dialog, int whichButton) {

    	    	v.cancel();
    	        dialog.dismiss();
    	        
    	        isClicked=true;

    	        Intent mainActivityIntent = new Intent(getApplicationContext(), EMA_formal_1.class);
    	        mainActivityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    	        getApplication().startActivity(mainActivityIntent);
    	    }
    	    });
    	builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
    	    public void onClick(DialogInterface dialog, int whichButton) {
    	        dialog.dismiss();
    	        v.cancel();
    	        isClicked=true;
				
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
                if(!isClicked)
                	showNotification();
                };
            
        }, 30000, 60000);
    	
		
    	return START_NOT_STICKY;
	}
	
	public void showNotification(){
		 
	    
		
        // define sound URI, the sound to be played when there's a notification
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
         
        // intent triggered, you can add other intent for other actions
        Intent intent = new Intent(getApplicationContext(), EMA_formal_1.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
         
        // this is it, we'll build the notification!
        Notification mNotification = new Notification.Builder(this)
             
            .setContentTitle("EAGER Study")
            .setContentText("Please Take the Survey!")
            .setSmallIcon(R.drawable.surveyicon)
            .setContentIntent(pIntent)
            .setSound(soundUri)
            .build();
         
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
 
        mNotification.defaults = Notification.DEFAULT_ALL;
        // Hide the notification after it was selected
         mNotification.flags |= Notification.FLAG_AUTO_CANCEL;
         
         
         
         
        notificationManager.notify(0, mNotification);
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
