package com.ivana.mojirecepti;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ivana.mojirecepti.asynctask.UploadImageTask;
import com.ivana.mojirecepti.database.ReceptiBaza;
import com.ivana.mojirecepti.model.Recept;
import com.ivana.mojirecepti.model.Sastojak;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class DodajActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener, UploadImageTask.OnUploadSlikeZavrsen {

    private ArrayList<Sastojak> sastojciSvi;
    private TextView labelDodajSastojakJedinica1;
    private TextView labelDodajSastojakJedinica2;
    private TextView labelDodajSastojakJedinica3;
    private TextView labelDodajSastojakJedinica4;
    private TextView labelDodajSastojakJedinica5;
    private ReceptiBaza baza;
    private EditText inputNazivJela;
    private EditText inputKalorije;
    private EditText inputVremePripreme;
    private Recept recept = new Recept();
    private Sastojak sastojak1;
    private Sastojak sastojak2;
    private Sastojak sastojak3;
    private Sastojak sastojak4;
    private Sastojak sastojak5;
    private ArrayAdapter spinnerTipAdapter;
    private EditText inputDodajSastojakKolicina1;
    private EditText inputDodajSastojakKolicina2;
    private EditText inputDodajSastojakKolicina3;
    private EditText inputDodajSastojakKolicina4;
    private EditText inputDodajSastojakKolicina5;
    private ImageView slika;
    private EditText inputPriprema;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodaj);

        baza = new ReceptiBaza(this);

        sastojciSvi = baza.getSastojciSve();
        ArrayList<String> sastojciNazivi = new ArrayList<>();

        sastojciNazivi.add("Izaberite sastojak");

        for (Sastojak s : sastojciSvi) {
            sastojciNazivi.add(s.getNaziv());
        }

        ArrayAdapter spinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, sastojciNazivi);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinnerDodajSastojakIzbor1 = findViewById(R.id.spinnerDodajSastojakIzbor1);
        Spinner spinnerDodajSastojakIzbor2 = findViewById(R.id.spinnerDodajSastojakIzbor2);
        Spinner spinnerDodajSastojakIzbor3 = findViewById(R.id.spinnerDodajSastojakIzbor3);
        Spinner spinnerDodajSastojakIzbor4 = findViewById(R.id.spinnerDodajSastojakIzbor4);
        Spinner spinnerDodajSastojakIzbor5 = findViewById(R.id.spinnerDodajSastojakIzbor5);

        spinnerDodajSastojakIzbor1.setAdapter(spinnerAdapter);
        spinnerDodajSastojakIzbor2.setAdapter(spinnerAdapter);
        spinnerDodajSastojakIzbor3.setAdapter(spinnerAdapter);
        spinnerDodajSastojakIzbor4.setAdapter(spinnerAdapter);
        spinnerDodajSastojakIzbor5.setAdapter(spinnerAdapter);

        spinnerDodajSastojakIzbor1.setOnItemSelectedListener(onItemSelectedListener(spinnerDodajSastojakIzbor1));
        spinnerDodajSastojakIzbor2.setOnItemSelectedListener(onItemSelectedListener(spinnerDodajSastojakIzbor2));
        spinnerDodajSastojakIzbor3.setOnItemSelectedListener(onItemSelectedListener(spinnerDodajSastojakIzbor3));
        spinnerDodajSastojakIzbor4.setOnItemSelectedListener(onItemSelectedListener(spinnerDodajSastojakIzbor4));
        spinnerDodajSastojakIzbor5.setOnItemSelectedListener(onItemSelectedListener(spinnerDodajSastojakIzbor5));

        labelDodajSastojakJedinica1 = findViewById(R.id.labelDodajSastojakJedinica1);
        labelDodajSastojakJedinica2 = findViewById(R.id.labelDodajSastojakJedinica2);
        labelDodajSastojakJedinica3 = findViewById(R.id.labelDodajSastojakJedinica3);
        labelDodajSastojakJedinica4 = findViewById(R.id.labelDodajSastojakJedinica4);
        labelDodajSastojakJedinica5 = findViewById(R.id.labelDodajSastojakJedinica5);

        inputDodajSastojakKolicina1 = findViewById(R.id.inputDodajSastojakKolicina1);
        inputDodajSastojakKolicina2 = findViewById(R.id.inputDodajSastojakKolicina2);
        inputDodajSastojakKolicina3 = findViewById(R.id.inputDodajSastojakKolicina3);
        inputDodajSastojakKolicina4 = findViewById(R.id.inputDodajSastojakKolicina4);
        inputDodajSastojakKolicina5 = findViewById(R.id.inputDodajSastojakKolicina5);

        Button dugmeSnimi = findViewById(R.id.dugmeSnime);
        dugmeSnimi.setOnClickListener(this);

        inputNazivJela = findViewById(R.id.inputNazivJela);
        inputKalorije = findViewById(R.id.inputKalorije);
        inputVremePripreme = findViewById(R.id.inputVremePripreme);

        Spinner spinnerTip = findViewById(R.id.spinnerTip);

        String[] tipovi = { "Dorucak", "Rucak", "Vecera", "Napici", "Uzina"};
        spinnerTipAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,tipovi);
        spinnerTipAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTip.setAdapter(spinnerTipAdapter);
        spinnerTip.setOnItemSelectedListener(this);

        inputPriprema = findViewById(R.id.inputPriprema);

        slika = findViewById(R.id.slika);

        Button dodajSliku = findViewById(R.id.dugmeDodajSliku);
        dodajSliku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyStoragePermissions();
            }
        });
    }

    private Sastojak getSastojakNaPosiciji(int pozicija) {
        // -1 zato sto smo dodali kao prvi sastojak u spinner tekst "Izaberite sastojak"
        if (pozicija == 0) pozicija = 1;
        return sastojciSvi.get(pozicija - 1);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            switch (String.valueOf(spinnerTipAdapter.getItem(position))) {
                case "Dorucak":
                    recept.setTip(Recept.Tip.DORUCAK);
                    break;
                case "Rucak":
                    recept.setTip(Recept.Tip.RUCAK);
                    break;
                case "Vecera":
                    recept.setTip(Recept.Tip.VECERA);
                    break;
                case "Napici":
                    recept.setTip(Recept.Tip.NAPICI);
                    break;
                case "Uzina":
                    recept.setTip(Recept.Tip.UZINA);
                    break;
            }
            return;

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        try {
            recept.setNaziv(inputNazivJela.getText().toString());
            recept.setKalorije(Integer.valueOf(inputKalorije.getText().toString()));
            recept.setVremePripreme(Integer.valueOf(inputVremePripreme.getText().toString()));
            recept.setPriprema(inputPriprema.getText().toString());
            ArrayList<Sastojak> sastojci = new ArrayList<>();

            if (sastojak1 != null && !TextUtils.isEmpty(inputDodajSastojakKolicina1.getText().toString())) {
                sastojak1.setKolicina(Integer.valueOf(inputDodajSastojakKolicina1.getText().toString()));
                sastojci.add(sastojak1);
            }

            if (sastojak2 != null && !TextUtils.isEmpty(inputDodajSastojakKolicina2.getText().toString())) {
                sastojak2.setKolicina(Integer.valueOf(inputDodajSastojakKolicina2.getText().toString()));
                sastojci.add(sastojak2);
            }

            if (sastojak3 != null && !TextUtils.isEmpty(inputDodajSastojakKolicina3.getText().toString())) {
                sastojak3.setKolicina(Integer.valueOf(inputDodajSastojakKolicina3.getText().toString()));
                sastojci.add(sastojak3);
            }

            if (sastojak4 != null && !TextUtils.isEmpty(inputDodajSastojakKolicina4.getText().toString())) {
                sastojak4.setKolicina(Integer.valueOf(inputDodajSastojakKolicina4.getText().toString()));
                sastojci.add(sastojak4);
            }

            if (sastojak5 != null && !TextUtils.isEmpty(inputDodajSastojakKolicina5.getText().toString())) {
                sastojak5.setKolicina(Integer.valueOf(inputDodajSastojakKolicina5.getText().toString()));
                sastojci.add(sastojak5);
            };

            recept.setSastojci(sastojci);
            baza.dodajRecept(recept);
            Toast.makeText(this,"Recept unet.", Toast.LENGTH_LONG).show();
            finish();
        }catch(Exception e) {
            Toast.makeText(this,"Niste uneli sva polja.", Toast.LENGTH_LONG).show();
        }

    }

    private AdapterView.OnItemSelectedListener onItemSelectedListener(final Spinner spinnerDodajSastojakIzbor) {
        AdapterView.OnItemSelectedListener onItemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) return; // izabrana je opcija "Izaberite sastojak"
                Sastojak sastojak = getSastojakNaPosiciji(position);
                switch (spinnerDodajSastojakIzbor.getId()) {
                    case R.id.spinnerDodajSastojakIzbor1:
                        labelDodajSastojakJedinica1.setText(sastojak.getMernaJedinica());
                        sastojak1 = sastojak;
                        break;
                    case R.id.spinnerDodajSastojakIzbor2:
                        labelDodajSastojakJedinica2.setText(sastojak.getMernaJedinica());
                        sastojak2 = sastojak;
                        break;
                    case R.id.spinnerDodajSastojakIzbor3:
                        labelDodajSastojakJedinica3.setText(sastojak.getMernaJedinica());
                        sastojak3 = sastojak;
                        break;
                    case R.id.spinnerDodajSastojakIzbor4:
                        labelDodajSastojakJedinica4.setText(sastojak.getMernaJedinica());
                        sastojak4 = sastojak;
                        break;
                    case R.id.spinnerDodajSastojakIzbor5:
                        labelDodajSastojakJedinica5.setText(sastojak.getMernaJedinica());
                        sastojak5 = sastojak;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
        return onItemSelectedListener;
    }

    static final int REQUEST_IMAGE_CAPTURE = 1;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        } catch (ActivityNotFoundException e) {
            // display error state to the user
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            slika.setImageBitmap(imageBitmap);
            Uri tempUri = getImageUri(getApplicationContext(), imageBitmap);
            String uri = getRealPathFromURI(tempUri);
            Log.d("ImageUri", uri);
            new UploadImageTask(this).execute(uri);
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "recept-slika", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    /**
     * Checks if the app has permission to write to device storage
     *
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     */
    private void verifyStoragePermissions() {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    this,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        } else {
            dispatchTakePictureIntent();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_EXTERNAL_STORAGE:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    dispatchTakePictureIntent();
                }  else {
                    Toast.makeText(this,"Nazalost ne mozemo nastaviti, niste dozvolili snimanje slika.", Toast.LENGTH_LONG).show();
                }
                return;
        }
    }


    @Override
    public void uploadovanaSlika(String urlSlike) {
        recept.setSlika(urlSlike);
    }
}