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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UniDegerlendir extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uni_degerlendir);
        final Spinner spnSehir, spnUni, spnBolum;
        spnSehir=(Spinner) findViewById(R.id.spnSehir);
        spnUni=(Spinner) findViewById(R.id.spnUni);
        spnBolum=(Spinner) findViewById(R.id.spnBolum);

        String kullanici="";
        if(getIntent().hasExtra("kullanici")) kullanici=getIntent().getExtras().getString("kullanici");

        final String finalKullanici = kullanici;

        ArkaplanIsleri ai=new ArkaplanIsleri(getBaseContext(),UniDegerlendir.this);
        String[] params = new String[1];
        params[0]=Genel_Islemler.siteadresi+"sehirler?xml=1";
        String r=ai.getResponseFrom(params,ArkaplanIsleri.RequestType.GET);

        XMLParse x=new XMLParse(r, XMLParse.XMLType.StringVar);
        if(x.getElementCount("sehir")>0)
        {
            ArrayList<String> shr=new ArrayList<String>();
            for(int i=0;i<x.getElementCount("sehir");i++)
            {
                shr.add(Integer.parseInt(x.getElementValue("sehirler/"+String.valueOf(i)+"/sehir/0/plaka")),x.getElementValue("sehirler/"+String.valueOf(i)+"/sehir/0/sehir"));
            }
            ArrayAdapter<String> shr_adp=new ArrayAdapter<String>(getBaseContext(),R.layout.support_simple_spinner_dropdown_item,shr);
            spnSehir.setAdapter(shr_adp);

        }

        spnSehir.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(final AdapterView<?> parent, View view, final int position, long id) {
                if(position>-1)
                {
Toast.makeText(getBaseContext(),String.valueOf(position),Toast.LENGTH_LONG).show();
                    String[] params = new String[1];
                    params[0]=Genel_Islemler.siteadresi+"universiteler/"+(position+1)+"?xml=1";
                    //params[1]="sehir";
                    //params[2]=((TextView)((View) parent.getSelectedItem()).findViewById(R.id.textView)).getText().toString();

                    Log.i("Şehir Seçimi","Başarılı");
                    ArkaplanIsleri ai=new ArkaplanIsleri(getBaseContext(),UniDegerlendir.this);
                    String r=ai.getResponseFrom(params,ArkaplanIsleri.RequestType.GET);
                    XMLParse x=new XMLParse(r, XMLParse.XMLType.StringVar);
                    if(x.getElementCount("universite")>0)
                    {
// Üniversite listesi doldurulacak. Üniversite listesi r değişkeninin içinde yer alıyor.
                        ArrayList<String> uni=new ArrayList<String>();
                        for(int i=0;i<x.getElementCount("universite");i++)
                        {
                            Log.i(ArkaplanIsleri.TAG_Job,x.getElementValue("universiteler/"+String.valueOf(i)+"/universite/0/universite_id"));
                            uni.add(Integer.parseInt(x.getElementValue("universiteler/"+String.valueOf(i)+"/universite/0/universite_id")),x.getElementValue("universiteler/"+String.valueOf(i)+"/universite/0/ad"));
                        }
                        ArrayAdapter<String> uni_adp=new ArrayAdapter<String>(getBaseContext(),R.layout.support_simple_spinner_dropdown_item,uni);
                        spnUni.setAdapter(uni_adp);

                    }
                    else
                        Log.i("Şehir Seçimi","Başarısız");
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        spnUni.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(final AdapterView<?> parent, View view, final int position, long id) {
                if(position>-1)
                {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Genel_Islemler.siteadresi+"bolumgetir.php",
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    if(response.contains("success"))
                                    {
                                        //eğer sonuç olumlu ise bölüm listesini doldur..
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //hata oluşursa mesaj burada verilecek
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("uni", ((TextView)((View) parent.getSelectedItem()).findViewById(R.id.textView)).getText().toString());
                            return params;
                        }
                    };

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

                StringRequest stringRequest = new StringRequest(Request.Method.POST, Genel_Islemler.siteadresi+"unidegerlendir.php",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if(response.contains("success"))
                                {
                                    //eğer sonuç olumlu ise anasayfaya geri dön..
                                    Toast.makeText(getBaseContext(),"Değerlendirmeniz kaydedildi.",Toast.LENGTH_LONG).show();
                                    finish();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //hata oluşursa mesaj burada verilecek
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("uni.adi", ((TextView)((View) spnUni.getSelectedItem()).findViewById(R.id.textView)).getText().toString());
                        params.put("sehir.kira", String.valueOf(rtKira));
                        params.put("sehir.yurt", String.valueOf(rtYurt));
                        params.put("sehir.yemek", String.valueOf(rtYemek));
                        params.put("sehir.alisveris", String.valueOf(rtAlisVeris));
                        params.put("sehir.sosyal", String.valueOf(rtSehirSosyal));
                        params.put("uni.sosyal", String.valueOf(rtUniSosyal));
                        params.put("bolum.isimkani", String.valueOf(rtIsImkani));
                        params.put("sehir.ulasim", String.valueOf(rtUlasim));
                        params.put("sehir.guvenlik", String.valueOf(rtGuvenlik));
                        params.put("bolum.adi", ((TextView)((View) spnBolum.getSelectedItem()).findViewById(R.id.textView)).getText().toString());
                        return params;
                    }
                };

            }
        });
    }
}
