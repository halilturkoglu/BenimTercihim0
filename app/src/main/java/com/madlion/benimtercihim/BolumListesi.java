package com.madlion.benimtercihim;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by halilturkoglu on 23.12.2016.
 */

public class BolumListesi extends ListView {

    public Context cont=null;
    public BolumListesi(Context context) {
        super(context);
        init(context);
        // TODO Auto-generated constructor stub
    }

    public BolumListesi(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BolumListesi(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private ArrayList<Bolum> bolumler = null;
    private OrderAdapter m_adapter;
    private void init(Context context) {
        cont=context;
    }

    public Runnable returnRes = new Runnable() {

        @Override
        public void run() {
            if(bolumler != null && bolumler.size() > 0){
                for(int i=0;i<bolumler.size();i++)
                    m_adapter.add(bolumler.get(i));
            }
        }
    };

    private class OrderAdapter extends ArrayAdapter<Bolum>
    {
        private ArrayList<Bolum> items;
        public OrderAdapter(Context context, int textViewResourceId, ArrayList<Bolum> items)
        {
            super(context, textViewResourceId, items);
            this.items = items;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            View v = convertView;
            if (v == null)
            {
                LayoutInflater vi = (LayoutInflater) cont.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.listview_bolum, null);
            }
            Bolum o = items.get(position);
            if (o != null)
            {
                TextView ad = (TextView) v.findViewById(R.id.BolumAdi);
                TextView uni = (TextView) v.findViewById(R.id.Universite);
                TextView puan = (TextView) v.findViewById(R.id.Puan);
                if(ad != null) ad.setText(o.getBolumAdi());
                if(uni != null) uni.setText(o.getUniversite());
                if(puan != null) puan.setText(String.valueOf(o.getPuan()));
            }
            return v;
        }
    }
}

