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

public class EMA_formal_15 extends BaseActivity{

	final Question question_fifteen = new Question();
	SeekBar seekBar1;
	TextView question;
	
	@SuppressWarnings("static-access")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ema_formal_15);
		if (this.actQuestionList.get(this.actQuestionList.size()-2).getQuestionText().equals("I am in someone��s home."))
			nextActivity = EMA_formal_17.class;
		else
			nextActivity = EMA_formal_16.class;
		final String ClassName = this.getClass().getSimpleName();
		seekBar1 = (SeekBar)findViewById(R.id.seekBar1);
		seekBar1.getThumb().setColorFilter(Color.TRANSPARENT, Mode.SRC_IN);
		
		question = (TextView) findViewById(R.id.ema_formal_question15);
		question_fifteen.setActivityText(ClassName);
		this.actQuestionList.add(question_fifteen);
		
		seekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
		{
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				question_fifteen.setAnswerText(String.valueOf(seekBar1.getProgress()));
			}
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				seekBar1.getThumb().setColorFilter(null);
				seekBar.getThumb().mutate().setAlpha(255);
			}
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				question_fifteen.setAnswerText(String.valueOf(seekBar1.getProgress()));
			}
		});
		
		Random rn = new Random();
		int answer = rn.nextInt(4) + 1;
		if (answer==1){
			question.setText(R.string.ema_formal_text15a);
			question_fifteen.setQuestionText(getText(R.string.ema_formal_text15a).toString());
		}else if (answer==2){
			question.setText(R.string.ema_formal_text15b);
			question_fifteen.setQuestionText(getText(R.string.ema_formal_text15b).toString());
		}else if (answer==3){
			question.setText(R.string.ema_formal_text15c);
			question_fifteen.setQuestionText(getText(R.string.ema_formal_text15c).toString());
		}else if (answer==4){
			question.setText(R.string.ema_formal_text15d);
			question_fifteen.setQuestionText(getText(R.string.ema_formal_text15d).toString());
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