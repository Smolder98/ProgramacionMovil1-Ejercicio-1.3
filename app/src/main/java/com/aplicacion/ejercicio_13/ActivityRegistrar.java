package com.aplicacion.ejercicio_13;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import Configuraciones.SQLiteConexion;
import Configuraciones.Transacciones;

public class ActivityRegistrar extends AppCompatActivity {

    EditText txtnombres, txtapellidos, txtedad, txtcorreo, txtdireccion;

    Button btnregistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);



        txtnombres   = (EditText) findViewById(R.id.txtRegistrarNombres);
        txtapellidos = (EditText) findViewById(R.id.txtRegistrarApellidos);
        txtedad      = (EditText) findViewById(R.id.txtRegistrarEdad);
        txtcorreo    = (EditText) findViewById(R.id.txtRegistrarCorreo);
        txtdireccion = (EditText) findViewById(R.id.txtRegistrarDireccion);



        btnregistrar = (Button) findViewById(R.id.btnRegistrarPersona);
        btnregistrar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                agregarPersona();
            }
        });
    }

    private void agregarPersona(){

        SQLiteConexion conexion = new SQLiteConexion(this, Transacciones.NAME_DATABASE, null, 1);
        SQLiteDatabase database = conexion.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(Transacciones.NOMBRES, txtnombres.getText().toString());
        values.put(Transacciones.APELLIDOS, txtapellidos.getText().toString());
        values.put(Transacciones.EDAD, txtedad.getText().toString());
        values.put(Transacciones.CORREO, txtcorreo.getText().toString());
        values.put(Transacciones.DIRECCION, txtdireccion.getText().toString());

        Long result = database.insert(Transacciones.TABLA_PERSONA, Transacciones.ID, values);

        Toast.makeText(getApplicationContext(), "Registro Exitoso!! Codigo de persona: " + result.toString()
                ,Toast.LENGTH_LONG).show();

        limpiarPantalla();
    }

    private void limpiarPantalla(){
        txtnombres.setText("");
        txtapellidos.setText("");
        txtedad.setText("");
        txtcorreo.setText("");
        txtdireccion.setText("");
    }
}