package com.madlion.benimtercihim;

import android.app.AlertDialog;
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

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class SifreHatirlat extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sifre_hatirlat);
        Button btnSifreSifirla=(Button) findViewById(R.id.btnSifreSifirla);
        String kullanici=getIntent().getExtras().getString("kullanici");
        btnSifreSifirla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Şifre sıfırlama işlemi buradan yapılacak


                final EditText eposta=(EditText) findViewById(R.id.edtEposta);
                if(eposta.getText().toString()!="") {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Genel_Islemler.siteadresi+"sifresifirla.php",
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    // Display the first 500 characters of the response string.
                                    // Sonuç geldiğinde burada mesaj verilecek
                                    Toast.makeText(getBaseContext(), "İstek gönderildi. Gelen sonuç : " + response, Toast.LENGTH_LONG).show();
                                    if (response.contains("success")) {
                                        //eğer sonuç olumlu ise anasayfaya geri dön.
                                        Log.i("Yapılanİşlem","Şifre sıfırlama talebi alındı");

                                        finish();
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //hata oluşursa mesaj burada verilecek
                            Toast.makeText(getBaseContext(), "İstek gönderildi. Gelen sonuç : " + error.getMessage(), Toast.LENGTH_LONG).show();
                            Log.i("Yapılanİşlem","Şifre sıfırlama işleminde hata");
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();

                            params.put("eposta", eposta.getText().toString());
                            return params;
                        }
                    };
                }
            }
        });
    }
}
