package com.example.eager_presurvey.UI;


import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eager_presurvey.BaseActivity;
import com.example.eager_presurvey.R;
import com.example.eager_presurvey.util.Question;

public class Presurvey_3 extends BaseActivity{
	CheckBox[] checkboxArray=new CheckBox[14];
	EditText question6_other;
	final Question question_six=new Question();
	TextView question6;
	@SuppressWarnings("static-access")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.presurvey_3);
        nextActivity = Presurvey_4.class;
        question6=(TextView) findViewById(R.id.textView1);
        
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
			}
		}
        
        question6_other= (EditText) findViewById(R.id.other1);
        final String ClassName = this.getClass().getSimpleName();
        question_six.setActivityText(ClassName);
		question_six.setQuestionText(question6.getText().toString());
		this.actQuestionList.add(question_six);
        
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
    	String answer="", answerTp2="";
    	boolean flag=true;
    	if (checkboxArray[11].isChecked()){
			
			if (question6_other.getText()==null || question6_other.getText().toString().length()<2){
				flag=false;
			}else{
				answerTp2=checkboxArray[11].getText().toString()+" "+question6_other.getText().toString();
				//locationquestion2.setAnswerText(answerTp);
			}
		}
    	if (!flag){
    		Toast.makeText(getBaseContext(),"Please specify for \"Other:\"", Toast.LENGTH_LONG).show();
    		return;
    	}
    	
		   int counter=0;
		   for (int i=0; i<checkboxArray.length;i++){
			   
			   if (checkboxArray[i].isChecked()){
				   if (counter==0) 
					   answer=answer+checkboxArray[i].getText().toString();
				   else if (i==11)
					   answer=answer+", "+answerTp2;
				   else
					   answer=answer+", "+checkboxArray[i].getText().toString();
				   counter++;
				   
			   }
			   
		   }
		   
		   if (counter==0) 
			   answer="N.A.";
		   question_six.setAnswerText(answer);
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
    
    
}