package com.example.exsql;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatosProvider extends ContentProvider {

    private static final String uri = "content://com.example.exsql/datos";
    public static final Uri CONTENT_URI = Uri.parse(uri);

    public static final class Datos implements BaseColumns{
        private Datos(){}

        //Nombre de columnas
        public static final String COL_NOMBRE = "nombre";
        public static final String COL_TELEFONO = "telefono";
        public static final String COL_EMAIL="email";
    }

    private DatosSqliteHelper datosDB;
    private static final String BD_NOMBRE = "DBDatos";
    private static final int BD_VERSION = 1;
    private static final String TABLA_DATOS = "Datos";

    //UriMatcher
    private static final int DATOS = 1;
    private static final int DATOS_ID = 2;
    private static final UriMatcher uriMatcher;

    //Se inicializa el UriMatcher
    static{
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI("com.example.sql","datos",DATOS);
        uriMatcher.addURI("com.example.sql","datos/#",DATOS_ID);
    }


    @Override
    public boolean onCreate() {
        datosDB = new DatosSqliteHelper(getContext(), BD_NOMBRE, null, BD_VERSION);
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection,
                        String selection, String[] selectionArgs, String sortOrder){

        //por ID
        String where = selection;
        if (uriMatcher.match(uri) == DATOS_ID){
            where = "_id" + uri.getLastPathSegment();
        }

        SQLiteDatabase db = datosDB.getWritableDatabase();

        Cursor c = db.query(TABLA_DATOS, projection, where, selectionArgs, null,
                null, sortOrder);

        return c;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long regId = 1;
        SQLiteDatabase db = datosDB.getWritableDatabase();

        regId = db.insert(TABLA_DATOS,null,values);

        Uri newUri = ContentUris.withAppendedId(CONTENT_URI, regId);

        return newUri;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int cont;

        String where = selection;
        if (uriMatcher.match(uri)==DATOS_ID){
            where = "_id="+uri.getLastPathSegment();
        }

        SQLiteDatabase db = datosDB.getWritableDatabase();

        cont = db.update(TABLA_DATOS, values, where, selectionArgs);
        return cont;

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int cont;
        String where = selection;

        if (uriMatcher.match(uri)==DATOS_ID){
            where = "_id"+uri.getLastPathSegment();
        }
        SQLiteDatabase db = datosDB.getWritableDatabase();

        cont=db.delete(TABLA_DATOS, where, selectionArgs);

        return cont;
    }

    @Override
    public String getType(Uri uri) {
        int match = uriMatcher.match(uri);

        switch (match){
            case DATOS:
                return "vnd.android.cursor.dir/vnd.example.cliente";
            case DATOS_ID:
                return "vnd.android.cursor.item/vnd.example.cliente";
            default:
                return null;
        }
    }





}
