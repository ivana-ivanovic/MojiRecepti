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

public class OmiljeniFragment extends Fragment  {
    public OmiljeniFragment(){}
    public static OmiljeniFragment newInstance(){
        OmiljeniFragment fragment = new OmiljeniFragment();
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_omiljeni, container, false);

        ListView listaVecera = v.findViewById(R.id.listOmiljeniVecera);
        ListView listaDorucak = v.findViewById(R.id.listOmiljeniDorucak);
        ListView listaRucak = v.findViewById(R.id.listOmiljeniRucak);
        ListView listaUzina = v.findViewById(R.id.listOmiljeniUzina);
        ListView listaNapici = v.findViewById(R.id.listOmiljeniNapici);
        ReceptiBaza db = new ReceptiBaza(getContext());

        ReceptAdapter adapterVecera = new ReceptAdapter(getContext(), R.layout.vertikalni_recept_red, db.getOmiljeniPoKategoriji(Recept.Tip.VECERA));
        ReceptAdapter adapterDorucak = new ReceptAdapter(getContext(), R.layout.vertikalni_recept_red, db.getOmiljeniPoKategoriji(Recept.Tip.DORUCAK));
        ReceptAdapter adapterRucak = new ReceptAdapter(getContext(), R.layout.vertikalni_recept_red, db.getOmiljeniPoKategoriji(Recept.Tip.RUCAK));
        ReceptAdapter adapterUzina = new ReceptAdapter(getContext(), R.layout.vertikalni_recept_red, db.getOmiljeniPoKategoriji(Recept.Tip.UZINA));
        ReceptAdapter adapterNapici = new ReceptAdapter(getContext(), R.layout.vertikalni_recept_red, db.getOmiljeniPoKategoriji(Recept.Tip.NAPICI));
        listaVecera.setAdapter(adapterVecera);
        listaDorucak.setAdapter(adapterDorucak);
        listaRucak.setAdapter(adapterRucak);
        listaUzina.setAdapter(adapterUzina);
        listaNapici.setAdapter(adapterNapici);

        listaVecera.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Recept recept = adapterVecera.getItem(position);
                pokreniReceptActivity(recept.getId());
            }
        });

        listaDorucak.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Recept recept = adapterDorucak.getItem(position);
                pokreniReceptActivity(recept.getId());
            }
        });

        listaRucak.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Recept recept = adapterRucak.getItem(position);
                pokreniReceptActivity(recept.getId());
            }
        });

        listaUzina.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Recept recept = adapterUzina.getItem(position);
                pokreniReceptActivity(recept.getId());
            }
        });

        listaNapici.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Recept recept = adapterNapici.getItem(position);
                pokreniReceptActivity(recept.getId());
            }
        });

        return v;
    }

    private void pokreniReceptActivity(int receptId) {
        Intent intent = new Intent(getContext(), ReceptActivity.class);
        intent.putExtra(ReceptActivity.RECEPT_ID, receptId);
        startActivity(intent);
    }


}
