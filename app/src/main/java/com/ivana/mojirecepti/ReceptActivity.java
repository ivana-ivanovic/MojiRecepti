package com.ivana.mojirecepti;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ivana.mojirecepti.asynctask.DownloadImageTask;
import com.ivana.mojirecepti.database.ReceptiBaza;
import com.ivana.mojirecepti.model.Recept;
import com.ivana.mojirecepti.model.Sastojak;

import java.util.ArrayList;

public class ReceptActivity extends AppCompatActivity implements View.OnClickListener {
    Recept r;
    ReceptiBaza baza;
    public static final String RECEPT_ID = "recept_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recept);

        int receptId = getIntent().getIntExtra(RECEPT_ID, 0);

        Log.d("ReceptActivity", "receptId=" + receptId);

        baza = new ReceptiBaza(this);
        r = baza.getReceptPoID(receptId);

        ImageView slika = findViewById(R.id.slikaReceptA);
        ImageButton dugmeSlikaReceptA = findViewById(R.id.dugmeSlikaReceptA);
        TextView labelReceptANaziv = findViewById(R.id.labelReceptANaziv);
        TextView labelReceptAKalorije = findViewById(R.id.labelReceptAKalorije);
        ListView listReceptASastojci = findViewById(R.id.listReceptASastojci);
        TextView labelReceptAVremePripreme = findViewById(R.id.labelReceptAVremePripreme);
        TextView labelReceptAPriprema = findViewById(R.id.labelReceptAPriprema);

        new DownloadImageTask(slika).execute(r.getSlika());
        labelReceptANaziv.setText(r.getNaziv());
        labelReceptAKalorije.setText( String.valueOf(r.getKalorije()) + " kcal.");
        labelReceptAVremePripreme.setText("Priprema: " + String.valueOf(r.getVremePripreme()) + " min.\n" + String.valueOf(r.getPriprema()));
        labelReceptAPriprema.setText(r.getPriprema());

        ArrayList<String> listaSastojakaString = new ArrayList<>();
        for (Sastojak s: r.getSastojci()) {
            listaSastojakaString.add(s.toString());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.sastojak_red_recept_activity, listaSastojakaString);
        listReceptASastojci.setAdapter(adapter);

        dugmeSlikaReceptA.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int o = r.getOmiljeni();
        if (o == 1) {
            baza.ukloniIzOmiljenog(r.getId());
            Toast.makeText(this,"Recept je uklonjen iz omiljenih.", Toast.LENGTH_LONG).show();
        } else if (o == 0) {
            baza.dodajUOmiljeni(r.getId());
            Toast.makeText(this,"Recept je dodat u omiljene", Toast.LENGTH_LONG).show();
        }
    }
}