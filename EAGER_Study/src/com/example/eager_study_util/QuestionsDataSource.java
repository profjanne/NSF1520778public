package com.example.eager_study_util;

import java.util.UUID;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


public class QuestionsDataSource {

	// Database fields
	private SQLiteDatabase database;
	private MySQLiteHelper dbHelper;
	static SharedPreferences settings;
	private Context mContext;


	public QuestionsDataSource(Context context) {
		dbHelper = MySQLiteHelper.getInstance(context);
		mContext = context;
	}


	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}


	public void deleteDB() {		    

		dbHelper.onUpgrade(database, 1, 2);
		
	}


	public String getAnswer(String activity, String question){

		String queryStringClause = MySQLiteHelper.COLUMN_ACTIVITY + " = " + "?" 
				+ " AND " + MySQLiteHelper.COLUMN_QUESTION + " = " + "?";

		String[] queryStringArgs = {activity , question};

		String[] columns = {MySQLiteHelper.COLUMN_ANSWER};

		Cursor returnCursor = database.query(MySQLiteHelper.TABLE_QUESTIONS, columns, 
				queryStringClause, queryStringArgs, null, null, null);
		returnCursor.moveToFirst();

		String answerVal = returnCursor.getString(
				returnCursor.getColumnIndex(MySQLiteHelper.COLUMN_ANSWER));

		Log.d("returned value", answerVal);



		return answerVal;
	}


	
	private String getUserID(Context ctx){
		settings=ctx.getSharedPreferences("TEST", 4);
		String idString = settings.getString("UID", "ERROR");
		
		return idString;
	}
	
	
	public void createQuestionB(Question tempQuestion) {
		
		String activity = tempQuestion.getActivityText();
		String question = tempQuestion.getQuestionText();
		String answer = tempQuestion.getAnswerText();
		String date = tempQuestion.getDate();
		String time = tempQuestion.getTime();
		byte[] image1 = tempQuestion.getImage1();
		byte[] image2 = tempQuestion.getImage2();

		createQuestion(activity,question,answer,date,time, image1, image2);
	}



	private void createQuestion(String activity, String question, String answer,String date, String time, byte[] image1, byte[] image2) {
		
		
		
		ContentValues values = new ContentValues();
		
		values.put(MySQLiteHelper.COLUMN_GUID,getUUID());
		values.put(MySQLiteHelper.COLUMN_ID,getUserID(mContext));
		
		values.put(MySQLiteHelper.COLUMN_DATE,date);
		values.put(MySQLiteHelper.COLUMN_TIME,time);
		
		values.put(MySQLiteHelper.COLUMN_ACTIVITY, activity);
		values.put(MySQLiteHelper.COLUMN_QUESTION, question);
		values.put(MySQLiteHelper.COLUMN_ANSWER, answer);
		values.put(MySQLiteHelper.COLUMN_IMAGE1, image1);
		values.put(MySQLiteHelper.COLUMN_IMAGE2, image2);
		//check for existing entry

		Log.d("dbquery",answer);

		database.beginTransaction();
		try
		{

		
		//insert new row
		long insertId = database.insert(MySQLiteHelper.TABLE_QUESTIONS, null,
				values);
		Log.d("dbaddrow", Long.toString(insertId));

		database.setTransactionSuccessful();
		}
		finally
		{
			database.endTransaction();
		}




	}


	private String getUUID() {
		
		return UUID.randomUUID().toString();
		}




}
