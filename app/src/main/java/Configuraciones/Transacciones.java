package Configuraciones;

public class Transacciones {

    //Nombre de la base de datos
     public static final String NAME_DATABASE = "PM1E13";

     //Creacion de la tabla persona en la base de datos
        public static final String TABLA_PERSONA = "personas";

        //Creacion de los atributos de la tabla
        public static final String ID = "id";
        public static final String NOMBRES = "nombres";
        public static final String APELLIDOS = "apellidos";
        public static final String EDAD = "edad";
        public static final String CORREO = "correo";
        public static final String DIRECCION = "direccion";


     //Creacion y eliminacion de la tabla Personas

        public static final String CREATE_TABLE_PERSONA = "CREATE TABLE " + TABLA_PERSONA +
                                                            "("+
                                                                ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                                                                NOMBRES +" TEXT, "+
                                                                APELLIDOS +" TEXT, "+
                                                                EDAD +" INTEGER, "+
                                                                CORREO +" TEXT, "+
                                                                DIRECCION +" TEXT"+
                                                            ")";



        public static final String DROP_TABLE_PERSONA = "DROP TABLE IF EXIST " + TABLA_PERSONA;






        //Seleccionar todas las personas
        public static final String SELECT_TABLE_PERSONA = "SELECT * FROM " + TABLA_PERSONA;




}
