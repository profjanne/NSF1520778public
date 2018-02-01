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

public class Presurvey_13 extends BaseActivity{
	TextView lat_text, lng_text, lat_text2, lng_text2, question40A, question40B;
	double lat=0, lng=0, lat2=0, lng2=0;
	final Question question_fourtyA = new Question(), question_fourtyB = new Question(),
			question_fourtyA_lat_lng = new Question(), question_fourtyB_lat_lng = new Question();
	
	@SuppressWarnings("static-access")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.presurvey_13);
        nextActivity = Presurvey_14.class;
		lat_text=(TextView) findViewById(R.id.textView4);
		lng_text=(TextView) findViewById(R.id.textView5);
		question40A=(TextView) findViewById(R.id.textView2);
		question40B=(TextView) findViewById(R.id.textView6);
		lat_text2=(TextView) findViewById(R.id.textView8);
		lng_text2=(TextView) findViewById(R.id.textView9);
		final String ClassName = this.getClass().getSimpleName();
		question_fourtyA.setActivityText(ClassName);
		question_fourtyA.setQuestionText(question40A.getText().toString());
		this.actQuestionList.add(question_fourtyA);
		question_fourtyA_lat_lng.setActivityText(ClassName);
		question_fourtyA_lat_lng.setQuestionText("Place1");
		this.actQuestionList.add(question_fourtyA_lat_lng);
		
		question_fourtyB.setActivityText(ClassName);
		question_fourtyB.setQuestionText(question40B.getText().toString());
		this.actQuestionList.add(question_fourtyB);
		question_fourtyB_lat_lng.setActivityText(ClassName);
		question_fourtyB_lat_lng.setQuestionText("Place2");
		this.actQuestionList.add(question_fourtyB_lat_lng);
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
        	for (int i=0; i<4; i++){
      		   this.actQuestionList.remove(this.actQuestionList.size()-1);
      		   }
 		   this.finish();
        }
        return true;
        
    }
    
    public void next_button(View view){
    	String question40A_answer="N.A.", question40B_answer="N.A.";
    	if ((lat==0)&&(lng==0)){
			
		}else{
			question40A_answer=String.valueOf(lat)+" "+String.valueOf(lng);
		}
	
    	question_fourtyA.setAnswerText(question40A_answer);
    	question_fourtyA_lat_lng.setAnswerText(question40A_answer);
	
	
	if ((lat2==0)&&(lng2==0)){
		
		}else{
			question40B_answer=String.valueOf(lat2)+" "+String.valueOf(lng2);
			}

	question_fourtyB.setAnswerText(question40B_answer);
	question_fourtyB_lat_lng.setAnswerText(question40B_answer);




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
	for (int i=0; i<4; i++){
		   this.actQuestionList.remove(this.actQuestionList.size()-1);
		   }
		   this.finish();
	   }
	   @SuppressWarnings("static-access")
	public void back_button(View view){
		   for (int i=0; i<4; i++){
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
	  } 
	}
	}
}