package com.example.eager_study_UI;

import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;

import com.example.eager_study.BaseActivity;
import com.example.eager_study.R;
import com.example.eager_study_util.Question;

public class EMA_formal_4 extends BaseActivity{

	final Question question_four = new Question();
	SeekBar seekBar1;
	
	@SuppressWarnings("static-access")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ema_formal_4);
		nextActivity = EMA_formal_5.class;
		final String ClassName = this.getClass().getSimpleName();
		
		seekBar1 = (SeekBar)findViewById(R.id.seekBar1);
		seekBar1.getThumb().setColorFilter(Color.TRANSPARENT, Mode.SRC_IN);
		
		question_four.setActivityText(ClassName);
		question_four.setQuestionText(getText(R.string.ema_formal_text4).toString());
		this.actQuestionList.add(question_four);
		
		seekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
		{
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				question_four.setAnswerText(String.valueOf(seekBar1.getProgress()));
			}
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				seekBar1.getThumb().setColorFilter(null);
				seekBar.getThumb().mutate().setAlpha(255);
			}
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				question_four.setAnswerText(String.valueOf(seekBar1.getProgress()));
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
