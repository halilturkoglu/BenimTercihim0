package com.madlion.benimtercihim;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class UyeSayfasi extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uye_sayfasi);
        final String kullanici=savedInstanceState.getString("kullanici");

        Button btnUniDeg, btnUniSor, btnSifreSifirla, btnUyelikSil;

        btnUniDeg=(Button) findViewById(R.id.btnUniDegerlendir);
        btnUniSor=(Button) findViewById(R.id.btnUniSorgula);
        btnSifreSifirla=(Button) findViewById(R.id.btnSifreDegistir);
        btnUyelikSil=(Button) findViewById(R.id.btnUyelikSil);

        btnUniDeg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(UyeSayfasi.this,Sorgula.class);
                i.putExtra("kullanici",kullanici);
                startActivity(i);
            }
        });
        btnUniSor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(UyeSayfasi.this,UniDegerlendir.class);
                i.putExtra("kullanici",kullanici);
                startActivity(i);
            }
        });
        btnSifreSifirla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(UyeSayfasi.this,SifreDegistir.class);
                i.putExtra("kullanici",kullanici);
                startActivity(i);
            }
        });
        btnUyelikSil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder a = new AlertDialog.Builder(getBaseContext()).setTitle("UYARI").setMessage("Üyeliğinizi silmek istediğinze emin misiniz?\nBu işlemin geri dönüşü yoktur.").setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, Genel_Islemler.siteadresi+"uyeliksil.php",
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        // Sonuç geldiğinde burada mesaj verilecek
                                        Toast.makeText(getBaseContext(),"İstek gönderildi. Gelen sonuç : "+response,Toast.LENGTH_LONG).show();
                                        if(response.contains("success"))
                                        {
                                            //
                                            //
                                            //Kullanıcı Silindi
                                            //
                                            //Uygulama anasayfasına geç
                                            finish();
                                        }
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                //
                                //
                                //Üyelik Silinemedi
                                //
                                //hata oluşursa mesaj burada verilecek
                                Toast.makeText(getBaseContext(),"İstek gönderildi. Gelen sonuç : "+error.getMessage(),Toast.LENGTH_LONG).show();
                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<String, String>();
                                params.put("kullanci_adi", kullanici);
                                return params;
                            }
                        };
                        dialog.dismiss();
                    }
                }).setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                a.show();

            }
        });

    }
}
