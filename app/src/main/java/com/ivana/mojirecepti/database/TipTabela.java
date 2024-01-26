package com.ivana.mojirecepti.database;

import android.provider.BaseColumns;

public class TipTabela {

    private TipTabela() {   }

    public static class TipKolone implements BaseColumns{
        public static final String TABLE_NAME = "tip";
        public static final String COLUMN_TIP = "tip";
    }

    public static final String SQL_CREATE =
            "CREATE TABLE "+ TipKolone.TABLE_NAME + " (" +
                    TipKolone._ID + " INTEGER PRIMARY KEY, " +
                    TipKolone.COLUMN_TIP + " TEXT NOT NULL )";

    public static final String SQL_DELETE =
            "DROP TABLE IF EXISTS "+ TipKolone.TABLE_NAME;

    public static final String SQL_INSERT =
            "INSERT INTO "+ TipKolone.TABLE_NAME+" ( "+TipKolone.COLUMN_TIP+") "+
                    " VALUES ('DORUCAK'), ('RUCAK'), ('VECERA'), ('NAPICI'), ('UZINA')" ;


}
