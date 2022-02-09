package com.aplicacion.ejercicio_13;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import Configuraciones.Transacciones;

public class MainActivity extends AppCompatActivity {

    Button btnMenuRegistrar, btnMenuEditar, btnMenuEliminar, btnMenuSeleccionar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnMenuRegistrar = (Button) findViewById(R.id.btnMenuNuevaPersona);
        btnMenuEditar    = (Button) findViewById(R.id.btnMenuEditarPersona);
        btnMenuSeleccionar    = (Button) findViewById(R.id.btnMenuSeleccionarPersona);
        btnMenuEliminar    = (Button) findViewById(R.id.btnMenuEliminarPersona);


        btnMenuRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), ActivityRegistrar.class);
                startActivity(intent);
            }
        });

        btnMenuEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), ActivityEditar.class);
                startActivity(intent);
            }
        });

        btnMenuSeleccionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), ActivitySeleccionar.class);
                startActivity(intent);

            }
        });

        btnMenuEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), ActivityEliminar.class);
                startActivity(intent);
            }
        });


    }
}