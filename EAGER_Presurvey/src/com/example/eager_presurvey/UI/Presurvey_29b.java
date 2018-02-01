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

public class Presurvey_29b extends BaseActivity{
	final Question question_seventytwo=new Question(), question_seventythree=new Question(), question_seventyfour=new Question(), question_seventyfive=new Question(),
			question_seventysix=new Question();
	TextView question72, question73, question74, question75, question76;
	RadioGroup radioGroup1, radioGroup2;
	EditText text1, text2, text0;
	@SuppressWarnings("static-access")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.presurvey_29b);
        nextActivity = Presurvey_30.class;
        
        radioGroup1=(RadioGroup) findViewById(R.id.rGrp1);
        radioGroup2=(RadioGroup) findViewById(R.id.rGrp2);
        
        question72= (TextView) findViewById(R.id.TextView0001);
        question73= (TextView) findViewById(R.id.textView1);
        question74= (TextView) findViewById(R.id.textView2);
        question75= (TextView) findViewById(R.id.textView3);
        question76= (TextView) findViewById(R.id.textView4);
     
        
        text0= (EditText) findViewById(R.id.EditText01);
        text1= (EditText) findViewById(R.id.editText1);
        text2= (EditText) findViewById(R.id.editText2);
        
        final String ClassName = this.getClass().getSimpleName();
        question_seventytwo.setActivityText(ClassName);
        question_seventytwo.setQuestionText(question72.getText().toString());
		this.actQuestionList.add(question_seventytwo);
        
        question_seventythree.setActivityText(ClassName);
        question_seventythree.setQuestionText(question73.getText().toString());
		this.actQuestionList.add(question_seventythree);
		
		question_seventyfour.setActivityText(ClassName);
		question_seventyfour.setQuestionText(question74.getText().toString());
		this.actQuestionList.add(question_seventyfour);
        
		question_seventyfive.setActivityText(ClassName);
		question_seventyfive.setQuestionText(question75.getText().toString());
		this.actQuestionList.add(question_seventyfive);
		
		question_seventysix.setActivityText(ClassName);
		question_seventysix.setQuestionText(question76.getText().toString());
		this.actQuestionList.add(question_seventysix);
	
		
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
		
		radioGroup2.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if(checkedId!=-1){
					String answer="";
					RadioButton selRadBtn = (RadioButton) group.findViewById(checkedId);
					answer = (String) selRadBtn.getText();
					question_seventyfour.setAnswerText(answer);
					if (answer.equals("Born in the United States")||answer.equals("Born in a U.S. Territory (for example, Puerto Rico)")){
						text1.setVisibility(View.INVISIBLE);
						text2.setVisibility(View.INVISIBLE);
						question75.setVisibility(View.INVISIBLE);
						question76.setVisibility(View.INVISIBLE);
		        		  
						question_seventyfive.setAnswerText("N.A.");
						question_seventysix.setAnswerText("N.A.");
					}else{
						text1.setVisibility(View.VISIBLE);
						text2.setVisibility(View.VISIBLE);
						question75.setVisibility(View.VISIBLE);
						question76.setVisibility(View.VISIBLE);
		        		  
						question_seventyfive.setAnswerText("");
						question_seventysix.setAnswerText("");
					}
					
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
        	for (int i=0; i<5; i++){
      		   this.actQuestionList.remove(this.actQuestionList.size()-1);
      		   }
 		   this.finish();
        }
        return true;
        
    }
    
    public void next_button(View view){
    	String question75_answer="N.A.", question76_answer="N.A.", question72_answer="N.A.";
    	if ((question_seventyfive.getAnswerText()!=null)&&(question_seventyfive.getAnswerText().equals("N.A."))){
		}else{
    	if ((text1.getText()!=null)&&(text1.getText().toString().length()>1)){
    		question75_answer=text1.getText().toString();
    	}
    	
    	question_seventyfive.setAnswerText(question75_answer);
		}
    	
    	if ((text0.getText()!=null)&&(text0.getText().toString().length()>1)){
    		question72_answer=text0.getText().toString();
    	}
    	
    	question_seventytwo.setAnswerText(question72_answer);
    	
    	if ((question_seventysix.getAnswerText()!=null)&&(question_seventysix.getAnswerText().equals("N.A."))){
		}else{
    	if ((text2.getText()!=null)&&(text2.getText().toString().length()>1)){
    		question76_answer=text2.getText().toString();
    	}
    	
    	question_seventysix.setAnswerText(question76_answer);
		}
    	

    	if ((question_seventythree.getAnswerText()==null) || (question_seventythree.getAnswerText()==""))
    		question_seventythree.setAnswerText("N.A.");
    	

    	if ((question_seventyfour.getAnswerText()==null) || (question_seventyfour.getAnswerText()==""))
    		question_seventyfour.setAnswerText("N.A.");
    	
		   startNext();
	   }
	   
    @SuppressWarnings("static-access")
	public void back_button(View view){
		   for (int i=0; i<5; i++){
	 		   this.actQuestionList.remove(this.actQuestionList.size()-1);
	 		   }
		   this.finish();
	   }
	   
	   @SuppressWarnings("static-access")
	@Override
	   public void onBackPressed(){
 	for (int i=0; i<5; i++){
		   this.actQuestionList.remove(this.actQuestionList.size()-1);
		   }
		   this.finish();
	   }
    
    
}