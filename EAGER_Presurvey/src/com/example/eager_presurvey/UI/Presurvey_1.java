package com.example.eager_presurvey.UI;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.example.eager_presurvey.BaseActivity;
import com.example.eager_presurvey.R;
import com.example.eager_presurvey.util.Question;

public class Presurvey_1 extends BaseActivity{
	EditText date, other1, other2; //street_number, city, state, zip,
	TextView question1, question2, question3, question4;
	final Question question_one = new Question(), question_two = new Question(), question_three = new Question(), question_four = new Question(),
			question_five=new Question();
	RadioGroup radioGroup1, radioGroup2;
	RadioButton radioButton1, radioButton2;
	String other_answer1="", other_answer2="";
	double lat=0, lng=0;
	TextView lat_text, lng_text;
	
	private static SharedPreferences settings;
	private static SharedPreferences.Editor edit;
	public static final String PREFS_NAME = "home_location";
	
	@SuppressWarnings("static-access")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.presurvey_1);
        nextActivity = Presurvey_2.class;
        settings=getSharedPreferences(PREFS_NAME, MODE_MULTI_PROCESS);
	    edit = settings.edit();
	    
        edit.putFloat("lat", (float)40.500525);
	    edit.putFloat("lng", (float) -74.447592);
    	edit.commit();
    	
		date= (EditText) findViewById(R.id.editText5);
		date.setHint("MM/YYYY");
		other1 = (EditText) findViewById(R.id.other1);
		other2 = (EditText) findViewById(R.id.other2);
		radioGroup1= (RadioGroup) findViewById(R.id.rGrp1);
		radioGroup2= (RadioGroup) findViewById(R.id.rGrp2);
		radioButton1 = (RadioButton) findViewById(R.id.rb14);
		radioButton2 = (RadioButton) findViewById(R.id.rb24);
		
		lat_text=(TextView) findViewById(R.id.textView004);
		lng_text=(TextView) findViewById(R.id.textView005);
		
		final String ClassName = this.getClass().getSimpleName();
		
		question1=(TextView) findViewById(R.id.textView1);
		question2=(TextView) findViewById(R.id.textView2);
		question3=(TextView) findViewById(R.id.textView3);
		question4=(TextView) findViewById(R.id.textView4);
		
		question_one.setActivityText(ClassName);
		question_one.setQuestionText(question1.getText().toString());
		this.actQuestionList.add(question_one);
		
		question_two.setActivityText(ClassName);
		question_two.setQuestionText(question2.getText().toString());
		this.actQuestionList.add(question_two);
		
		question_three.setActivityText(ClassName);
		question_three.setQuestionText(question3.getText().toString());
		this.actQuestionList.add(question_three);
		
		question_four.setActivityText(ClassName);
		question_four.setQuestionText(question4.getText().toString());
		this.actQuestionList.add(question_four);
		
		question_five.setActivityText(ClassName);
		question_five.setQuestionText("Home");
		this.actQuestionList.add(question_five);
		
		radioGroup1.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if(checkedId!=-1){
					radioButton1.setChecked(false);
					RadioButton selRadBtn = (RadioButton) group.findViewById(checkedId);
					other_answer1 = (String) selRadBtn.getText();
					
				}
			}
		});
		
		radioButton1.setOnClickListener(new OnClickListener() {
			            @Override
			            public void onClick(View view) {
			            	RadioButton selRadBtn = (RadioButton) view;
			            	radioGroup1.clearCheck();
			            	selRadBtn.setChecked(true);
			            	other_answer1 = (String) selRadBtn.getText();
			            }	
			        });
		radioGroup2.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if(checkedId!=-1){
					radioButton2.setChecked(false);
					RadioButton selRadBtn = (RadioButton) group.findViewById(checkedId);
					other_answer2 = (String) selRadBtn.getText();
					
				}
			}
		});
		
		radioButton2.setOnClickListener(new OnClickListener() {
			            @Override
			            public void onClick(View view) {
			            	RadioButton selRadBtn = (RadioButton) view;
			            	radioGroup2.clearCheck();
			            	selRadBtn.setChecked(true);
			            	other_answer2 = (String) selRadBtn.getText();
			            }
			        });
    }

    public void map_button(View view){
    	Intent intent = new Intent(this, SelectionMap.class);
    	startActivityForResult(intent, 1000);
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
	    	  
	    	  edit.putFloat("lat", (float) lat);
	  	      edit.putFloat("lng", (float) lng);
	      	  edit.commit();
	      } 
	      break; 
	     
	    }

	  } 
	}
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
    	String question1_answer="", question2_answer="N.A.";

    	question_five.setAnswerText("");
    	if ((lat==0)&&(lng==0)){
    		question1_answer="N.A.";
		}else{
			question1_answer=String.valueOf(lat)+" "+String.valueOf(lng);
			question_five.setAnswerText(question1_answer);
		}
	
    	
    	
    	question_one.setAnswerText(question1_answer);
    	
    	if ((date.getText()!=null)&&(date.getText().toString().length()>1)){
    		question2_answer=date.getText().toString();
    	}
    	
    	question_two.setAnswerText(question2_answer);
    	
    	String question3_answer="N.A.";
    	if (other_answer1.length()>2){
    		if (other_answer1.equals("Other [SPECIFY]")){
    			if ((other1.getText()!=null)&&(other1.getText().toString().length()>1))
    				question3_answer=other_answer1+": "+other1.getText().toString();
    		}else{
    			question3_answer=other_answer1;
    		}
    	}
    	question_three.setAnswerText(question3_answer);
    	
    	String question4_answer="N.A.";
    	if (other_answer2.length()>2){
    		if (other_answer2.equals("Other [SPECIFY]")){
    			if ((other2.getText()!=null)&&(other2.getText().toString().length()>1))
    				question4_answer=other_answer2+": "+other2.getText().toString();
    		}else{
    			question4_answer=other_answer2;
    		}
    	}
    	question_four.setAnswerText(question4_answer);

    	startNext();

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
    
    
}
