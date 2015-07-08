package com.example.exsql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class DatosSqliteHelper extends SQLiteOpenHelper {

    //Sentencia SQL para crear la tabla de datos
    String sqlCreate =  "CREATE TABLE Datos " +
                        "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        " nombre TEXT, " +
                        " telefono TEXT, " +
                        " email TEXT )";


    public DatosSqliteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreate);

        String nombre = "Edwin";
        String telefono = "5845478";
        String email = "edwin@gmail.com";

        //Insertamos los datos en la tabla Clientes

        db.execSQL("INSERT INTO Datos (nombre, telefono, email)"+
                    "VALUES ('"+nombre+"','"+telefono+"','"+email+"')");

        nombre = "Andres";
        telefono = "5845487";
        email = "Andres@gmail.com";

        db.execSQL("INSERT INTO Datos (nombre, telefono, email)"+
                "VALUES ('"+nombre+"','"+telefono+"','"+email+"')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Metodo para actualizar la tabla en este caso elimina la que exista y
        //crea una nueva

        db.execSQL("DROP TABLE IF EXISTS Datos"); //Elimina la versión anterior de la tabla

        db.execSQL(sqlCreate); //Crea de nuevo la tabla

    }
}


