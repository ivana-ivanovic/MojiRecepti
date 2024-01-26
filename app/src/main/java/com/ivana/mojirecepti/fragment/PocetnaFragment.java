package com.ivana.mojirecepti.fragment;

import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.ivana.mojirecepti.R;
import com.ivana.mojirecepti.ReceptActivity;
import com.ivana.mojirecepti.adapter.ReceptAdapter;
import com.ivana.mojirecepti.database.ReceptiBaza;
import com.ivana.mojirecepti.model.Recept;

public class PocetnaFragment extends Fragment implements AdapterView.OnItemClickListener{
    private ReceptAdapter adapter;
    public PocetnaFragment(){}
    public static PocetnaFragment newInstance(){
        PocetnaFragment fragment = new PocetnaFragment();
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pocetna, container, false);

        TextView labelPocetnaKategorija = v.findViewById(R.id.labelPocetnaKategorija);
        ReceptiBaza baza = new ReceptiBaza(getContext());
        Calendar trenutnoVreme = Calendar.getInstance();


        int trenutniSat = trenutnoVreme.get(Calendar.HOUR_OF_DAY);
        if (trenutniSat >= 7 && trenutniSat <= 11) {
            labelPocetnaKategorija.setText(R.string.menu_dorucak);
            ListView lista = v.findViewById(R.id.listPocetnaKategorije);
            adapter = new ReceptAdapter(getContext(), R.layout.vertikalni_recept_red, baza.getReceptiPoKategoriji(Recept.Tip.DORUCAK));
            lista.setAdapter(adapter);
            lista.setOnItemClickListener(this);
        }else   if (trenutniSat >= 12 && trenutniSat <= 17) {
            labelPocetnaKategorija.setText(R.string.menu_rucak);
            ListView lista = v.findViewById(R.id.listPocetnaKategorije);
            adapter = new ReceptAdapter(getContext(), R.layout.vertikalni_recept_red, baza.getReceptiPoKategoriji(Recept.Tip.RUCAK));
            lista.setAdapter(adapter);
            lista.setOnItemClickListener(this);
        } else  if (trenutniSat >= 18 && trenutniSat <= 23) {
            labelPocetnaKategorija.setText(R.string.menu_vecera);
            ListView lista = v.findViewById(R.id.listPocetnaKategorije);
            adapter = new ReceptAdapter(getContext(), R.layout.vertikalni_recept_red, baza.getReceptiPoKategoriji(Recept.Tip.VECERA));
            lista.setAdapter(adapter);
            lista.setOnItemClickListener(this);
        }

        return v;
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
