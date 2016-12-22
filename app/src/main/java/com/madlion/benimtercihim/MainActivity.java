package com.madlion.benimtercihim;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button yeniuye, devam, sifrehatirla, giris;

        yeniuye=(Button) findViewById(R.id.yeniuye);
        devam=(Button) findViewById(R.id.devamet);
        sifrehatirla=(Button) findViewById(R.id.sifrehatirla);
        giris=(Button) findViewById(R.id.btn_giris);

        yeniuye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,YeniUye.class);
                startActivity(i);
            }
        });
        devam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,Sorgula.class);
                startActivity(i);
            }
        });
        sifrehatirla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,SifreHatirlat.class);
                startActivity(i);
            }
        });
        giris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText k=(EditText) findViewById(R.id.kullanici);
                final EditText s=(EditText) findViewById(R.id.sifre);

                StringRequest stringRequest = new StringRequest(Request.Method.POST, Genel_Islemler.siteadresi+"giris.php",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Sonuç geldiğinde burada mesaj verilecek
                                Toast.makeText(getBaseContext(),"İstek gönderildi. Gelen sonuç : "+response,Toast.LENGTH_LONG).show();
                                if(response.contains("success"))
                                {
                                    //
                                    //
                                    //Oturum Açıldı
                                    //
                                    //eğer sonuç olumlu ise üye sayfasına geç.
                                    Intent i=new Intent(MainActivity.this,UyeSayfasi.class);
                                    i.putExtra("kullanici", k.getText().toString());
                                    startActivity(i);
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //
                        //
                        //Oturum Açılamadı
                        //
                        //hata oluşursa mesaj burada verilecek
                        Toast.makeText(getBaseContext(),"İstek gönderildi. Gelen sonuç : "+error.getMessage(),Toast.LENGTH_LONG).show();

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("kullanci_adi", k.getText().toString());
                        params.put("sifre", s.getText().toString());
                        return params;
                    }
                };
            }
        });

    }
}
