package com.aplicacion.ejercicio_13;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import Tablas.Persona;

public class ActivityMostrarSeleccion extends AppCompatActivity {

    EditText txtcodigo, txtnombres, txtapellidos, txtedad, txtcorreo, txtdireccion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_seleccion);

        txtcodigo = (EditText) findViewById(R.id.txtMostrarCodigo);
        txtnombres = (EditText) findViewById(R.id.txtMostrarNombres);
        txtapellidos = (EditText) findViewById(R.id.txtMostrarApellidos);
        txtedad = (EditText) findViewById(R.id.txtMostrarEdad);
        txtcorreo = (EditText) findViewById(R.id.txtMostrarCorreo);
        txtdireccion = (EditText) findViewById(R.id.txtMostrarDireccion);



        Bundle objEnviado = getIntent().getExtras();

        Persona persona = null;

        if(objEnviado != null){
            persona = (Persona) objEnviado.getSerializable("persona");


            txtcodigo.setText(persona.getId()+"");
            txtnombres.setText(persona.getNombres());
            txtapellidos.setText(persona.getApellidos());
            txtedad.setText(persona.getEdad()+"");
            txtcorreo.setText(persona.getCorreo());
            txtdireccion.setText(persona.getDireccion());
        }
    }
}