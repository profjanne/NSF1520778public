package com.example.eager_presurvey.UI;


import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.example.eager_presurvey.BaseActivity;
import com.example.eager_presurvey.R;
import com.example.eager_presurvey.util.Question;

public class Presurvey_29 extends BaseActivity{
	final Question question_seventythree=new Question();
	TextView question73;
	RadioGroup radioGroup1;
	
	@SuppressWarnings("static-access")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.presurvey_29);
        nextActivity = Presurvey_29b.class;
        
        radioGroup1=(RadioGroup) findViewById(R.id.rGrp1);
        
        
        question73= (TextView) findViewById(R.id.textView1);
        
        
        final String ClassName = this.getClass().getSimpleName();
        question_seventythree.setActivityText(ClassName);
        question_seventythree.setQuestionText(question73.getText().toString());
		this.actQuestionList.add(question_seventythree);
		
	
		
		radioGroup1.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if(checkedId!=-1){
					String answer="";
					RadioButton selRadBtn = (RadioButton) group.findViewById(checkedId);
					answer = (String) selRadBtn.getText();
					question_seventythree.setAnswerText(answer);
					
				}
			}
		});
		
		

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @SuppressWarnings("static-access")
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
    	switch (item.getItemId()) {
        case android.R.id.home:
        	for (int i=0; i<1; i++){
      		   this.actQuestionList.remove(this.actQuestionList.size()-1);
      		   }
 		   this.finish();
        }
        return true;
        
    }
    
    public void next_button(View view){

    	if ((question_seventythree.getAnswerText()==null) || (question_seventythree.getAnswerText()==""))
    		question_seventythree.setAnswerText("N.A.");
    	
		   startNext();
	   }
	   
    @SuppressWarnings("static-access")
	public void back_button(View view){
		   for (int i=0; i<1; i++){
	 		   this.actQuestionList.remove(this.actQuestionList.size()-1);
	 		   }
		   this.finish();
	   }
	   
	   @SuppressWarnings("static-access")
	@Override
	   public void onBackPressed(){
 	for (int i=0; i<1; i++){
		   this.actQuestionList.remove(this.actQuestionList.size()-1);
		   }
		   this.finish();
	   }
    
    
}