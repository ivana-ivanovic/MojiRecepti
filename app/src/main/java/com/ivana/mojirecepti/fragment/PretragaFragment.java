package com.ivana.mojirecepti.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.ivana.mojirecepti.R;
import com.ivana.mojirecepti.ReceptActivity;
import com.ivana.mojirecepti.adapter.ReceptAdapter;
import com.ivana.mojirecepti.database.ReceptiBaza;
import com.ivana.mojirecepti.model.Recept;

import java.util.ArrayList;
import java.util.List;

public class PretragaFragment extends Fragment implements SearchView.OnQueryTextListener, AdapterView.OnItemClickListener {
    private ArrayList<Recept> listaRecepata;
    private ReceptAdapter adapter;

    public PretragaFragment(){}
    public static PretragaFragment newInstance(){
        PretragaFragment fragment = new PretragaFragment();
        return fragment;
    }


    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pretraga, container, false);
        ListView lista = v.findViewById(R.id.listPretraga);
        ReceptiBaza db = new ReceptiBaza(getContext());
        adapter = new ReceptAdapter(getContext(), R.layout.vertikalni_recept_red, db.getReceptiSve());
        lista.setAdapter(adapter);
        lista.setOnItemClickListener(this);
        SearchView searchNaziv = v.findViewById(R.id.searchNaziv);
        searchNaziv.setOnQueryTextListener(this);
        return v;
    }



    @Override
    public boolean onQueryTextSubmit(String query) {
        // ovo ne radi treba objekat da pretrazuje
        if (listaRecepata.contains(query)) {
            adapter.getFilter().filter(query);
        } else {
            Toast.makeText(getContext(), "Recept nije pronadjen", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adapter.getFilter().filter(newText);
        return false;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Recept recept = adapter.getItem(position);
        Intent intent = new Intent(getContext(), ReceptActivity.class);
        intent.putExtra(ReceptActivity.RECEPT_ID, recept.getId());
        startActivity(intent);
    }
}
