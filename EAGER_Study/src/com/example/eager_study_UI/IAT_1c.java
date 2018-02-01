package com.example.eager_study_UI;

import java.util.Random;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.example.eager_study.BaseActivity;
import com.example.eager_study.R;

public class IAT_1c extends BaseActivity{
	String[] uncomfortable_words = {"Anxious", "Nervous", "Fearful", "Uncertain", "Afraid", "Edgy", "Tense", "Worked up", "Uneasy", "Guarded"};
	String[] comfortable_words = {"Calm", "Relaxed", "Restful", "At ease", "Outgoing", "Open", "Lively", "Social", "Unafraid", "Friendly"};
	String[] self_words = {"Self"};
	String[] other_words = {"James", "Jacob", "Benjamin", "Mason", "Alexander", "Ava", "Emma", "Olivia", "Isabella", "Mia"};
	TextView progress_text, left_category1, right_category1, left_category2, right_category2, description_or_test, left_or, right_or, x_text;
	int progress=1;
	int trials = 5;
	boolean last_entry = true;
	String first_name = null;
	int counting=0, correct_answer=-1, time_counter_index=0; //correct answer: 0 left, 1 right
	long tStart=0, tEnd=0;
	
	boolean[] categories = {true, true, true, true}; //true is left, false is right. This corresponds to 4 catgories: nervousness_words, comfortable_words, self_words, other_words
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
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
		
		nextActivity = IAT_2.class;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.iat_1c);
		progress_text = (TextView) findViewById(R.id.textView3);
		left_category1 = (TextView) findViewById(R.id.textView1);
		left_category2 = (TextView) findViewById(R.id.textView7);
		right_category1 = (TextView) findViewById(R.id.textView2);
		right_category2 = (TextView) findViewById(R.id.textView8);
		description_or_test = (TextView) findViewById(R.id.textView4);
		x_text = (TextView) findViewById(R.id.textView9);
		x_text.setVisibility(View.INVISIBLE);
		left_or = (TextView) findViewById(R.id.textView5);
		right_or = (TextView) findViewById(R.id.textView6);
		left_or.setVisibility(View.INVISIBLE);
		right_or.setVisibility(View.INVISIBLE);
		progress_text.setText("Example");
		left_category1.setText("Self");
		left_category2.setText(" ");
		right_category1.setText("Other");
		right_category2.setText(" ");
		boolean[] temp_exist = {false, false, true, true};
		   preperation(true, false, true, false, temp_exist);
		
		
	}
	@Override
	   public boolean onCreateOptionsMenu(Menu menu) {
	      // Inflate the menu; this adds items to the action bar if it is present.
	      getMenuInflater().inflate(R.menu.main, menu);
	      return true;
	   }

	public void preperation(boolean a1, boolean a2, boolean a3, boolean a4, boolean[] isExist){
		   String[] select=null;
		   Random rn = new Random();
		   Random rn2 = new Random();
		   int answer = 1;
		   int index=0;
		   do{
		   answer = rn.nextInt(4) + 1;
		   switch (answer){
		   		case 1: select=uncomfortable_words;
		   			break;
		   		case 2: select=comfortable_words;
		   			break;
		   		case 3: select=self_words;
		   			break;
		   		case 4: select=other_words;
		   			break;
		   }
		   
		   
		   index = rn2.nextInt(select.length);
		   }while ((select[index].equals(description_or_test.getText().toString()))||(!isExist[answer-1]));
		   
		   description_or_test.setText(select[index]);
		   description_or_test.setTextSize(30);
		   description_or_test.setGravity(Gravity.CENTER);
		   if (answer==1 || answer==2){
			   description_or_test.setTextColor(getResources().getColor(R.color.darkgreen));
		   }else{
			   description_or_test.setTextColor(Color.BLACK);
		   }
		   categories[0]=a1;
		   categories[1]=a2;
		   categories[2]=a3;
		   categories[3]=a4;
		   if (categories[answer-1]) correct_answer=0;
		   else correct_answer=1;
		   tStart = System.currentTimeMillis();
		   counting++;
	}
	public void right_button(View view){
		x_text.setVisibility(View.INVISIBLE);
		   if (counting==trials){
			   if (correct_answer==1){
				   description_or_test.setText("Good, press one of the buttons below to begin. Stay focused!");
				   description_or_test.setTextSize(30);
				   description_or_test.setGravity(Gravity.CENTER);
				   description_or_test.setTextColor(Color.BLACK);
				   counting++;
			   }else{
				   x_text.setVisibility(View.VISIBLE);
				   
				   last_entry=false;
			   }
		   }else if (counting>trials){
			   Intent intent = new Intent(this, nextActivity);
			   intent.putExtra("first_name", first_name);
			   startActivity(intent);
		   }else if (counting<trials){
			   if (correct_answer==1){
				   last_entry = true;
				   tEnd = System.currentTimeMillis();
				   
				   time_counter_index++;
				   boolean[] temp_exist = {false, false, true, true};
				  
				   String[] select=null;
				   Random rn = new Random();
				   Random rn2 = new Random();
				   int answer = 1;
				   int index=0;
				   do{
				   answer = rn.nextInt(4) + 1;
				   switch (answer){
				   		case 1: select=uncomfortable_words;
				   			break;
				   		case 2: select=comfortable_words;
				   			break;
				   		case 3: select=self_words;
				   			break;
				   		case 4: select=other_words;
				   			break;
				   }
				   
				   
				   index = rn2.nextInt(select.length);
				   }while ((select[index].equals(description_or_test.getText().toString()))||(!temp_exist[answer-1]));
				   
				   description_or_test.setText(select[index]);
				   description_or_test.setTextSize(30);
				   description_or_test.setGravity(Gravity.CENTER);
				   if (answer==1 || answer==2){
					   description_or_test.setTextColor(getResources().getColor(R.color.darkgreen));
				   }else{
					   description_or_test.setTextColor(Color.BLACK);
				   }
				   if (categories[answer-1]) correct_answer=0;
				   else correct_answer=1;
				   
				   tStart = System.currentTimeMillis();
				   counting++;
			   }else{
				   x_text.setVisibility(View.VISIBLE);
				   last_entry=false;
				   
			   }
		   }else{
			   this.finish();
		   }
	   }
	
	public void left_button(View view){
		x_text.setVisibility(View.INVISIBLE);
		if (counting==trials){
			   if (correct_answer==0){
				   description_or_test.setText("Good, press one of the buttons below to begin. Stay focused!");
				   description_or_test.setTextSize(30);
				   description_or_test.setGravity(Gravity.CENTER);
				   description_or_test.setTextColor(Color.BLACK);
				   counting++;
			   }else{
				   x_text.setVisibility(View.VISIBLE);
				   
				   last_entry=false;
				   
			   }
		   }else if (counting>trials){
			   Intent intent = new Intent(this, nextActivity);
			   intent.putExtra("first_name", first_name);
			   startActivity(intent);
		   }else if (counting<trials){
			   if (correct_answer==0){
				   last_entry = true;
				   tEnd = System.currentTimeMillis();
				   
				   time_counter_index++;
				   boolean[] temp_exist = {false, false, true, true};
				  
				   String[] select=null;
				   Random rn = new Random();
				   Random rn2 = new Random();
				   int answer = 1;
				   int index=0;
				   do{
				   answer = rn.nextInt(4) + 1;
				   switch (answer){
				   		case 1: select=uncomfortable_words;
				   			break;
				   		case 2: select=comfortable_words;
				   			break;
				   		case 3: select=self_words;
				   			break;
				   		case 4: select=other_words;
				   			break;
				   }
				   
				   
				   index = rn2.nextInt(select.length);
				   }while ((select[index].equals(description_or_test.getText().toString()))||(!temp_exist[answer-1]));
				   
				   description_or_test.setText(select[index]);
				   description_or_test.setTextSize(30);
				   description_or_test.setGravity(Gravity.CENTER);
				   if (answer==1 || answer==2){
					   description_or_test.setTextColor(getResources().getColor(R.color.darkgreen));
				   }else{
					   description_or_test.setTextColor(Color.BLACK);
				   }
				   if (categories[answer-1]) correct_answer=0;
				   else correct_answer=1;
				   
				   tStart = System.currentTimeMillis();
				   counting++;
			   }else{
				   x_text.setVisibility(View.VISIBLE);
				
				   last_entry=false;
				   
			   }
		   }else{
			   this.finish();
		   }
	   }
	   public void back_button(View view){
	   }

}
