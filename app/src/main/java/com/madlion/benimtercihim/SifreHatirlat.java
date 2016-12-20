package com.madlion.benimtercihim;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.net.URI;

public class SifreHatirlat extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sifre_hatirlat);
        Button btnSifreSifirla=(Button) findViewById(R.id.btnSifreSifirla);
        btnSifreSifirla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Şifre sıfırlama işlemi buradan yapılacak
                EditText eposta=(EditText) findViewById(R.id.edtEposta);
                if(eposta.getText().toString()!="")
                {
                    URI u=Genel_Islemler.adresyaz("islem=sifresifirla&eposta="+eposta.getText().toString());
                    String c=Genel_Islemler.getWebPage(u.toString(),getBaseContext());
                    if(c.equals("success"))
                        Toast.makeText(getBaseContext(),getString(R.string.sifresifirlamesajbasari),Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(getBaseContext(),getString(R.string.sifresifirlamesajhata),Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
