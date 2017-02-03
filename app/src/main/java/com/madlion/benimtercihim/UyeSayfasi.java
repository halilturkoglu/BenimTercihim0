package com.madlion.benimtercihim;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class UyeSayfasi extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uye_sayfasi);
        final String kullanici=getIntent().getExtras().getString("kullanici");
        final String kid=getIntent().getExtras().getString("kid");
        ((TextView) findViewById(R.id.textView33)).setText(kullanici);

        Button btnUniDeg, btnUniSor, btnSifreSifirla, btnUyelikSil;

        btnUniDeg=(Button) findViewById(R.id.btnUniDegerlendir);
        btnUniSor=(Button) findViewById(R.id.btnUniSorgula);
        btnSifreSifirla=(Button) findViewById(R.id.btnSifreDegistir);
        btnUyelikSil=(Button) findViewById(R.id.btnUyelikSil);

        btnUniDeg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(UyeSayfasi.this,UniDegerlendir.class);
                i.putExtra("kullanici",kullanici);
                i.putExtra("kid",kid);
                startActivity(i);
                Log.i(ArkaplanIsleri.TAG_Job,"Üniversite Değerlendirme");

            }
        });
        btnUniSor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(UyeSayfasi.this,Sorgula.class);
                i.putExtra("kullanici",kullanici);
                i.putExtra("kid",kid);
                startActivity(i);
                Log.i(ArkaplanIsleri.TAG_Job,"Üniversite Sorgulama");

            }
        });
        btnSifreSifirla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(UyeSayfasi.this,SifreDegistir.class);
                i.putExtra("kid",kid);
                i.putExtra("kullanici",kullanici);
                startActivity(i);
                Log.i(ArkaplanIsleri.TAG_Job,"Şifre Sıfırlama");

            }
        });
        btnUyelikSil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        AlertDialog.Builder a = new AlertDialog.Builder(getBaseContext()).setTitle("UYARI").setMessage("Üyeliğinizi silmek istediğinze emin misiniz?\nBu işlemin geri dönüşü yoktur.").setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                        String[] params=new String[3];
                        params[0]=ArkaplanIsleri.siteadresi+"giris?xml=1";
                        params[1]="kullanici";
                        params[2]=kullanici;
                        ArkaplanIsleri ai=new ArkaplanIsleri(getBaseContext(),UyeSayfasi.this);
                        String r=ai.getResponseFrom(params, ArkaplanIsleri.RequestType.POST);
                        if(r.contains("silme_basarili"))
                        {
                            Log.i(ArkaplanIsleri.TAG_Response,r);
                            Intent i=new Intent(UyeSayfasi.this,MainActivity.class);
                            startActivity(i);
                        }
                        else
                            Log.i(ArkaplanIsleri.TAG_Response,"Üye silinemedi.");

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
