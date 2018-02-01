package com.example.eager_study_UI;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.Time;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.example.eager_study.BaseActivity;
import com.example.eager_study.R;
import com.example.eager_study_util.Question;

public class EMA_formal_1 extends BaseActivity{

	final Question question_one = new Question(), question_location = new Question();
	public SharedPreferences location_service;
	RadioGroup radioGroup1;

	
	@SuppressWarnings("static-access")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ema_formal_1);
		nextActivity = EMA_formal_2.class;
		final String ClassName = this.getClass().getSimpleName();
		radioGroup1= (RadioGroup) findViewById(R.id.rGrp1);
		
		Question startQuestion = new Question();
		startQuestion.setActivityText(this.getClass().getSimpleName());
		startQuestion.setQuestionText("Survey Start Time");
		Time now = new Time();
		now.setToNow();
		startQuestion.setAnswerText(now.toString());
		this.actQuestionList.add(startQuestion);
		
		
		
		question_location.setActivityText(ClassName);
		question_location.setQuestionText("Survey Prompting Location. Answer is formated as: (latitude, longitude).");
		this.actQuestionList.add(question_location);
		location_service = getApplicationContext().getSharedPreferences("location_service",MODE_MULTI_PROCESS);
		double lat1 = location_service.getFloat("lat1", 0);
		double lon1 = location_service.getFloat("lon1", 0);
		String answer_location = "("+String.valueOf(lat1)+", "+String.valueOf(lon1)+")";
		question_location.setAnswerText(answer_location);
		
		question_one.setActivityText(ClassName);
		question_one.setQuestionText(getText(R.string.ema_formal_text1).toString());
		this.actQuestionList.add(question_one);
		
		radioGroup1.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if(checkedId!=-1){
					RadioButton selRadBtn = (RadioButton) group.findViewById(checkedId);
					String answerText = (String) selRadBtn.getText();
					question_one.setAnswerText(answerText);
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
	   
	   public void next_button(View view){
		   if ((question_one.getAnswerText()!=null)&&
				   ((question_one.getAnswerText().equals("Indoors NOT at work"))||(question_one.getAnswerText().equals("Outdoors"))||(question_one.getAnswerText().equals("In a vehicle NOT driving"))))
			   nextActivity = EMA_formal_10.class;
		   else nextActivity = EMA_formal_2.class;
		   startNext();
	   }
	   
	   @SuppressWarnings("static-access")
	public void back_button(View view){
		   this.actQuestionList.remove(this.actQuestionList.size()-1);
		   this.actQuestionList.remove(this.actQuestionList.size()-1);
		   this.actQuestionList.remove(this.actQuestionList.size()-1);
		   this.finish();
	   }
	@SuppressWarnings("static-access")
	@Override
	   public void onBackPressed(){
		   this.actQuestionList.remove(this.actQuestionList.size()-1);
		   this.actQuestionList.remove(this.actQuestionList.size()-1);
		   this.actQuestionList.remove(this.actQuestionList.size()-1);
		   this.finish();
	   }
	@SuppressWarnings("static-access")
	@Override
       public boolean onOptionsItemSelected(MenuItem item) {
               switch (item.getItemId()) {
               case android.R.id.home:
            	   this.actQuestionList.remove(this.actQuestionList.size()-1);
            	   this.actQuestionList.remove(this.actQuestionList.size()-1);
            	   this.actQuestionList.remove(this.actQuestionList.size()-1);
        		   this.finish();
               }
               return true;
       }
	   
}
