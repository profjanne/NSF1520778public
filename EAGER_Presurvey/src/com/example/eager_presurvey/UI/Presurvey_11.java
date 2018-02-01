package com.example.eager_presurvey.UI;


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

public class Presurvey_11 extends BaseActivity{
	EditText time1, time2, time3, other2;
	final Question question_thirtyfour = new Question(), question_thirtyfive = new Question(), question_thirtysix = new Question(), 
			question_thirtyseven = new Question(), question_thirtysevenB = new Question();
	TextView question34, question35, question36, question37, question37B;
	RadioGroup radioGroup1, radioGroup2;
	RadioButton radioButton2;
	String other_answer2="";
	@SuppressWarnings("static-access")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.presurvey_11);
        nextActivity = Presurvey_12.class;
        time1= (EditText) findViewById(R.id.editText1);
        time1.setHint("Format (hh:mm) with hour from 00 to 23 and minute from 00 to 59");
        time2= (EditText) findViewById(R.id.editText2);
        time2.setHint("Format (hh:mm) with hour from 00 to 23 and minute from 00 to 59");
        time3= (EditText) findViewById(R.id.EditText01);
        
        radioGroup1=(RadioGroup) findViewById(R.id.rGrp1);
        radioGroup2=(RadioGroup) findViewById(R.id.rGrp2);
        radioButton2= (RadioButton) findViewById(R.id.rb28);
        question34= (TextView) findViewById(R.id.textView1);
        question35= (TextView) findViewById(R.id.textView3);
        question36= (TextView) findViewById(R.id.textView4);
        question37= (TextView) findViewById(R.id.textView5);
        question37B= (TextView) findViewById(R.id.TextView0001);
        other2 = (EditText) findViewById(R.id.other1);
        
        final String ClassName = this.getClass().getSimpleName();
        question_thirtyfour.setActivityText(ClassName);
        question_thirtyfour.setQuestionText(question34.getText().toString());
		this.actQuestionList.add(question_thirtyfour);
		
		question_thirtyfive.setActivityText(ClassName);
		question_thirtyfive.setQuestionText(question35.getText().toString());
		this.actQuestionList.add(question_thirtyfive);
		
		question_thirtysix.setActivityText(ClassName);
		question_thirtysix.setQuestionText(question36.getText().toString());
		this.actQuestionList.add(question_thirtysix);
		
		question_thirtyseven.setActivityText(ClassName);
		question_thirtyseven.setQuestionText(question37.getText().toString());
		this.actQuestionList.add(question_thirtyseven);
		
		question_thirtysevenB.setActivityText(ClassName);
		question_thirtysevenB.setQuestionText(question37B.getText().toString());
		this.actQuestionList.add(question_thirtysevenB);
		
		radioGroup1.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if(checkedId!=-1){
					String answer="N.A.";
					RadioButton selRadBtn = (RadioButton) group.findViewById(checkedId);
					answer = (String) selRadBtn.getText();
					question_thirtyfour.setAnswerText(answer);
				}
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
    	
    	if ((question_thirtyfour.getAnswerText()==null) || (question_thirtyfour.getAnswerText()==""))
    		question_thirtyfour.setAnswerText("N.A.");
    	
    	String time1_answer="", time2_answer="", time3_answer="";
    	if ((time1.getText()!=null)&&(time1.getText().toString().length()>1)){
    		time1_answer=time1.getText().toString();
    	}
    	
    	question_thirtyfive.setAnswerText(time1_answer);
    	
    	if ((time2.getText()!=null)&&(time2.getText().toString().length()>1)){
    		time2_answer=time2.getText().toString();
    	}
    	
    	question_thirtysix.setAnswerText(time2_answer);
    	
    	if ((time3.getText()!=null)&&(time3.getText().toString().length()>0)){
    		time3_answer=time3.getText().toString();
    	}
    	
    	question_thirtysevenB.setAnswerText(time3_answer);
    	
    	String question4_answer="";
    	if (other_answer2.length()>2){
    		if (other_answer2.equals("Other [SPECIFY]")){
    			if ((other2.getText()!=null)&&(other2.getText().toString().length()>1))
    				question4_answer=other_answer2+": "+other2.getText().toString();
    		}else{
    			question4_answer=other_answer2;
    		}
    	}
    	question_thirtyseven.setAnswerText(question4_answer);
    	
    	if (!question4_answer.equals("working now"))
    		nextActivity = Presurvey_13.class;
    	else
    		nextActivity = Presurvey_12.class;
    	
    
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