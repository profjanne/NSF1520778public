package com.example.eager_study;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import com.example.eager_study_UI.Address_1;
import com.example.eager_study_UI.EMA_formal_1;
import com.example.eager_study_UI.IAT_1;
import com.example.eager_study_util.DownloadPacket;

import android.util.Log;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;


public class MainActivity extends BaseActivity {

	static SharedPreferences settings;
	public static final String PREFS_NAME = "TEST";
	public static SharedPreferences.Editor edit;
	private static final int TEXT_ID = 0;
	private static final String TAG = "MainActivity";
	private MenuItem start, end;
	public static final int MEDIA_TYPE_IMAGE = 1;
	String path = null; 
	public static ArrayList<Double> lat = new ArrayList<Double>();
	public static ArrayList<Double> lon = new ArrayList<Double>();
	public static int work_index=0;
	double radius = 200; // meters
	public static double home_lat = 40.515242, home_lon = -74.461400;
	int IAT_triggering_group=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
		
		settings=getSharedPreferences(PREFS_NAME, MODE_MULTI_PROCESS);
	    edit = settings.edit();
	    
	    path=this.getFilesDir().getAbsolutePath();
	    
	    IAT_triggering_group=settings.getInt("IAT_group", 0);
	    Log.i("iat group", String.valueOf(IAT_triggering_group));
	    if (IAT_triggering_group==0){
	    	Random rn = new Random();
            int answer= rn.nextInt(100)%2+1;
            
            IAT_triggering_group = answer;
	    	edit.putInt("IAT_group", answer);
	    	
        	edit.commit();
	    }

	    if(settings.getBoolean("Alert_Dialog", false)==false){
	         createDialog();
	    }else{
	    	inputAddress();
	    }
	    
	    long iat_interval=0;
	    iat_interval=settings.getLong("iat_interval", 0);
	    if (iat_interval==0){
	    	edit.putLong("iat_interval", 3600*1000);
	    	edit.commit();
	    }
	    
	    Calendar now = Calendar.getInstance();
	    int day_of_year, hour_of_day;
	    boolean before=false;
	    day_of_year=now.get(Calendar.DAY_OF_YEAR);
	    hour_of_day=now.get(Calendar.HOUR_OF_DAY);

	    
	    if (hour_of_day<10){
	    	before=true;
	    }
	    Calendar schedule = Calendar.getInstance();
	    if (before) {
	    	schedule.set(Calendar.DAY_OF_YEAR, day_of_year);
	    	schedule.set(Calendar.HOUR_OF_DAY, 10);
	    	schedule.set(Calendar.MINUTE, 0);
	    	schedule.set(Calendar.SECOND, 0);
	    }else{
	    	int kk;
	    	if (now.get(Calendar.YEAR)==2016)
	    		kk=366;
	    	else kk=365;
	    	if (day_of_year>=kk)
	    		schedule.set(Calendar.DAY_OF_YEAR, 1);
	    	else
	    		schedule.set(Calendar.DAY_OF_YEAR, day_of_year+1);
	    	schedule.set(Calendar.HOUR_OF_DAY, 10);
	    	schedule.set(Calendar.MINUTE, 0);
	    	schedule.set(Calendar.SECOND, 0);
	    }
	    
	    Log.i("iat time now", String.valueOf(now.getTimeInMillis()));
	    Log.i("iat time scheduled", String.valueOf(schedule.getTimeInMillis()));
	    PendingIntent iat_layer_1 = PendingIntent.getService(getApplicationContext(), 1,
	            new Intent(getApplicationContext(), iat_trigger_1.class),PendingIntent.FLAG_CANCEL_CURRENT);
	    AlarmManager alarm_layer_1 = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        alarm_layer_1.setRepeating(AlarmManager.RTC_WAKEUP, schedule.getTimeInMillis(), AlarmManager.INTERVAL_DAY, iat_layer_1);


    }
    
    private void inputAddress(){
    	if (!isMyServiceRunning(GetLocationService.class)){
	    	try{
	    		FileInputStream fileIn=openFileInput("EAGER_Study.txt");
	    		InputStreamReader InputRead= new InputStreamReader(fileIn);
	    		BufferedReader reader = new BufferedReader(InputRead);
	    		String output = "";
	    		String input = reader.readLine();
	    		output=output+input+"\n";
	    		home_lat = Double.parseDouble(input);
	    		input = reader.readLine();
	    		output=output+input+"\n";
	    		home_lon = Double.parseDouble(input);
	    		input = reader.readLine();
	    		output=output+input+"\n";
	    		work_index = (int) Double.parseDouble(input);
	    		input = reader.readLine();
	    		output=output+input+"\n";
	    		lat.clear();
	    		lon.clear();
	    		while ((input!=null)&&(input.length()>0)){
	    			lat.add(Double.parseDouble(input));
	    			input = reader.readLine();
	    			output=output+input+"\n";
	    			lon.add(Double.parseDouble(input));
	    			input = reader.readLine();
	    			output=output+input+"\n";	
	    		}
	    		Intent serviceIntent = new Intent(this,GetLocationService.class);
	    		serviceIntent.putExtra("usual_places_lat", lat);
	    		serviceIntent.putExtra("usual_places_lon", lon);
	    		serviceIntent.putExtra("distance", radius);
	    		serviceIntent.putExtra("home_latitude", home_lat);
	    		serviceIntent.putExtra("home_longitude", home_lon);
	    		serviceIntent.setAction("GetLocationService");
	    		startService(serviceIntent);
	    	}catch (Exception e) {
				String uid=settings.getString("UID", "ERROR");
	    		if (uid.equals("ERROR"))
	    			System.out.println("WRONG!");
	    		else{
	    			DownloadPacket sp=new DownloadPacket(this,uid);
					sp.send();
	    		}
	    		
			}	
			
	    }
    }

    public void address_input_window(){
		AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
    	builder.setTitle("No data is in server. Please input the addresses manually.");
    	builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
    	    public void onClick(DialogInterface dialog, int whichButton) {
    	        dialog.dismiss();
    	        
    	        Intent adddressIntent = new Intent(getApplicationContext(),Address_1.class);
            	startActivity(adddressIntent);
    	    }
    	    });
    	builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
    	    public void onClick(DialogInterface dialog, int whichButton) {
    	        dialog.dismiss();
    	        finish();
    	        
    	    }
    	    });
    	final AlertDialog alert = builder.create();
    	alert.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
    	alert.setCanceledOnTouchOutside(false);
        alert.setCancelable(false);
    	alert.show();
    }
    
    public void open_internet_window(){
		AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
    	builder.setTitle("Please turn on the cellular network or WIFI!");
    	builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
    	    public void onClick(DialogInterface dialog, int whichButton) {
    	    	dialog.dismiss();
    	        finish();
    	    }
    	    });
    	final AlertDialog alert = builder.create();
    	alert.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
    	alert.setCanceledOnTouchOutside(false);
        alert.setCancelable(false);
    	alert.show();
    }
    
	/** Create a file Uri for saving an image or video */
	@SuppressWarnings("unused")
	private  Uri getOutputMediaFileUri(int type) {
		
		return Uri.fromFile(getOutputMediaFile(type));
	}
	private  File getOutputMediaFile(int type) {
		File mediaStorageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

		if (!mediaStorageDir.exists()) {
			if (!mediaStorageDir.mkdirs()) {
				Log.d("HazardTracker", "failed to create directory");
				return null;
			}
		}

		// Create a media file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		File mediaFile;
		if (type == MEDIA_TYPE_IMAGE) {
			mediaFile = new File(mediaStorageDir.getPath() + File.separator
					+ "IMG_EAGER_" + timeStamp + ".jpg");
		} else {
			return null;
		}
		
		return mediaFile;
	}
    public void startEntry(View view) {
		Intent intent = new Intent(this, EMA_formal_1.class);
		startActivity(intent);
	}
    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
    public void IAT_button(View view) {
		Intent intent = new Intent(this, IAT_1.class);
		startActivity(intent);
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

    	switch (item.getItemId()) { 
        
        case 0: 
        	Intent serviceIntent = new Intent(this,GetLocationService.class);
        	serviceIntent.putExtra("usual_places_lat", lat);
        	serviceIntent.putExtra("usual_places_lon", lon);
        	serviceIntent.putExtra("distance", radius);
        	serviceIntent.putExtra("home_latitude", home_lat);
        	serviceIntent.putExtra("home_longitude", home_lon);
        	serviceIntent.setAction("GetLocationService");
        	startService(serviceIntent);
        	item.setEnabled(false);
        	end.setEnabled(true);
            return true;
        case 1:
        	try{
        	stopService(new Intent(this,GetLocationService.class));
        	} catch(Exception e){}
        	start.setEnabled(true);
        	end.setEnabled(false);
        	return true;
        case 2:
        	try{
        		Intent notificationIntent = new Intent(this,NotificationService.class);
        		notificationIntent.setAction("NotificationService");
            	startService(notificationIntent);
        	}catch(Exception e){}
        	return true;
        case 3: 
        	Intent adddressIntent = new Intent(this,Address_1.class);
        	startActivity(adddressIntent);
        	return true;
        } 
        return false;   
    }
    
    private void createDialog() {
		 
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Hello User");
        builder.setMessage("What is your ID:");
 
         // Use an EditText view to get user input.
         final EditText input = new EditText(this);
         input.setId(TEXT_ID);
         builder.setView(input);
 
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
 
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {
                String value = input.getText().toString();
                Log.d(TAG, "User ID: " + value);
                // set the sharedpreference here, keep the id. do something here
                if(!value.isEmpty()){
                	System.out.println("value is "+value+"...");
                	edit.putString("UID", value);
                	edit.putBoolean("Alert_Dialog", true);
                	edit.commit();
                	inputAddress();
                	
                	
                }
                else{
                	alert_dialog();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
        	 
            @Override
            public void onClick(DialogInterface dialog, int which) {
            	alert_dialog();
            }
        });
     // display box
        AlertDialog alert = builder.create();
        alert.setCanceledOnTouchOutside(false);
        alert.show();
 
    }
	private void alert_dialog() {
        AlertDialog.Builder alertbox = new AlertDialog.Builder(this);
        alertbox.setMessage("You Must Input Your ID!!!");
        alertbox.setPositiveButton("OK", new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int arg1) {
        	createDialog();
        }
  });


  // display box
        AlertDialog alert = alertbox.create();
        alert.setCanceledOnTouchOutside(false);
        alert.setCancelable(false);
        alert.show();
}
	@Override
	   public void onBackPressed(){
	   } 
}
