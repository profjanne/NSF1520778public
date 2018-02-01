package com.example.eager_presurvey.UI;



import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.eager_presurvey.BaseActivity;
import com.example.eager_presurvey.R;
import com.example.eager_presurvey.util.Question;


public class Presurvey_10 extends BaseActivity{
	TextView textView[];
	TextView question10;
	final Question question_ten = new Question();
	@SuppressWarnings("static-access")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.presurvey_10);
        question10=(TextView) findViewById(R.id.textView0);
        nextActivity = Presurvey_11.class;
        
        textView = new TextView[]{
    			(TextView) findViewById(R.id.TextView1),
    			(TextView) findViewById(R.id.TextView2),
    			(TextView) findViewById(R.id.TextView3),
    			(TextView) findViewById(R.id.TextView4),
    			(TextView) findViewById(R.id.TextView5),
    			(TextView) findViewById(R.id.TextView6),
    			(TextView) findViewById(R.id.TextView7),
    			(TextView) findViewById(R.id.TextView8),
    			(TextView) findViewById(R.id.TextView9),
    			(TextView) findViewById(R.id.TextView10),
    			(TextView) findViewById(R.id.TextView11),
    			(TextView) findViewById(R.id.TextView12),
    			(TextView) findViewById(R.id.TextView13),
    			(TextView) findViewById(R.id.TextView14),
    			(TextView) findViewById(R.id.TextView15),
    			(TextView) findViewById(R.id.TextView16),
    			(TextView) findViewById(R.id.TextView17),
    			(TextView) findViewById(R.id.TextView18),
    			(TextView) findViewById(R.id.TextView19),
    			(TextView) findViewById(R.id.TextView20),
    			(TextView) findViewById(R.id.TextView21),
    			(TextView) findViewById(R.id.TextView22),
    			(TextView) findViewById(R.id.TextView23),
    			(TextView) findViewById(R.id.TextView24),
    			(TextView) findViewById(R.id.TextView25),
    			(TextView) findViewById(R.id.TextView26),
    			(TextView) findViewById(R.id.TextView27),
    			(TextView) findViewById(R.id.TextView28),
    			(TextView) findViewById(R.id.TextView29),
    			(TextView) findViewById(R.id.TextView30),
    			(TextView) findViewById(R.id.TextView31),
    			(TextView) findViewById(R.id.TextView32),
    			(TextView) findViewById(R.id.TextView33),
    			(TextView) findViewById(R.id.TextView34),
    			(TextView) findViewById(R.id.TextView35),
    			(TextView) findViewById(R.id.TextView36),
    			(TextView) findViewById(R.id.TextView37),
    			(TextView) findViewById(R.id.TextView38),
    			(TextView) findViewById(R.id.TextView39),
    			(TextView) findViewById(R.id.TextView40),
    			(TextView) findViewById(R.id.TextView41),
    			(TextView) findViewById(R.id.TextView42)
    		};
        for (int i=0; i<textView.length; i++){
			final int j=i;
		textView[i].setOnClickListener(new View.OnClickListener() {
       	 
            public void onClick(View v) {
                if (textView[j].isSelected())
                	textView[j].setSelected(false);
                else
                	textView[j].setSelected(true);
            }
        });
		}
        
        final String ClassName = this.getClass().getSimpleName();
		question_ten.setActivityText(ClassName);
		question_ten.setQuestionText(question10.getText().toString());
		this.actQuestionList.add(question_ten);
		
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
    	String answer="";
		   for (int i=0; i<textView.length; i++){
			   if (textView[i].isSelected()) {
				   answer=answer+textView[i].getText()+"|   ";
			   }
		   }
		   if (answer=="")
			   answer = "N.A.";
		   
		   question_ten.setAnswerText(answer);
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