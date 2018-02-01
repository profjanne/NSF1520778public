package com.example.eager_presurvey.UI;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.eager_presurvey.BaseActivity;
import com.example.eager_presurvey.R;
import com.example.eager_presurvey.util.Question;

public class Presurvey_16 extends BaseActivity{
	CheckBox[] checkboxArray=new CheckBox[20];
	final Question question_fourtythree=new Question();
	TextView question43;
	@SuppressWarnings("static-access")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.presurvey_16);
        nextActivity = Presurvey_17.class;
        question43=(TextView) findViewById(R.id.textView2);
        
        for (int i=0; i<checkboxArray.length;i++){
			switch (i){
			case 0: checkboxArray[i]=(CheckBox) findViewById(R.id.chk1);
			break;
			case 1: checkboxArray[i]=(CheckBox) findViewById(R.id.chk2);
			break;
			case 2: checkboxArray[i]=(CheckBox) findViewById(R.id.chk3);
			break;
			case 3: checkboxArray[i]=(CheckBox) findViewById(R.id.chk4);
			break;
			case 4: checkboxArray[i]=(CheckBox) findViewById(R.id.chk5);
			break;
			case 5: checkboxArray[i]=(CheckBox) findViewById(R.id.chk6);
			break;
			case 6: checkboxArray[i]=(CheckBox) findViewById(R.id.chk7);
			break;
			case 7: checkboxArray[i]=(CheckBox) findViewById(R.id.chk8);
			break;
			case 8: checkboxArray[i]=(CheckBox) findViewById(R.id.chk9);
			break;
			case 9: checkboxArray[i]=(CheckBox) findViewById(R.id.chk10);
			break;
			case 10: checkboxArray[i]=(CheckBox) findViewById(R.id.chk11);
			break;
			case 11: checkboxArray[i]=(CheckBox) findViewById(R.id.chk12);
			break;
			case 12: checkboxArray[i]=(CheckBox) findViewById(R.id.chk13);
			break;
			case 13: checkboxArray[i]=(CheckBox) findViewById(R.id.chk14);
			break;
			case 14: checkboxArray[i]=(CheckBox) findViewById(R.id.chk15);
			break;
			case 15: checkboxArray[i]=(CheckBox) findViewById(R.id.chk16);
			break;
			case 16: checkboxArray[i]=(CheckBox) findViewById(R.id.chk17);
			break;
			case 17: checkboxArray[i]=(CheckBox) findViewById(R.id.chk18);
			break;
			case 18: checkboxArray[i]=(CheckBox) findViewById(R.id.chk19);
			break;
			case 19: checkboxArray[i]=(CheckBox) findViewById(R.id.chk20);
			}
		}
        
        final String ClassName = this.getClass().getSimpleName();
        question_fourtythree.setActivityText(ClassName);
        question_fourtythree.setQuestionText(question43.getText().toString());
		this.actQuestionList.add(question_fourtythree);
		
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
        	
      		   this.actQuestionList.remove(this.actQuestionList.size()-1);
      		   
 		   this.finish();
        }
        return true;
        
    }
    public void next_button(View view){
    	int counter=0;
    	String answer="";
		   for (int i=0; i<checkboxArray.length;i++){
			   
			   if (checkboxArray[i].isChecked()){
				   if (counter==0) 
					   answer=answer+checkboxArray[i].getText().toString();
				   else
					   answer=answer+", "+checkboxArray[i].getText().toString();
				   counter++;
				   
			   }
			   
		   }
		   
		   if (counter==0) 
			   answer="N.A.";
		   question_fourtythree.setAnswerText(answer);
		   startNext();
	   }
	   
    @SuppressWarnings("static-access")
	@Override
	   public void onBackPressed(){
	
		   this.actQuestionList.remove(this.actQuestionList.size()-1);
		   
		   this.finish();
	   }
	   @SuppressWarnings("static-access")
	public void back_button(View view){
		   
		   this.actQuestionList.remove(this.actQuestionList.size()-1);
		   
		   this.finish();
	   }
    
    
}