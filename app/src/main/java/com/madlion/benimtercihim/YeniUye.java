package com.madlion.benimtercihim;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by halilturkoglu on 14.12.2016.
 */
public class YeniUye extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yeni_uye);

        final RequestQueue queue = Volley.newRequestQueue(getBaseContext());
        final String url=ArkaplanIsleri.siteadresi+"kayit&export=xml";

        Button btnUye=(Button) findViewById(R.id.btnUyeOl);
        btnUye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Display the first 500 characters of the response string.
                                // Sonuç geldiğinde burada mesaj verilecek
                                Log.i(ArkaplanIsleri.TAG_Response,response);

                                if(response!=null)
                                {
                                    XMLParse x=new XMLParse(response, XMLParse.XMLType.StringVar);
                                    if(x.getElementValue("sunucu_cevabi/0/code").equals("kayit_basarili")) {
                                        Log.i(ArkaplanIsleri.TAG_Response, "Kayıt Başarılı");
                                        Intent i=new Intent(YeniUye.this,MainActivity.class);
                                        i.putExtra("kullanici",((EditText)findViewById(R.id.edtKullanici)).getText().toString());
                                        i.putExtra("sifre",((EditText)findViewById(R.id.edtSifre1)).getText().toString());
                                        setResult(1002,i);
                                        finishActivity(1001);
                                    }
                                    else {
                                        Log.i(ArkaplanIsleri.TAG_Response, "Kayıt Başarısız");
                                        Toast.makeText(getBaseContext(),"Kayıt İşlemi başarısız oldu. Lütfen daha sonra tekrar deneyiniz.",Toast.LENGTH_LONG).show();
                                    }
                                    finish();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //hata oluşursa mesaj burada verilecek
                        Log.i(ArkaplanIsleri.TAG_Response,error.getMessage().isEmpty()?url:error.getMessage());

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> params = new HashMap<String, String>();

                        String adsoyad,kullaniciadi,eposta,sifre,sifre2;
                        adsoyad=((EditText)findViewById(R.id.edtAdSoyad)).getText().toString();
                        kullaniciadi=((EditText)findViewById(R.id.edtKullanici)).getText().toString();
                        eposta=((EditText)findViewById(R.id.edtEposta)).getText().toString();
                        sifre=((EditText)findViewById(R.id.edtSifre1)).getText().toString();
                        sifre2=((EditText)findViewById(R.id.edtSifre2)).getText().toString();
                        if(adsoyad!="" && kullaniciadi !="" && eposta!="" && sifre!="")
                        {
                            if(sifre.equals(sifre2))
                            {
                                params.put("ad_soyad",adsoyad);
                                params.put("kullanici_adi",kullaniciadi);
                                params.put("eposta",eposta);
                                params.put("sifre",sifre);
                            }
                            else
                                new AlertDialog.Builder(YeniUye.this).setTitle("Hatalı İşlem").setMessage("Girdiğiniz şifreler uyumlu değil").show();
                        }

                        return params;
                    }
                };
// Add the request to the RequestQueue.
                queue.add(stringRequest);
            }
        });
    }
}
