package com.example.eager_presurvey.UI;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.example.eager_presurvey.BaseActivity;
import com.example.eager_presurvey.R;
import com.example.eager_presurvey.util.Question;

public class Presurvey_30 extends BaseActivity{
	final Question question_seventyseven=new Question(), question_seventyeight=new Question(), question_seventynine=new Question(), question_seventyeightb=new Question();
	TextView question77, question78, question79, question78b;
	EditText text1, text2, text3;
	RadioGroup radioGroup1;
	@SuppressWarnings("static-access")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.presurvey_30);
        nextActivity = Presurvey_31.class;

        question77= (TextView) findViewById(R.id.textView2);
        question78= (TextView) findViewById(R.id.textView3);
        question79= (TextView) findViewById(R.id.textView4);
        question78b= (TextView) findViewById(R.id.textView3b);
        radioGroup1=(RadioGroup) findViewById(R.id.rGrp4);
        
        text1= (EditText) findViewById(R.id.editText2);
        text2= (EditText) findViewById(R.id.editText3);
        text3= (EditText) findViewById(R.id.editText3b);
        final String ClassName = this.getClass().getSimpleName();
        question_seventyseven.setActivityText(ClassName);
        question_seventyseven.setQuestionText(question77.getText().toString());
		this.actQuestionList.add(question_seventyseven);
		
		question_seventyeight.setActivityText(ClassName);
		question_seventyeight.setQuestionText(question78.getText().toString());
		this.actQuestionList.add(question_seventyeight);
		
		question_seventyeightb.setActivityText(ClassName);
		question_seventyeightb.setQuestionText(question78b.getText().toString());
		this.actQuestionList.add(question_seventyeightb);
        
		question_seventynine.setActivityText(ClassName);
		question_seventynine.setQuestionText(question79.getText().toString());
		this.actQuestionList.add(question_seventynine);
		
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
    	String question77_answer="N.A.", question78_answer="N.A.", question78b_answer="N.A.";
    	
    	if ((text1.getText()!=null)&&(text1.getText().toString().length()>0)){
    		question77_answer=text1.getText().toString();
    	}
    	
    	question_seventyseven.setAnswerText(question77_answer);
		
    	if ((text2.getText()!=null)&&(text2.getText().toString().length()>0)){
    		question78_answer=text2.getText().toString();
    	}
    	
    	question_seventyeight.setAnswerText(question78_answer);
    	
    	if ((text3.getText()!=null)&&(text3.getText().toString().length()>0)){
    		question78b_answer=text3.getText().toString();
    	}
    	
    	question_seventyeightb.setAnswerText(question78b_answer);
    	

    	if ((question_seventynine.getAnswerText()==null) || (question_seventynine.getAnswerText()==""))
    		question_seventynine.setAnswerText("N.A.");
    	
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