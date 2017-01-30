package com.madlion.benimtercihim;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UniDegerlendir extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uni_degerlendir);
        final Spinner spnSehir, spnUni, spnBolum, spnFakulte;
        spnSehir=(Spinner) findViewById(R.id.spnSehir);
        spnUni=(Spinner) findViewById(R.id.spnUni);
        spnFakulte=(Spinner) findViewById(R.id.spnFakulte);
        spnBolum=(Spinner) findViewById(R.id.spnBolum);

        String kullanici="";
        String kid="";
        if(getIntent().hasExtra("kullanici")) kullanici=getIntent().getExtras().getString("kullanici");
        if(getIntent().hasExtra("kid")) kid=getIntent().getExtras().getString("kid");
        Log.i(ArkaplanIsleri.TAG_Job,kullanici+"-"+String.valueOf(kid));

        final String finalKullanici = kullanici;
        final String finalKid = kid;

        ArkaplanIsleri ai=new ArkaplanIsleri(getBaseContext(),UniDegerlendir.this);
        String[] params = new String[1];
        params[0]=Genel_Islemler.siteadresi+"sehirler";
        String r=ai.getResponseFrom(params,ArkaplanIsleri.RequestType.GET);
        try {
            JSONArray liste = new JSONObject(r).getJSONArray("sehirler");
            ArrayList<String> shr=new ArrayList<String>(liste.length());
            shr.add("Seçiniz");
            JSONObject x;
            if(liste.length()>0)
            {
                Integer plaka=0;
                String sehir="";
                for(int i=0;i<liste.length();i++)
                {
                    x=liste.getJSONObject(i);
                    plaka=x.getInt("plaka");
                    sehir=x.getString("sehir");
                    shr.add(String.valueOf(plaka)+"-"+sehir);
                }
                ArrayAdapter<String> shr_adp=new ArrayAdapter<String>(getBaseContext(),R.layout.support_simple_spinner_dropdown_item,shr);
                spnSehir.setAdapter(shr_adp);
            }
            else
            {
                Toast.makeText(getBaseContext(),"Şehirler listesi alınamadı. Lütfen daha sonra tekrar deneyiniz.",Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        spnSehir.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(final AdapterView<?> parent, View view, final int position, long id) {
                if(position>0)
                {
                    Toast.makeText(getBaseContext(),String.valueOf(position),Toast.LENGTH_LONG).show();
                    String[] params = new String[1];
                    params[0]=Genel_Islemler.siteadresi+"universiteler&sehirid="+parent.getItemAtPosition(position).toString().split("-")[0];
                    ArkaplanIsleri ai=new ArkaplanIsleri(getBaseContext(),UniDegerlendir.this);
                    String r=ai.getResponseFrom(params,ArkaplanIsleri.RequestType.GET);
                    try {
                        JSONArray liste = new JSONObject(r).getJSONArray("universiteler");
                        ArrayList<String> shr=new ArrayList<String>(liste.length());
                        shr.add("Seçiniz");
                        JSONObject x;
                        if(liste.length()>0)
                        {
                            Integer uniid=0;
                            String uniadi="";
                            for(int i=0;i<liste.length();i++)
                            {
                                x=liste.getJSONObject(i);
                                uniid=x.getInt("universite_id");
                                uniadi=x.getString("ad");
                                shr.add(String.valueOf(uniid)+"-"+uniadi);
                            }
                            ArrayAdapter<String> shr_adp=new ArrayAdapter<String>(getBaseContext(),R.layout.support_simple_spinner_dropdown_item,shr);
                            spnUni.setAdapter(shr_adp);
                        }
                        else
                        {
                            Toast.makeText(getBaseContext(),"Üniversite listesi alınamadı. Lütfen daha sonra tekrar deneyiniz.",Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        spnUni.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(final AdapterView<?> parent, View view, final int position, long id) {
                if(position>0)
                {
                    String[] params = new String[1];
                    params[0]=Genel_Islemler.siteadresi+"fakulteler&universiteid="+ parent.getItemAtPosition(position).toString().split("-")[0];
                    ArkaplanIsleri ai=new ArkaplanIsleri(getBaseContext(),UniDegerlendir.this);
                    String r=ai.getResponseFrom(params,ArkaplanIsleri.RequestType.GET);
                    try {
                        JSONArray liste = new JSONObject(r).getJSONArray("fakulteler");
                        ArrayList<String> shr=new ArrayList<String>(liste.length());
                        shr.add("Seçiniz");
                        JSONObject x;
                        if(liste.length()>0)
                        {
                            Integer fakulteid=0;
                            String fakulteadi="";
                            for(int i=0;i<liste.length();i++)
                            {
                                x=liste.getJSONObject(i);
                                fakulteid=x.getInt("fakulte_id");
                                fakulteadi=x.getString("ad");
                                shr.add(String.valueOf(fakulteid)+"-"+fakulteadi);
                            }
                            ArrayAdapter<String> shr_adp=new ArrayAdapter<String>(getBaseContext(),R.layout.support_simple_spinner_dropdown_item,shr);
                            spnFakulte.setAdapter(shr_adp);
                        }
                        else
                        {
                            Toast.makeText(getBaseContext(),"Fakülteler listesi alınamadı. Lütfen daha sonra tekrar deneyiniz.",Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        spnFakulte.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position>0)
                {
                    String[] params = new String[1];
                    params[0]=Genel_Islemler.siteadresi+"bolumler&fakulteid="+ parent.getItemAtPosition(position).toString().split("-")[0];
                    ArkaplanIsleri ai=new ArkaplanIsleri(getBaseContext(),UniDegerlendir.this);
                    String r=ai.getResponseFrom(params,ArkaplanIsleri.RequestType.GET);
                    try {
                        JSONArray liste = new JSONObject(r).getJSONArray("bolumler");
                        ArrayList<String> shr=new ArrayList<String>(liste.length());
                        shr.add("Seçiniz");
                        JSONObject x;
                        if(liste.length()>0)
                        {
                            Integer bolumid=0;
                            String bolumadi="";
                            for(int i=0;i<liste.length();i++)
                            {
                                x=liste.getJSONObject(i);
                                bolumid=x.getInt("bolum_id");
                                bolumadi=x.getString("ad");
                                shr.add(String.valueOf(bolumid)+"-"+bolumadi);
                            }
                            ArrayAdapter<String> shr_adp=new ArrayAdapter<String>(getBaseContext(),R.layout.support_simple_spinner_dropdown_item,shr);
                            spnBolum.setAdapter(shr_adp);
                        }
                        else
                        {
                            Toast.makeText(getBaseContext(),"Bölümler listesi alınamadı. Lütfen daha sonra tekrar deneyiniz.",Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Button btnDegerlendir=(Button) findViewById(R.id.btnDegerlendir);
        btnDegerlendir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean bosgec=false;
                String shr=spnSehir.getSelectedItem().toString();
                String uni="", blm="", fkl="";
                if(shr.isEmpty() || shr.equals("Seçiniz")) {
                    Toast.makeText(UniDegerlendir.this, "Şehir seçimi yapmadınız.", Toast.LENGTH_SHORT).show();
                    bosgec=true;
                }
                else
                {
                    shr=shr.split("-")[0];
                    uni=spnUni.getSelectedItem().toString();
                    if(uni.isEmpty() || uni.equals("Seçiniz")) {
                        Toast.makeText(UniDegerlendir.this, "Üniversite seçimi yapmadınız.", Toast.LENGTH_SHORT).show();
                        bosgec=true;
                    }
                    else
                    {
                        uni=uni.split("-")[0];
                        fkl=spnFakulte.getSelectedItem().toString();
                        if(fkl.isEmpty() || fkl.equals("Seçiniz")) {
                            Toast.makeText(UniDegerlendir.this, "Fakülte seçimi yapmadınız.", Toast.LENGTH_SHORT).show();
                            bosgec=true;
                        }
                        else
                        {
                            fkl=fkl.split("-")[0];
                            blm=spnBolum.getSelectedItem().toString();
                            if(blm.isEmpty() || blm.equals("Seçiniz")) {
                                Toast.makeText(UniDegerlendir.this, "Bölüm seçimi yapmadınız.", Toast.LENGTH_SHORT).show();
                                bosgec=true;
                            }
                            else
                                blm=blm.split("-")[0];
                        }
                    }
                }




                final float rtKira, rtYurt, rtYemek, rtAlisVeris, rtSehirSosyal, rtUniSosyal, rtIsImkani, rtUlasim, rtGuvenlik;

                rtKira=((RatingBar) findViewById(R.id.rtKira_p4)).getRating();
                rtYurt=((RatingBar) findViewById(R.id.rtYurt_p4)).getRating();
                rtYemek=((RatingBar) findViewById(R.id.rtYemek_p4)).getRating();
                rtAlisVeris=((RatingBar) findViewById(R.id.rtAlisVeris_p4)).getRating();
                rtSehirSosyal=((RatingBar) findViewById(R.id.rtSehirSosyal_p4)).getRating();
                rtUniSosyal=((RatingBar) findViewById(R.id.rtUniSosyal_p4)).getRating();
                rtIsImkani=((RatingBar) findViewById(R.id.rtIsImkani_p4)).getRating();
                rtUlasim=((RatingBar) findViewById(R.id.rtUlasim_p4)).getRating();
                rtGuvenlik=((RatingBar) findViewById(R.id.rtGuvenlik_p4)).getRating();


                Log.i(ArkaplanIsleri.TAG_Job,shr+"-"+uni+"-"+fkl+"-"+blm);
                String[] params=new String[27];
                params[0]=Genel_Islemler.siteadresi+"degerlendir";
                params[1]="uniid"; params[2]=uni;
                params[3]="sehirkira"; params[4]= String.valueOf(rtKira);
                params[5]="sehiryurt"; params[6]= String.valueOf(rtYurt);
                params[7]="sehiryemek"; params[8]= String.valueOf(rtYemek);
                params[9]="sehiralisveris"; params[10]= String.valueOf(rtAlisVeris);
                params[11]="sehirsosyal"; params[12]= String.valueOf(rtSehirSosyal);
                params[13]="unisosyal"; params[14]= String.valueOf(rtUniSosyal);
                params[15]="bolumisimkani"; params[16]= String.valueOf(rtIsImkani);
                params[17]="sehir"; params[18]= shr;
                params[19]="sehirguvenlik"; params[20]= String.valueOf(rtGuvenlik);
                params[21]="bolumid"; params[22]= blm;
                params[23]="kullanici_id"; params[24]= finalKid;
                params[25]="fakulteid"; params[26]= fkl;
//                params[27]="sehirulasim"; params[28]= String.valueOf(rtUlasim);
//                Şehir ulaşım bilgisi veritabanında yok onun için kaldırdım

                if(!bosgec)
                {
                    ArkaplanIsleri ai=new ArkaplanIsleri(getBaseContext(),UniDegerlendir.this);
                    String r=ai.getResponseFrom(params, ArkaplanIsleri.RequestType.POST);
                    if(r.contains("basarili"))
                    {
                        Toast.makeText(getBaseContext(),"Değerlendirmeniz kaydedildi.",Toast.LENGTH_LONG).show();
                        finish();
                    }
                    else
                        Toast.makeText(getBaseContext(),"Değerlendirmeniz kaydedilemedi.",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
