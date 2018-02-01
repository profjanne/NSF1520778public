package com.example.eager_study_util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManagerFactory;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.util.Log;

public class SendPacket {
	    
	    public static int idx=0;
	    public static int lastpos=0;
	    private SharedPreferences sharedPreferences; 
	    private SharedPreferences.Editor editor; 
	    public static final String URL = "https://209.40.204.206/FlaskApp/database/xianyi";
	 
	    private static final String CLIENT_KET_PASSWORD = "tlstest";

	    private static final String CLIENT_TRUST_PASSWORD = "tlstest";

	    private static final String CLIENT_AGREEMENT = "TLS";

	    private static final String CLIENT_KEY_MANAGER = "X509";

	    private static final String CLIENT_TRUST_MANAGER = "X509";

	    private static final String CLIENT_KEY_KEYSTORE = "BKS";

	    private static final String CLIENT_TRUST_KEYSTORE = "BKS";
	    
	    
	    private static final String SQL_GET_TABLES = "select name from sqlite_master where name not in "
				+ "(\"android_metadata\", \"sqlite_sequence\") "
				+ "and name not like \"sqlite_%\"";
	    
	    private AssetManager mAssetManager = null;

	    private Context mcontext;
	    @SuppressWarnings("unused")
		private String SQLite_NAME;
	  
	    @SuppressWarnings("unused")
		private String imei=null;
	    
	    private String dbname;
	    public SendPacket(Context mcontext,String dbname){
	    	this.mcontext=mcontext;
	    	this.mAssetManager=mcontext.getAssets();
	    	this.sharedPreferences=mcontext.getSharedPreferences("Position",0);
	    	editor=mcontext.getSharedPreferences("Position",0).edit();
	    	this.SQLite_NAME="/storage/sdcard0/"+dbname+".db";
	    	this.dbname=dbname;
	    	TelephonyManager telephonyManager = (TelephonyManager) mcontext.getSystemService(Context.TELEPHONY_SERVICE);
			imei =  md5(telephonyManager.getDeviceId());
	    }
	  
	 
	    private String md5(String in) {
			MessageDigest digest;
			try {
			digest = MessageDigest.getInstance("MD5");
			digest.reset();
			digest.update(in.getBytes());
			byte[] a = digest.digest();
			int len = a.length;
			StringBuilder sb = new StringBuilder(len << 1);
			for (int i = 0; i < len; i++) {
			sb.append(Character.forDigit((a[i] & 0xf0) >> 4, 16));
			sb.append(Character.forDigit(a[i] & 0x0f, 16));
			}
			return sb.toString();
			} catch (NoSuchAlgorithmException e) { e.printStackTrace(); }
			return null;
			}
	    
	    
	    public void send(){
	    	CheckInternetTask task=new CheckInternetTask();
	    	task.execute();
	    }
	    
	    
	public boolean isNetworkAavailable() {
     ConnectivityManager cm =
         (ConnectivityManager) mcontext.getSystemService(Context.CONNECTIVITY_SERVICE);
     NetworkInfo netInfo = cm.getActiveNetworkInfo();
     if (netInfo != null && netInfo.isConnected()) {
         return true;
     }
     return false;
 }
	
	private class GetXMLTask extends AsyncTask<String, Void, String> {
     @Override
     protected String doInBackground(String... urls) {
         String output = null;
         for (String url : urls) {
             output = getOutputFromUrl(url);
         }
         
         System.out.println(output);
         return output;
     }
     
     private String getOutputFromUrl(String url) {
         StringBuffer output = new StringBuffer("");
         try {
             InputStream stream = getHttpConnection(url);
             BufferedReader buffer = new BufferedReader(
                     new InputStreamReader(stream));
             String s = "";
             while ((s = buffer.readLine()) != null)
                 output.append(s);
         } catch (IOException e1) {
        	 Log.e("Error-stream", e1.getMessage());
             e1.printStackTrace();
         }
         return output.toString();
     }

     // Makes HttpURLConnection and returns InputStream
     private InputStream getHttpConnection(String urlString)
             throws IOException {
         InputStream stream = null;
         URL url=null;
         HttpsURLConnection httpConnection = null;

         try {          	
   
             SSLContext sslContext = SSLContext.getInstance(CLIENT_AGREEMENT);
          
             KeyManagerFactory keyManager = KeyManagerFactory
                 .getInstance(CLIENT_KEY_MANAGER);
            
             TrustManagerFactory trustManager = TrustManagerFactory
                 .getInstance(CLIENT_TRUST_MANAGER);

          
             KeyStore keyKeyStore = KeyStore.getInstance(CLIENT_KEY_KEYSTORE);
             KeyStore trustKeyStore = KeyStore
                 .getInstance(CLIENT_TRUST_KEYSTORE);

             InputStream is = mAssetManager.open("xenpub.bks");
             
             keyKeyStore.load(is, CLIENT_KET_PASSWORD.toCharArray());
             is.reset();
             
             trustKeyStore.load(is, CLIENT_TRUST_PASSWORD.toCharArray());
             is.close();

            
             keyManager.init(keyKeyStore, CLIENT_KET_PASSWORD.toCharArray());
             trustManager.init(trustKeyStore);
 
             sslContext.init(keyManager.getKeyManagers(),
                 trustManager.getTrustManagers(), null);
         	
             
         	
         	
         	
         	
         	
             url = new URL(urlString);
             httpConnection= (HttpsURLConnection) url.openConnection();
             
             httpConnection.setSSLSocketFactory(sslContext.getSocketFactory());
             httpConnection.setHostnameVerifier(new TrustAnyHostnameVerifier());
          
             
             httpConnection.setDoInput(true);
             httpConnection.setDoOutput(true);
             httpConnection.setRequestProperty("Accept", "*/*");
             httpConnection.setRequestProperty("Pragma", "No-cache");
             httpConnection.setRequestProperty("Cache-Control", "no-cache");
             httpConnection.setRequestProperty("connection", "keep-alive");
             httpConnection.setRequestProperty("accept-charset", "UTF-8");
             httpConnection.setRequestProperty("Content-Type", "application/xml");

             httpConnection.setConnectTimeout(3*60*1000);
             httpConnection.setReadTimeout(60000);

             httpConnection.setRequestMethod("POST");
             httpConnection.connect();
             
             
             PrintWriter out = new PrintWriter(httpConnection.getOutputStream(), true);
             out.println("<?xml version='1.0' encoding='UTF-8'?>");

             out.print("<database name=\'"+dbname+"\'>");
             SQLiteDatabase db=null;
             Cursor c = null;
             try {
            	 File sd = Environment.getExternalStorageDirectory();
            	 String backupDBPath = "sociologysurvey.db";
            	 File name = new File(sd, backupDBPath);

             db = SQLiteDatabase.openOrCreateDatabase(name, null);

             Cursor tableCursor=db.rawQuery(SQL_GET_TABLES, null);
             while(tableCursor.moveToNext()){
     			String tableName = tableCursor
     				.getString(tableCursor.getColumnIndex("name"));

     			System.out.println(tableName);
     			if (tableName.equals("questions")){
     				System.out.println("I AM HERE 1");
     				c = db.rawQuery("select UUID, userid, date, time, activity, question, answer from " + tableName, null);
         			
     			} else {
     				c = db.rawQuery("select * from " + tableName, null);
     			}

				String[] columnNames = c.getColumnNames();
				int numCols=columnNames.length;
				System.out.println(c.getCount());
				idx=sharedPreferences.getInt(tableName, 0);
				System.out.println(idx);
				if(c.moveToFirst()){
					out.print("<table name=\'" +tableName+"\'>");	
			
				c.move(-1);
				c.move(idx);

				while (c.moveToNext()) {
					
					out.print("<item>"); 
					String uuid=null;
					for(int col=0;col<numCols;col++){
						
						out.print("<col name=\'"+columnNames[col]+"\'>");
						int type=c.getType(col);
						if(type==1){
							out.print(c.getInt(col));
							
						}
						else if(type==2){
							out.print(c.getDouble(col));
						}
						else if(type==3){
							String s=c.getString(col);
							if (col == 0){
								uuid = s;
							}
							if(s.contains("&")){
								s=s.replace("&", "&amp;");
								Log.i("AMPERSAND", s);
							}
							if(s.contains("<"))
								s=s.replace("<", "&lt;");
							if(s.contains(">"))
								s=s.replace(">", "&gt;");
							if(s.contains("\""))
								s.replace("\"", "&quot;");
							if(s.contains("'"))
								s.replace("'", "&apos;");
							out.print(s);
						}
						
						out.println("</col>");
					}
					if (tableName.equals("questions")){
						System.out.println("I AM HERE 2");
	     				Cursor c2 = db.rawQuery("select image1 from " + tableName+" where UUID = '"+uuid+"';", null);
	     				Cursor c3 = db.rawQuery("select image2 from " + tableName+" where UUID = '"+uuid+"';", null);
	     				out.print("<col name=\'"+"image1"+"\'>");
	     				if (c2.moveToFirst()){
	     				if (c2.getType(0)==4 ){
	     					Log.d("Check", "ready to print blob "+c2.getBlob(0).length);
	     					byte[] bytearray1 = c2.getBlob(0);
							
							char[] HEX_CHARS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
								      'B', 'C', 'D', 'E', 'F' };
							char[] hexChars = new char[bytearray1.length * 2];
							int v;
						    for (int j = 0; j < bytearray1.length; j++) {
						      v = bytearray1[j] & 0xFF;
						      hexChars[j * 2] = HEX_CHARS[v >>> 4];
						      hexChars[j * 2 + 1] = HEX_CHARS[v & 0x0F];
						    }
						    
						    String encodedImage = new String(hexChars);
							out.print(encodedImage);
							System.out.println(encodedImage);
							System.out.println(encodedImage.length());
							
	     				}
	     				}
	     				out.println("</col>");
	     				out.print("<col name=\'"+"image2"+"\'>");
	     				if (c3.moveToFirst()){
	     				if (c3.getType(0)==4){
	     					Log.d("Check", "ready to print blob "+c3.getBlob(0).length);
	     					byte[] bytearray1 = c3.getBlob(0);
							
							char[] HEX_CHARS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
								      'B', 'C', 'D', 'E', 'F' };
							char[] hexChars = new char[bytearray1.length * 2];
							int v;
						    for (int j = 0; j < bytearray1.length; j++) {
						      v = bytearray1[j] & 0xFF;
						      hexChars[j * 2] = HEX_CHARS[v >>> 4];
						      hexChars[j * 2 + 1] = HEX_CHARS[v & 0x0F];
						    }
						    
						    String encodedImage = new String(hexChars);
						   
							out.print(encodedImage);
							System.out.println(encodedImage);
							System.out.println(encodedImage.length());
							Log.d("Check", "finished printing blob");
	     				}
	     				}
	     				out.println("</col>");
	     			}
					out.println("</item>");
				}
				out.println("</table>");
				}
				idx=c.getCount();
				editor.putInt(tableName, c.getCount());
				editor.commit();
				c.close();
             }
             out.println("</database>");
             tableCursor.close();
				out.flush();
				out.close();
 
             
         }catch (Exception e) {
     			Log.e("Error", "Erroryy", e);
     		} finally {
     			if(c!=null)
     				c.close();
     			if (db != null)
     				db.close();
     		}
             
             
             
             System.out.println("Response Code : " + httpConnection.getResponseCode());
             
             if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                 stream = httpConnection.getInputStream();
                 
                 System.out.println(stream.toString());
             }
             else{
            	 stream = httpConnection.getErrorStream();
            	 String result = getStringFromInputStream(stream);
            	 
         		System.out.println(result);
         		System.out.println("Done");
             }
         }   catch (MalformedURLException e)
         {
             e.printStackTrace();
         }
         catch (IOException e)
         {
             e.printStackTrace();
         }
         catch (NoSuchAlgorithmException e)
         {
             e.printStackTrace();
         }
         catch (KeyManagementException e)
         {
             e.printStackTrace();
         }
         catch (KeyStoreException e)
         {
             e.printStackTrace();
         }
         catch (CertificateException e)
         {
             e.printStackTrace();
         }
         catch (UnrecoverableKeyException e)
         {
             e.printStackTrace();
         }catch(Exception e){
        	 e.printStackTrace();
         }
         finally
         {
           // do nothing
         }
         return stream;
     }
     
     @Override
     protected void onPostExecute(String output) {
     }
     
     public class TrustAnyHostnameVerifier implements HostnameVerifier{

			@Override
			public boolean verify(String hostname, SSLSession session) {
				return true;
			}
     	
     }
     
     @SuppressWarnings("unused")
	public boolean isNetworkAavailable(Context ctx) {

         NetworkInfo info = (NetworkInfo) ((ConnectivityManager) ctx
                 .getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();

         if (info == null || !info.isConnected()) {
             return false;
         }
         if (info.isRoaming()) {
             return false;
         }
         return true;
     }
     
     private  String getStringFromInputStream(InputStream is) {
    	 
 		BufferedReader br = null;
 		StringBuilder sb = new StringBuilder();
  
 		String line;
 		try {
  
 			br = new BufferedReader(new InputStreamReader(is));
 			while ((line = br.readLine()) != null) {
 				sb.append(line);
 			}
  
 		} catch (IOException e) {
 			e.printStackTrace();
 		} finally {
 			if (br != null) {
 				try {
 					br.close();
 				} catch (IOException e) {
 					e.printStackTrace();
 				}
 			}
 		}
  
 		return sb.toString();
  
 	}
 }
	
	class CheckInternetTask extends AsyncTask<Void, Void, Boolean> {

	    protected void onPostExecute(Boolean result) {
	    	if(result){
	    		GetXMLTask task = new GetXMLTask();
	            task.execute(new String[] { URL });
	    	}
	    }

		@Override
		protected Boolean doInBackground(Void... params) {
			if (isNetworkAavailable()) {
		        try {
		        	HttpURLConnection urlc = (HttpURLConnection) 
		                    (new URL("http://clients3.google.com/generate_204")
		                    .openConnection());
		                urlc.setRequestProperty("User-Agent", "Android");
		                urlc.setRequestProperty("Connection", "close");
		                urlc.setConnectTimeout(1500); 
		                urlc.connect();
		                return (urlc.getResponseCode() == 204 &&
		                            urlc.getContentLength() == 0);
		        } catch (IOException e) {
		            Log.e("network error", "Error checking internet connection", e);
		        }catch (Exception exp){
		        	Log.e("exception","whatever exceptions", exp);
		        }
		    } else {
		        Log.d("network error", "No network available!");
		    }
		    return false;
		}
	}
}
