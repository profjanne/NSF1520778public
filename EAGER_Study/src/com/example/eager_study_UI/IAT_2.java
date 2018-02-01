package com.example.eager_study_UI;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.Calendar;
import java.util.Random;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.text.format.Time;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eager_study.BaseActivity;
import com.example.eager_study.MainActivity;
import com.example.eager_study.R;
import com.example.eager_study_util.UploadService;

public class IAT_2 extends BaseActivity{
	static SharedPreferences settings;
	String[] uncomfortable_words = {"Anxious", "Nervous", "Fearful", "Uncertain", "Afraid", "Edgy", "Tense", "Worked up", "Uneasy", "Guarded"};
	String[] comfortable_words = {"Calm", "Relaxed", "Restful", "At ease", "Outgoing", "Open", "Lively", "Social", "Unafraid", "Friendly"};
	String[] self_words = {"Self"};
	String[] other_words = {"James", "Jacob", "Benjamin", "Mason", "Alexander", "Ava", "Emma", "Olivia", "Isabella", "Mia"};
	TextView progress_text, left_category1, right_category1, left_category2, right_category2, description_or_test, left_or, right_or, x_text;
	int progress=1;
	int[] trials = {10, 10, 10, 20, 10, 10, 20};
	boolean last_entry = true;
	String first_name = null;
	int counting=0, correct_answer=-1, time_counter_index=0; //correct answer: 0 left, 1 right
	long tStart=0, tEnd=0;
	long[] time_counter = null;
	boolean[] error_array = null;
	boolean[] categories = {true, true, true, true}; //true is left, false is right. This corresponds to 4 catgories: nervousness_words, comfortable_words, self_words, other_words
	String result=null;
	private static final String SQLite_NAME = "/data/com.example.eager_study/databases/questions.db";//database
	private File data = Environment.getDataDirectory();
	SQLiteDatabase myDB = null;
	private String TableName = "iat";
	public SharedPreferences location_service;
	public SharedPreferences.Editor location_edit;
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
		location_service = getApplicationContext().getSharedPreferences("location_service",MODE_MULTI_PROCESS);
		location_edit=location_service.edit();
		int total_time_counter=0;
		for (int kk=0; kk<trials.length; kk++)
			total_time_counter +=trials[kk];
		time_counter = new long[total_time_counter];
		error_array = new boolean[total_time_counter];
		for (int kk=0; kk<total_time_counter; kk++)
			error_array[kk] = false;
		for (int i=0; i<other_words.length; i++){
			if (other_words[i].equals(self_words[0])){
				other_words[i]="Jerry";
			}
		}
		
			
		super.onCreate(savedInstanceState);
		setContentView(R.layout.iat_2);
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
		progress_text.setText("1/7");
		left_category1.setText("Self");
		left_category2.setText(" ");
		//left_category2.setVisibility(View.INVISIBLE);
		right_category1.setText("Other");
		right_category2.setText(" ");
		//right_category2.setVisibility(View.INVISIBLE);
		description_or_test.setText("Block 1: Press one of the buttons below to begin.");
		description_or_test.setTextColor(Color.BLACK);
		nextActivity = MainActivity.class;
		
		
	}
	@Override
	   public boolean onCreateOptionsMenu(Menu menu) {
	      // Inflate the menu; this adds items to the action bar if it is present.
	      getMenuInflater().inflate(R.menu.main, menu);
	      return true;
	   }
	public String score_calculation(){
		String output = null;
		long[] B3 = new long[trials[2]], B4 = new long[trials[3]], B6 = new long[trials[5]], B7 = new long[trials[6]];
		boolean[] E3 = new boolean[trials[2]], E4 = new boolean[trials[3]], E6 = new boolean[trials[5]], E7 = new boolean[trials[6]];
		boolean[] V3 = new boolean[trials[2]], V4 = new boolean[trials[3]], V6 = new boolean[trials[5]], V7 = new boolean[trials[6]];
		int short_time_task_counting = 0;
		double correct_mean_B3=0, correct_mean_B4=0, correct_mean_B6=0, correct_mean_B7=0;
		double sd_B3_B6=0, sd_B4_B7=0, mean_B3_B6=0, mean_B4_B7=0;
		for (int i = trials[0]+trials[1]; i<trials[0]+trials[1]+trials[2]; i++){
			B3[i-(trials[0]+trials[1])] = time_counter[i];
			E3[i-(trials[0]+trials[1])] = error_array[i];
			if (time_counter[i]>10000)
				V3[i-(trials[0]+trials[1])] = false;
			else 
				V3[i-(trials[0]+trials[1])] = true;
			if (time_counter[i]<300)
			short_time_task_counting++;
		}
		
		for (int i = trials[0]+trials[1]+trials[2]; i<trials[0]+trials[1]+trials[2]+trials[3]; i++){
			B4[i-(trials[0]+trials[1]+trials[2])] = time_counter[i];
			E4[i-(trials[0]+trials[1]+trials[2])] = error_array[i];
			if (time_counter[i]>10000)
				V4[i-(trials[0]+trials[1]+trials[2])] = false;
			else 
				V4[i-(trials[0]+trials[1]+trials[2])] = true;
			if (time_counter[i]<300)
				short_time_task_counting++;
		}
		
		for (int i = trials[0]+trials[1]+trials[2]+trials[3]+trials[4]; i<trials[0]+trials[1]+trials[2]+trials[3]+trials[4]+trials[5]; i++){
			B6[i-(trials[0]+trials[1]+trials[2]+trials[3]+trials[4])] = time_counter[i];
			E6[i-(trials[0]+trials[1]+trials[2]+trials[3]+trials[4])] = error_array[i];
			if (time_counter[i]>10000)
				V6[i-(trials[0]+trials[1]+trials[2]+trials[3]+trials[4])] = false;
			else 
				V6[i-(trials[0]+trials[1]+trials[2]+trials[3]+trials[4])] = true;
			if (time_counter[i]<300)
				short_time_task_counting++;
		}
		
		for (int i = trials[0]+trials[1]+trials[2]+trials[3]+trials[4]+trials[5]; i<trials[0]+trials[1]+trials[2]+trials[3]+trials[4]+trials[5]+trials[6]; i++){
			B7[i-(trials[0]+trials[1]+trials[2]+trials[3]+trials[4]+trials[5])] = time_counter[i];
			E7[i-(trials[0]+trials[1]+trials[2]+trials[3]+trials[4]+trials[5])] = error_array[i];
			if (time_counter[i]>10000)
				V7[i-(trials[0]+trials[1]+trials[2]+trials[3]+trials[4]+trials[5])] = false;
			else 
				V7[i-(trials[0]+trials[1]+trials[2]+trials[3]+trials[4]+trials[5])] = true;
			if (time_counter[i]<300)
				short_time_task_counting++;
		}
		
		if (short_time_task_counting > 0.1*(trials[2]+trials[3]+trials[5]+trials[6]))
			return "Test is not valid. More than 10 percent of trials have latencies shorter than 300 ms.";
		
		int count1=0, count_B3_B6=0;
		for (int i=0; i<B3.length; i++){
			if ((V3[i])&&(!E3[i])){
				correct_mean_B3=correct_mean_B3+B3[i];
				count1++;
			}
			
			if (V3[i]){
				mean_B3_B6 = mean_B3_B6 + B3[i];
				count_B3_B6++;
			}
		}
		correct_mean_B3=correct_mean_B3*1.0/count1;
		
		int count2=0;
		for (int i=0; i<B6.length; i++){
			if ((V6[i])&&(!E6[i])){
				correct_mean_B6=correct_mean_B6+B6[i];
				count2++;
			}
			
			if (V6[i]){
				mean_B3_B6 = mean_B3_B6 + B6[i];
				count_B3_B6++;
			}
		}
		correct_mean_B6=correct_mean_B6*1.0/count2;
		mean_B3_B6 = mean_B3_B6*1.0/count_B3_B6;
		
		int count3=0, count_B4_B7=0;
		for (int i=0; i<B4.length; i++){
			if ((V4[i])&&(!E4[i])){
				correct_mean_B4=correct_mean_B4+B4[i];
				count3++;
			}
			if (V4[i]){
				mean_B4_B7 = mean_B4_B7 + B4[i];
				count_B4_B7++;
			}
		}
		correct_mean_B4=correct_mean_B4*1.0/count3;
		
		int count4=0;
		for (int i=0; i<B7.length; i++){
			if ((V7[i])&&(!E7[i])){
				correct_mean_B7=correct_mean_B7+B7[i];
				count4++;
			}
			if (V7[i]){
				mean_B4_B7 = mean_B4_B7 + B7[i];
				count_B4_B7++;
			}
		}
		mean_B4_B7 = mean_B4_B7*1.0/count_B4_B7;
		correct_mean_B7=correct_mean_B7*1.0/count4;
		
		for (int i=0; i<B3.length; i++){
			if (V3[i]){
				sd_B3_B6 = sd_B3_B6 + (mean_B3_B6-B3[i])*(mean_B3_B6-B3[i]);
			}
		}
		for (int i=0; i<B6.length; i++){
			if (V6[i]){
				sd_B3_B6 = sd_B3_B6 + (mean_B3_B6-B6[i])*(mean_B3_B6-B6[i]);
			}
		}
		
		sd_B3_B6 = Math.sqrt(sd_B3_B6*1.0/count_B3_B6);
		
		for (int i=0; i<B4.length; i++){
			if (V4[i]){
				sd_B4_B7 = sd_B4_B7 + (mean_B4_B7-B4[i])*(mean_B4_B7-B4[i]);
			}
		}
		for (int i=0; i<B7.length; i++){
			if (V7[i]){
				sd_B4_B7 = sd_B4_B7 + (mean_B4_B7-B7[i])*(mean_B4_B7-B7[i]);
			}
		}
		
		sd_B4_B7 = Math.sqrt(sd_B4_B7*1.0/count_B4_B7);
		double final_mean_B3 = 0;
		int count5=0;
		for (int i=0; i<B3.length; i++){
			if (V3[i]){
				count5++;
				if (!E3[i]){
					final_mean_B3 = final_mean_B3+B3[i];
				}else{
					final_mean_B3 = final_mean_B3 + 600 + correct_mean_B3;
				}
			}
		}
		final_mean_B3 = final_mean_B3*1.0/count5;
		
		double final_mean_B4 = 0;
		int count6=0;
		for (int i=0; i<B4.length; i++){
			if (V4[i]){
				count6++;
				if (!E4[i]){
					final_mean_B4 = final_mean_B4+B4[i];
				}else{
					final_mean_B4 = final_mean_B4 + 600 + correct_mean_B4;
				}
			}
		}
		final_mean_B4 = final_mean_B4*1.0/count6;
		
		double final_mean_B6 = 0;
		int count7=0;
		for (int i=0; i<B6.length; i++){
			if (V6[i]){
				count7++;
				if (!E6[i]){
					final_mean_B6 = final_mean_B6+B6[i];
				}else{
					final_mean_B6 = final_mean_B6 + 600 + correct_mean_B6;
				}
			}
		}
		final_mean_B6 = final_mean_B6*1.0/count7;
		
		double final_mean_B7 = 0;
		int count8=0;
		for (int i=0; i<B7.length; i++){
			if (V7[i]){
				count8++;
				if (!E7[i]){
					final_mean_B7 = final_mean_B7+B7[i];
				}else{
					final_mean_B7 = final_mean_B7 + 600 + correct_mean_B7;
				}
			}
		}
		final_mean_B7 = final_mean_B7*1.0/count8;
		
		double Dscore = ((final_mean_B6-final_mean_B3)/sd_B3_B6+(final_mean_B7-final_mean_B4)/sd_B4_B7)/2.0;
		output = "The final D score is " + String.valueOf(Dscore);
		return output;
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
		   if (counting==0){
			   boolean[] temp_exist = {false, false, true, true};
			   preperation(true, false, true, false, temp_exist); //first two values do not matter in this case.
			   
		   }else if (counting==trials[0]){
			   if (correct_answer==1){
				   last_entry = true;
				   tEnd = System.currentTimeMillis();
				   time_counter[time_counter_index]=tEnd-tStart;
				   time_counter_index++;
				   progress_text.setText("2/7");
				   right_category1.setText(" ");
				   right_category2.setText("Uncomfortable");
				   left_category1.setText(" ");
				   left_category2.setText("Comfortable");
				   description_or_test.setText("Block 2: Press one of the buttons below to begin.");
				   description_or_test.setGravity(Gravity.NO_GRAVITY);
				   description_or_test.setTextAppearance(this, android.R.style.TextAppearance_Small);
				   description_or_test.setTextColor(Color.BLACK);
				   counting++;
			   }else{
				   x_text.setVisibility(View.VISIBLE);
				   if (last_entry)
					   error_array[time_counter_index] = true;
				   last_entry=false;
				   
			   }
		   }else if (counting==trials[0]+1){
			   boolean[] temp_exist = {true, true, false, false};
			   preperation(false, true, false, true, temp_exist);
		   }else if (counting==trials[0]+trials[1]+1){
			   if (correct_answer==1){
				   last_entry = true;
				   tEnd = System.currentTimeMillis();
				   time_counter[time_counter_index]=tEnd-tStart;
				   time_counter_index++;
				   progress_text.setText("3/7");
				   left_category1.setText("Self");
				   right_category2.setText("Uncomfortable");
				   right_category1.setText("Other");
				   left_category2.setText("Comfortable");
				   description_or_test.setText("Block 3: Press one of the buttons below to begin.");
				   description_or_test.setGravity(Gravity.NO_GRAVITY);
				   description_or_test.setTextAppearance(this, android.R.style.TextAppearance_Small);
				   description_or_test.setTextColor(Color.BLACK);
				   counting++;
			   }else{
				   x_text.setVisibility(View.VISIBLE);
				   if (last_entry)
					   error_array[time_counter_index] = true;
				   last_entry=false;
				   
			   }
		   }else if (counting==trials[0]+trials[1]+2){
			   boolean[] temp_exist = {true, true, true, true};
			   preperation(false, true, true, false, temp_exist);
		   }else if (counting==trials[0]+trials[1]+trials[2]+2){
			   if (correct_answer==1){
				   last_entry = true;
				   
				   tEnd = System.currentTimeMillis();
				   time_counter[time_counter_index]=tEnd-tStart;
				   time_counter_index++;
				   progress_text.setText("4/7");
				   right_category1.setText("Other");
				   right_category2.setText("Uncomfortable");
				   left_category1.setText("Self");
				   left_category2.setText("Comfortable");
				   description_or_test.setText("Block 4: Press one of the buttons below to begin.");
				   description_or_test.setGravity(Gravity.NO_GRAVITY);
				   description_or_test.setTextAppearance(this, android.R.style.TextAppearance_Small);
				   description_or_test.setTextColor(Color.BLACK);
				   counting++;
			   }else{
				   x_text.setVisibility(View.VISIBLE);
				   if (last_entry)
					   error_array[time_counter_index] = true;
				   last_entry=false;
				   
			   }
		   }else if (counting==trials[0]+trials[1]+trials[2]+3){
			   boolean[] temp_exist = {true, true, true, true};
			   preperation(false, true, true, false, temp_exist);
		   }else if (counting==trials[0]+trials[1]+trials[2]+trials[3]+3) {
			   if (correct_answer==1){
				   last_entry = true;
				 
				   tEnd = System.currentTimeMillis();
				   time_counter[time_counter_index]=tEnd-tStart;
				   time_counter_index++;
				   progress_text.setText("5/7");
				   left_category1.setText("Other");
				   right_category2.setText(" ");
				   right_category1.setText("Self");
				   left_category2.setText(" ");
				   description_or_test.setText("Block 5: Press one of the buttons below to begin.");
				   description_or_test.setGravity(Gravity.NO_GRAVITY);
				   description_or_test.setTextAppearance(this, android.R.style.TextAppearance_Small);
				   description_or_test.setTextColor(Color.BLACK);
				   counting++;
			   }else{
				   x_text.setVisibility(View.VISIBLE);
				   if (last_entry)
					   error_array[time_counter_index] = true;
				   last_entry=false;
				   
			   }
		   } else if (counting==trials[0]+trials[1]+trials[2]+trials[3]+4) {
			   boolean[] temp_exist = {false, false, true, true};
			   preperation(false, true, false, true, temp_exist);
		   }else if (counting==trials[0]+trials[1]+trials[2]+trials[3]+trials[4]+4) {
			   if (correct_answer==1){
				   last_entry = true;
				   
				   tEnd = System.currentTimeMillis();
				   time_counter[time_counter_index]=tEnd-tStart;
				   time_counter_index++;
				   progress_text.setText("6/7");
				   left_category1.setText("Other");
				   right_category2.setText("Uncomfortable");
				   right_category1.setText("Self");
				   left_category2.setText("Comfortable");
				   description_or_test.setText("Block 6: Press one of the buttons below to begin.");
				   description_or_test.setGravity(Gravity.NO_GRAVITY);
				   description_or_test.setTextAppearance(this, android.R.style.TextAppearance_Small);
				   description_or_test.setTextColor(Color.BLACK);
				   counting++;
			   }else{
				   x_text.setVisibility(View.VISIBLE);
				   if (last_entry)
					   error_array[time_counter_index] = true;
				   last_entry=false;
				   
			   }
		   } else if (counting==trials[0]+trials[1]+trials[2]+trials[3]+trials[4]+5) {
			   boolean[] temp_exist = {true, true, true, true};
			   preperation(false, true, false, true, temp_exist);
		   }else if (counting==trials[0]+trials[1]+trials[2]+trials[3]+trials[4]+trials[5]+5) {
			   if (correct_answer==1){
				   last_entry = true;
				   
				   tEnd = System.currentTimeMillis();
				   time_counter[time_counter_index]=tEnd-tStart;
				   time_counter_index++;
				   progress_text.setText("7/7");
				   left_category1.setText("Other");
				   right_category2.setText("Uncomfortable");
				   right_category1.setText("Self");
				   left_category2.setText("Comfortable");
				   description_or_test.setText("Block 7: Press one of the buttons below to begin.");
				   description_or_test.setGravity(Gravity.NO_GRAVITY);
				   description_or_test.setTextAppearance(this, android.R.style.TextAppearance_Small);
				   description_or_test.setTextColor(Color.BLACK);
				   counting++;
			   }else{
				   x_text.setVisibility(View.VISIBLE);
				   if (last_entry)
					   error_array[time_counter_index] = true;
				   last_entry=false;
				   
			   }
		   } else if (counting==trials[0]+trials[1]+trials[2]+trials[3]+trials[4]+trials[5]+6) {
			   boolean[] temp_exist = {true, true, true, true};
			   preperation(false, true, false, true, temp_exist);
		   } else if (counting==trials[0]+trials[1]+trials[2]+trials[3]+trials[4]+trials[5]+trials[6]+6){
			   if (correct_answer==1){
				   last_entry = true;
				   tEnd = System.currentTimeMillis();
				   time_counter[time_counter_index]=tEnd-tStart;
				   time_counter_index++;
				   
				
				   result = score_calculation();
				  
				   
				   description_or_test.setText("You have completed the test. Please press one of the buttons below to quit.");
				   description_or_test.setGravity(Gravity.NO_GRAVITY);
				   description_or_test.setTextAppearance(this, android.R.style.TextAppearance_Small);
				   description_or_test.setTextColor(Color.BLACK);
				   counting++;
			   }else{
				   x_text.setVisibility(View.VISIBLE);
				   if (last_entry)
					   error_array[time_counter_index] = true;
				   last_entry=false;
				   
			   }
		   }else if (counting<trials[0]+trials[1]+trials[2]+trials[3]+trials[4]+trials[5]+trials[6]+6){
			   if (correct_answer==1){
				   last_entry = true;
				   tEnd = System.currentTimeMillis();
				   time_counter[time_counter_index]=tEnd-tStart;
				   time_counter_index++;
				   boolean[] temp_exist = null;
				   
				   if (counting>0&&counting<trials[0]) temp_exist = new boolean[] {false, false, true, true};
				   if (counting>trials[0]+1&&counting<trials[0]+trials[1]+1) temp_exist = new boolean[] {true, true, false, false};
				   if (counting>trials[0]+trials[1]+2&&counting<trials[0]+trials[1]+trials[2]+2) temp_exist = new boolean[] {true, true, true, true};
				   if (counting>trials[0]+trials[1]+trials[2]+3&&counting<trials[0]+trials[1]+trials[2]+trials[3]+3) temp_exist = new boolean[] {true, true, true, true};
				   if (counting>trials[0]+trials[1]+trials[2]+trials[3]+4&&counting<trials[0]+trials[1]+trials[2]+trials[3]+trials[4]+4) temp_exist = new boolean[] {false, false, true, true};
				   if (counting>trials[0]+trials[1]+trials[2]+trials[3]+trials[4]+5&&counting<trials[0]+trials[1]+trials[2]+trials[3]+trials[4]+trials[5]+5) temp_exist = new boolean[] {true, true, true, true};
				   if (counting>trials[0]+trials[1]+trials[2]+trials[3]+trials[4]+trials[5]+6&&counting<trials[0]+trials[1]+trials[2]+trials[3]+trials[4]+trials[5]+trials[6]+6) temp_exist = new boolean[] {true, true, true, true};
				   
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
				   if (last_entry)
					   error_array[time_counter_index] = true;
				   last_entry=false;
				   
			   }
		   }else{
			   /* Create a Database. */
			   settings=getSharedPreferences("TEST", MODE_MULTI_PROCESS);
			   String idString = settings.getString("UID", "ERROR");
			   int iatgroup = settings.getInt("IAT_group", 0);
			   
			   double lat1 = location_service.getFloat("lat1", 0);
			   double lon1 = location_service.getFloat("lon1", 0);
				try {
					File dbfile = new File(data,SQLite_NAME);
					myDB = SQLiteDatabase
							.openOrCreateDatabase(dbfile, null);
					ContentValues values = new ContentValues();
					Time now = new Time();
					now.setToNow();
					String time = now.format("%m-%d-%Y")+" "+now.format("%H:%M:%S");
					
					values.put("time",time);
					values.put("userid",idString);
					values.put("iat_group",iatgroup);
					values.put("lat",lat1);
					values.put("lon",lon1);
					values.put("result",result);
					
					myDB.insertWithOnConflict(TableName, null, values, SQLiteDatabase.CONFLICT_REPLACE);
					
					System.out.println("inserted?:"+lat1+" "+lon1);
				} catch (Exception e) {
					Log.e("Error", "Error", e);
				} finally {
					if (myDB != null)
						myDB.close();
				}
				exportDB();
				   Toast.makeText(getApplicationContext(), "Thank you for completing the IAT test!", 
						   Toast.LENGTH_SHORT).show();
				   
			   Intent intent = new Intent(this, nextActivity);
			   startActivity(intent);
		   }
	   }
	
	public void left_button(View view){
		x_text.setVisibility(View.INVISIBLE);
		   if (counting==0){
			   boolean[] temp_exist = {false, false, true, true};
			   preperation(true, false, true, false, temp_exist); //first two values do not matter in this case.
			   
		   }else if (counting==trials[0]){
			   if (correct_answer==0){
				   last_entry = true;
				   tEnd = System.currentTimeMillis();
				   time_counter[time_counter_index]=tEnd-tStart;
				   time_counter_index++;
				   progress_text.setText("2/7");
				   right_category1.setText(" ");
				   right_category2.setText("Uncomfortable");
				   left_category1.setText(" ");
				   left_category2.setText("Comfortable");
				   description_or_test.setText("Block 2: Press one of the buttons below to begin.");
				   description_or_test.setGravity(Gravity.NO_GRAVITY);
				   description_or_test.setTextAppearance(this, android.R.style.TextAppearance_Small);
				   description_or_test.setTextColor(Color.BLACK);
				   counting++;
			   }else{
				   x_text.setVisibility(View.VISIBLE);
				   if (last_entry)
					   error_array[time_counter_index] = true;
				   last_entry=false;
				   
			   }
		   }else if (counting==trials[0]+1){
			   boolean[] temp_exist = {true, true, false, false};
			   preperation(false, true, false, true, temp_exist);
		   }else if (counting==trials[0]+trials[1]+1){
			   if (correct_answer==0){
				   last_entry = true;
				  
				   tEnd = System.currentTimeMillis();
				   time_counter[time_counter_index]=tEnd-tStart;
				   time_counter_index++;
				   progress_text.setText("3/7");
				   left_category1.setText("Self");
				   right_category2.setText("Uncomfortable");
				   right_category1.setText("Other");
				   left_category2.setText("Comfortable");
				   description_or_test.setText("Block 3: Press one of the buttons below to begin.");
				   description_or_test.setGravity(Gravity.NO_GRAVITY);
				   description_or_test.setTextAppearance(this, android.R.style.TextAppearance_Small);
				   description_or_test.setTextColor(Color.BLACK);
				   counting++;
			   }else{
				   x_text.setVisibility(View.VISIBLE);
				   if (last_entry)
					   error_array[time_counter_index] = true;
				   last_entry=false;
				   
			   }
		   }else if (counting==trials[0]+trials[1]+2){
			   boolean[] temp_exist = {true, true, true, true};
			   preperation(false, true, true, false, temp_exist);
		   }else if (counting==trials[0]+trials[1]+trials[2]+2){
			   if (correct_answer==0){
				   last_entry = true;
				   
				   tEnd = System.currentTimeMillis();
				   time_counter[time_counter_index]=tEnd-tStart;
				   time_counter_index++;
				   progress_text.setText("4/7");
				   right_category1.setText("Other");
				   right_category2.setText("Uncomfortable");
				   left_category1.setText("Self");
				   left_category2.setText("Comfortable");
				   description_or_test.setText("Block 4: Press one of the buttons below to begin.");
				   description_or_test.setGravity(Gravity.NO_GRAVITY);
				   description_or_test.setTextAppearance(this, android.R.style.TextAppearance_Small);
				   description_or_test.setTextColor(Color.BLACK);
				   counting++;
			   }else{
				   x_text.setVisibility(View.VISIBLE);
				   if (last_entry)
					   error_array[time_counter_index] = true;
				   last_entry=false;
				   
			   }
		   }else if (counting==trials[0]+trials[1]+trials[2]+3){
			   boolean[] temp_exist = {true, true, true, true};
			   preperation(false, true, true, false, temp_exist);
		   }else if (counting==trials[0]+trials[1]+trials[2]+trials[3]+3) {
			   if (correct_answer==0){
				   last_entry = true;
				   tEnd = System.currentTimeMillis();
				   time_counter[time_counter_index]=tEnd-tStart;
				   time_counter_index++;
				   progress_text.setText("5/7");
				   left_category1.setText("Other");
				   right_category2.setText(" ");
				   right_category1.setText("Self");
				   left_category2.setText(" ");
				   description_or_test.setText("Block 5: Press one of the buttons below to begin.");
				   description_or_test.setGravity(Gravity.NO_GRAVITY);
				   description_or_test.setTextAppearance(this, android.R.style.TextAppearance_Small);
				   description_or_test.setTextColor(Color.BLACK);
				   counting++;
			   }else{
				   x_text.setVisibility(View.VISIBLE);
				   if (last_entry)
					   error_array[time_counter_index] = true;
				   last_entry=false;
				   
			   }
		   } else if (counting==trials[0]+trials[1]+trials[2]+trials[3]+4) {
			   boolean[] temp_exist = {false, false, true, true};
			   preperation(false, true, false, true, temp_exist);
		   }else if (counting==trials[0]+trials[1]+trials[2]+trials[3]+trials[4]+4) {
			   if (correct_answer==0){
				   last_entry = true;
				   
				   tEnd = System.currentTimeMillis();
				   time_counter[time_counter_index]=tEnd-tStart;
				   time_counter_index++;
				   progress_text.setText("6/7");
				   left_category1.setText("Other");
				   right_category2.setText("Uncomfortable");
				   right_category1.setText("Self");
				   left_category2.setText("Comfortable");
				   description_or_test.setText("Block 6: Press one of the buttons below to begin.");
				   description_or_test.setGravity(Gravity.NO_GRAVITY);
				   description_or_test.setTextAppearance(this, android.R.style.TextAppearance_Small);
				   description_or_test.setTextColor(Color.BLACK);
				   counting++;
			   }else{
				   x_text.setVisibility(View.VISIBLE);
				   if (last_entry)
					   error_array[time_counter_index] = true;
				   last_entry=false;
				   
			   }
		   } else if (counting==trials[0]+trials[1]+trials[2]+trials[3]+trials[4]+5) {
			   boolean[] temp_exist = {true, true, true, true};
			   preperation(false, true, false, true, temp_exist);
		   }else if (counting==trials[0]+trials[1]+trials[2]+trials[3]+trials[4]+trials[5]+5) {
			   if (correct_answer==0){
				   last_entry = true;
				   
				   tEnd = System.currentTimeMillis();
				   time_counter[time_counter_index]=tEnd-tStart;
				   time_counter_index++;
				   progress_text.setText("7/7");
				   left_category1.setText("Other");
				   right_category2.setText("Uncomfortable");
				   right_category1.setText("Self");
				   left_category2.setText("Comfortable");
				   description_or_test.setText("Block 7: Press one of the buttons below to begin.");
				   description_or_test.setGravity(Gravity.NO_GRAVITY);
				   description_or_test.setTextAppearance(this, android.R.style.TextAppearance_Small);
				   description_or_test.setTextColor(Color.BLACK);
				   counting++;
			   }else{
				   x_text.setVisibility(View.VISIBLE);
				   if (last_entry)
					   error_array[time_counter_index] = true;
				   last_entry=false;
				   
			   }
		   } else if (counting==trials[0]+trials[1]+trials[2]+trials[3]+trials[4]+trials[5]+6) {
			   boolean[] temp_exist = {true, true, true, true};
			   preperation(false, true, false, true, temp_exist);
		   } else if (counting==trials[0]+trials[1]+trials[2]+trials[3]+trials[4]+trials[5]+trials[6]+6){
			   if (correct_answer==0){
				   last_entry = true;
				   tEnd = System.currentTimeMillis();
				   time_counter[time_counter_index]=tEnd-tStart;
				   time_counter_index++;
				   
				
				   result = score_calculation();
				  
				   description_or_test.setText("You have completed the test. Please press one of the buttons below to quit.");
				   description_or_test.setGravity(Gravity.NO_GRAVITY);
				   description_or_test.setTextAppearance(this, android.R.style.TextAppearance_Small);
				   description_or_test.setTextColor(Color.BLACK);
				   counting++;
			   }else{
				   x_text.setVisibility(View.VISIBLE);
				   if (last_entry)
					   error_array[time_counter_index] = true;
				   last_entry=false;
				   
			   }
		   }else if (counting<trials[0]+trials[1]+trials[2]+trials[3]+trials[4]+trials[5]+trials[6]+6){
			   if (correct_answer==0){
				   last_entry = true;
				   tEnd = System.currentTimeMillis();
				   time_counter[time_counter_index]=tEnd-tStart;
				   time_counter_index++;
				   boolean[] temp_exist = null;
				   
				   if (counting>0&&counting<trials[0]) temp_exist = new boolean[] {false, false, true, true};
				   if (counting>trials[0]+1&&counting<trials[0]+trials[1]+1) temp_exist = new boolean[] {true, true, false, false};
				   if (counting>trials[0]+trials[1]+2&&counting<trials[0]+trials[1]+trials[2]+2) temp_exist = new boolean[] {true, true, true, true};
				   if (counting>trials[0]+trials[1]+trials[2]+3&&counting<trials[0]+trials[1]+trials[2]+trials[3]+3) temp_exist = new boolean[] {true, true, true, true};
				   if (counting>trials[0]+trials[1]+trials[2]+trials[3]+4&&counting<trials[0]+trials[1]+trials[2]+trials[3]+trials[4]+4) temp_exist = new boolean[] {false, false, true, true};
				   if (counting>trials[0]+trials[1]+trials[2]+trials[3]+trials[4]+5&&counting<trials[0]+trials[1]+trials[2]+trials[3]+trials[4]+trials[5]+5) temp_exist = new boolean[] {true, true, true, true};
				   if (counting>trials[0]+trials[1]+trials[2]+trials[3]+trials[4]+trials[5]+6&&counting<trials[0]+trials[1]+trials[2]+trials[3]+trials[4]+trials[5]+trials[6]+6) temp_exist = new boolean[] {true, true, true, true};
				   
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
				   if (last_entry)
					   error_array[time_counter_index] = true;
				   last_entry=false;
				   
			   }
		   }else{
			   /* Create a Database. */
			   settings=getSharedPreferences("TEST", MODE_MULTI_PROCESS);
			   String idString = settings.getString("UID", "ERROR");
			   int iatgroup = settings.getInt("IAT_group", 0);
			   double lat1 = location_service.getFloat("lat1", 0);
			   double lon1 = location_service.getFloat("lon1", 0);
				try {
					File dbfile = new File(data,SQLite_NAME);
					myDB = SQLiteDatabase
							.openOrCreateDatabase(dbfile, null);
					ContentValues values = new ContentValues();
					Time now = new Time();
					now.setToNow();
					String time = now.format("%m-%d-%Y")+" "+now.format("%H:%M:%S");
					
					values.put("time",time);
					values.put("userid",idString);
					values.put("iat_group",iatgroup);
					values.put("lat",lat1);
					values.put("lon",lon1);
					values.put("result",result);
					
					myDB.insertWithOnConflict(TableName, null, values, SQLiteDatabase.CONFLICT_REPLACE);
					
					System.out.println("inserted?:"+lat1+" "+lon1);
				} catch (Exception e) {
					Log.e("Error", "Error", e);
				} finally {
					if (myDB != null)
						myDB.close();
				}
				exportDB();
				   Toast.makeText(getApplicationContext(), "Thank you for completing the IAT test!", 
						   Toast.LENGTH_SHORT).show();
				   
			   Intent intent = new Intent(this, nextActivity);
			   startActivity(intent);
		   }
	   }
	
	private void exportDB(){
		File sd = Environment.getExternalStorageDirectory();
	       File data = Environment.getDataDirectory();
	       FileChannel source=null;
	       FileChannel destination=null;
	       String currentDBPath = "/data/com.example.eager_study/databases/questions.db";
	       String backupDBPath = "sociologysurvey.db";
	       File currentDB = new File(data, currentDBPath);
	       File backupDB = new File(sd, backupDBPath);
	       try {
	            source = new FileInputStream(currentDB).getChannel();
	            destination = new FileOutputStream(backupDB).getChannel();
	            destination.transferFrom(source, 0, source.size());
	            source.close();
	            destination.close();
	            
	        } catch(IOException e) {
	        	e.printStackTrace();
	        }
	long uploadTime = 0;
        
		Calendar cur =Calendar.getInstance();
		
	
		
		PendingIntent pi = PendingIntent.getService(this, 0,
		            new Intent(this, UploadService.class),PendingIntent.FLAG_UPDATE_CURRENT);
 
		
		uploadTime=cur.getTimeInMillis()+20*1000;
		AlarmManager alarmManager=(AlarmManager) getSystemService(ALARM_SERVICE);
		alarmManager.set(AlarmManager.RTC_WAKEUP, uploadTime, pi);
	}
	   public void back_button(View view){
	   }

}
