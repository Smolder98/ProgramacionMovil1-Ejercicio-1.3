package com.aplicacion.ejercicio_13;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import Configuraciones.SQLiteConexion;
import Configuraciones.Transacciones;
import Tablas.Persona;

public class ActivitySeleccionar extends AppCompatActivity {

    SQLiteConexion conexion;

    ListView listViewPersonas;

    ArrayList<String> lista_personas_string;
    ArrayList<Persona> lista_obj_personas;
    ArrayAdapter arrayAdapterPersonas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccionar);


        conexion = new SQLiteConexion(this, Transacciones.NAME_DATABASE, null, 1);

        listViewPersonas = (ListView) findViewById(R.id.listViewPersonas);

        obtenerListaPersonas();

        llenarlist();

        listViewPersonas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Persona persona = lista_obj_personas.get(i);

                Bundle bundle = new Bundle();
                bundle.putSerializable("persona", persona);

                Intent intent = new Intent(getApplicationContext(), ActivityMostrarSeleccion.class);
                intent.putExtras(bundle);

                startActivity(intent);


            }
        });

    }

    private void obtenerListaPersonas() {

        Persona persona = null;

        lista_obj_personas = new ArrayList<Persona>();

        SQLiteDatabase database = conexion.getReadableDatabase();

        Cursor cursor = database.rawQuery(Transacciones.SELECT_TABLE_PERSONA, null);

        while (cursor.moveToNext()){

            persona = new Persona();

            persona.setId(cursor.getInt(0));
            persona.setNombres(cursor.getString(1));
            persona.setApellidos(cursor.getString(2));
            persona.setEdad(cursor.getInt(3));
            persona.setCorreo(cursor.getString(4));
            persona.setDireccion(cursor.getString(5));

            lista_obj_personas.add(persona);

        }

        cursor.close();

        llenarListaString();
    }

    private void llenarListaString() {
        lista_personas_string = new ArrayList<String>();

        for (Persona p: lista_obj_personas) {
            lista_personas_string.add(
                    p.getId() + " | " + p.getNombres()+" "+ p.getApellidos()
            );
        }
    }

    private void llenarlist() {
        arrayAdapterPersonas = null;
        arrayAdapterPersonas = new ArrayAdapter(this, android.R.layout.simple_list_item_1, lista_personas_string);
        listViewPersonas.setAdapter(arrayAdapterPersonas);
    }
}