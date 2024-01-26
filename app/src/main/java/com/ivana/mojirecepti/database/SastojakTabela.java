package com.ivana.mojirecepti.database;

import android.provider.BaseColumns;

public class SastojakTabela {

    private SastojakTabela() { }
    public static class SastojakKolone implements BaseColumns {
        public static final String TABLE_NAME = "sastojak";
        public static final String COLUMN_SASTOJAK = "sastojak";
        public static final String COLUMN_MERNA_JEDINICA = "merna_jedinica";
    }

    public static final String SQL_CREATE =
            "CREATE TABLE "+ SastojakKolone.TABLE_NAME + " (" +
                    SastojakKolone._ID + " INTEGER PRIMARY KEY, " +
                    SastojakKolone.COLUMN_SASTOJAK + " TEXT NOT NULL UNIQUE, " +
                    SastojakKolone.COLUMN_MERNA_JEDINICA + " TEXT NOT NULL " +
                    " )";

    public static final String SQL_DELETE =
            "DROP TABLE IF EXISTS "+ SastojakKolone.TABLE_NAME;
}
