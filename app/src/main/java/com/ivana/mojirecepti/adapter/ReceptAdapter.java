package com.ivana.mojirecepti.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ivana.mojirecepti.R;
import com.ivana.mojirecepti.asynctask.DownloadImageTask;
import com.ivana.mojirecepti.model.Recept;

import java.util.ArrayList;

public class ReceptAdapter extends ArrayAdapter<Recept> {
    private int resourceLayout;
    Context mContext;
    private ArrayList<Recept> listaRecepata;
    private ArrayList<Recept> listaRecepataPuna;

    public ReceptAdapter(Context context, int resource, ArrayList<Recept> items) {
        super(context, resource, items);
        this.resourceLayout = resource;
        this.mContext = context;
        this.listaRecepataPuna = items;
        this.listaRecepata = items;
    }

    @Override
    public View getView(int position,  View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(mContext);
            v = vi.inflate(resourceLayout, null);
        }
        Recept p = getItem(position);
        if (p != null) {
            TextView labelNaziv = (TextView) v.findViewById(R.id.labelNazivVertikalni);
            TextView labelVreme = (TextView) v.findViewById(R.id.labelVremeVertikalni);
            TextView labelKalorije = (TextView) v.findViewById(R.id.labelKalorijeVertikalni);
            ImageView image = (ImageView) v.findViewById(R.id.imageVertikalni);
            if (labelNaziv != null) {
                labelNaziv.setText(p.getNaziv());
            }

            if (labelVreme != null) {
                labelVreme.setText(String.valueOf(p.getVremePripreme()));
            }

            if (labelKalorije != null) {
                labelKalorije.setText(String.valueOf(p.getKalorije()));
            }
            if (image != null) {
                new DownloadImageTask(image).execute(p.getSlika());
            }
        }
        return v;
    }

    @Override
    public int getCount() {
        return listaRecepata.size();
    }

    @Override
    public Recept getItem(int position) {
        return listaRecepata.get(position);
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                if (constraint == null || constraint.length() == 0) {
                    results.count = listaRecepataPuna.size();
                    results.values = listaRecepataPuna;
                } else {//do the search
                    ArrayList<Recept> resultsData = new ArrayList<Recept>();
                    String searchStr = constraint.toString().toUpperCase();
                    for (Recept s : listaRecepataPuna)
                        if (s.getNaziv().toUpperCase().contains(searchStr) || String.valueOf(s.getKalorije()).contains(searchStr)) resultsData.add(s);
                    results.count = resultsData.size();
                    results.values = resultsData;
                }
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                listaRecepata = (ArrayList<Recept>) results.values;
                notifyDataSetChanged();
            }
        };

    }
}
