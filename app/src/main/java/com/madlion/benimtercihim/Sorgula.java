package com.madlion.benimtercihim;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;

import java.util.ArrayList;

public class Sorgula extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sorgula);
        Button btnSorgula;
        ArrayList<String> puan=new ArrayList<String>();
        puan.add("MF-1");
        puan.add("TM-1");
        puan.add("MF-2");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_item,
                puan );
        ((Spinner) findViewById(R.id.spnPuan)).setAdapter(arrayAdapter);


        btnSorgula = (Button) findViewById(R.id.btnSorgula);
        btnSorgula.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String spnPuan;
                final Integer edtMin, edtMak;
                final float rtKira, rtYurt, rtYemek, rtAlisVeris, rtSehirSosyal, rtUniSosyal, rtIsImkani, rtUlasim, rtGuvenlik;

                edtMin = Integer.valueOf(((EditText) findViewById(R.id.edtMin)).getText().toString());
                edtMak = Integer.valueOf(((EditText) findViewById(R.id.edtMak)).getText().toString());

                rtKira = ((RatingBar) findViewById(R.id.rtKira)).getRating();
                rtYurt = ((RatingBar) findViewById(R.id.rtYurt)).getRating();
                rtYemek = ((RatingBar) findViewById(R.id.rtYemek)).getRating();
                rtAlisVeris = ((RatingBar) findViewById(R.id.rtAlisVeris)).getRating();
                rtSehirSosyal = ((RatingBar) findViewById(R.id.rtSehirSosyal)).getRating();
                rtUniSosyal = ((RatingBar) findViewById(R.id.rtUniSosyal)).getRating();
                rtIsImkani = ((RatingBar) findViewById(R.id.rtIsImkani)).getRating();
                rtUlasim = ((RatingBar) findViewById(R.id.rtUlasim)).getRating();
                rtGuvenlik = ((RatingBar) findViewById(R.id.rtGuvenlik)).getRating();

                Log.i("Yapılanİşlem","Sorgula düğmesine basıldı");

                spnPuan = ((Spinner) findViewById(R.id.spnPuan)).getSelectedItem().toString();

                String[] params=new String[23];
                params[0]=ArkaplanIsleri.siteadresi + "sorgula&export=xml";
                params[1]="bolumpuan"; params[2]= String.valueOf(edtMin);//"BETWEEN "+edtMin + " AND " + edtMak;
                params[3]="sehirkira"; params[4]= String.valueOf(rtKira);
                params[5]="sehiryurt"; params[6]= String.valueOf(rtYurt);
                params[7]="sehiryemek"; params[8]= String.valueOf(rtYemek);
                params[9]="sehiralisveris"; params[10]= String.valueOf(rtAlisVeris);
                params[11]="sehirsosyal"; params[12]= String.valueOf(rtSehirSosyal);
                params[13]="unisosyal"; params[14]= String.valueOf(rtUniSosyal);
                params[15]="bolumisimkani"; params[16]= String.valueOf(rtIsImkani);
                params[17]="sehirulasim"; params[18]= String.valueOf(rtUlasim);
                params[19]="sehirguvenlik"; params[20]= String.valueOf(rtGuvenlik);
                params[21]="bolumpuanturu"; params[22]= spnPuan;

                ArkaplanIsleri ai=new ArkaplanIsleri(getBaseContext(),Sorgula.this);
                String r=ai.getResponseFrom(params, ArkaplanIsleri.RequestType.POST);

                XMLParse x=new XMLParse(r, XMLParse.XMLType.StringVar);
                if(x.getElementCount("bolum")>0)
                {
                    ArrayList<String> bolum=new ArrayList<String>();
                    for(int i=0;i<x.getElementCount("bolum");i++)
                    {
                        Log.i(ArkaplanIsleri.TAG_Job,x.getElementValue("bolumler/"+String.valueOf(i)+"/bolum/0/bolumid"));
                        bolum.add(Integer.parseInt(x.getElementValue("bolumler/"+String.valueOf(i)+"/bolum/0/bolumid")),x.getElementValue("bolumler/"+String.valueOf(i)+"/bolum/0/bolumadi"));
                    }
                    Log.i(ArkaplanIsleri.TAG_Response,"Sorgu Sonucu Kayıt Sayısı:"+String.valueOf(bolum.size()));
                    Intent i = new Intent(Sorgula.this, SorguRapor.class);
                    i.putStringArrayListExtra("liste",bolum);
                    startActivity(i);

                }

            }
        });
    }

}
