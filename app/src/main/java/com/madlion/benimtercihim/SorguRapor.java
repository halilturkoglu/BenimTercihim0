package com.madlion.benimtercihim;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
            ListView l=(ListView) findViewById(R.id.lstSorguSonuc);
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                    this,
                    R.layout.listview_bolum,
                    liste );
            l.setAdapter(arrayAdapter);
            Log.i("Yapılanİşlem","bölümler listesi oluşturuldu");
        }
        else
        {
            Toast.makeText(getBaseContext(),"Arama sonucunda kriterlere uygun kayıt bulunamadı. Aramanızı düzeltip tekrar deneyiniz.",Toast.LENGTH_LONG).show();
            Log.i("Yapılanİşlem","bölüm listesi için içerik bulunamadı");
            finish();
        }
    }
}
