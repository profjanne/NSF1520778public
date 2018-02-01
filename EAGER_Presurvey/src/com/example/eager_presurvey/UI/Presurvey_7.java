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

public class Presurvey_7 extends BaseActivity{
	final Question question_twentysix=new Question(), question_twentyseven=new Question();
	TextView question26, question27;
	RadioGroup radioGroup1, radioGroup2;
	@SuppressWarnings("static-access")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.presurvey_7);
        nextActivity = Presurvey_8.class;
        
        question26= (TextView) findViewById(R.id.textView1);
        question27= (TextView) findViewById(R.id.textView4);
        
        radioGroup1=(RadioGroup) findViewById(R.id.rGrp1);
        radioGroup2=(RadioGroup) findViewById(R.id.rGrp4);
        
        final String ClassName = this.getClass().getSimpleName();
        
        question_twentysix.setActivityText(ClassName);
        question_twentysix.setQuestionText(question26.getText().toString());
		this.actQuestionList.add(question_twentysix);
		
		question_twentyseven.setActivityText(ClassName);
		question_twentyseven.setQuestionText(question27.getText().toString());
		this.actQuestionList.add(question_twentyseven);
		
		radioGroup1.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if(checkedId!=-1){
					String answer="N.A.";
					RadioButton selRadBtn = (RadioButton) group.findViewById(checkedId);
					answer = (String) selRadBtn.getText();
					question_twentysix.setAnswerText(answer);
					
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
					question_twentyseven.setAnswerText(answer);
					
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
    	if ((question_twentysix.getAnswerText()==null) || (question_twentysix.getAnswerText()==""))
    		question_twentysix.setAnswerText("N.A.");
    	
    	if ((question_twentyseven.getAnswerText()==null) || (question_twentyseven.getAnswerText()==""))
    		question_twentyseven.setAnswerText("N.A.");
    	
		   startNext();
	   }
	   
    @SuppressWarnings("static-access")
	public void back_button(View view){
		   for (int i=0; i<2; i++){
	 		   this.actQuestionList.remove(this.actQuestionList.size()-1);
	 		   }
		   this.finish();
	   }
	   @SuppressWarnings("static-access")
	@Override
	   public void onBackPressed(){
	for (int i=0; i<2; i++){
		   this.actQuestionList.remove(this.actQuestionList.size()-1);
		   }
		   this.finish();
	   }
    
    
}
