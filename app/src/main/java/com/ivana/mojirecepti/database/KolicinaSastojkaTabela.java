package com.ivana.mojirecepti.database;

import android.provider.BaseColumns;

public class KolicinaSastojkaTabela {
    private KolicinaSastojkaTabela() {
    }
    public static class KolicinaSastojkaKolone implements BaseColumns {
        public static final String TABLE_NAME = "kolicina_sastojaka";
        public static final String COLUMN_RECEPT_ID = "id_recept";
        public static final String COLUMN_SASTOJAK_ID = "id_sastojak";
        public static final String COLUMN_KOLICINA = "kolicina";
    }
    public static final String SQL_CREATE =
            "CREATE TABLE "+ KolicinaSastojkaKolone.TABLE_NAME + " (" +
                    KolicinaSastojkaKolone._ID + " INTEGER PRIMARY KEY, " +
                    KolicinaSastojkaKolone.COLUMN_RECEPT_ID + " INTEGER NOT NULL, " +
                    KolicinaSastojkaKolone.COLUMN_SASTOJAK_ID + " INTEGER NOT NULL, " +
                    KolicinaSastojkaKolone.COLUMN_KOLICINA + " INTEGER NOT NULL" +
                    " )";

    public static final String SQL_DELETE =
            "DROP TABLE IF EXISTS "+ KolicinaSastojkaKolone.TABLE_NAME;

}
