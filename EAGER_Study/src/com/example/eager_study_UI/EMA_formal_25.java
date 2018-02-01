package com.example.eager_study_UI;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eager_study.BaseActivity;
import com.example.eager_study.MainActivity;
import com.example.eager_study.R;
import com.example.eager_study_util.Question;
import com.example.eager_study_util.UploadService;

public class EMA_formal_25 extends BaseActivity{

	final Question question_twentyfive = new Question();
	private Uri fileUri;
	public static final int MEDIA_TYPE_IMAGE = 1;
	private TextView textView;
	private Button photo_button;
	private int picture_count=0;
	private File image_file;
	static SharedPreferences settings;
	private String timeStamp, UID;
	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	@SuppressWarnings("static-access")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ema_formal_25);
		textView = (TextView)findViewById(R.id.textView1);
		textView.setVisibility(View.INVISIBLE);
		photo_button = (Button)findViewById(R.id.take_photo);
		nextActivity = MainActivity.class;
		final String ClassName = this.getClass().getSimpleName();
		question_twentyfive.setActivityText(ClassName);
		question_twentyfive.setQuestionText(getText(R.string.ema_formal_text25).toString());
		this.actQuestionList.add(question_twentyfive);
		
		settings=getSharedPreferences("TEST", 0);
	    UID=settings.getString("UID", "ERROR");
	}
	@Override
	   public boolean onCreateOptionsMenu(Menu menu) {
	      // Inflate the menu; this adds items to the action bar if it is present.
	      getMenuInflater().inflate(R.menu.main, menu);
	      return true;
	   }
	   
	   public void next_button(View view){
		   if ((question_twentyfive.getAnswerText()==null)||(question_twentyfive.getAnswerText().length()<=0)){
			   question_twentyfive.setAnswerText("0");
		   }
		   if ((question_twentyfive.getAnswerText()!=null)&&(question_twentyfive.getAnswerText().length()>0)){
			   addAllQuestions();
				exportDB();
			   Toast.makeText(getApplicationContext(), "Thank you for completing the survey!", 
					   Toast.LENGTH_SHORT).show();
			   startNext();
			   Intent startMain = new Intent(Intent.ACTION_MAIN);
			   startMain.addCategory(Intent.CATEGORY_HOME);
			   startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			   startActivity(startMain);
		   }else{
			   Toast.makeText(getApplicationContext(), "Please take at least one photo.", 
					   Toast.LENGTH_SHORT).show();
		   }
	   }
	   
	   public void take_photo_button(View view){
		   
		   Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
			  fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE); // create a file to save the image

			  intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name
				// start the image capture Intent
			  startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
			  
			
	   }
	   public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
	        int width = image.getWidth();
	        int height = image.getHeight();

	        float bitmapRatio = (float)width / (float) height;
	        if (bitmapRatio > 0) {
	            width = maxSize;
	            height = (int) (width / bitmapRatio);
	        } else {
	            height = maxSize;
	            width = (int) (height * bitmapRatio);
	        }
	        return Bitmap.createScaledBitmap(image, width, height, true);
	    }
	   @Override
	   protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		   
		   if (resultCode == RESULT_OK) {
			   ProgressDialog dialog = new ProgressDialog(this); // this = YourActivity
			   dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			   dialog.setMessage("Loading. Please wait...");
			   dialog.setIndeterminate(true);
			   dialog.setCanceledOnTouchOutside(false);
			   dialog.show();
			   picture_count = picture_count + 1;
				if (picture_count<2){
						question_twentyfive.setAnswerText(String.valueOf(picture_count));
						if (image_file.exists()){
							Bitmap bMap1 = BitmapFactory.decodeFile(image_file.getAbsolutePath());
							if (bMap1.getHeight()>500 || bMap1.getWidth()>500){
								bMap1 = getResizedBitmap(bMap1, 500);
							}
							File file = new File (image_file.getAbsolutePath());
						    if (file.exists ()) file.delete (); 
						    try {
						           FileOutputStream out1 = new FileOutputStream(file);
						           bMap1.compress(Bitmap.CompressFormat.JPEG, 90, out1);
						           out1.flush();
						           out1.close();

						    } catch (Exception e) {
						           e.printStackTrace();
						    }
							try{
								ByteArrayOutputStream stream = new ByteArrayOutputStream();
								bMap1.compress(Bitmap.CompressFormat.JPEG, 100, stream);
								stream.flush();
								byte[] imageInByte = stream.toByteArray();
								question_twentyfive.setImage1(imageInByte);
						    } catch (Exception e){
						    	e.printStackTrace();
						    }
						}
				}else if (picture_count==2){
					textView.setVisibility(View.VISIBLE);
					photo_button.setEnabled(false);
					question_twentyfive.setAnswerText(String.valueOf(picture_count));
					if (image_file.exists()){
						Bitmap bMap2 = BitmapFactory.decodeFile(image_file.getAbsolutePath());
						if (bMap2.getHeight()>500 || bMap2.getWidth()>500){
							bMap2 = getResizedBitmap(bMap2, 500);
						}
						File file = new File (image_file.getAbsolutePath());
						
					    if (file.exists ()) file.delete (); 
					    try {
					    	   FileOutputStream out1 = new FileOutputStream(file);
					           bMap2.compress(Bitmap.CompressFormat.JPEG, 90, out1);
					           out1.flush();
					           out1.close();
					           
					     
					    } catch (Exception e) {
					           e.printStackTrace();
					    }
					    try{
						ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
						bMap2.compress(Bitmap.CompressFormat.JPEG, 100, stream2);
						stream2.flush();
						byte[] imageInByte2 = stream2.toByteArray();
						question_twentyfive.setImage2(imageInByte2);
					    } catch (Exception e){
					    	e.printStackTrace();
					    }
					}
				}
				
				dialog.dismiss();
		   }
		   
		   
	   }
	 
		   
	   
	   private  Uri getOutputMediaFileUri(int type) {
			image_file = getOutputMediaFile(type);
			return Uri.fromFile(image_file);
		}
		private  File getOutputMediaFile(int type) {
			
			File mediaStorageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

			// Create the storage directory if it does not exist
			if (!mediaStorageDir.exists()) {
				if (!mediaStorageDir.mkdirs()) {
					Log.d("EMA_formal_25", "failed to create directory");
					return null;
				}
			}

			// Create a media file name
			timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
			File mediaFile;
			if (type == MEDIA_TYPE_IMAGE) {
				String name=mediaStorageDir.getPath() + File.separator
						+ "IMG_EAGER_P" +UID+"_"+ timeStamp + ".jpg";
				mediaFile = new File(name);
				
			} else {
				return null;
			}
			Toast.makeText(getBaseContext(),mediaStorageDir.getPath() ,Toast.LENGTH_SHORT).show();

			return mediaFile;
		}
	   @SuppressWarnings("static-access")
	public void back_button(View view){

		   this.actQuestionList.remove(this.actQuestionList.size()-1);
		   this.finish();
	   }
	   private void exportDB(){
			File sd = Environment.getExternalStorageDirectory();
		       File data = Environment.getDataDirectory();
		       FileChannel source=null;
		       FileChannel destination=null;
		       String currentDBPath = "/data/com.example.eager_study/databases/questions.db";
		       String backupDBPath = "sociologysurvey.db";
		       File currentDB = new File(data, currentDBPath);
		       File backupDB = new File(sd, backupDBPath);
		       try {
		            source = new FileInputStream(currentDB).getChannel();
		            destination = new FileOutputStream(backupDB).getChannel();
		            destination.transferFrom(source, 0, source.size());
		            source.close();
		            destination.close();
		            
		        } catch(IOException e) {
		        	e.printStackTrace();
		        }
		long uploadTime = 0;
	        
			Calendar cur =Calendar.getInstance();
			
		
			
			PendingIntent pi = PendingIntent.getService(this, 0,
			            new Intent(this, UploadService.class),PendingIntent.FLAG_UPDATE_CURRENT);
	 
			
			uploadTime=cur.getTimeInMillis()+20*1000;
			AlarmManager alarmManager=(AlarmManager) getSystemService(ALARM_SERVICE);
			alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, uploadTime,
					3*60*60*1000, pi);
		}
	   
	   @SuppressWarnings("static-access")
	@Override
	   public void onBackPressed(){
		   this.actQuestionList.remove(this.actQuestionList.size()-1);
		   this.finish();
	   }
	   @SuppressWarnings("static-access")
	@Override
       public boolean onOptionsItemSelected(MenuItem item) {
               switch (item.getItemId()) {
               case android.R.id.home:
            	   this.actQuestionList.remove(this.actQuestionList.size()-1);
        		   this.finish();
               }
               return true;
       }
	   
}