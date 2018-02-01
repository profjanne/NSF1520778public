package com.example.eager_presurvey.UI;


import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;
import com.example.eager_presurvey.BaseActivity;
import com.example.eager_presurvey.R;
import com.example.eager_presurvey.util.Question;

public class Presurvey_24 extends BaseActivity{
	TextView question63, question64;
	RadioGroup radioGroup1, radioGroup2;
	EditText other1, other2;
	RadioButton radioButton1, radioButton2;
	String other_answer1="", other_answer2="";
	final Question question_sixtythree = new Question(), question_sixtyfour = new Question();
	@SuppressWarnings("static-access")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
		
        super.onCreate(savedInstanceState);
        setContentView(R.layout.presurvey_24);
        nextActivity = Presurvey_27.class;
		
        radioGroup1= (RadioGroup) findViewById(R.id.rGrp1);
		radioGroup2= (RadioGroup) findViewById(R.id.rGrp2);
		other1 = (EditText) findViewById(R.id.other1);
		other2 = (EditText) findViewById(R.id.other2);
		
		
		question63=(TextView) findViewById(R.id.textView3);
		question64=(TextView) findViewById(R.id.textView4);
		

		final String ClassName = this.getClass().getSimpleName();
		question_sixtythree.setActivityText(ClassName);
		question_sixtythree.setQuestionText(question63.getText().toString());
		this.actQuestionList.add(question_sixtythree);
		
		question_sixtyfour.setActivityText(ClassName);
		question_sixtyfour.setQuestionText(question64.getText().toString());
		this.actQuestionList.add(question_sixtyfour);
		
		radioGroup1.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if(checkedId!=-1){
					
					RadioButton selRadBtn = (RadioButton) group.findViewById(checkedId);
					other_answer1 = (String) selRadBtn.getText();
					
				}
			}
		});
		
	
		radioGroup2.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if(checkedId!=-1){
					
					RadioButton selRadBtn = (RadioButton) group.findViewById(checkedId);
					other_answer2 = (String) selRadBtn.getText();
					
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
        	for (int i=0; i<2; i++){
      		   this.actQuestionList.remove(this.actQuestionList.size()-1);
      		   }
 		   this.finish();
        }
        return true;
        
    }
    
    public void next_button(View view){
    	
    	String answer1="N.A.", answer2="N.A.";
    	if (other_answer1.length()>1){
    		if (other_answer1.equals("Yes (please tell me which ones you avoid):")){
    			if ((other1.getText()!=null)&&(other1.getText().toString().length()>1))
    				answer1=other_answer1+": "+other1.getText().toString();
    		}else{
    			answer1=other_answer1;
    		}
    	}
    	question_sixtythree.setAnswerText(answer1);
    	
    	if (other_answer2.length()>1){
    		if (other_answer2.equals("Yes (please tell me which ones you avoid):")){
    			if ((other2.getText()!=null)&&(other2.getText().toString().length()>1))
    				answer2=other_answer2+": "+other2.getText().toString();
    		}else{
    			answer2=other_answer2;
    		}
    	}
    	
    	question_sixtyfour.setAnswerText(answer2);
    	
    	startNext();
    	
	   }
    
    
    @SuppressWarnings("static-access")
	@Override
	   public void onBackPressed(){
	for (int i=0; i<2; i++){
		   this.actQuestionList.remove(this.actQuestionList.size()-1);
		   }
		   this.finish();
	   }
	   @SuppressWarnings("static-access")
	public void back_button(View view){
		   for (int i=0; i<2; i++){
		   this.actQuestionList.remove(this.actQuestionList.size()-1);
		   }
		   this.finish();
	   }
    
	
	
	
}