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

public class Presurvey_23 extends BaseActivity{
	private static int kk=-1;
	final Question question_fiftythree=new Question(), question_fiftyfour=new Question();
	TextView question53, question54,instruction;
	RadioGroup radioGroup1, radioGroup2;
	
	private static SharedPreferences settings;
	public static final String PREFS_NAME = "show_test";
	
	@SuppressWarnings("static-access")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
		String image_label=" (imageE)";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.presurvey_23);
        
        settings=getSharedPreferences(PREFS_NAME, MODE_MULTI_PROCESS);
        int show_test = settings.getInt("text", 0);
        instruction= (TextView) findViewById(R.id.TextView0001);
  
        if (show_test!=23){
           instruction.setVisibility(View.GONE);
        }
        
        question53= (TextView) findViewById(R.id.textView2);
        question54= (TextView) findViewById(R.id.textView3);
        int count=0;
        for (int i=0; i<5; i++){
        	if (SharedValues.imageClass[i])
        		count++;
        }
        if (count==0)
        	nextActivity = Presurvey_24.class;
        else{
        	Random rn = new Random();
            int answer= rn.nextInt(count)+1;
            for (int i=0; i<5; i++)
            	if (SharedValues.imageClass[i]){
            		answer--;
            		if (answer==0)
            			kk=i;
            	}
            SharedValues.imageClass[kk]=false;
            switch (kk){
    			case 0: nextActivity = Presurvey_19.class;
    			break;
    			case 1: nextActivity = Presurvey_20.class;
    			break;
    			case 2: nextActivity = Presurvey_21.class;
    			break;
    			case 3: nextActivity = Presurvey_22.class;
    			break;
    			case 4: nextActivity = Presurvey_23.class;
    		}
        }
        
        final String ClassName = this.getClass().getSimpleName();
        question_fiftythree.setActivityText(ClassName);
        question_fiftythree.setQuestionText(question53.getText().toString()+image_label);
		this.actQuestionList.add(question_fiftythree);
		
		question_fiftyfour.setActivityText(ClassName);
		question_fiftyfour.setQuestionText(question54.getText().toString()+image_label);
		this.actQuestionList.add(question_fiftyfour);
		
		radioGroup1=(RadioGroup) findViewById(R.id.rGrp2);
        radioGroup2=(RadioGroup) findViewById(R.id.rGrp3);
        
        radioGroup1.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if(checkedId!=-1){
					String answer="";
					RadioButton selRadBtn = (RadioButton) group.findViewById(checkedId);
					answer = (String) selRadBtn.getText();
					question_fiftythree.setAnswerText(answer);
					
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
					question_fiftyfour.setAnswerText(answer);
					
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
        	if (kk>=0)
     		   SharedValues.imageClass[kk]=true;
 		   this.finish();
        }
        return true;
        
    }
    
    public void next_button(View view){
    	if ((question_fiftythree.getAnswerText()==null) || (question_fiftythree.getAnswerText()==""))
    		question_fiftythree.setAnswerText("N.A.");
    	

    	if ((question_fiftyfour.getAnswerText()==null) || (question_fiftyfour.getAnswerText()==""))
    		question_fiftyfour.setAnswerText("N.A.");
    	
		   startNext();
	   }
	   
    @SuppressWarnings("static-access")
	public void back_button(View view){
		   for (int i=0; i<2; i++){
	 		   this.actQuestionList.remove(this.actQuestionList.size()-1);
	 		   }
		   if (kk>=0)
			   SharedValues.imageClass[kk]=true;
		   this.finish();
	   }
	   
	   @SuppressWarnings("static-access")
	@Override
	   public void onBackPressed(){
	for (int i=0; i<2; i++){
		   this.actQuestionList.remove(this.actQuestionList.size()-1);
		   }
		if (kk>=0)
		   SharedValues.imageClass[kk]=true;
		   this.finish();
	   }
    
    
}