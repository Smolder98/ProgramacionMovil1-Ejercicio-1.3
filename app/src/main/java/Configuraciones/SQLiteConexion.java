package Configuraciones;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class SQLiteConexion extends SQLiteOpenHelper {


    public SQLiteConexion(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        try{

            sqLiteDatabase.execSQL(Transacciones.CREATE_TABLE_PERSONA);

        }catch(Exception e){

            System.out.println("Error al conectar a la base de datos: " + e.getMessage());
        }


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL(Transacciones.DROP_TABLE_PERSONA);
        onCreate(sqLiteDatabase);
    }
}
