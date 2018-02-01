package com.example.eager_study;

import java.util.Calendar;
import java.util.Random;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;

public class iat_trigger_1 extends Service{
	private static SharedPreferences settings;
	private static SharedPreferences.Editor edit;
	private int IAT_triggering_group;
	@Override
    public int onStartCommand(Intent intent, int flags, int startId) {
		Log.i("iat_1", "I'm in first layer");
		Calendar now = Calendar.getInstance();
		int day_of_year=now.get(Calendar.DAY_OF_YEAR);
		
		settings=getSharedPreferences("TEST", MODE_MULTI_PROCESS);
	    edit = settings.edit();
	    IAT_triggering_group=settings.getInt("IAT_group", 0);
	    edit.putInt("IAT_home", 0);
	    edit.putInt("IAT_work", 0);
    	edit.commit();
    	PendingIntent iat_layer_2_work = PendingIntent.getService(getApplicationContext(), 2,
	            new Intent(getApplicationContext(), iat_trigger_2.class),PendingIntent.FLAG_CANCEL_CURRENT);
    	
    	PendingIntent iat_layer_2_home = PendingIntent.getService(getApplicationContext(), 3,
	            new Intent(getApplicationContext(), iat_trigger_2_home.class),PendingIntent.FLAG_CANCEL_CURRENT);
	    
    	AlarmManager alarm_layer_2 = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
	    
    	AlarmManager alarm_layer_3 = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
	    
    	Random rn = new Random();
        long answer= (rn.nextInt(2*3600)+3600)*1000; // after 1 to 3 hours in random
    	
    	Log.i("iat check interval", String.valueOf(answer));
	    if (IAT_triggering_group==1){
	    	
    	    alarm_layer_2.setRepeating(AlarmManager.RTC_WAKEUP, now.getTimeInMillis(),
    	    		answer, iat_layer_2_work);
    	    
    	    alarm_layer_3.setRepeating(AlarmManager.RTC_WAKEUP, now.getTimeInMillis()+5*1000,
    	    		answer+5*1000, iat_layer_2_home);
    	    
    	    
	    }else{
	    	Log.i("iat_day_of_year", String.valueOf(day_of_year));
	    	if (day_of_year%2==1){
	    		//home
	    		alarm_layer_3.setRepeating(AlarmManager.RTC_WAKEUP, now.getTimeInMillis()+5*1000,
	    	    		answer+5*1000, iat_layer_2_home);
	    		edit.putInt("IAT_work", 1);
	        	edit.commit();
	    	}else{
	    		//work
	    		alarm_layer_2.setRepeating(AlarmManager.RTC_WAKEUP, now.getTimeInMillis(),
	    	    		answer, iat_layer_2_work);
	    		edit.putInt("IAT_home", 1);
	        	edit.commit();
	    	}
	    }
	    

		
		return START_NOT_STICKY;
	}
	
	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}
}
