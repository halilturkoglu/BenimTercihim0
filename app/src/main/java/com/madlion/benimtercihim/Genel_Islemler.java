package com.madlion.benimtercihim;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

public class Genel_Islemler extends AsyncTask<String,Void,String> {
	//ev bağlantı
	//public static String siteadresi="http://176.217.115.206/deneme/";
	//işyeri bağlantı
	public static String siteadresi="http://halilturkoglu.com/adem.php?page=";//"http://api.ademdeliaslan.com.tr/";

	public static URI adresyaz(String parametre)
	{
		URI u=URI.create(siteadresi+"?"+parametre);
		return u;
	}
	
	public static int getVersion(Context cnt)
		{
		try {
			PackageInfo packageInfo = cnt.getPackageManager().getPackageInfo(cnt.getPackageName(), 0);
			return packageInfo.versionCode;
			} catch (NameNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		return 0;
		}
    public static void UnloadApp()
    	{
    	System.exit(0);
    	}
    public static boolean checkInternet(Context context) 
    	{
        ConnectivityManager con_manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (con_manager.getActiveNetworkInfo() != null && con_manager.getActiveNetworkInfo().isAvailable() && con_manager.getActiveNetworkInfo().isConnected()) 
        	return true;
        else
            return false;
    	}
    
	public static boolean isMyServiceRunning(Class<?> serviceClass, Context context) 
		{
	    ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
	    for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) 
	    	{
	        if (serviceClass.getName().equals(service.service.getClassName())) 
	        	{
	            return true;
	        	}
	    	}
	    return false;
		}
	public static String getNumber(Context cnt)
		{
		TelephonyManager tm = (TelephonyManager) cnt.getSystemService(Context.TELEPHONY_SERVICE); 
	    if(tm.getLine1Number()!=null && tm.getLine1Number()!="" && tm.getLine1Number().length()>0)
	    	return tm.getLine1Number().substring(1);
	    else
	    	return "";
		}
	public String GetId()
		{
		String m_szDevIDShort = "35" + //we make this look like a valid IMEI
	            Build.BOARD.length()%10+ Build.BRAND.length()%10 +
	            Build.CPU_ABI.length()%10 + Build.DEVICE.length()%10 +
	            Build.DISPLAY.length()%10 + Build.HOST.length()%10 +
	            Build.ID.length()%10 + Build.MANUFACTURER.length()%10 +
	            Build.MODEL.length()%10 + Build.PRODUCT.length()%10 +
	            Build.TAGS.length()%10 + Build.TYPE.length()%10 +
	            Build.USER.length()%10 ; //13 digits
		return m_szDevIDShort;
		}
	public static String getWebPage(String adresse,Context cnt)
		{
		String response="";
		if(!checkInternet(cnt)) return "0";
		try {
			URLConnection connection = new URL(adresse).openConnection();
			response=connection.getOutputStream().toString();
			} catch (IOException e) {
				e.printStackTrace();
			}
		return response;
		}

	@Override
	protected String doInBackground(String... params) {
		String urlm=params[0];
		try{
			URL url = new URL(urlm);
			HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.setRequestMethod("POST");
			httpURLConnection.setDoOutput(true);
			httpURLConnection.setDoInput(true);
			OutputStream outputStream = httpURLConnection.getOutputStream();
			BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
			String veri="";
			for(int i=1;i<params.length;i+=2)
			{
				veri+=URLEncoder.encode(params[i],"UTF-8")+"="+URLEncoder.encode(params[i+1],"UTF-8");
				if((i+1)<params.length) veri+="&";
			}

			bufferedWriter.write(veri);
			bufferedWriter.flush();
			bufferedWriter.close();
			outputStream.close();
			InputStream inputStream = httpURLConnection.getInputStream();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			StringBuilder stringBuilder = new StringBuilder();
			String line = "";


			while ((line=bufferedReader.readLine())!=null)
			{
				stringBuilder.append(line+"\n");
			}

			httpURLConnection.disconnect();
			Thread.sleep(5000);
			Log.i("VeriGönderildi",urlm+"?"+veri);

			return stringBuilder.toString().trim();
		}
		catch (MalformedURLException e){
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return null;

	}
}
