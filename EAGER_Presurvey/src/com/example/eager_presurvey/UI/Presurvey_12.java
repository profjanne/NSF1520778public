package com.example.eager_presurvey.UI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.eager_presurvey.BaseActivity;
import com.example.eager_presurvey.R;
import com.example.eager_presurvey.util.Question;

public class Presurvey_12 extends BaseActivity{
	EditText job;
	TextView lat_text, lng_text, lat_text2, lng_text2, question38, question39B, question39A;
	double lat=0, lng=0, lat2=0, lng2=0;
	Button buttonmap1;
	final Question question_thirtyeight = new Question(), question_thirtynineA = new Question(), question_thirtynineB = new Question(),
			question_thirtynineA_lat_lng = new Question(), question_thirtynineB_lat_lng = new Question();
	
	@SuppressWarnings("static-access")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.presurvey_12);
        nextActivity = Presurvey_13.class;
		job = (EditText) findViewById(R.id.editText1);
		lat_text=(TextView) findViewById(R.id.textView4);
		lng_text=(TextView) findViewById(R.id.textView5);
		buttonmap1=(Button) findViewById(R.id.Button_map2);

		lat_text2=(TextView) findViewById(R.id.textView8);
		lng_text2=(TextView) findViewById(R.id.textView9);
		question38=(TextView) findViewById(R.id.textView1);
		question39A=(TextView) findViewById(R.id.textView2);
		question39B=(TextView) findViewById(R.id.textView6);
		final String ClassName = this.getClass().getSimpleName();
		question_thirtyeight.setActivityText(ClassName);
		question_thirtyeight.setQuestionText(question38.getText().toString());
		this.actQuestionList.add(question_thirtyeight);
		
		question_thirtynineA.setActivityText(ClassName);
		question_thirtynineA.setQuestionText(question39A.getText().toString());
		this.actQuestionList.add(question_thirtynineA);
		question_thirtynineA_lat_lng.setActivityText(ClassName);
		question_thirtynineA_lat_lng.setQuestionText("Work1");
		this.actQuestionList.add(question_thirtynineA_lat_lng);
		
		question_thirtynineB.setActivityText(ClassName);
		question_thirtynineB.setQuestionText(question39B.getText().toString());
		this.actQuestionList.add(question_thirtynineB);
		question_thirtynineB_lat_lng.setActivityText(ClassName);
		question_thirtynineB_lat_lng.setQuestionText("Work2");
		this.actQuestionList.add(question_thirtynineB_lat_lng);
		
		job.addTextChangedListener(new TextWatcher() {

	          public void afterTextChanged(Editable s) {
	        	  if ((job.getText()!=null)&&(job.getText().toString().equals("1"))){
	        		  lng_text2.setVisibility(View.INVISIBLE);
	        		  lat_text2.setVisibility(View.INVISIBLE);
	        		  buttonmap1.setVisibility(View.INVISIBLE);
	        		  question39B.setVisibility(View.INVISIBLE);
	        		  
	        		  question_thirtynineB.setAnswerText("N.A.");
	        		  question_thirtynineB_lat_lng.setAnswerText("N.A.");
	        	  }else{
	        		  lng_text2.setVisibility(View.VISIBLE);
	        		  lat_text2.setVisibility(View.VISIBLE);
	        		  buttonmap1.setVisibility(View.VISIBLE);
	        		  question39B.setVisibility(View.VISIBLE);
	        		  question_thirtynineB.setAnswerText("");
	        		  question_thirtynineB_lat_lng.setAnswerText("");
	        	  }

	          }

	          public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

	          public void onTextChanged(CharSequence s, int start, int before, int count) {}
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
        	for (int i=0; i<5; i++){
      		   this.actQuestionList.remove(this.actQuestionList.size()-1);
      		   }
 		   this.finish();
        }
        return true;
        
    }
    
    public void next_button(View view){
    	String question38_answer="", question39A_answer="", question39B_answer="";
    	if ((job.getText()!=null)&&(job.getText().toString().length()>0)){
    		question38_answer=job.getText().toString();
    	}
    	
    	question_thirtyeight.setAnswerText(question38_answer);
    	

    	
    		if ((lat==0)&&(lng==0)){
    			
    			}else{
    				question39A_answer=String.valueOf(lat)+" "+String.valueOf(lng);
    			}
    		
    		question_thirtynineA.setAnswerText(question39A_answer);
    		question_thirtynineA_lat_lng.setAnswerText(question39A_answer);
    		
    		if ((question_thirtynineB.getAnswerText()!=null)&&(question_thirtynineB.getAnswerText().equals("N.A."))){
    		}else{
    		if ((lat2==0)&&(lng2==0)){
    			
    			}else{
    				question39B_answer=String.valueOf(lat2)+" "+String.valueOf(lng2);
    				}
    	
    		question_thirtynineB.setAnswerText(question39B_answer);
    		question_thirtynineB_lat_lng.setAnswerText(question39B_answer);
    		}
    	
    	
    	
    	
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
	for (int i=0; i<5; i++){
		   this.actQuestionList.remove(this.actQuestionList.size()-1);
		   }
		   this.finish();
	   }
	   @SuppressWarnings("static-access")
	public void back_button(View view){
		   for (int i=0; i<5; i++){
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