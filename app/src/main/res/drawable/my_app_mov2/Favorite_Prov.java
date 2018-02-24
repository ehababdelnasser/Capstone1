package com.example.lenovo.my_app_mov2;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Lenovo on 06/10/2017.
 */

public class Favorite_Prov extends ContentProvider {

    public static final int Data = 1;
    public static final int Data_by_Id = 2;
    private static final UriMatcher Uri_Match = getUriMatcher();
    private com.example.lenovo.my_app_mov2.Data_Base Db_inst;

    private static UriMatcher getUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(com.example.lenovo.my_app_mov2.Conr.Prov_N, com.example.lenovo.my_app_mov2.Conr.con_pth, Data);
        uriMatcher.addURI(com.example.lenovo.my_app_mov2.Conr.Prov_N, com.example.lenovo.my_app_mov2.Conr.con_pth + "/#", Data_by_Id);
        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        Db_inst = new com.example.lenovo.my_app_mov2.Data_Base(context);
        return true;
    }

    @Nullable
    @Override


    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        int match = Uri_Match.match(uri);
        SQLiteDatabase db = Db_inst.getWritableDatabase();
        Cursor retCursor;
        switch (match) {
            case Data:
                retCursor = db.query("TABLE1", null, null, null, null, null, null);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        int match = Uri_Match.match(uri);
        switch (match) {
            case Data:
                return "vnd.android.cursor.dir" + "/" + com.example.lenovo.my_app_mov2.Conr.Prov_N + "/" + com.example.lenovo.my_app_mov2.Conr.con_pth;
            case Data_by_Id:
                return "vnd.android.cursor.item" + "/" + com.example.lenovo.my_app_mov2.Conr.Prov_N + "/" + com.example.lenovo.my_app_mov2.Conr.con_pth;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues)
    {
        Log.v("xeno",uri.toString());

        Uri returnUri = null;
        SQLiteDatabase db = Db_inst.getWritableDatabase();
        final int match = Uri_Match.match(uri);
           switch (match)
        {
            case Data:
                long id = db.insertOrThrow("TABLE1", null, contentValues);
                if (id > 0) {
                    returnUri = ContentUris.withAppendedId(com.example.lenovo.my_app_mov2.Conr.CONT_URI, id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);

        return returnUri;

    }

    @Override

    public int delete(Uri uri, String selection, String[] selectionArgs) {

        int match = Uri_Match.match(uri);
        int res;
        SQLiteDatabase db = Db_inst.getWritableDatabase();
        switch (match) {
            case Data_by_Id:
                res = db.delete("TABLE1", " CTITLE = ?", new String[]{selection});
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri error: " + uri);
        }
        if (res != -1) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return res;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 1;
    }

}