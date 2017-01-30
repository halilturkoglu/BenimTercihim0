package com.madlion.benimtercihim;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class SifreDegistir extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sifre_degistir);
        final String kullanici;
        Integer kid=0;

        if(getIntent().hasExtra("kullanici"))
        {
            kullanici=getIntent().getExtras().getString("kullanici");
            if(getIntent().hasExtra("id")) kid=getIntent().getExtras().getInt("kid");
            final EditText es,ys1,ys2;
            Button btnSifreDegistir = (Button) findViewById(R.id.btnSifreDegistir);
            Button vazgec = (Button) findViewById(R.id.vazgec);
            es=(EditText) findViewById(R.id.eskisifre);
            ys1=(EditText) findViewById(R.id.yenisifre1);
            ys2=(EditText) findViewById(R.id.yenisifre2);

            vazgec.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            btnSifreDegistir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(ys1.equals(ys2))
                    {
                        //şifreler aynı ise, şifre değiştirme isteği gönder.
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, Genel_Islemler.siteadresi+"sifredegistir.php",
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        // Display the first 500 characters of the response string.
                                        // Sonuç geldiğinde burada mesaj verilecek
                                        Toast.makeText(getBaseContext(),"İstek gönderildi. Gelen sonuç : "+response,Toast.LENGTH_LONG).show();
                                        if(response.contains("success"))
                                        {
                                            //eğer sonuç olumlu ise uye sayfasına geri dön.
                                            Log.i("Yapılanİşlem","Şifre değiştirildi");

                                            finish();
                                        }
                                        else
                                            Log.i("Yapılanİşlem","şifre değiştilemedi");

                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                //hata oluşursa mesaj burada verilecek
                                Toast.makeText(getBaseContext(),"İstek gönderildi. Gelen sonuç : "+error.getMessage(),Toast.LENGTH_LONG).show();
                                Log.i("Yapılanİşlem","Şifre değiştirmede hata");

                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<String, String>();
                                params.put("kullanci_adi", kullanici);
                                params.put("sifre", es.getText().toString());
                                params.put("yenisifre", ys1.getText().toString());
                                return params;
                            }
                        };

                    }
                    else
                    {
                        Toast.makeText(getBaseContext(),"Girdiğiniz şifreler birbirine uymuyor",Toast.LENGTH_LONG).show();
                    }
                }
            });
       }

    }
}
