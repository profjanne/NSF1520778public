package com.example.eager_study_UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.example.eager_study.BaseActivity;
import com.example.eager_study.R;

public class IAT_1b extends BaseActivity{
	String first_name=null;
	String[] uncomfortable_words = {"Anxious", "Nervous", "Fearful", "Uncertain", "Afraid", "Edgy", "Tense", "Worked up", "Uneasy", "Guarded"};
	String[] comfortable_words = {"Calm", "Relaxed", "Restful", "At ease", "Outgoing", "Open", "Lively", "Social", "Unafraid", "Friendly"};
	String[] self_words = {"Self"};
	String[] other_words = {"James", "Jacob", "Benjamin", "Mason", "Alexander", "Ava", "Emma", "Olivia", "Isabella", "Mia"};
	TextView self_text=null, others_text=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.iat_1b);
	
		nextActivity = IAT_1c.class;
		
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
		    first_name = extras.getString("first_name");
		}
		if (first_name!=null){
			if ((first_name.charAt(0)>=65)&&(first_name.charAt(0)<=90)){
				self_words[0]=first_name;
			}else{
				if((first_name.charAt(0)>=97)&&(first_name.charAt(0)<=122)){
					char[] temp = new char[first_name.length()-1];
					first_name.getChars(1, first_name.length(), temp, 0);
					self_words[0] = Character.toString((char) (first_name.charAt(0)-32))+String.valueOf(temp);
				}
			}
		}
		
		for (int i=0; i<other_words.length; i++){
			if (other_words[i].equals(self_words[0])){
				other_words[i]="Jerry";
			}
		}
		
		
		
		
	}
	@Override
	   public boolean onCreateOptionsMenu(Menu menu) {
	      // Inflate the menu; this adds items to the action bar if it is present.
	      getMenuInflater().inflate(R.menu.main, menu);
	      return true;
	   }
	   
	   public void start_button(View view){
		  
			   
			   Intent intent = new Intent(this, nextActivity);
			   intent.putExtra("first_name", first_name);
			   startActivity(intent);
		   
			 
	   }
	   
	   public void back_button(View view){
		   this.finish();
	   }

}
