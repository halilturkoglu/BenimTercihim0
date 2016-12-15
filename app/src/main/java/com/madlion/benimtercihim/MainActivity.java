package com.madlion.benimtercihim;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button yeniuye, devam, sifrehatirla;
        yeniuye=(Button) findViewById(R.id.yeniuye);
        devam=(Button) findViewById(R.id.devamet);
        sifrehatirla=(Button) findViewById(R.id.sifrehatirla);

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

    }
}
