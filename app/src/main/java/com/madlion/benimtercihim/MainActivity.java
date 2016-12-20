package com.madlion.benimtercihim;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.net.URI;
import java.net.URL;

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
                EditText k=(EditText) findViewById(R.id.kullanici);
                EditText s=(EditText) findViewById(R.id.sifre);
                URI u=Genel_Islemler.adresyaz("islem=giris&kullanici="+k.getText().toString()+"&sifre="+s.getText().toString());
                String c=Genel_Islemler.getWebPage(u.toString(),getBaseContext());
                if(c.contains("success"))
                {
                    Intent i=new Intent(MainActivity.this,UyeSayfasi.class);
                    startActivity(i);
                }
                else
                {
                    Toast.makeText(getBaseContext(),getString(R.string.oturumacmahata),Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
