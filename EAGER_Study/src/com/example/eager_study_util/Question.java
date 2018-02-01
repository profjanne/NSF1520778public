package com.example.eager_study_util;

import android.text.format.Time;

public class Question {

	private int seekPosition = -1;
	
	private boolean enabled = true;
	
	private String questionText = "";
	private String answerText = "";
	private String activityText= "";
	
	private String date = "";
	private String time = "";

	private byte[] image1 = null, image2 = null;



	public void setImage1(byte[] image){
		this.image1=image;
	}
	public void setImage2(byte[] image){
		this.image2=image;
	}
	public byte[] getImage1(){
		return this.image1;
	}
	public byte[] getImage2(){
		return this.image2;
	}
	public int getSeekPosition() {
		return seekPosition;
	}
	
	//Enables setting answertext with int.
	public void setSeekPosition(int seekPosition) {
		this.seekPosition = seekPosition;
		setAnswerText(Integer.toString(seekPosition));
	}

	public String getAnswerText() {
		return answerText;
	}


	public void setAnswerText(String answerText) {
		//set time to time of answer
		Time now = new Time();
		now.setToNow();
		this.setDate(now.format("%m-%d-%Y"));
		this.setTime(now.format("%H:%M:%S"));
	
		
					
		this.answerText = answerText;
	}


	public String getQuestionText() {
		return questionText;
	}

	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}


	public String getActivityText() {
		return activityText;
	}


	public void setActivityText(String activityText) {
		this.activityText = activityText;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}


	public String getDate() {
		return date;
	}


	private void setDate(String date) {
		this.date = date;
	}


	public String getTime() {
		return time;
	}


	private void setTime(String time) {
		this.time = time;
	}
}
