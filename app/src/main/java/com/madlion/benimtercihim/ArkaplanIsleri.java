package com.madlion.benimtercihim;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

public class ArkaplanIsleri extends AsyncTask<String,Void,String>{


    Context context;
    Activity href;
    ProgressDialog progressDialog;
    AlertDialog.Builder builder;
    String response;
    public static String TAG_Response="Gelen Cevap";
    public static String TAG_Job="Yapılan İş";
    public static String siteadresi="http://halilturkoglu.com/adem.php?page=";//"http://api.ademdeliaslan.com.tr/";


    public enum RequestType {
        POST,
        GET
    }

    public String getResponse()
    {
        return this.response;
    }

    public ArkaplanIsleri(Context context,Activity act){
        this.context = context;
        this.href=act;
    }

    @Override
    protected void onPreExecute() {
        //Progress Dialog Görünmüyor sebebini bilmiyorum
        progressDialog = new ProgressDialog(href);
        progressDialog.setTitle("Lütfen bekleyiniz");
        progressDialog.setMessage("Sunucuya bağlanılıyor..");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public String getResponseFrom(String[] params, RequestType type) {
        String[] x=new String[params.length+1];
        x[0]=(type==RequestType.GET)?"GET":"POST";
        for(int i=0;i<params.length;i++)
            {
            x[i + 1] = params[i];
            }
        this.execute(x);
        while(this.getResponse()==null)
        {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Log.i(ArkaplanIsleri.TAG_Response,this.getResponse());
        return this.getResponse();
    }

    @Override
    protected String doInBackground(String ... params) {
        Boolean isGet= params[0].equals("GET");
        //çağrılacak olan web sayfasının adresi
        String urlm=params[1];

            try {
                // çağrılacak olan web sayfasona gönderilecek parametreler
                String veri="";
                for(int i=2;i<(params.length-1);i+=2)
                {
                    veri+=URLEncoder.encode(params[i],"UTF-8")+"="+URLEncoder.encode(params[i+1],"UTF-8");
                    if((i+2)<(params.length-1)) veri+="&";
                }

                //eğer iletişim metodu GET ise parametreler adresin sonuna eklenecek
                if(isGet)
                {
                    Log.i("Veri Oluşturma","Method GET olarak algılandı");
                    if(!veri.isEmpty())
                        urlm += params[1].contains("?")?"&":"?" + veri;
                }

                Log.i(ArkaplanIsleri.TAG_Job,urlm);
                URL url = new URL(urlm);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod(params[0]);
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);


                Log.i(ArkaplanIsleri.TAG_Job,params[0]);
                Log.i(ArkaplanIsleri.TAG_Job,url.toString());
                Log.i(ArkaplanIsleri.TAG_Job,veri);

                if(!isGet)
                {
                    Log.i("Veri Oluşturma","Method POST olarak algılandı");
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                    bufferedWriter.write(veri);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();
                }

                Log.i(ArkaplanIsleri.TAG_Job,"HTTP "+String.valueOf(httpURLConnection.getResponseCode())+" : "+httpURLConnection.getResponseMessage());

                InputStream inputStream = (InputStream) httpURLConnection.getContent();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                String line = "";


                while ((line=bufferedReader.readLine())!=null)
                {
                    stringBuilder.append(line+"\n");
                }

                httpURLConnection.disconnect();
//                Thread.sleep(5000);

                progressDialog.dismiss();
                response=stringBuilder.toString().trim();
                return response;
            }
            catch (MalformedURLException e){
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
    return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    public String toString() {
        return response;
    }

    @Override
    protected void onPostExecute(String json) {

    }


}
