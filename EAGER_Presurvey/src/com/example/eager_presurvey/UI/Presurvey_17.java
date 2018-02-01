package com.example.eager_presurvey.UI;

import java.util.Random;

import android.content.SharedPreferences;
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

public class Presurvey_17 extends BaseActivity{
	final Question question_fourtyfour=new Question(), question_fourtyfive=new Question(), question_fourtysix=new Question(), question_fourtyseven = new Question();
	TextView question44, question45, question46, question47;
	RadioGroup radioGroup1, radioGroup2, radioGroup3, radioGroup4;
	private static int kk=-1;
	
	private static SharedPreferences settings;
	private static SharedPreferences.Editor edit;
	public static final String PREFS_NAME = "show_test";
	@SuppressWarnings("static-access")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.presurvey_17);
        
        settings=getSharedPreferences(PREFS_NAME, MODE_MULTI_PROCESS);
	    edit = settings.edit();
	    
        SharedValues.reset();
        Random rn = new Random();
		int answer = rn.nextInt(5);
		kk=answer;
		SharedValues.imageClass[kk]=false;
		switch (kk){
		case 0: nextActivity = Presurvey_19.class;
		edit.putInt("text", 19);
    	edit.commit();
		break;
		case 1: nextActivity = Presurvey_20.class;
		edit.putInt("text", 20);
    	edit.commit();
		break;
		case 2: nextActivity = Presurvey_21.class;
		edit.putInt("text", 21);
    	edit.commit();
		break;
		case 3: nextActivity = Presurvey_22.class;
		edit.putInt("text", 22);
    	edit.commit();
		break;
		case 4: nextActivity = Presurvey_23.class;
		edit.putInt("text", 23);
    	edit.commit();
		}
		
        radioGroup1=(RadioGroup) findViewById(R.id.rGrp1);
        radioGroup2=(RadioGroup) findViewById(R.id.rGrp2);
        radioGroup3=(RadioGroup) findViewById(R.id.rGrp3);
        radioGroup4=(RadioGroup) findViewById(R.id.rGrp4);
        question44= (TextView) findViewById(R.id.textView1);
        question45= (TextView) findViewById(R.id.textView3);
        question46= (TextView) findViewById(R.id.textView4);
        question47= (TextView) findViewById(R.id.textView5);
        
        final String ClassName = this.getClass().getSimpleName();
        question_fourtyfour.setActivityText(ClassName);
        question_fourtyfour.setQuestionText(question44.getText().toString());
		this.actQuestionList.add(question_fourtyfour);
		
		question_fourtyfive.setActivityText(ClassName);
		question_fourtyfive.setQuestionText(question45.getText().toString());
		this.actQuestionList.add(question_fourtyfive);
        
		question_fourtysix.setActivityText(ClassName);
		question_fourtysix.setQuestionText(question46.getText().toString());
		this.actQuestionList.add(question_fourtysix);
		
		question_fourtyseven.setActivityText(ClassName);
		question_fourtyseven.setQuestionText(question47.getText().toString());
		this.actQuestionList.add(question_fourtyseven);
		
		radioGroup1.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if(checkedId!=-1){
					String answer="N.A.";
					RadioButton selRadBtn = (RadioButton) group.findViewById(checkedId);
					answer = (String) selRadBtn.getText();
					question_fourtyfour.setAnswerText(answer);
					
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
					question_fourtyfive.setAnswerText(answer);
					
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
					question_fourtysix.setAnswerText(answer);
					
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
					question_fourtyseven.setAnswerText(answer);
					
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
        	for (int i=0; i<4; i++){
      		   this.actQuestionList.remove(this.actQuestionList.size()-1);
      		   }
        	SharedValues.imageClass[kk]=true;
 		   this.finish();
        }
        return true;
        
    }

    public void next_button(View view){
  
    	if ((question_fourtyfour.getAnswerText()==null) || (question_fourtyfour.getAnswerText()==""))
    		question_fourtyfour.setAnswerText("N.A.");
    	
    	if ((question_fourtyfive.getAnswerText()==null) || (question_fourtyfive.getAnswerText()==""))
    		question_fourtyfive.setAnswerText("N.A.");
    	
    	if ((question_fourtysix.getAnswerText()==null) || (question_fourtysix.getAnswerText()==""))
    		question_fourtysix.setAnswerText("N.A.");
    	
    	if ((question_fourtyseven.getAnswerText()==null) || (question_fourtyseven.getAnswerText()==""))
    		question_fourtyseven.setAnswerText("N.A.");
    	
    	
		   startNext();
	   }
	   
    @SuppressWarnings("static-access")
	public void back_button(View view){
		   for (int i=0; i<4; i++){
	 		   this.actQuestionList.remove(this.actQuestionList.size()-1);
	 		   }
		   SharedValues.imageClass[kk]=true;
		   this.finish();
	   }
	   
	   @SuppressWarnings("static-access")
	@Override
	   public void onBackPressed(){
 	for (int i=0; i<4; i++){
		   this.actQuestionList.remove(this.actQuestionList.size()-1);
		   }
 	SharedValues.imageClass[kk]=true;
		   this.finish();
	   }
    
    
}