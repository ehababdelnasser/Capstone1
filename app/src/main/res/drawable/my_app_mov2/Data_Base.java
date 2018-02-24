package com.example.lenovo.my_app_mov2;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Lenovo on 06/10/2017.
 */

public class Data_Base extends SQLiteOpenHelper {
    private static final String Db_n = "movieappOp.db";
    private static final String Tab1 = "TABLE1";
    private static final int Db_V = 1;
    private static final String q =
            "CREATE TABLE  "
                    + Tab1 + "(" + Conr.B_id
                    + " integer primary key autoincrement, "
                    + Conr.CONTRACT_MOVIE_ID + " text not null,"
                    + Conr.CONTRACT_TITLE + " text not null,"
                    + Conr.CONTRACT_RELEASE_DATE + " text not null,"
                    + Conr.CONTRACT_POSTER_PATH + " text not null,"
                    + Conr.CONTRACT_OVERVIEW + " text not null,"
                    + Conr.CONTRACT_VOTEAVG + " text not null "
                    + ");";
    private static final String ubgraden =
            "ALTER TABLE "
                    + Tab1 + " ADD COLUMN " + "null" + " string;";



    public Data_Base(Context context) {
        super(context, Db_n, null, Db_V);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(q);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {}
    }


    public Boolean Check(String Title) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = null;
        res = db.rawQuery("SELECT * FROM " + Tab1 + " WHERE CTITLE = ?", new String[]{Title});
        if (res == null || res.getCount() == 0) {
            return false;
        } else {
            return true;
        }
    }

}

