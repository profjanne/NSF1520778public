package com.example.eager_study_UI;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.example.eager_study.BaseActivity;
import com.example.eager_study.R;
import com.example.eager_study_util.Question;

public class EMA_formal_22 extends BaseActivity{

	final Question question_twentytwo = new Question();
	RadioGroup radioGroup1, radioGroup2;
	EditText editText;
	String other_answer="";
	RadioButton radioButton;
	@SuppressWarnings("static-access")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ema_formal_22);
		nextActivity = EMA_formal_23.class;
		final String ClassName = this.getClass().getSimpleName();
		radioGroup1= (RadioGroup) findViewById(R.id.rGrp1);
		radioGroup2= (RadioGroup) findViewById(R.id.rGrp2);
		radioButton = (RadioButton) findViewById(R.id.rb24);
		question_twentytwo.setActivityText(ClassName);
		question_twentytwo.setQuestionText(getText(R.string.ema_formal_text22).toString());
		this.actQuestionList.add(question_twentytwo);
		radioGroup2.setVisibility(View.GONE);
		editText = (EditText) findViewById(R.id.other1);
		editText.setVisibility(View.GONE);
		radioGroup1.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if(checkedId!=-1){
					RadioButton selRadBtn = (RadioButton) group.findViewById(checkedId);
					String answerText = (String) selRadBtn.getText();
					question_twentytwo.setAnswerText(answerText);
					
					if (answerText.equals(getText(R.string.ema3_choice14).toString())){
						radioGroup2.setVisibility(View.VISIBLE);
						editText.setVisibility(View.VISIBLE);
				
				 } else {
					 radioGroup2.setVisibility(View.GONE);
					 editText.setVisibility(View.GONE);
				 }
				}
			}
		});
		radioGroup2.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if(checkedId!=-1){
					radioButton.setChecked(false);
					RadioButton selRadBtn = (RadioButton) group.findViewById(checkedId);
					other_answer = (String) selRadBtn.getText();
					
				}
			}
		});
		
		radioButton.setOnClickListener(new OnClickListener() {
			            @Override
			            public void onClick(View view) {
			            	RadioButton selRadBtn = (RadioButton) view;
			            	radioGroup2.clearCheck();
			            	selRadBtn.setChecked(true);
			            	other_answer = (String) selRadBtn.getText();
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
		   boolean flag=true;
		   String answerTp1=null;
			if (question_twentytwo.getAnswerText().charAt(0)=='O'){
				answerTp1=getText(R.string.ema3_choice14).toString();
				if (other_answer.length()<1) 
					flag=false;
				else if (other_answer.equals(getText(R.string.ema3_choice24).toString())){
					answerTp1=answerTp1+"->"+other_answer;
					if (editText.getText()==null || editText.getText().toString().length()<1){
						flag=false;
					}else{
						
						answerTp1=answerTp1+"->"+editText.getText().toString();
						
					}
				}else{
					answerTp1=answerTp1+"->"+other_answer;
				}
				
				if (flag){
					question_twentytwo.setAnswerText(answerTp1);
				}else{
					Toast.makeText(getBaseContext(),"Please specify for \"Other:\"", Toast.LENGTH_LONG).show();
				}
				
			}
			if (flag){
				if ((question_twentytwo.getAnswerText()!=null)&&(question_twentytwo.getAnswerText().length()>1)){
					
					startNext();
				}else{
					Toast.makeText(getApplicationContext(), "Please make sure the question is answered!", 
							   Toast.LENGTH_SHORT).show();
				}
			}
		   
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
	   @SuppressWarnings("static-access")
	@Override
       public boolean onOptionsItemSelected(MenuItem item) {
               switch (item.getItemId()) {
               case android.R.id.home:
            	   this.actQuestionList.remove(this.actQuestionList.size()-1);
        		   this.finish();
               }
               return true;
       }
}