package com.ivana.mojirecepti.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.ivana.mojirecepti.R;
import com.ivana.mojirecepti.ReceptActivity;
import com.ivana.mojirecepti.adapter.ReceptAdapter;
import com.ivana.mojirecepti.database.ReceptiBaza;
import com.ivana.mojirecepti.model.Recept;

public class KategorijeFragment extends Fragment implements AdapterView.OnItemClickListener {
    private static Recept.Tip tipRecepta;
    private ReceptAdapter adapter;

    public KategorijeFragment(){}
    public static KategorijeFragment newInstance(Recept.Tip tip){
        KategorijeFragment fragment = new KategorijeFragment();
        tipRecepta = tip;
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_karegorije, container, false);
        ListView lista = v.findViewById(R.id.listKategorije);
        ReceptiBaza db = new ReceptiBaza(getContext());
        adapter = new ReceptAdapter(getContext(), R.layout.vertikalni_recept_red, db.getReceptiPoKategoriji(tipRecepta));
        lista.setAdapter(adapter);
        lista.setOnItemClickListener(this);
        return v;
    }

    public static Recept.Tip getTipRecepta() {
        return tipRecepta;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Recept recept = adapter.getItem(position);
        Intent intent = new Intent(getContext(), ReceptActivity.class);
        int o = recept.getId();
        intent.putExtra(ReceptActivity.RECEPT_ID, recept.getId());
        startActivity(intent);
    }
}
