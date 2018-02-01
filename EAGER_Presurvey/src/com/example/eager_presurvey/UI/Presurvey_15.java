package com.example.eager_presurvey.UI;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.example.eager_presurvey.BaseActivity;
import com.example.eager_presurvey.R;
import com.example.eager_presurvey.util.Question;

public class Presurvey_15 extends BaseActivity{
	final Question question_fourtyone = new Question(), question_fourtytwo = new Question();
	TextView question41, question42;
	RadioGroup radioGroup1;
	RadioButton radioButton1;
	String other_answer1="";
	EditText other1, other2;
	CheckBox[] checkboxArray=new CheckBox[7];
	@SuppressWarnings("static-access")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.presurvey_15);
        nextActivity = Presurvey_16.class;

        question41=(TextView) findViewById(R.id.textView1);
		question42=(TextView) findViewById(R.id.textView2);
		other1 = (EditText) findViewById(R.id.other1);
		other2 = (EditText) findViewById(R.id.other2);
		radioGroup1= (RadioGroup) findViewById(R.id.rGrp1);
		radioButton1 = (RadioButton) findViewById(R.id.rb17);
		final String ClassName = this.getClass().getSimpleName();
		
		question_fourtyone.setActivityText(ClassName);
		question_fourtyone.setQuestionText(question41.getText().toString());
		this.actQuestionList.add(question_fourtyone);
		
		question_fourtytwo.setActivityText(ClassName);
		question_fourtytwo.setQuestionText(question42.getText().toString());
		this.actQuestionList.add(question_fourtytwo);
		
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
		
		for (int i=0; i<checkboxArray.length;i++){
			switch (i){
			case 0: checkboxArray[i]=(CheckBox) findViewById(R.id.chk1);
			break;
			case 1: checkboxArray[i]=(CheckBox) findViewById(R.id.chk2);
			break;
			case 2: checkboxArray[i]=(CheckBox) findViewById(R.id.chk3);
			break;
			case 3: checkboxArray[i]=(CheckBox) findViewById(R.id.chk4);
			break;
			case 4: checkboxArray[i]=(CheckBox) findViewById(R.id.chk5);
			break;
			case 5: checkboxArray[i]=(CheckBox) findViewById(R.id.chk6);
			break;
			case 6: checkboxArray[i]=(CheckBox) findViewById(R.id.chk7);
			break;
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
        	for (int i=0; i<2; i++){
      		   this.actQuestionList.remove(this.actQuestionList.size()-1);
      		   }
 		   this.finish();
        }
        return true;
        
    }
    
    
    public void next_button(View view){
    	String question41_answer="N.A.", question42_answer="";
    	if (other_answer1.length()>2){
    		if (other_answer1.equals("Other [SPECIFY]")){
    			if ((other1.getText()!=null)&&(other1.getText().toString().length()>1))
    				question41_answer=other_answer1+": "+other1.getText().toString();
    		}else{
    			question41_answer=other_answer1;
    		}
    	}
    	question_fourtyone.setAnswerText(question41_answer);
    	
    	String answerTp2="";
    	boolean flag=true;
    	if (checkboxArray[6].isChecked()){
			
			if (other2.getText()==null || other2.getText().toString().length()<2){
				flag=false;
			}else{
				answerTp2=checkboxArray[6].getText().toString()+" "+other2.getText().toString();
				//locationquestion2.setAnswerText(answerTp);
			}
		}
    	if (!flag){
    		Toast.makeText(getBaseContext(),"Please specify for \"Other:\"", Toast.LENGTH_LONG).show();
    		return;
    	}
    	
		   int counter=0;
		   for (int i=0; i<checkboxArray.length;i++){
			   
			   if (checkboxArray[i].isChecked()){
				   if (counter==0) 
					   question42_answer=question42_answer+checkboxArray[i].getText().toString();
				   else if (i==6)
					   question42_answer=question42_answer+", "+answerTp2;
				   else
					   question42_answer=question42_answer+", "+checkboxArray[i].getText().toString();
				   counter++;
				   
			   }
			   
		   }
		   
		   if (counter==0) 
			   question42_answer="N.A.";
		   question_fourtytwo.setAnswerText(question42_answer);
		   
		   startNext();
	   }
	   
    @SuppressWarnings("static-access")
	@Override
	   public void onBackPressed(){
	for (int i=0; i<2; i++){
		   this.actQuestionList.remove(this.actQuestionList.size()-1);
		   }
		   this.finish();
	   }
	   @SuppressWarnings("static-access")
	public void back_button(View view){
		   for (int i=0; i<2; i++){
		   this.actQuestionList.remove(this.actQuestionList.size()-1);
		   }
		   this.finish();
	   }
    
    
}