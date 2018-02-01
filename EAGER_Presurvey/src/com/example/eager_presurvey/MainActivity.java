package com.example.eager_presurvey;


import com.example.eager_presurvey.UI.Presurvey_1;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends BaseActivity {
	static SharedPreferences settings;
	public static SharedPreferences.Editor edit;
    public static final String PREFS_NAME = "TEST";
    private static final String TAG = "MainActivity";
    private static final int TEXT_ID = 0;
    private Context handle = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        settings=getSharedPreferences(PREFS_NAME, 0);
	    edit = settings.edit();
	    
	    

	    
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
                	
                	Intent intent = new Intent(handle, Presurvey_1.class);
            		startActivity(intent);
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
        // do something when the button is clicked
        public void onClick(DialogInterface dialog, int arg1) {
        	createDialog();
        }
  });


  // display box
        AlertDialog alert = alertbox.create();
        alert.setCanceledOnTouchOutside(false);
        alert.show();
}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager 
              = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    
    public void presurvey_button(View view){
		
    	if (isNetworkAvailable()){
	         createDialog();
    	
	         handle = this;
    	}else{
    		Toast.makeText(this, "Please make sure the network is connected!",
    				   Toast.LENGTH_LONG).show();
    	}
    	
	   }
}
