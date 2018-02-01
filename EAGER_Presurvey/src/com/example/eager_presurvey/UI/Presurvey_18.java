package com.example.eager_presurvey.UI;

import java.util.Random;


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

public class Presurvey_18 extends BaseActivity{
	final Question question_fourtyseven=new Question(), question_fourtyeight=new Question(), question_fourtynine=new Question(),
			question_fifty=new Question(), question_fiftyone=new Question(), question_fiftytwo=new Question();
	TextView question47, question48, question49, question50, question51, question52;
	RadioGroup radioGroup1, radioGroup2, radioGroup3, radioGroup4, radioGroup5, radioGroup6;
	private static int kk=-1;
	@SuppressWarnings("static-access")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.presurvey_18);
        SharedValues.reset();
        Random rn = new Random();
		int answer = rn.nextInt(5);
		kk=answer;
		SharedValues.imageClass[kk]=false;
		switch (kk){
		case 0: nextActivity = Presurvey_19.class;
		break;
		case 1: nextActivity = Presurvey_20.class;
		break;
		case 2: nextActivity = Presurvey_21.class;
		break;
		case 3: nextActivity = Presurvey_22.class;
		break;
		case 4: nextActivity = Presurvey_23.class;
		}
        radioGroup1=(RadioGroup) findViewById(R.id.rGrp1);
        radioGroup2=(RadioGroup) findViewById(R.id.rGrp2);
        radioGroup3=(RadioGroup) findViewById(R.id.rGrp3);
        radioGroup4=(RadioGroup) findViewById(R.id.rGrp4);
        radioGroup5=(RadioGroup) findViewById(R.id.rGrp5);
        radioGroup6=(RadioGroup) findViewById(R.id.rGrp6);
        question47= (TextView) findViewById(R.id.textView1);
        question48= (TextView) findViewById(R.id.textView2);
        question49= (TextView) findViewById(R.id.textView3);
        question50= (TextView) findViewById(R.id.textView4);
        question51= (TextView) findViewById(R.id.textView5);
        question52= (TextView) findViewById(R.id.textView6);
        
        final String ClassName = this.getClass().getSimpleName();
        question_fourtyseven.setActivityText(ClassName);
        question_fourtyseven.setQuestionText(question47.getText().toString());
		this.actQuestionList.add(question_fourtyseven);
		
		question_fourtyeight.setActivityText(ClassName);
		question_fourtyeight.setQuestionText(question48.getText().toString());
		this.actQuestionList.add(question_fourtyeight);
        
		question_fourtynine.setActivityText(ClassName);
		question_fourtynine.setQuestionText(question49.getText().toString());
		this.actQuestionList.add(question_fourtynine);
		
		question_fifty.setActivityText(ClassName);
		question_fifty.setQuestionText(question50.getText().toString());
		this.actQuestionList.add(question_fifty);
		
		question_fiftyone.setActivityText(ClassName);
		question_fiftyone.setQuestionText(question51.getText().toString());
		this.actQuestionList.add(question_fiftyone);
        
		question_fiftytwo.setActivityText(ClassName);
		question_fiftytwo.setQuestionText(question52.getText().toString());
		this.actQuestionList.add(question_fiftytwo);
		
		radioGroup1.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if(checkedId!=-1){
					String answer="N.A.";
					RadioButton selRadBtn = (RadioButton) group.findViewById(checkedId);
					answer = (String) selRadBtn.getText();
					question_fourtyseven.setAnswerText(answer);
					
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
					question_fourtyeight.setAnswerText(answer);
					
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
					question_fourtynine.setAnswerText(answer);
					
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
					question_fifty.setAnswerText(answer);
					
				}
			}
		});
		
		radioGroup5.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if(checkedId!=-1){
					String answer="N.A.";
					RadioButton selRadBtn = (RadioButton) group.findViewById(checkedId);
					answer = (String) selRadBtn.getText();
					question_fiftyone.setAnswerText(answer);
					
				}
			}
		});
		
		radioGroup6.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if(checkedId!=-1){
					String answer="N.A.";
					RadioButton selRadBtn = (RadioButton) group.findViewById(checkedId);
					answer = (String) selRadBtn.getText();
					question_fiftytwo.setAnswerText(answer);
					
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
        	for (int i=0; i<6; i++){
      		   this.actQuestionList.remove(this.actQuestionList.size()-1);
      		   }
        	SharedValues.imageClass[kk]=true;
 		   this.finish();
        }
        return true;
        
    }

    public void next_button(View view){
    	if ((question_fourtyseven.getAnswerText()==null) || (question_fourtyseven.getAnswerText()==""))
    		question_fourtyseven.setAnswerText("N.A.");
    	
    	if ((question_fourtyeight.getAnswerText()==null) || (question_fourtyeight.getAnswerText()==""))
    		question_fourtyeight.setAnswerText("N.A.");
    	
    	if ((question_fourtynine.getAnswerText()==null) || (question_fourtynine.getAnswerText()==""))
    		question_fourtynine.setAnswerText("N.A.");
    	
    	if ((question_fifty.getAnswerText()==null) || (question_fourtyseven.getAnswerText()==""))
    		question_fifty.setAnswerText("N.A.");
    	
    	if ((question_fiftyone.getAnswerText()==null) || (question_fiftyone.getAnswerText()==""))
    		question_fiftyone.setAnswerText("N.A.");
    	
    	if ((question_fiftytwo.getAnswerText()==null) || (question_fiftytwo.getAnswerText()==""))
    		question_fiftytwo.setAnswerText("N.A.");
    	
    	
		   startNext();
	   }
	   
    @SuppressWarnings("static-access")
	public void back_button(View view){
		   for (int i=0; i<6; i++){
	 		   this.actQuestionList.remove(this.actQuestionList.size()-1);
	 		   }
		   SharedValues.imageClass[kk]=true;
		   this.finish();
	   }
	   
	   @SuppressWarnings("static-access")
	@Override
	   public void onBackPressed(){
	for (int i=0; i<6; i++){
		   this.actQuestionList.remove(this.actQuestionList.size()-1);
		   }
	SharedValues.imageClass[kk]=true;
		   this.finish();
	   }
    
    
}