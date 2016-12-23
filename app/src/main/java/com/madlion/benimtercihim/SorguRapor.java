package com.madlion.benimtercihim;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

public class SorguRapor extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sorgu_rapor);
        ArrayList<String> liste=savedInstanceState.getStringArrayList("liste");
        if(liste.size()>0)
        {

        }
        else
        {
            Toast.makeText(getBaseContext(),"Arama sonucunda kriterlere uygun kayıt bulunamadı. Aramanızı düzeltip tekrar deneyiniz.",Toast.LENGTH_LONG).show();
            finish();
        }
    }
}
