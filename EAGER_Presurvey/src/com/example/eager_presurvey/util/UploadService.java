package com.example.eager_presurvey.util;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

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
	    	

	        Toast.makeText(getBaseContext(), "upload Service onStart--->", Toast.LENGTH_SHORT).show();
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