package com.example.eager_study_UI;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.eager_study.BaseActivity;
import com.example.eager_study.R;
import com.example.eager_study_util.Question;

public class EMA_formal_8 extends BaseActivity{

	final Question question_eight = new Question();
	TextView textView[];
	@SuppressWarnings("static-access")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ema_formal_8);
		nextActivity = EMA_formal_9.class;
		final String ClassName = this.getClass().getSimpleName();
		question_eight.setActivityText(ClassName);
		question_eight.setQuestionText(getText(R.string.ema_formal_text8).toString());
		this.actQuestionList.add(question_eight);
		
		textView = new TextView[]{
			(TextView) findViewById(R.id.textView1),
			(TextView) findViewById(R.id.textView2),
			(TextView) findViewById(R.id.textView3),
			(TextView) findViewById(R.id.textView4),
			(TextView) findViewById(R.id.textView5),
			(TextView) findViewById(R.id.textView6),
			(TextView) findViewById(R.id.textView7),
			(TextView) findViewById(R.id.textView8),
			(TextView) findViewById(R.id.textView9),
			(TextView) findViewById(R.id.textView10),
			(TextView) findViewById(R.id.textView11),
			(TextView) findViewById(R.id.textView12),
			(TextView) findViewById(R.id.textView13),
			(TextView) findViewById(R.id.textView14),
			(TextView) findViewById(R.id.textView15),
			(TextView) findViewById(R.id.textView16)
		};
		
		for (int i=0; i<textView.length; i++){
			final int j=i;
		textView[i].setOnClickListener(new View.OnClickListener() {
       	 
            public void onClick(View v) {
                if (textView[j].isSelected())
                	textView[j].setSelected(false);
                else
                	textView[j].setSelected(true);
            }
        });
		}
		
	}
	@Override
	   public boolean onCreateOptionsMenu(Menu menu) {
	      // Inflate the menu; this adds items to the action bar if it is present.
	      getMenuInflater().inflate(R.menu.main, menu);
	      return true;
	   }
	   
	   public void next_button(View view){
		   String answer="";
		   for (int i=0; i<16; i++){
			   if (textView[i].isSelected()) {
				   answer=answer+textView[i].getText()+"|   ";
			   }
		   }
		   question_eight.setAnswerText(answer);
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
