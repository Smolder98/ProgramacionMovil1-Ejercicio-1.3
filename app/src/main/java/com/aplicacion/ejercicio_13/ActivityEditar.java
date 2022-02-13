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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
                if(permitirEnviar()) editarPersona();
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



    private boolean permitirEnviar(){
        String mensaje = "";

        String id = txtcodigo.getText().toString();
        String nombres = txtnombres.getText().toString();
        String apellidos = txtapellidos.getText().toString();
        String correo = txtCorreo.getText().toString();
        String edad = txtedad.getText().toString();
        String direccion = txtDireccion.getText().toString();

        if(isTextEmpty(nombres)) mensaje = "El campo nombres esta vacio";
        if(isTextEmpty(id)) mensaje = "Ninguna persona a sido seleccionada";
        else if(isTextEmpty(apellidos)) mensaje = "El campo apellidos esta vacio";
        else if(isTextEmpty(edad)) mensaje = "El campo edad esta vacio";
        else if(isTextEmpty(correo)) mensaje = "El campo correo esta vacio";
        else if(isTextEmpty(direccion)) mensaje = "El campo direccion esta vacio";
        else if(!isNumeric(edad)) mensaje = "La edad debe ser numerica";
        else if(!validarCorreo(correo)) mensaje = "Correo no valido";
        else if(!isText(nombres)) mensaje = "Los nombres no son validos: Solo deben ser letras";
        else if(!isText(apellidos)) mensaje = "Los apellidos no son validos: Solo deben ser letras";


        if(mensaje.length() != 0){
            Toast.makeText(getApplicationContext(), "Error al procesar datos: " + mensaje, Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }



    /******************************************************************/

    //Si el correo es valido
    private boolean validarCorreo(String email){
        Pattern pattern = Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"+"[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

        // El email a validar
//        String email = txtcorreo.getText().toString();

        Matcher mather = pattern.matcher(email);

        if(mather.find()) {
            return true;
        }else{
//            Toast.makeText(this, "El correo ingresado no es valido: " + email, Toast.LENGTH_SHORT).show();
            return false;
        }

    }
    private static boolean isNumeric(String cadena){
        try {
            Double.parseDouble(cadena);
            return true;
        } catch (NumberFormatException nfe){
            return false;
        }
    }

    private static boolean isText(String text){

        // Validando un texto que solo acepte letras sin importar tamaño
        Pattern pat = Pattern.compile("^[a-zA-ZáéíóúÁÉÓÚÍ ]+$");
        Matcher mat = pat.matcher(text);

        return (mat.matches());
//        return (mat.matches())?true:false;

    }


    //Si el texto esta vacio
    private static boolean isTextEmpty(String text){

        return (text.length()==0)?true:false;
    }
}