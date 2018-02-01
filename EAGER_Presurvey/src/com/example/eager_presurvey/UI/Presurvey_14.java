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

public class Presurvey_14 extends BaseActivity{
	TextView lat_text, lng_text, question40C;
	double lat=0, lng=0;
	final Question question_fourtyC = new Question(),
			question_fourtyC_lat_lng = new Question();
	@SuppressWarnings("static-access")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.presurvey_14);
        nextActivity = Presurvey_15.class;
		lat_text=(TextView) findViewById(R.id.textView4);
		lng_text=(TextView) findViewById(R.id.textView5);
		question40C=(TextView) findViewById(R.id.textView2);
		
		final String ClassName = this.getClass().getSimpleName();
		question_fourtyC.setActivityText(ClassName);
		question_fourtyC.setQuestionText(question40C.getText().toString());
		this.actQuestionList.add(question_fourtyC);
		question_fourtyC_lat_lng.setActivityText(ClassName);
		question_fourtyC_lat_lng.setQuestionText("Place3");
		this.actQuestionList.add(question_fourtyC_lat_lng);

        
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
      		 this.actQuestionList.remove(this.actQuestionList.size()-1);
      		 this.actQuestionList.remove(this.actQuestionList.size()-1);
 		   this.finish();
        }
        return true;
        
    }
    
    public void next_button(View view){
    	String question40C_answer="N.A.";
    	if ((lat==0)&&(lng==0)){
    		question40C_answer="N.A.";
		}else{
			question40C_answer=String.valueOf(lat)+" "+String.valueOf(lng);
		}
	
    	question_fourtyC.setAnswerText(question40C_answer);
    	question_fourtyC_lat_lng.setAnswerText(question40C_answer);
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
    @SuppressWarnings("static-access")
	@Override
	   public void onBackPressed(){
	
		   this.actQuestionList.remove(this.actQuestionList.size()-1);
		   this.actQuestionList.remove(this.actQuestionList.size()-1);
		   this.finish();
	   }
	   @SuppressWarnings("static-access")
	public void back_button(View view){
		   
		   this.actQuestionList.remove(this.actQuestionList.size()-1);
		   this.actQuestionList.remove(this.actQuestionList.size()-1);
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

	  } 
	}
	}
}
