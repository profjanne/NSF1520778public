package com.example.eager_study_UI;

import android.content.Context;
import android.os.Bundle;
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
import com.example.eager_study.R;
import com.example.eager_study_util.Question;

public class EMA_formal_21 extends BaseActivity{
	final Question question_twentyone = new Question();
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
		setContentView(R.layout.ema_formal_21);
		nextActivity = EMA_formal_22.class;
		final String ClassName = this.getClass().getSimpleName();
		question_twentyone.setActivityText(ClassName);
		question_twentyone.setQuestionText(getText(R.string.ema_formal_text21).toString());
		this.actQuestionList.add(question_twentyone);
		
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
			   question_twentyone.setAnswerText(String.valueOf(selected+1));
			   startNext();
			   
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
