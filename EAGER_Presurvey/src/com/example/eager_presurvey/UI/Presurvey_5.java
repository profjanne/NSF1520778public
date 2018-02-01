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

public class Presurvey_5 extends BaseActivity{
	final Question question_twelve=new Question(), question_thirteen=new Question(), question_fourteen=new Question(), 
			question_fifteen=new Question(), question_sixteen=new Question(), question_seventeen=new Question(), question_eighteen=new Question();
	TextView question12, question13, question14, question15, question16, question17, question18;
	RadioGroup radioGroup1, radioGroup2, radioGroup3, radioGroup4,
		radioGroup5, radioGroup6, radioGroup7;
	@SuppressWarnings("static-access")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.presurvey_5);
        nextActivity = Presurvey_6.class;
        
        question12= (TextView) findViewById(R.id.textView0);
        question13= (TextView) findViewById(R.id.textView1);
        question14= (TextView) findViewById(R.id.textView2);
        question15= (TextView) findViewById(R.id.textView3);
        question16= (TextView) findViewById(R.id.textView4);
        question17= (TextView) findViewById(R.id.textView5);
        question18= (TextView) findViewById(R.id.textView6);
        radioGroup1=(RadioGroup) findViewById(R.id.rGrp0);
        radioGroup2=(RadioGroup) findViewById(R.id.rGrp1);
        radioGroup3=(RadioGroup) findViewById(R.id.rGrp2);
        radioGroup4=(RadioGroup) findViewById(R.id.rGrp3);
        radioGroup5=(RadioGroup) findViewById(R.id.rGrp4);
        radioGroup6=(RadioGroup) findViewById(R.id.rGrp5);
        radioGroup7=(RadioGroup) findViewById(R.id.rGrp6);
        
        final String ClassName = this.getClass().getSimpleName();
        
        question_twelve.setActivityText(ClassName);
        question_twelve.setQuestionText(question12.getText().toString());
		this.actQuestionList.add(question_twelve);
		
		question_thirteen.setActivityText(ClassName);
		question_thirteen.setQuestionText(question13.getText().toString());
		this.actQuestionList.add(question_thirteen);
		
		question_fourteen.setActivityText(ClassName);
		question_fourteen.setQuestionText(question14.getText().toString());
		this.actQuestionList.add(question_fourteen);
        
		question_fifteen.setActivityText(ClassName);
		question_fifteen.setQuestionText(question15.getText().toString());
		this.actQuestionList.add(question_fifteen);
		
		question_sixteen.setActivityText(ClassName);
		question_sixteen.setQuestionText(question16.getText().toString());
		this.actQuestionList.add(question_sixteen);
		
		question_seventeen.setActivityText(ClassName);
		question_seventeen.setQuestionText(question17.getText().toString());
		this.actQuestionList.add(question_seventeen);
		
		question_eighteen.setActivityText(ClassName);
		question_eighteen.setQuestionText(question18.getText().toString());
		this.actQuestionList.add(question_eighteen);
		
		radioGroup1.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if(checkedId!=-1){
					String answer="N.A.";
					RadioButton selRadBtn = (RadioButton) group.findViewById(checkedId);
					answer = (String) selRadBtn.getText();
					question_twelve.setAnswerText(answer);
					
				}
			}
		});
		
		radioGroup2.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if(checkedId!=-1){
					String answer="N.A.";
					RadioButton selRadBtn = (RadioButton) group.findViewById(checkedId);
					answer = (String) selRadBtn.getText();
					question_thirteen.setAnswerText(answer);
					
				}
			}
		});
		
		radioGroup3.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if(checkedId!=-1){
					String answer="N.A.";
					RadioButton selRadBtn = (RadioButton) group.findViewById(checkedId);
					answer = (String) selRadBtn.getText();
					question_fourteen.setAnswerText(answer);
					
				}
			}
		});
		
		radioGroup4.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if(checkedId!=-1){
					String answer="N.A.";
					RadioButton selRadBtn = (RadioButton) group.findViewById(checkedId);
					answer = (String) selRadBtn.getText();
					question_fifteen.setAnswerText(answer);
					
				}
			}
		});
		
		radioGroup5.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if(checkedId!=-1){
					String answer="N.A.";
					RadioButton selRadBtn = (RadioButton) group.findViewById(checkedId);
					answer = (String) selRadBtn.getText();
					question_sixteen.setAnswerText(answer);
					
				}
			}
		});
		
		radioGroup6.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if(checkedId!=-1){
					String answer="N.A.";
					RadioButton selRadBtn = (RadioButton) group.findViewById(checkedId);
					answer = (String) selRadBtn.getText();
					question_seventeen.setAnswerText(answer);
					
				}
			}
		});
		
		radioGroup7.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if(checkedId!=-1){
					String answer="N.A.";
					RadioButton selRadBtn = (RadioButton) group.findViewById(checkedId);
					answer = (String) selRadBtn.getText();
					question_eighteen.setAnswerText(answer);
					
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
        	for (int i=0; i<7; i++){
      		   this.actQuestionList.remove(this.actQuestionList.size()-1);
      		   }
 		   this.finish();
        }
        return true;
        
    }
    
 
    public void next_button(View view){
    	if ((question_twelve.getAnswerText()==null) || (question_twelve.getAnswerText()==""))
    		question_twelve.setAnswerText("N.A.");
    	
    	if ((question_thirteen.getAnswerText()==null) || (question_thirteen.getAnswerText()==""))
    		question_thirteen.setAnswerText("N.A.");
    	
    	if ((question_fourteen.getAnswerText()==null) || (question_fourteen.getAnswerText()==""))
    		question_fourteen.setAnswerText("N.A.");
    	
    	if ((question_fifteen.getAnswerText()==null) || (question_fifteen.getAnswerText()==""))
    		question_fifteen.setAnswerText("N.A.");
    	
    	if ((question_sixteen.getAnswerText()==null) || (question_sixteen.getAnswerText()==""))
    		question_sixteen.setAnswerText("N.A.");
    	
    	if ((question_seventeen.getAnswerText()==null) || (question_seventeen.getAnswerText()==""))
    		question_seventeen.setAnswerText("N.A.");
    	
    	if ((question_eighteen.getAnswerText()==null) || (question_eighteen.getAnswerText()==""))
    		question_eighteen.setAnswerText("N.A.");
    	
		   startNext();
	   }
	   
	   @SuppressWarnings("static-access")
		public void back_button(View view){
			   for (int i=0; i<7; i++){
		 		   this.actQuestionList.remove(this.actQuestionList.size()-1);
		 		   }
			   this.finish();
		   }
	   @SuppressWarnings("static-access")
	   @Override
	   public void onBackPressed(){
    	for (int i=0; i<7; i++){
 		   this.actQuestionList.remove(this.actQuestionList.size()-1);
 		   }
		   this.finish();
	   }
    
}
