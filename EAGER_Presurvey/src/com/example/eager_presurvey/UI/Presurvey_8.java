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


public class Presurvey_8 extends BaseActivity{
	final Question question_twentyeight=new Question(), question_twentynine=new Question();
	TextView question28, question29;
	RadioGroup radioGroup1, radioGroup2;
	@SuppressWarnings("static-access")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.presurvey_8);
        nextActivity = Presurvey_9.class;
        question28= (TextView) findViewById(R.id.textView1);
        question29= (TextView) findViewById(R.id.textView2);
        
        radioGroup1=(RadioGroup) findViewById(R.id.rGrp1);
        radioGroup2=(RadioGroup) findViewById(R.id.rGrp2);
        
        final String ClassName = this.getClass().getSimpleName();
        
        question_twentyeight.setActivityText(ClassName);
        question_twentyeight.setQuestionText(question28.getText().toString());
		this.actQuestionList.add(question_twentyeight);
		
		question_twentynine.setActivityText(ClassName);
		question_twentynine.setQuestionText(question29.getText().toString());
		this.actQuestionList.add(question_twentynine);
		
		radioGroup1.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if(checkedId!=-1){
					String answer="N.A.";
					RadioButton selRadBtn = (RadioButton) group.findViewById(checkedId);
					answer = (String) selRadBtn.getText();
					question_twentyeight.setAnswerText(answer);
					
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
					question_twentynine.setAnswerText(answer);
					
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
        	for (int i=0; i<2; i++){
      		   this.actQuestionList.remove(this.actQuestionList.size()-1);
      		   }
 		   this.finish();
        }
        return true;
        
    }
    
    public void next_button(View view){
    	if ((question_twentyeight.getAnswerText()==null) || (question_twentyeight.getAnswerText()==""))
    		question_twentyeight.setAnswerText("N.A.");
    	
    	if ((question_twentynine.getAnswerText()==null) || (question_twentynine.getAnswerText()==""))
    		question_twentynine.setAnswerText("N.A.");
    	
		   startNext();
	   }
	   
    @SuppressWarnings("static-access")
	public void back_button(View view){
		   for (int i=0; i<2; i++){
	 		   this.actQuestionList.remove(this.actQuestionList.size()-1);
	 		   }
		   this.finish();
	   }
	   @SuppressWarnings("static-access")
	@Override
	   public void onBackPressed(){
	for (int i=0; i<2; i++){
		   this.actQuestionList.remove(this.actQuestionList.size()-1);
		   }
		   this.finish();
	   }
    
    
}