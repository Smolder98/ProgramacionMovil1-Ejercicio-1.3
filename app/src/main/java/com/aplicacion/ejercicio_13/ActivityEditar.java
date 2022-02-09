package com.aplicacion.ejercicio_13;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import Configuraciones.SQLiteConexion;
import Configuraciones.Transacciones;
import Tablas.Persona;

public class ActivityEditar extends AppCompatActivity {

    SQLiteConexion conexion;
    Spinner spinnerPersonas;
    EditText txtcodigo, txtnombres, txtapellidos, txtedad, txtCorreo, txtDireccion;
    Button btnEditar;

    ArrayList<String> lista_personas_string;
    ArrayList<Persona> lista_obj_personas;

    ArrayAdapter<CharSequence> arrayAdapterPersonas;

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);
        
        conexion = new SQLiteConexion(this, Transacciones.NAME_DATABASE, null, 1);
        
        spinnerPersonas = (Spinner) findViewById(R.id.spinnerPersonas);
        btnEditar = (Button) findViewById(R.id.btnEditarPersona);
        
        txtcodigo = (EditText) findViewById(R.id.txtEditarCodigo);
        txtnombres = (EditText) findViewById(R.id.txtEditarNombres);
        txtapellidos = (EditText) findViewById(R.id.txtEditarApellidos);
        txtedad = (EditText) findViewById(R.id.txtEditarEdad);
        txtCorreo = (EditText) findViewById(R.id.txtEditarCorreo);
        txtDireccion = (EditText) findViewById(R.id.txtEditarDireccion);

        
        obtenerListaPersonas();

        llenarSpiner();


        spinnerPersonas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                txtcodigo.setText(lista_obj_personas.get(i).getId() + "");
                txtnombres.setText(lista_obj_personas.get(i).getNombres());
                txtapellidos.setText(lista_obj_personas.get(i).getApellidos());
                txtedad.setText(lista_obj_personas.get(i).getEdad() + "");
                txtCorreo.setText(lista_obj_personas.get(i).getCorreo());
                txtDireccion.setText(lista_obj_personas.get(i).getDireccion());

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editarPersona();
            }
        });
    }

    private void llenarSpiner() {
        arrayAdapterPersonas = null;
        arrayAdapterPersonas = new ArrayAdapter(this, android.R.layout.simple_spinner_item, lista_personas_string);
        spinnerPersonas.setAdapter(arrayAdapterPersonas);
    }

    private void editarPersona(){

        SQLiteConexion conexion = new SQLiteConexion(this, Transacciones.NAME_DATABASE, null, 1);
        SQLiteDatabase database = conexion.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(Transacciones.ID, txtcodigo.getText().toString());
        values.put(Transacciones.NOMBRES, txtnombres.getText().toString());
        values.put(Transacciones.APELLIDOS, txtapellidos.getText().toString());
        values.put(Transacciones.EDAD, txtedad.getText().toString());
        values.put(Transacciones.CORREO, txtCorreo.getText().toString());
        values.put(Transacciones.DIRECCION, txtDireccion.getText().toString());

        Long result = database.replace(Transacciones.TABLA_PERSONA, Transacciones.ID, values);

        Toast.makeText(getApplicationContext(), "Actualizacion Exitosa!! Codigo de persona: " + result.toString()
                ,Toast.LENGTH_LONG).show();

        limpiarPantalla();

        obtenerListaPersonas();

        llenarSpiner();
    }

    private void limpiarPantalla() {

        txtcodigo.setText("");
        txtnombres.setText("");
        txtapellidos.setText("");
        txtedad.setText("");
        txtCorreo.setText("");
        txtDireccion.setText("");
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
}