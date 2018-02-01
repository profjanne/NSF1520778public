package com.example.eager_study_UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.eager_study.BaseActivity;
import com.example.eager_study.R;

public class IAT_1 extends BaseActivity{
	EditText editText;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.iat_1);
		editText= (EditText) findViewById(R.id.editText1);
	
		nextActivity = IAT_1a.class;
		
		
	}
	@Override
	   public boolean onCreateOptionsMenu(Menu menu) {
	      // Inflate the menu; this adds items to the action bar if it is present.
	      getMenuInflater().inflate(R.menu.main, menu);
	      return true;
	   }
	   
	   public void start_button(View view){
		   if (editText.getText()!=null && editText.getText().toString().length()>1) {
			   
			   Intent intent = new Intent(this, nextActivity);
			   intent.putExtra("first_name", editText.getText().toString());
			   startActivity(intent);
		   }else{
			   Toast.makeText(getBaseContext(),"Please enter your first name to participate!", Toast.LENGTH_SHORT).show();
		   }
	   }
	   
	   public void back_button(View view){
		   this.finish();
	   }

}
