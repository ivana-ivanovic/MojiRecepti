package com.ivana.mojirecepti.database;

import android.provider.BaseColumns;

public class ReceptTabela {
    private ReceptTabela() {
    }
    public static class ReceptKolone implements BaseColumns {
        public static final String TABLE_NAME = "recept";
        public static final String COLUMN_NAZIV = "naziv";
        public static final String COLUMN_PRIPREMA = "priprema";
        public static final String COLUMN_VREME_PRIPREME = "vreme_pripreme";
        public static final String COLUMN_KALORIJE = "kalorije";
        public static final String COLUMN_OMILJENI = "omiljeni";
        public static final String COLUMN_SLIKA = "slika";
        public static final String COLUMN_TIP_ID = "id_tip";
    }

    public static final String SQL_CREATE =
            "CREATE TABLE "+ ReceptKolone.TABLE_NAME + " (" +
                    ReceptKolone._ID + " INTEGER PRIMARY KEY, " +
                    ReceptKolone.COLUMN_NAZIV + " TEXT NOT NULL, " +
                    ReceptKolone.COLUMN_PRIPREMA + " TEXT NOT NULL, " +
                    ReceptKolone.COLUMN_VREME_PRIPREME + " INTEGER NOT NULL, " +
                    ReceptKolone.COLUMN_KALORIJE + " INTEGER NOT NULL, " +
                    ReceptKolone.COLUMN_OMILJENI + " INTEGER NOT NULL, " +
                    ReceptKolone.COLUMN_SLIKA + " TEXT NOT NULL, " +
                    ReceptKolone.COLUMN_TIP_ID + " INTEGER NOT NULL " +
                    " )";

    public static final String SQL_DELETE =
            "DROP TABLE IF EXISTS "+ ReceptKolone.TABLE_NAME;



}
