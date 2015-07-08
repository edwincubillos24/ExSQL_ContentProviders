package com.example.exsql;

import com.example.exsql.DatosProvider.Datos;
import android.app.Activity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends Activity {

    private Button btnInsert, btnQuery, btnDelete, btnCalls;
    private TextView txtResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtResults = (TextView) findViewById(R.id.TxtResults);
        btnQuery = (Button) findViewById(R.id.BtnQuery);
        btnInsert = (Button) findViewById(R.id.BtnInsert);
        btnDelete = (Button) findViewById(R.id.BtnDelete);
        btnCalls = (Button) findViewById(R.id.BtnCalls);

        btnQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] projection = new String[]{
                        Datos._ID,
                        Datos.COL_NOMBRE,
                        Datos.COL_TELEFONO,
                        Datos.COL_EMAIL};

                Uri datosUri = DatosProvider.CONTENT_URI;

                ContentResolver cr = getContentResolver();

                Cursor cur = cr.query(datosUri,
                        projection, //columnas a devolver
                        null,       //Condicion del query
                        null,       //Argumentos variables de la query
                        null);      //Orden de los resultados

                if (cur.moveToFirst()){
                    String nombre, telefono, email;

                    int colNombre = cur.getColumnIndex(Datos.COL_NOMBRE);
                    int colTelefono = cur.getColumnIndex(Datos.COL_TELEFONO);
                    int colEmail = cur.getColumnIndex(Datos.COL_EMAIL);

                    txtResults.setText("");

                    do{
                        nombre=cur.getString(colNombre);
                        telefono = cur.getString(colTelefono);
                        email = cur.getString(colEmail);

                        txtResults.append(nombre + " - "+telefono+" - "+ email);
                    }while (cur.moveToNext());
                }
            }
        });

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();

                values.put(Datos.COL_NOMBRE, "Carlitos");
                values.put(Datos.COL_TELEFONO, "54547454");
                values.put(Datos.COL_EMAIL,"carlos@gmail.com");

                ContentResolver cr = getContentResolver();

                cr.insert(DatosProvider.CONTENT_URI, values);

            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentResolver cr = getContentResolver();

                cr.delete(DatosProvider.CONTENT_URI,
                        Datos.COL_NOMBRE + " = 'Carlitos'", null );
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
