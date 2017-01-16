package com.madlion.benimtercihim;

import android.content.Intent;
import android.drm.DrmStore;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==1001)
        {
            if(resultCode==1002)
            {
                ((EditText) findViewById(R.id.kullanici)).setText(data.getStringExtra("kullanici"));
                ((EditText) findViewById(R.id.sifre)).setText(data.getStringExtra("sifre"));

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        Button yeniuye, devam, sifrehatirla, giris;

        yeniuye=(Button) findViewById(R.id.yeniuye);
        devam=(Button) findViewById(R.id.devamet);
        sifrehatirla=(Button) findViewById(R.id.sifrehatirla);
        giris=(Button) findViewById(R.id.btn_giris);




           yeniuye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,YeniUye.class);
                startActivityForResult(i,1001);
                Log.i(ArkaplanIsleri.TAG_Job,"Yeni üye kaydı yapma");

            }
        });
        devam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,Sorgula.class);
                startActivity(i);
                Log.i(ArkaplanIsleri.TAG_Job,"Oturum Açmadan sorgulama yapma");

            }
        });
        sifrehatirla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,SifreHatirlat.class);
                startActivity(i);
                Log.i(ArkaplanIsleri.TAG_Job,"Şifre Sıfırlama Ekranına Geçiş");

            }
        });
        giris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText k=(EditText) findViewById(R.id.kullanici);
                final EditText s=(EditText) findViewById(R.id.sifre);
                Log.i(ArkaplanIsleri.TAG_Job,"Oturum Açma girişimi");

                String[] params = new String[6];
                params[0]=Genel_Islemler.siteadresi+"giris?xml=1";
                params[1]="eposta";
                params[2]=k.getText().toString();
                params[3]="sifre";
                params[4]=s.getText().toString();

                ArkaplanIsleri ai=new ArkaplanIsleri(getBaseContext(),MainActivity.this);
                String r=ai.getResponseFrom(params, ArkaplanIsleri.RequestType.POST);
                if(r.contains("giris_basarili"))
                {
                    Log.i(ArkaplanIsleri.TAG_Response,r);
                    Intent i=new Intent(MainActivity.this,UyeSayfasi.class);
                    i.putExtra("kullanici",k.getText().toString());
                    startActivity(i);
                }
                else
                    Log.i(ArkaplanIsleri.TAG_Job,"Oturum Açılamadı.");
            }
        });

    }

}
