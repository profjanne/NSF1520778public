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

public class Presurvey_4 extends BaseActivity{
	EditText date;
	final Question question_seven=new Question(), question_eight=new Question(), question_nine=new Question(), 
			question_ten=new Question(), question_eleven=new Question();
	TextView question7, question8, question9, question10, question11;
	RadioGroup radioGroup1, radioGroup2, radioGroup3, radioGroup4;
	@SuppressWarnings("static-access")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.presurvey_4);
        nextActivity = Presurvey_5.class;
        date= (EditText) findViewById(R.id.editText1);
        date.setHint("MM/YYYY");
        
        radioGroup1=(RadioGroup) findViewById(R.id.rGrp1);
        radioGroup2=(RadioGroup) findViewById(R.id.rGrp2);
        radioGroup3=(RadioGroup) findViewById(R.id.rGrp3);
        radioGroup4=(RadioGroup) findViewById(R.id.rGrp4);
        
        question7= (TextView) findViewById(R.id.textView2);
        question8= (TextView) findViewById(R.id.textView4);
        question9= (TextView) findViewById(R.id.textView5);
        question10= (TextView) findViewById(R.id.textView6);
        question11= (TextView) findViewById(R.id.textView7);
        
        final String ClassName = this.getClass().getSimpleName();
        question_seven.setActivityText(ClassName);
        question_seven.setQuestionText(question7.getText().toString());
		this.actQuestionList.add(question_seven);
		
        question_eight.setActivityText(ClassName);
        question_eight.setQuestionText(question8.getText().toString());
		this.actQuestionList.add(question_eight);
        
		question_nine.setActivityText(ClassName);
        question_nine.setQuestionText(question9.getText().toString());
		this.actQuestionList.add(question_nine);
		
		question_ten.setActivityText(ClassName);
        question_ten.setQuestionText(question10.getText().toString());
		this.actQuestionList.add(question_ten);
		
		question_eleven.setActivityText(ClassName);
        question_eleven.setQuestionText(question11.getText().toString());
		this.actQuestionList.add(question_eleven);
		
		radioGroup1.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if(checkedId!=-1){
					String answer="N.A.";
					RadioButton selRadBtn = (RadioButton) group.findViewById(checkedId);
					answer = (String) selRadBtn.getText();
					question_eight.setAnswerText(answer);
					
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
					question_nine.setAnswerText(answer);
					
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
					question_ten.setAnswerText(answer);
					
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
					question_eleven.setAnswerText(answer);
					
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
        	for (int i=0; i<5; i++){
      		   this.actQuestionList.remove(this.actQuestionList.size()-1);
      		   }
 		   this.finish();
        }
        return true;
        
    }
    
  
    public void next_button(View view){
    	String question7_answer="N.A.";
    	
    	if ((date.getText()!=null)&&(date.getText().toString().length()>1)){
    		question7_answer=date.getText().toString();
    	}
    	
    	question_seven.setAnswerText(question7_answer);
    	
    	if ((question_eight.getAnswerText()==null) || (question_eight.getAnswerText()==""))
    	     question_eight.setAnswerText("N.A.");
    	
    	if ((question_nine.getAnswerText()==null) || (question_nine.getAnswerText()==""))
    		question_nine.setAnswerText("N.A.");
    	
    	if ((question_ten.getAnswerText()==null) || (question_ten.getAnswerText()==""))
    		question_ten.setAnswerText("N.A.");
    	
    	if ((question_eleven.getAnswerText()==null) || (question_eleven.getAnswerText()==""))
    		question_eleven.setAnswerText("N.A.");
    	
		   startNext();
	   }
	   
	   @SuppressWarnings("static-access")
	public void back_button(View view){
		   for (int i=0; i<5; i++){
	 		   this.actQuestionList.remove(this.actQuestionList.size()-1);
	 		   }
		   this.finish();
	   }
	   
	   @SuppressWarnings("static-access")
	@Override
	   public void onBackPressed(){
    	for (int i=0; i<5; i++){
 		   this.actQuestionList.remove(this.actQuestionList.size()-1);
 		   }
		   this.finish();
	   }
    
    
}
