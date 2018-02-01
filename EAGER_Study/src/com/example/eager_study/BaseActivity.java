package com.example.eager_study;

import java.util.ArrayList;
import com.example.eager_study_util.Question;
import com.example.eager_study_util.QuestionsDataSource;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class BaseActivity extends ActionBarActivity {

	protected Class<?> nextActivity;
	protected QuestionsDataSource datasource;
	protected static ArrayList<Question> actQuestionList = new ArrayList<Question>();
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        datasource = new QuestionsDataSource(this);
        datasource.open();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
 
    
    public void startNext() {

		if(checkAnswered()){
		    Intent intent = new Intent(this, nextActivity);
		    startActivity(intent);
		}
		else{
			Toast.makeText(getBaseContext(), "Please make sure the question is answered", Toast.LENGTH_SHORT).show();
		}
	}
    
    public void back(){
    	super.onBackPressed();
    }
    
    public void addAllQuestions(){
		int count = actQuestionList.size();

		for (int i = 0; i< count; i++){
			Question tempQuestion = new Question();
			tempQuestion = actQuestionList.get(i);

			if (tempQuestion.isEnabled()){
				createQuestion(tempQuestion);
			}
		}

	}
    
    private boolean checkAnswered(){

		int count = actQuestionList.size();

		boolean answered = true;
		Question tempQuestion = new Question();
		for (int i = 0; i< count; i++){
			
			tempQuestion = actQuestionList.get(i);
			
			if (tempQuestion.isEnabled()&&(tempQuestion.getAnswerText().equals(""))){
				answered = false;
				return answered;
			}

		}

		return answered;
	}
    
    public void createQuestion(Question tempQuestion){
		datasource.createQuestionB(tempQuestion);		
	}	

	@Override
	public void onPause(){
		super.onPause();
		datasource.close();
	}

	@Override
	public void onResume(){
		super.onResume();
		datasource.open();
	}
}


