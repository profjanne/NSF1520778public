package com.example.eager_presurvey.UI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.eager_presurvey.BaseActivity;
import com.example.eager_presurvey.R;
import com.example.eager_presurvey.util.Question;

public class Presurvey_25 extends BaseActivity{
	TextView lat_text, lng_text, lat_text2, lng_text2, lat_text3, lng_text3, question66, question67, question68;
	double lat=0, lng=0, lat2=0, lng2=0, lat3=0, lng3=0;
	final Question question_sixtysix = new Question(), question_sixtyseven = new Question(), question_sixtyeight = new Question();
	@SuppressWarnings("static-access")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.presurvey_25);
        nextActivity = Presurvey_26.class;
		lat_text=(TextView) findViewById(R.id.textView3);
		lng_text=(TextView) findViewById(R.id.textView4);
		

		lat_text2=(TextView) findViewById(R.id.textView6);
		lng_text2=(TextView) findViewById(R.id.textView7);
		
		lat_text3=(TextView) findViewById(R.id.textView9);
		lng_text3=(TextView) findViewById(R.id.textView10);
		
		question66=(TextView) findViewById(R.id.textView2);
		question67=(TextView) findViewById(R.id.textView5);
		question68=(TextView) findViewById(R.id.textView8);

		final String ClassName = this.getClass().getSimpleName();
		question_sixtysix.setActivityText(ClassName);
		question_sixtysix.setQuestionText(question66.getText().toString());
		this.actQuestionList.add(question_sixtysix);
		
		question_sixtyseven.setActivityText(ClassName);
		question_sixtyseven.setQuestionText(question67.getText().toString());
		this.actQuestionList.add(question_sixtyseven);
		
		question_sixtyeight.setActivityText(ClassName);
		question_sixtyeight.setQuestionText(question68.getText().toString());
		this.actQuestionList.add(question_sixtyeight);
        
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
        	for (int i=0; i<3; i++){
      		   this.actQuestionList.remove(this.actQuestionList.size()-1);
      		   }
 		   this.finish();
        }
        return true;
        
    }
    
    public void next_button(View view){
    	String question66_answer="N.A.", question67_answer="N.A.", question68_answer="N.A.";
    	if ((lat==0)&&(lng==0)){
			
		}else{
			question66_answer=String.valueOf(lat)+" "+String.valueOf(lng);
		}
	
    	question_sixtysix.setAnswerText(question66_answer);
	
	
	if ((lat2==0)&&(lng2==0)){
		
		}else{
			question67_answer=String.valueOf(lat2)+" "+String.valueOf(lng2);
			}

	question_sixtyseven.setAnswerText(question67_answer);
	
	if ((lat3==0)&&(lng3==0)){
		
	}else{
		question68_answer=String.valueOf(lat3)+" "+String.valueOf(lng3);
		}

	question_sixtyeight.setAnswerText(question68_answer);
	
    	startNext();
    	
	   }
    
    public void map_button(View view){
    	Intent intent = new Intent(this, SelectionMap.class);
    	startActivityForResult(intent, 1000);
	   }
    public void map_button2(View view){
    	Intent intent = new Intent(this, SelectionMap.class);
    	startActivityForResult(intent, 900);
	   }
    public void map_button3(View view){
    	Intent intent = new Intent(this, SelectionMap.class);
    	startActivityForResult(intent, 800);
	   }
    @SuppressWarnings("static-access")
	@Override
	   public void onBackPressed(){
	for (int i=0; i<3; i++){
		   this.actQuestionList.remove(this.actQuestionList.size()-1);
		   }
		   this.finish();
	   }
	   @SuppressWarnings("static-access")
	public void back_button(View view){
		   for (int i=0; i<3; i++){
		   this.actQuestionList.remove(this.actQuestionList.size()-1);
		   }
		   this.finish();
	   }
    
	@Override 
	public void onActivityResult(int requestCode, int resultCode, Intent data) {     
	  super.onActivityResult(requestCode, resultCode, data); 
	  if((resultCode != RESULT_CANCELED)&&(data!=null)){
	  switch(requestCode) { 
	    case (1000) : { 
	      if (resultCode == Activity.RESULT_OK) { 
	    	  lat = data.getDoubleExtra("latitude", 0);
	    	  lng = data.getDoubleExtra("longitude", 0);
	    	  lat_text.setText("Selected Latitude: "+lat);
	    	  lng_text.setText("Selected Longitude: "+lng);
	      } 
	      break; 
	     
	    }
	    case (900): { 
		      if (resultCode == Activity.RESULT_OK) { 
		    	  lat2 = data.getDoubleExtra("latitude", 0);
		    	  lng2 = data.getDoubleExtra("longitude", 0);
		    	  lat_text2.setText("Selected Latitude: "+lat2);
		    	  lng_text2.setText("Selected Longitude: "+lng2);
		      } 
		      break; 
		     
		    }
	    case (800): { 
		      if (resultCode == Activity.RESULT_OK) { 
		    	  lat3 = data.getDoubleExtra("latitude", 0);
		    	  lng3 = data.getDoubleExtra("longitude", 0);
		    	  lat_text3.setText("Selected Latitude: "+lat3);
		    	  lng_text3.setText("Selected Longitude: "+lng3);
		      } 
		      break; 
		     
		    }
	  } 
	}
	}
}