package com.example.eager_presurvey.UI;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.Calendar;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.example.eager_presurvey.BaseActivity;
import com.example.eager_presurvey.MainActivity;
import com.example.eager_presurvey.R;
import com.example.eager_presurvey.util.Question;
import com.example.eager_presurvey.util.UploadService;

public class Presurvey_31 extends BaseActivity{
	final Question question_seventynine=new Question();
	TextView question79;
	
	RadioGroup radioGroup1;
	@SuppressWarnings("static-access")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.presurvey_31);
     

        question79= (TextView) findViewById(R.id.textView4);
        radioGroup1=(RadioGroup) findViewById(R.id.rGrp4);
        

        
        final String ClassName = this.getClass().getSimpleName();
 
		
		question_seventynine.setActivityText(ClassName);
		question_seventynine.setQuestionText(question79.getText().toString());
		this.actQuestionList.add(question_seventynine);
		nextActivity = MainActivity.class;
		radioGroup1.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if(checkedId!=-1){
					String answer="";
					RadioButton selRadBtn = (RadioButton) group.findViewById(checkedId);
					answer = (String) selRadBtn.getText();
					question_seventynine.setAnswerText(answer);
					
				}
			}
		});
		
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @SuppressWarnings("static-access")
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
    	switch (item.getItemId()) {
        case android.R.id.home:
        	for (int i=0; i<1; i++){
      		   this.actQuestionList.remove(this.actQuestionList.size()-1);
      		   }
 		   this.finish();
        }
        return true;
        
    }
    
    public void next_button(View view){

    	if ((question_seventynine.getAnswerText()==null) || (question_seventynine.getAnswerText()==""))
    		question_seventynine.setAnswerText("N.A.");
    	
    	if(checkAnswered()){
    		addAllQuestions();
			exportDB();
		   Toast.makeText(getApplicationContext(), "Thank you for completing the survey!", 
				   Toast.LENGTH_SHORT).show();
		   startNext();
		   Intent startMain = new Intent(Intent.ACTION_MAIN);
		   startMain.addCategory(Intent.CATEGORY_HOME);
		   startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		   startActivity(startMain);
    		
			
	
		}
		else{
			Toast.makeText(getBaseContext(), "Please make sure the question is answered", Toast.LENGTH_SHORT).show();
		}
    	
	   }
    
    private void exportDB(){
		File sd = Environment.getExternalStorageDirectory();
	       File data = Environment.getDataDirectory();
	       FileChannel source=null;
	       FileChannel destination=null;
	       String currentDBPath = "/data/com.example.eager_presurvey/databases/presurvey.db";
	       String backupDBPath = "sociologysurvey.db";
	       File currentDB = new File(data, currentDBPath);
	       File backupDB = new File(sd, backupDBPath);
	       try {
	            source = new FileInputStream(currentDB).getChannel();
	            destination = new FileOutputStream(backupDB).getChannel();
	            destination.transferFrom(source, 0, source.size());
	            source.close();
	            destination.close();
	            Toast.makeText(this, "DB Exported!", Toast.LENGTH_LONG).show();
	        } catch(IOException e) {
	        	e.printStackTrace();
	        }
	       long uploadTime = 0;
        
		Calendar cur =Calendar.getInstance();
		
	
		
		PendingIntent pi = PendingIntent.getService(this, 0,
		            new Intent(this, UploadService.class),PendingIntent.FLAG_UPDATE_CURRENT);
 
		
		uploadTime=cur.getTimeInMillis()+10*1000;
		AlarmManager alarmManager=(AlarmManager) getSystemService(ALARM_SERVICE);
		alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, uploadTime,
				3*60*60*1000, pi);
				
	}
	   
    @SuppressWarnings("static-access")
	public void back_button(View view){
		   for (int i=0; i<1; i++){
	 		   this.actQuestionList.remove(this.actQuestionList.size()-1);
	 		   }
		   this.finish();
	   }
	   
	   @SuppressWarnings("static-access")
	@Override
	   public void onBackPressed(){
	for (int i=0; i<1; i++){
		   this.actQuestionList.remove(this.actQuestionList.size()-1);
		   }
		   this.finish();
	   }
    
    
}