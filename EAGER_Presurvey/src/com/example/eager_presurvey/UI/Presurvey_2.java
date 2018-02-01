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

public class Presurvey_2 extends BaseActivity{
	final Question question_five = new Question();
	TextView question5;
	RadioGroup radioGroup1;
	RadioButton radioButton1;
	EditText other1;
	String other_answer1="";
	@SuppressWarnings("static-access")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.presurvey_2);
        question5=(TextView) findViewById(R.id.textView1);
        radioButton1=(RadioButton) findViewById(R.id.rb14);
        other1 = (EditText) findViewById(R.id.other1);
        radioGroup1 = (RadioGroup) findViewById(R.id.rGrp1);
        
        final String ClassName = this.getClass().getSimpleName();
        nextActivity = Presurvey_3.class;
        question_five.setActivityText(ClassName);
        question_five.setQuestionText(question5.getText().toString());
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
        	
      		   this.actQuestionList.remove(this.actQuestionList.size()-1);
      		   
 		   this.finish();
        }
        return true;
    }
    
 
    public void next_button(View view){
    	String question5_answer="N.A.";
    	if (other_answer1.length()>2){
    		if (other_answer1.equals("Other [SPECIFY]")){
    			if ((other1.getText()!=null)&&(other1.getText().toString().length()>1))
    				question5_answer=other_answer1+": "+other1.getText().toString();
    		}else{
    			question5_answer=other_answer1;
    		}
    	}
    	question_five.setAnswerText(question5_answer);
		   startNext();
	   }
	   
	   @SuppressWarnings("static-access")
	public void back_button(View view){
		   this.actQuestionList.remove(this.actQuestionList.size()-1);
		   this.finish();
	   }
	   @SuppressWarnings("static-access")
	@Override
	   public void onBackPressed(){
    	
 		   this.actQuestionList.remove(this.actQuestionList.size()-1);
 		   
		   this.finish();
	   }
    
    
}