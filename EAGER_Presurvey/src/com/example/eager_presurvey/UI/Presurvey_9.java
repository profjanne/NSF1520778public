package com.example.eager_presurvey.UI;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.example.eager_presurvey.BaseActivity;
import com.example.eager_presurvey.R;
import com.example.eager_presurvey.util.Question;

public class Presurvey_9 extends BaseActivity{
	final Question question_thirty=new Question(), question_thirtyone=new Question(), question_thirtytwo=new Question(), question_thirtythree=new Question();
	TextView question30, question31, question32, question33;
	RadioGroup radioGroup1, radioGroup2, radioGroup3, radioGroup4;
	@SuppressWarnings("static-access")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.presurvey_9);
        nextActivity = Presurvey_10.class;
        
        question30= (TextView) findViewById(R.id.textView1);
        question31= (TextView) findViewById(R.id.textView2);
        question32= (TextView) findViewById(R.id.textView3);
        question33= (TextView) findViewById(R.id.textView4);
        
        radioGroup1=(RadioGroup) findViewById(R.id.rGrp1);
        radioGroup2=(RadioGroup) findViewById(R.id.rGrp2);
        radioGroup3=(RadioGroup) findViewById(R.id.rGrp3);
        radioGroup4=(RadioGroup) findViewById(R.id.rGrp4);
        
        final String ClassName = this.getClass().getSimpleName();
        
        question_thirty.setActivityText(ClassName);
        question_thirty.setQuestionText(question30.getText().toString());
		this.actQuestionList.add(question_thirty);
		
		question_thirtyone.setActivityText(ClassName);
		question_thirtyone.setQuestionText(question31.getText().toString());
		this.actQuestionList.add(question_thirtyone);
		
		question_thirtytwo.setActivityText(ClassName);
		question_thirtytwo.setQuestionText(question32.getText().toString());
		this.actQuestionList.add(question_thirtytwo);
		
		question_thirtythree.setActivityText(ClassName);
		question_thirtythree.setQuestionText(question33.getText().toString());
		this.actQuestionList.add(question_thirtythree);
		
		radioGroup1.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if(checkedId!=-1){
					String answer="N.A.";
					RadioButton selRadBtn = (RadioButton) group.findViewById(checkedId);
					answer = (String) selRadBtn.getText();
					question_thirty.setAnswerText(answer);
					
				}
			}
		});
		
		radioGroup2.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if(checkedId!=-1){
					String answer="N.A.";
					RadioButton selRadBtn = (RadioButton) group.findViewById(checkedId);
					answer = (String) selRadBtn.getText();
					question_thirtyone.setAnswerText(answer);
					
				}
			}
		});
		
		radioGroup3.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if(checkedId!=-1){
					String answer="N.A.";
					RadioButton selRadBtn = (RadioButton) group.findViewById(checkedId);
					answer = (String) selRadBtn.getText();
					question_thirtytwo.setAnswerText(answer);
					
				}
			}
		});
		
		radioGroup4.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if(checkedId!=-1){
					String answer="N.A.";
					RadioButton selRadBtn = (RadioButton) group.findViewById(checkedId);
					answer = (String) selRadBtn.getText();
					question_thirtythree.setAnswerText(answer);
					
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
        	for (int i=0; i<4; i++){
      		   this.actQuestionList.remove(this.actQuestionList.size()-1);
      		   }
 		   this.finish();
        }
        return true;
        
    }
    

    public void next_button(View view){
    	if ((question_thirty.getAnswerText()==null) || (question_thirty.getAnswerText()==""))
    		question_thirty.setAnswerText("N.A.");
    	
    	if ((question_thirtyone.getAnswerText()==null) || (question_thirtyone.getAnswerText()==""))
    		question_thirtyone.setAnswerText("N.A.");
    	
    	if ((question_thirtytwo.getAnswerText()==null) || (question_thirtytwo.getAnswerText()==""))
    		question_thirtytwo.setAnswerText("N.A.");
    	
    	if ((question_thirtythree.getAnswerText()==null) || (question_thirtythree.getAnswerText()==""))
    		question_thirtythree.setAnswerText("N.A.");
    	
		   startNext();
	   }
	   
    @SuppressWarnings("static-access")
	public void back_button(View view){
		   for (int i=0; i<4; i++){
	 		   this.actQuestionList.remove(this.actQuestionList.size()-1);
	 		   }
		   this.finish();
	   }
	   @SuppressWarnings("static-access")
	@Override
	   public void onBackPressed(){
	for (int i=0; i<4; i++){
		   this.actQuestionList.remove(this.actQuestionList.size()-1);
		   }
		   this.finish();
	   }
    
    
}