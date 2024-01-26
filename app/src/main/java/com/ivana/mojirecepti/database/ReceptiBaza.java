package com.ivana.mojirecepti.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;
import android.widget.Toast;

import com.ivana.mojirecepti.MainActivity;
import com.ivana.mojirecepti.model.Recept;
import com.ivana.mojirecepti.model.Sastojak;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;


public class ReceptiBaza extends SQLiteOpenHelper {
    private Context context;
    public static final int VERZIJA_BAZE = 17;
    public static final String IME_BAZE = "Recepti.db";

    public ReceptiBaza( Context context) {
        super(context, IME_BAZE, null, VERZIJA_BAZE);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ReceptTabela.SQL_CREATE);
        db.execSQL(SastojakTabela.SQL_CREATE);
        db.execSQL(TipTabela.SQL_CREATE);
        db.execSQL(KolicinaSastojkaTabela.SQL_CREATE);
        db.execSQL(TipTabela.SQL_INSERT);
        execBatchSqlFromAssets("import.sql", context, db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(ReceptTabela.SQL_DELETE);
        db.execSQL(SastojakTabela.SQL_DELETE);
        db.execSQL(TipTabela.SQL_DELETE);
        db.execSQL(KolicinaSastojkaTabela.SQL_DELETE);

        onCreate(db);

    }

    public void dodajRecept (Recept r ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ReceptTabela.ReceptKolone.COLUMN_NAZIV, r.getNaziv());
        cv.put(ReceptTabela.ReceptKolone.COLUMN_KALORIJE, r.getKalorije());
        cv.put(ReceptTabela.ReceptKolone.COLUMN_OMILJENI, r.getOmiljeni());
        cv.put(ReceptTabela.ReceptKolone.COLUMN_PRIPREMA, r.getPriprema());
        cv.put(ReceptTabela.ReceptKolone.COLUMN_SLIKA, r.getSlika());
        cv.put(ReceptTabela.ReceptKolone.COLUMN_TIP_ID, r.getTip().getValue());
        cv.put(ReceptTabela.ReceptKolone.COLUMN_VREME_PRIPREME, r.getVremePripreme());

        int idRecept = (int) db.insert(ReceptTabela.ReceptKolone.TABLE_NAME, null, cv);
        if (idRecept == -1){
            Toast.makeText(context,"Greska: Recept nije upisan.", Toast.LENGTH_LONG).show();
        }

        cv = new ContentValues();
        for (Sastojak s: r.getSastojci()) {
            String[] projection = {SastojakTabela.SastojakKolone._ID};
            String selection = SastojakTabela.SastojakKolone.COLUMN_SASTOJAK + " LIKE ?" ;
            String[] selectionArgs = {s.getNaziv()};
            Cursor cursor = db.query(SastojakTabela.SastojakKolone.TABLE_NAME, projection, selection,
                    selectionArgs, null, null, null);
            if (cursor != null) cursor.moveToFirst();
            int idSastojak = cursor.getInt(cursor.getColumnIndex(SastojakTabela.SastojakKolone._ID));
            cv.put(KolicinaSastojkaTabela.KolicinaSastojkaKolone.COLUMN_RECEPT_ID, idRecept);
            cv.put(KolicinaSastojkaTabela.KolicinaSastojkaKolone.COLUMN_KOLICINA, s.getKolicina());
            cv.put(KolicinaSastojkaTabela.KolicinaSastojkaKolone.COLUMN_SASTOJAK_ID, idSastojak);
            db.insert(KolicinaSastojkaTabela.KolicinaSastojkaKolone.TABLE_NAME, null, cv);
        }

    }

    private static void execBatchSqlFromAssets(String asset, Context c, SQLiteDatabase db) {
        InputStream input;
        String sql = "";
        try {
            input = c.getAssets().open(asset);
            // myData.txt can't be more than 2 gigs.
            int size = input.available();
            byte[] buffer = new byte[size];
            input.read(buffer);
            input.close();

            // byte buffer into a string
            sql = new String(buffer);

            Log.d(TAG, "Sadrzaj sql: " + sql);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        String[] queries = sql.split(";");
        for (String query : queries) {
            db.execSQL(query);
        }
    }

    public ArrayList<Recept> getReceptiPoKategoriji(Recept.Tip tip){
        ArrayList<Recept> lista = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String[] projection = {
                ReceptTabela.ReceptKolone._ID,
                ReceptTabela.ReceptKolone.COLUMN_NAZIV,
                ReceptTabela.ReceptKolone.COLUMN_VREME_PRIPREME,
                ReceptTabela.ReceptKolone.COLUMN_KALORIJE,
                ReceptTabela.ReceptKolone.COLUMN_SLIKA
                };
        String selection = ReceptTabela.ReceptKolone.COLUMN_TIP_ID + " = ?" ;
        String[] selectionArgs = {String.valueOf(tip.getValue())};
        Cursor cursor = db.query(ReceptTabela.ReceptKolone.TABLE_NAME, projection, selection,
                selectionArgs, null, null, null);
        while(cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex(ReceptTabela.ReceptKolone._ID));
            String naziv = cursor.getString(cursor.getColumnIndex(ReceptTabela.ReceptKolone.COLUMN_NAZIV));
            int kalorije = cursor.getInt(cursor.getColumnIndex(ReceptTabela.ReceptKolone.COLUMN_KALORIJE));
            int vremePripreme = cursor.getInt(cursor.getColumnIndex(ReceptTabela.ReceptKolone.COLUMN_VREME_PRIPREME));
            String slika = cursor.getString(cursor.getColumnIndex(ReceptTabela.ReceptKolone.COLUMN_SLIKA));
            Recept r = new Recept(id, naziv, vremePripreme, kalorije, slika);
            lista.add(r);
        }

        cursor.close();
        return lista;
    }

    public ArrayList<Recept> getReceptiSve(){
        ArrayList<Recept> lista = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String[] projection = {
                ReceptTabela.ReceptKolone._ID,
                ReceptTabela.ReceptKolone.COLUMN_NAZIV,
                ReceptTabela.ReceptKolone.COLUMN_VREME_PRIPREME,
                ReceptTabela.ReceptKolone.COLUMN_KALORIJE,
                ReceptTabela.ReceptKolone.COLUMN_SLIKA
        };

        Cursor cursor = db.query(ReceptTabela.ReceptKolone.TABLE_NAME, projection, null,
                null, null, null, null);
        while(cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex(ReceptTabela.ReceptKolone._ID));
            String naziv = cursor.getString(cursor.getColumnIndex(ReceptTabela.ReceptKolone.COLUMN_NAZIV));
            int kalorije = cursor.getInt(cursor.getColumnIndex(ReceptTabela.ReceptKolone.COLUMN_KALORIJE));
            int vremePripreme = cursor.getInt(cursor.getColumnIndex(ReceptTabela.ReceptKolone.COLUMN_VREME_PRIPREME));
            String slika = cursor.getString(cursor.getColumnIndex(ReceptTabela.ReceptKolone.COLUMN_SLIKA));
            Recept r = new Recept(id, naziv, vremePripreme, kalorije, slika);
            lista.add(r);
        }

        cursor.close();
        return lista;
    }

    public ArrayList<Recept> getOmiljeniPoKategoriji(Recept.Tip tip){
        ArrayList<Recept> lista = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String[] projection = {
                ReceptTabela.ReceptKolone._ID,
                ReceptTabela.ReceptKolone.COLUMN_NAZIV,
                ReceptTabela.ReceptKolone.COLUMN_VREME_PRIPREME,
                ReceptTabela.ReceptKolone.COLUMN_KALORIJE,
                ReceptTabela.ReceptKolone.COLUMN_SLIKA
        };

        String selection = ReceptTabela.ReceptKolone.COLUMN_TIP_ID + " = ? AND " + ReceptTabela.ReceptKolone.COLUMN_OMILJENI + " = ?";
        String[] selectionArgs = {String.valueOf(tip.getValue()), String.valueOf(1)};
        Cursor cursor = db.query(ReceptTabela.ReceptKolone.TABLE_NAME, projection, selection,
                selectionArgs, null, null, null);
        while(cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex(ReceptTabela.ReceptKolone._ID));
            String naziv = cursor.getString(cursor.getColumnIndex(ReceptTabela.ReceptKolone.COLUMN_NAZIV));
            int kalorije = cursor.getInt(cursor.getColumnIndex(ReceptTabela.ReceptKolone.COLUMN_KALORIJE));
            int vremePripreme = cursor.getInt(cursor.getColumnIndex(ReceptTabela.ReceptKolone.COLUMN_VREME_PRIPREME));
            String slika = cursor.getString(cursor.getColumnIndex(ReceptTabela.ReceptKolone.COLUMN_SLIKA));
            Recept r = new Recept(id, naziv, vremePripreme, kalorije, slika);
            lista.add(r);
        }

        cursor.close();
        return lista;
    }

    public ArrayList<Sastojak> getSastojciSve(){
        ArrayList<Sastojak> lista = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String[] projection = {
                SastojakTabela.SastojakKolone._ID,
                SastojakTabela.SastojakKolone.COLUMN_SASTOJAK,
                SastojakTabela.SastojakKolone.COLUMN_MERNA_JEDINICA
        };

        Cursor cursor = db.query(SastojakTabela.SastojakKolone.TABLE_NAME, projection, null,
                null, null, null, null);
        while(cursor.moveToNext()){
            String sastojak = cursor.getString(cursor.getColumnIndex(SastojakTabela.SastojakKolone.COLUMN_SASTOJAK));
            String mernaJedinica = cursor.getString(cursor.getColumnIndex(SastojakTabela.SastojakKolone.COLUMN_MERNA_JEDINICA));
            Sastojak r = new Sastojak( sastojak, mernaJedinica );
            lista.add(r);
        }
        cursor.close();
        return lista;
    }

    public Recept getReceptPoID (int id){
        ArrayList<Sastojak> sastojci = getSastojciPoReceptu(id);
        Recept r = new Recept();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = ReceptTabela.ReceptKolone._ID + " = ?" ;
        String[] selectionArgs = {String.valueOf(id)};
        Cursor cursor = db.query(ReceptTabela.ReceptKolone.TABLE_NAME, null, selection,
                selectionArgs, null, null, null);
        if (cursor.moveToFirst()) {
            String naziv = cursor.getString(cursor.getColumnIndex(ReceptTabela.ReceptKolone.COLUMN_NAZIV));
            int kalorije = cursor.getInt(cursor.getColumnIndex(ReceptTabela.ReceptKolone.COLUMN_KALORIJE));
            int vremePripreme = cursor.getInt(cursor.getColumnIndex(ReceptTabela.ReceptKolone.COLUMN_VREME_PRIPREME));
            int omiljeni = cursor.getInt(cursor.getColumnIndex(ReceptTabela.ReceptKolone.COLUMN_OMILJENI));
            int tipID = cursor.getInt(cursor.getColumnIndex(ReceptTabela.ReceptKolone.COLUMN_TIP_ID));
            String slika = cursor.getString(cursor.getColumnIndex(ReceptTabela.ReceptKolone.COLUMN_SLIKA));
            String priprema = cursor.getString(cursor.getColumnIndex(ReceptTabela.ReceptKolone.COLUMN_PRIPREMA));
            Recept.Tip tip;
            switch (tipID) {
                case 1:
                    tip= Recept.Tip.DORUCAK;
                    break;
                case 2:
                    tip= Recept.Tip.RUCAK;
                    break;
                case 3:
                    tip= Recept.Tip.VECERA;
                    break;
                case 4:
                    tip= Recept.Tip.NAPICI;
                    break;
                case 5:
                    tip= Recept.Tip.UZINA;
                    break;
                default:
                    tip = Recept.Tip.DORUCAK;
            }
            r = new Recept(id, naziv,  priprema,  slika, sastojci, vremePripreme,  omiljeni,  kalorije, tip);
        }

        cursor.close();

        return r;
    }

    private ArrayList<Sastojak>  getSastojciPoReceptu(int id) {
        ArrayList<Sastojak> lista = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT sastojak.*,  kolicina_sastojaka.kolicina FROM sastojak INNER JOIN kolicina_sastojaka ON sastojak._id = kolicina_sastojaka.id_sastojak\n" +
                "WHERE kolicina_sastojaka.id_recept = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id)});
        while(cursor.moveToNext()){
            String sastojak = cursor.getString(cursor.getColumnIndex(SastojakTabela.SastojakKolone.COLUMN_SASTOJAK));
            String mernaJedinica = cursor.getString(cursor.getColumnIndex(SastojakTabela.SastojakKolone.COLUMN_MERNA_JEDINICA));
            int kolicina = cursor.getInt(cursor.getColumnIndex(KolicinaSastojkaTabela.KolicinaSastojkaKolone.COLUMN_KOLICINA));
            Sastojak r = new Sastojak( sastojak, mernaJedinica, kolicina);
            lista.add(r);
        }

        cursor.close();
        return lista;
    }

    public void ukloniIzOmiljenog (int id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ReceptTabela.ReceptKolone.COLUMN_OMILJENI, 0);
        String selection = ReceptTabela.ReceptKolone._ID + " = ?" ;
        String[] selectionArgs = {String.valueOf(id)};
        int count = db.update(ReceptTabela.ReceptKolone.TABLE_NAME, cv, selection, selectionArgs);
    }
    public void dodajUOmiljeni (int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ReceptTabela.ReceptKolone.COLUMN_OMILJENI, 1);
        String selection = ReceptTabela.ReceptKolone._ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};
        int count = db.update(ReceptTabela.ReceptKolone.TABLE_NAME, cv, selection, selectionArgs);

    }


}
