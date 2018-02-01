package com.example.eager_study_util;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;


public class UploadService extends Service{
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	 @Override  
	    public void onCreate()  
	    {  
		 System.out.println("upload Service onCreate--->");
	        
	        super.onCreate();  

	    }  
	 
	     
	    public int onStartCommand(Intent intent, int flags, int startId)  
	    {
	        SendPacket sp=new SendPacket(this,"sociologysurvey");
			sp.send();
			this.stopSelf();
			return START_STICKY;  
	    }  
	 
	    @Override 
	    public void onDestroy()
	    {  
	        System.out.println("upload Service onDestroy--->");
	        super.onDestroy();
	    }

}