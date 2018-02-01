package com.example.eager_study_UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.example.eager_study.BaseActivity;
import com.example.eager_study.R;

public class IAT_1a2 extends BaseActivity{
	String first_name=null;
	String[] uncomfortable_words = {"Anxious", "Nervous", "Fearful", "Uncertain", "Afraid", "Edgy", "Tense", "Worked up", "Uneasy", "Guarded"};
	String[] comfortable_words = {"Calm", "Relaxed", "Restful", "At ease", "Outgoing", "Open", "Lively", "Social", "Unafraid", "Friendly"};
	int button_press=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.iat_1a2);
	
		nextActivity = IAT_1b.class;
		
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
		    first_name = extras.getString("first_name");
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
