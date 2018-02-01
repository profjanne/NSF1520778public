package com.example.eager_study_util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class MySQLiteHelper extends SQLiteOpenHelper{


	private static MySQLiteHelper sInstance;

	private static final String DATABASE_NAME = "questions.db";
	private static final int DATABASE_VERSION = 1;

	public static final String TABLE_QUESTIONS = "questions";
	public static final String COLUMN_GUID = "UUID";
	public static final String COLUMN_ID = "userid";
	
	public static final String COLUMN_DATE = "date";
	public static final String COLUMN_TIME= "time";
	
	public static final String COLUMN_ACTIVITY = "activity";
	public static final String COLUMN_QUESTION = "question";
	public static final String COLUMN_ANSWER = "answer";
	public static final String COLUMN_IMAGE1 = "image1";
	public static final String COLUMN_IMAGE2 = "image2";
	
	public static final String TABLE_LOCATION = "location";
	

	
	
	private static final String DATABASE_CREATE_LOCATION = "create table "
	+ TABLE_LOCATION	+ " (" 
	+ "id INTEGER PRIMARY KEY, "
	+ "time datetime default current_timestamp, "
	+ COLUMN_ID + " text not null, "
	+ "lat real, lon real);";

	private static final String DATABASE_CREATE_IAT = "create table "
			+ "iat"	+ " (" 
			+ "id INTEGER PRIMARY KEY, "
			+ "time datetime default current_timestamp, "
			+ COLUMN_ID + " text not null, "
			+ "iat_group real, "
			+ "lat real, "
			+ "lon real, "
			+ "result text not null);";
	
	private static final String DATABASE_CREATE = "create table "
	+ TABLE_QUESTIONS	+ " (" 
	+ COLUMN_GUID		+ " text not null, "
	+ COLUMN_ID 		+ " text not null, "
	+ COLUMN_DATE		+ " text not null, "  
	+ COLUMN_TIME		+ " text not null, "  
	+ COLUMN_ACTIVITY	+ " text not null, "  
	+ COLUMN_QUESTION	+ " text not null, "  
	+ COLUMN_ANSWER		+ " text, "
	+ COLUMN_IMAGE1     + " mediumblob, "
	+ COLUMN_IMAGE2     + " mediumblob"
	+ ");";
	public MySQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	public static MySQLiteHelper getInstance(Context context){
		if (sInstance == null) {
	      sInstance = new MySQLiteHelper(context.getApplicationContext());
	    }
	    return sInstance;
		
		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DATABASE_CREATE);
		db.execSQL(DATABASE_CREATE_LOCATION);
		db.execSQL(DATABASE_CREATE_IAT);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(MySQLiteHelper.class.getName(),
		        "Upgrading database from version " + oldVersion + " to "
		            + newVersion + ", which will destroy all old data");
		    db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTIONS);
		    onCreate(db);
	}
}
