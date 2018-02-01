package com.example.eager_study_UI;

import java.util.Random;

import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.eager_study.BaseActivity;
import com.example.eager_study.R;
import com.example.eager_study_util.Question;

public class EMA_formal_14 extends BaseActivity{

	final Question question_fourteen = new Question();
	SeekBar seekBar1;
	TextView question;
	
	@SuppressWarnings("static-access")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ema_formal_14);
		nextActivity = EMA_formal_15.class;
		final String ClassName = this.getClass().getSimpleName();
		seekBar1 = (SeekBar)findViewById(R.id.seekBar1);
		seekBar1.getThumb().setColorFilter(Color.TRANSPARENT, Mode.SRC_IN);
		
		question = (TextView) findViewById(R.id.ema_formal_question14);
		question_fourteen.setActivityText(ClassName);
		this.actQuestionList.add(question_fourteen);
		
		seekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
		{
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				question_fourteen.setAnswerText(String.valueOf(seekBar1.getProgress()));
			}
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				seekBar1.getThumb().setColorFilter(null);
				seekBar.getThumb().mutate().setAlpha(255);
			}
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				question_fourteen.setAnswerText(String.valueOf(seekBar1.getProgress()));
			}
		});
		
		Random rn = new Random();
		int answer = rn.nextInt(2) + 1;
		if (answer==1){
			question.setText(R.string.ema_formal_text14a);
			question_fourteen.setQuestionText(getText(R.string.ema_formal_text14a).toString());
		}else if (answer==2){
			question.setText(R.string.ema_formal_text14b);
			question_fourteen.setQuestionText(getText(R.string.ema_formal_text14b).toString());
		}
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