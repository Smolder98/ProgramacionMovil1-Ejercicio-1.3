package com.aplicacion.ejercicio_13;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
                if(permitirEnviar()) agregarPersona();
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

    private boolean permitirEnviar(){
        String mensaje = "";

        String nombres = txtnombres.getText().toString();
        String apellidos = txtapellidos.getText().toString();
        String correo = txtcorreo.getText().toString();
        String edad = txtedad.getText().toString();
        String direccion = txtdireccion.getText().toString();

        if(isTextEmpty(nombres)) mensaje = "El campo nombres esta vacio";
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