package com.madlion.benimtercihim;

import android.content.Intent;
import android.net.Uri;
import android.os.Parcel;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Sorgula extends AppCompatActivity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sorgula);
        Button btnSorgula;

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

                spnPuan = ((TextView) ((Spinner) findViewById(R.id.spnPuan)).getChildAt(((Spinner) findViewById(R.id.spnPuan)).getSelectedItemPosition()).findViewById(R.id.textView)).getText().toString();

                StringRequest stringRequest = new StringRequest(Request.Method.POST, Genel_Islemler.siteadresi + "unisorgula.php",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Display the first 500 characters of the response string.
                                // Sonuç geldiğinde burada mesaj verilecek
                                Toast.makeText(getBaseContext(), "İstek gönderildi. Gelen sonuç : " + response, Toast.LENGTH_LONG).show();
                                if (response.contains("success")) {
                                    // eğer sonuç olumlu ise sorgu rapor sayfasına gönder..
                                    //
                                    //
                                    //
                                    // Ama nasıl ???
                                    //
                                    //
                                    // putextra ile activity ler arası veri transferi mümkün ama
                                    // bütün bir bölüm üniversite listesini nasıl göndereceğiz


                                    ArrayList<String> sonuclar=null;
                                    try {
                                        JSONArray jsonArray = new JSONArray(response);
                                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                                        String code = jsonObject.getString("code");
                                        String message = jsonObject.getString("message");
                                        if(code.equals("success"))
                                        {
                                            String[] msg=message.split(";");
                                            for(int i=0;i<msg.length;i++)
                                            {
                                                sonuclar.add(msg[i]);
                                            }
                                        }
                                        else
                                        {
                                            Toast.makeText(getBaseContext(),"Bölümler listesi alınamadı. Lütfen daha sonra tekrar deneyiniz.",Toast.LENGTH_LONG).show();
                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                    Intent i = new Intent(Sorgula.this, SorguRapor.class);
                                    i.putStringArrayListExtra("liste",sonuclar);
                                    startActivity(i);

                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //hata oluşursa mesaj burada verilecek
                        Toast.makeText(getBaseContext(), "İstek gönderildi. Gelen sonuç : " + error.getMessage(), Toast.LENGTH_LONG).show();

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        //bu bilgiler post metodu ile PHP kanadına gönderilecek,
                        //PHP kanadında SQL kodu oluşturulacak ve
                        //sorgulama sonucunda elde edilen veriler geri döndürülecek.
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("bolum.puan", edtMin + ":" + edtMak);
                        params.put("sehir.kira", String.valueOf(rtKira));
                        params.put("sehir.yurt", String.valueOf(rtYurt));
                        params.put("sehir.yemek", String.valueOf(rtYemek));
                        params.put("sehir.alisveris", String.valueOf(rtAlisVeris));
                        params.put("sehir.sosyal", String.valueOf(rtSehirSosyal));
                        params.put("uni.sosyal", String.valueOf(rtUniSosyal));
                        params.put("bolum.isimkani", String.valueOf(rtIsImkani));
                        params.put("sehir.ulasim", String.valueOf(rtUlasim));
                        params.put("sehir.guvenlik", String.valueOf(rtGuvenlik));
                        params.put("bolum.puanturu", spnPuan);
                        return params;

                        /*
                        sql="select * from sehir, uni, bolum where "
                                +"puan between "+edtMin+" and "+edtMak
                                +" and sehir.kira>="+rtKira
                                +" and sehir.yurt>="+rtYurt
                                +" and sehir.yemek>="+rtYemek
                                +" and sehir.alisveris>="+rtAlisVeris
                                +" and sehir.sosyal>="+rtSehirSosyal
                                +" and uni.sosyal>="+rtUniSosyal
                                +" and bolum.isimkani>="+rtIsImkani
                                +" and sehir.ulasim>="+rtUlasim
                                +" and sehir.guvenlik>="+rtGuvenlik
                                +" and bolum.puanturu='"+spnPuan+"'"
                                +"";
                                */

                    }
                };

            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Sorgula Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
