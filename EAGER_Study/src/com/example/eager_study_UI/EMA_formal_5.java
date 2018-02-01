package com.example.eager_study_UI;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.example.eager_study.BaseActivity;
import com.example.eager_study.R;
import com.example.eager_study_util.Question;

public class EMA_formal_5 extends BaseActivity{

	final Question question_five = new Question();
	RadioGroup radioGroup1;
	@SuppressWarnings("static-access")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ema_formal_5);
		nextActivity = EMA_formal_6.class;
		final String ClassName = this.getClass().getSimpleName();
		radioGroup1= (RadioGroup) findViewById(R.id.rGrp1);
		
		question_five.setActivityText(ClassName);
		question_five.setQuestionText(getText(R.string.ema_formal_text5).toString());
		this.actQuestionList.add(question_five);
		
		radioGroup1.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if(checkedId!=-1){
					RadioButton selRadBtn = (RadioButton) group.findViewById(checkedId);
					String answerText = (String) selRadBtn.getText();
					question_five.setAnswerText(answerText);
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
		   
		   startNext();
	   }
	   
	   @SuppressWarnings("static-access")
	public void back_button(View view){
		   //super.onBackPressed();
		   //nextActivity = EMA_formal_4.class;
		   //startNext();
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
