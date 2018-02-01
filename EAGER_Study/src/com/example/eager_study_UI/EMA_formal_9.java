package com.example.eager_study_UI;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.eager_study.BaseActivity;
import com.example.eager_study.MainActivity;
import com.example.eager_study.R;
import com.example.eager_study_util.Question;
import com.example.eager_study_util.UploadService;

public class EMA_formal_9 extends BaseActivity{
	final Question question_nine = new Question();
	Integer[] imageIDs = {
            R.drawable.picture1,
            R.drawable.picture2,
            R.drawable.picture3,
            R.drawable.picture4,
            R.drawable.picture5,
            R.drawable.picture6,
            R.drawable.picture7,
            R.drawable.picture8,
            R.drawable.picture9
    };
	private boolean[] thumbnailsselection;
    private int count=9;
    CheckBox[] checkboxArray = new CheckBox[9];
	@SuppressWarnings("static-access")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ema_formal_9);
		nextActivity = MainActivity.class;
		final String ClassName = this.getClass().getSimpleName();
		question_nine.setActivityText(ClassName);
		question_nine.setQuestionText(getText(R.string.ema_formal_text9).toString());
		this.actQuestionList.add(question_nine);
		
		this.thumbnailsselection = new boolean[this.count];
		GridView gridView = (GridView) findViewById(R.id.PhoneImageGrid);
        gridView.setAdapter(new ImageAdapter(this));
 
       
		
	}
	@Override
	   public boolean onCreateOptionsMenu(Menu menu) {
	      // Inflate the menu; this adds items to the action bar if it is present.
	      getMenuInflater().inflate(R.menu.main, menu);
	      return true;
	   }
	   
	   public void next_button(View view){
		   int selected=-1;
		   int count=0;
		   for (int i=0; i<thumbnailsselection.length;i++){
			   if (thumbnailsselection[i]){
				   count++;
				   selected=i;
			   } 
		   }
		   if (count>1)
			   Toast.makeText(getBaseContext(), "Please only select one image!", Toast.LENGTH_SHORT).show();
		   else if (count==1) {
			   question_nine.setAnswerText(String.valueOf(selected+1));
			   addAllQuestions();
				exportDB();
			   Toast.makeText(getApplicationContext(), "Thank you for completing the survey!", 
					   Toast.LENGTH_SHORT).show();
			   startNext();
			   Intent startMain = new Intent(Intent.ACTION_MAIN);
			   startMain.addCategory(Intent.CATEGORY_HOME);
			   startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			   startActivity(startMain);
		   } else{
			   Toast.makeText(getBaseContext(), "Please select one image!", Toast.LENGTH_SHORT).show();
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
	   
	   public class ImageAdapter extends BaseAdapter 
	    {
		   private LayoutInflater mInflater;
	        @SuppressWarnings("unused")
			private Context context;
	 
	        public ImageAdapter(Context c) 
	        {
	        	
	            mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	            
	            context = c;
	        }
	 
	        //---returns the number of images---
	        public int getCount() {
	            return imageIDs.length;
	        }
	 
	        //---returns the ID of an item--- 
	        public Object getItem(int position) {
	            return position;
	        }
	 
	        public long getItemId(int position) {
	            return position;
	        }
	 
	        //---returns an ImageView view---
	        public View getView(int position, View convertView, ViewGroup parent) 
	        {
	        	ViewHolder holder;
	           
	            if (convertView == null) {
	            	holder = new ViewHolder();
	                convertView = mInflater.inflate(
	                        R.layout.galleryitem, null);
	                holder.imageview = (ImageView) convertView.findViewById(R.id.thumbImage);
	                holder.checkbox = (CheckBox) convertView.findViewById(R.id.itemCheckBox);
	                
	                if (checkboxArray[position]==null)
	                	checkboxArray[position]=holder.checkbox;
	               
	                convertView.setTag(holder);
	        
	                holder.imageview.setPadding(5, 5, 5, 5);
	            } else {
	                
	            	holder = (ViewHolder) convertView.getTag();
	            }
	            holder.checkbox.setId(position);
	            holder.imageview.setId(position);
	           
	            holder.checkbox.setOnClickListener(new View.OnClickListener() {
	            	 
	                public void onClick(View v) {
	               
	                    CheckBox cb = (CheckBox) v;
	                    int id = cb.getId();
	                    if (thumbnailsselection[id]){
	                        cb.setChecked(false);
	                        thumbnailsselection[id] = false;
	                    } else {
	                        cb.setChecked(true);
	                        thumbnailsselection[id] = true;
	                    }
	                }
	            });
	            
	            holder.imageview.setOnClickListener(new View.OnClickListener() {
	            	 
	                public void onClick(View v) {
	                    ImageView imageView1 = (ImageView) v;
	                	int id = imageView1.getId();
	                	if (thumbnailsselection[id]){
	                        checkboxArray[id].setChecked(false);
	                        thumbnailsselection[id] = false;
	                    } else {
	                    	checkboxArray[id].setChecked(true);
	                    	thumbnailsselection[id] = true;
	                    }
	                }
	            });
	            
	            
	            holder.imageview.setImageResource(imageIDs[position]);
	            holder.checkbox.setChecked(thumbnailsselection[position]);
	            holder.id = position;
	            return convertView;
	        }
	    }    
	   class ViewHolder {
	        ImageView imageview;
	        CheckBox checkbox;
	        int id;
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
	 
			
			uploadTime=cur.getTimeInMillis()+2*60*1000;
			AlarmManager alarmManager=(AlarmManager) getSystemService(ALARM_SERVICE);
			alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, uploadTime,
					3*60*60*1000, pi);
			
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
