package com.example.eager_study_util;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.ArrayList;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManagerFactory;

import com.example.eager_study.MainActivity;


import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

public class DownloadPacket {

	    
	    public static int lastpos=0;
	 

	    public static String URL = "https://209.40.204.206/FlaskApp/download_database/xianyi";
	 
	    private static final String CLIENT_KET_PASSWORD = "tlstest";

	    private static final String CLIENT_TRUST_PASSWORD = "tlstest";

	    private static final String CLIENT_AGREEMENT = "TLS";

	    private static final String CLIENT_KEY_MANAGER = "X509";

	    private static final String CLIENT_TRUST_MANAGER = "X509";

	    private static final String CLIENT_KEY_KEYSTORE = "BKS";

	    private static final String CLIENT_TRUST_KEYSTORE = "BKS";
	    

	    
	    private AssetManager mAssetManager = null;

	    private Context mcontext;
	    
	    @SuppressWarnings("unused")
		private String userid;
	    
	    public DownloadPacket(Context mcontext,String uid){
	    	this.mcontext=mcontext;
	    	this.mAssetManager=mcontext.getAssets();
	    	this.userid=uid;
	    	URL=URL+"/"+uid;
	    	
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
         
         System.out.println("following is the output");
         System.out.println(output);
         String[] entries = output.split(":");
         if ((entries==null)||(entries.length<=1))
        	 return "No corresponding data available in server!";
         else{
        	 boolean home=true, work1=true, work2=true, place1=true, place2=true, place3=true;
        	 MainActivity.lat = new ArrayList<Double>();
        	 MainActivity.lon = new ArrayList<Double>();
        	 for (int i=1; i<entries.length; i++){
        		 String[] name_value = entries[i].split("\\?");
        		 if ((home)&&(name_value[0].equals("Home"))){
        			 String[] value = name_value[1].split(" ");
        			 MainActivity.home_lat = Double.valueOf(value[0]).doubleValue();
        			 MainActivity.home_lon = Double.valueOf(value[1]).doubleValue();
        			 home=false;
        		 }else if ((work1)&&(name_value[0].equals("Work1"))){
        			 if (name_value[1].equals("N.A.")){
        				 MainActivity.work_index=-1;
        			 }else{
        				 MainActivity.work_index=0;
        				 String[] value = name_value[1].split(" ");
        				 MainActivity.lat.add(Double.valueOf(value[0]).doubleValue());
                     	 MainActivity.lon.add(Double.valueOf(value[1]).doubleValue());
        			 }
        			 work1=false;
        		 }else if ((work2)&&(name_value[0].equals("Work2"))){
        			 if (name_value[1].equals("N.A.")){
        				 //do nothing
        			 }else{
        				 MainActivity.work_index=1;
        				 String[] value = name_value[1].split(" ");
        				 MainActivity.lat.add(Double.valueOf(value[0]).doubleValue());
                     	 MainActivity.lon.add(Double.valueOf(value[1]).doubleValue());
        			 }
        			 work2=false;
        		 }else if ((place1)&&(name_value[0].equals("Place1"))){
        			 if (name_value[1].equals("N.A.")){
        				 //do nothing
        			 }else{
        				 String[] value = name_value[1].split(" ");
        				 MainActivity.lat.add(Double.valueOf(value[0]).doubleValue());
                     	 MainActivity.lon.add(Double.valueOf(value[1]).doubleValue());
        			 }
        			 place1=false;
        		 }else if ((place2)&&(name_value[0].equals("Place2"))){
        			 if (name_value[1].equals("N.A.")){
        				 //do nothing
        			 }else{
        				 String[] value = name_value[1].split(" ");
        				 MainActivity.lat.add(Double.valueOf(value[0]).doubleValue());
                     	 MainActivity.lon.add(Double.valueOf(value[1]).doubleValue());
        			 }
        			 place2=false;
        		 }else if ((place3)&&(name_value[0].equals("Place3"))){
        			 if (name_value[1].equals("N.A.")){
        				 //do nothing
        			 }else{
        				 String[] value = name_value[1].split(" ");
        				 MainActivity.lat.add(Double.valueOf(value[0]).doubleValue());
                     	 MainActivity.lon.add(Double.valueOf(value[1]).doubleValue());
        			 }
        			 place3=false;
        		 }
        	 }
        	 
        	 try {
     			FileOutputStream fileout=mcontext.openFileOutput("EAGER_Study.txt", Activity.MODE_PRIVATE);
     			OutputStreamWriter outputWriter=new OutputStreamWriter(fileout);
     			outputWriter.write(String.valueOf(MainActivity.home_lat)+"\n");
     			outputWriter.write(String.valueOf(MainActivity.home_lon)+"\n");
     			outputWriter.write(String.valueOf(MainActivity.work_index)+"\n");
     			for (int i=0; i<MainActivity.lat.size(); i++){
     				outputWriter.write(String.valueOf(MainActivity.lat.get(i))+"\n");
     				outputWriter.write(String.valueOf(MainActivity.lon.get(i))+"\n");
     			}
     			outputWriter.close();
     			
     			
     		} catch (Exception e) {
     			e.printStackTrace();
     		}
        	 
         }
         
         return "Suceed";
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
    	 System.out.println(output);
    	 if (output.equals("No corresponding data available in server!")) {
    		 ((MainActivity) mcontext).address_input_window();
    	 }
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
	    	}else{
	    		((MainActivity) mcontext).open_internet_window();
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
