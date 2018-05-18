package ca14080.repasoparcial;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class ControlBDCarnet {
    private static final String[] camposAlumno = new String[]{"carnet", "nombre", "apellido", "sexo", "matganadas"};
    private static final String[] camposNota = new String[]{"codmateria", "carnet", "ciclo", "notafinal"};
    private static final String[] camposMateria = new String[]{"codmateria", "nommateria", "unidadesval"};

    private final Context context;
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;
    static final String DROP_TABLE1 = "DROP TABLE IF EXISTS Nota; ";
    static final String DROP_TABLE2 = "DROP TABLE IF EXISTS Materia; ";
    static final String DROP_TABLE3 = "DROP TABLE IF EXISTS Alumno; ";

    public ControlBDCarnet(Context ctx) {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }


    private static class DatabaseHelper extends SQLiteOpenHelper{

        private  static final String BASE_DATOS = "Alumno.s3db";
        private static final int VERSION = 1;


        public DatabaseHelper (Context context){
            super(context, BASE_DATOS,null,VERSION);
        }

        @Override

        public void onCreate (SQLiteDatabase db){

            try {
                db.execSQL("CREATE TABLE alumno (carnet VARCHAR(7) NOT NULL PRIMARY KEY, nombre VARCHAR(30),apellido VARCHAR(30), sexo VARCHAR(1), matganadas INTEGER)");
                db.execSQL("CREATE TABLE materia (codmateria VARCHAR(6) NOT NULL PRIMARY KEY, nommateria VARCHAR(30), unidadesval CHAR(1))");
                db.execSQL("CREATE TABLE nota (codmateria VARCHAR(6),carnet VARCHAR(7), ciclo CHAR(5), notafinal REAL)");
            }catch (SQLException e){
                e.printStackTrace();
            }
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
//
                db.execSQL(DROP_TABLE1);
                db.execSQL(DROP_TABLE2);
                db.execSQL(DROP_TABLE3);
                onCreate(db);
            }catch (Exception e) {

            }
        }

    }

    public void abrir() throws SQLException{
        db = DBHelper.getWritableDatabase();

        return;
    }

    public void cerrar(){
        DBHelper.close();
    }
//********************************************CRUD ESTUDIANTE************************************************************

    public  String insertar (Alumno alumno){
        String regInsertados="Registro Insertado N = ";
        long contador = 0;

        if(verificarIntegridad(alumno,5)){
            regInsertados="Error al insertar el registro";
        }else{
            ContentValues alum = new ContentValues();
            alum.put("carnet",alumno.getCarnet());
            alum.put("nombre",alumno.getNombre());
            alum.put("apellido",alumno.getApellido());
            alum.put("sexo",alumno.getSexo());
            alum.put("matganadas",alumno.getMatganadas());
            contador=db.insert("alumno",null,alum);
            regInsertados=regInsertados+contador;
        }
        return regInsertados;
    }

    public Alumno ConsultarAlumno(String carnet){
        String[] id={carnet};

        Cursor cursor = db.query("alumno",camposAlumno,"carnet = ?",id,null,null,null);
       if(cursor.moveToFirst()) {
           Alumno alumno = new Alumno();
           alumno.setCarnet(cursor.getString(0));
           alumno.setNombre(cursor.getString(1));
           alumno.setApellido(cursor.getString(2));
           alumno.setSexo(cursor.getString(3));
           alumno.setMatganadas(cursor.getInt(4));
           return alumno;
       }else{
           return null;
       }
    }

    public String eliminar(Alumno alumno){
        //Eliminando EN CASCADA
        String regAfectados="filas Afectadas = ";
        int contador=0;
        if(verificarIntegridad(alumno,3)){
            contador+=db.delete("nota","carnet = '"+alumno.getCarnet()+"'",null);
        }
        contador+=db.delete("alumno","carnet = '"+alumno.getCarnet()+"'",null);
        regAfectados+=contador;
        return regAfectados;

    }

public String actualizar(Alumno alumno){
    if(verificarIntegridad(alumno,5)){
         String[]id={alumno.getCarnet()};
         ContentValues cv = new ContentValues();
         cv.put("nombre",alumno.getNombre());
         cv.put("apellido",alumno.getApellido());
         cv.put("sexo",alumno.getSexo());
         db.update("alumno",cv,"carnet = ?",id);
         return  "Registro Actualizado Correctamente";
    }else{
        return "Registro con carnet"+alumno.getCarnet()+"NO EXISTE";
    }

}

//********************************************CRUD MATERIA***************************************************************



//********************************************CRUD NOTA******************************************************************

//********************************************VERIFICADOR DE INTEGRIDAD**************************************************
private boolean verificarIntegridad(Object dato, int relacion) throws SQLException{

    switch(relacion){

        case 1:
        {

        }

        case 2:
        {
        }

        case 3:
        {
            Alumno alumno=(Alumno)dato;
           Cursor c=db.query(true,"nota",new String[]{"carnet"},"carnet='"+alumno.getCarnet()+"'",null,null,null,null,null);
            if(c.moveToFirst())
                return true;
            else
                return false;



        }

        case 4:
        {

        }

        case 5:
        {
            Alumno alumno2=(Alumno)dato;
            String[] id={alumno2.getCarnet()};
            abrir();
            Cursor c2 = db.query("alumno",null, "carnet = ?",id,null,null,null);
            if(c2.moveToFirst()){
                return true;
            }
               return false;
        }

        case 6:
        {

        }

        default:
            return false;

    }
}}
