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
                //**********************************TRIGGERS***********************************************//
                db.execSQL("CREATE TRIGGER Fk1 " +
                        "BEFORE INSERT ON nota FOR EACH ROW " +
                        "BEGIN " +
                        "SELECT CASE WHEN ((SELECT carnet FROM alumno WHERE carnet = NEW.carnet) IS NULL) THEN RAISE(ABORT, 'No existe alumno') " +
                        "END; "+
                        "END;");

                db.execSQL("CREATE TRIGGER Fk2 " +
                        "BEFORE INSERT ON nota FOR EACH ROW " +
                        "BEGIN " +
                        "SELECT CASE WHEN ((SELECT codmateria FROM materia WHERE codmateria = NEW.codmateria) IS NULL) THEN RAISE(ABORT, 'No existe materia') " +
                        "END; "+
                        "END;");


                db.execSQL("CREATE TRIGGER nota1 " +
                        "AFTER UPDATE OF notafinal ON nota " +
                        "FOR EACH ROW WHEN new.notafinal>=6 AND old.notafinal<6 " +
                        "BEGIN " +
                        "UPDATE alumno SET matganadas=matganadas+1 " +
                        "WHERE alumno.carnet=new.carnet ; " +
                        "END; ");
                db.execSQL("CREATE TRIGGER nota2 " +
                        "AFTER UPDATE OF notafinal ON nota " +
                        "FOR EACH ROW WHEN new.notafinal<6 AND old.notafinal>=6 " +
                        "BEGIN " +
                        "UPDATE alumno SET matganadas=matganadas-1 WHERE alumno.carnet=new.carnet; " +
                        "END; ");


                //******************************************************************************************//
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

    public String eliminar2(Alumno alumno){
        //Eliminacion Restringida
        String regAfectados="filas afectadas= ";
        int contador=0;
        if (verificarIntegridad(alumno,3)) {
            regAfectados="Error, existen registros asociados";
        }
        else
        {
            contador+=db.delete("alumno", "carnet='"+alumno.getCarnet()+"'", null);
            regAfectados+=contador;
        }


        return regAfectados;
    }

//********************************************CRUD MATERIA***************************************************************
    public String insertar (Materia materia){
    String regInsertados ="Registro Insertado N = ";
    long contador = 0;

    ContentValues mate = new ContentValues();
    mate.put("codmateria",materia.getCodmateria());
    mate.put("nommateria",materia.getNommateria());
    mate.put("unidadesval",materia.getUnidadesval());
    contador=db.insert("materia",null,mate);

    if(contador==-1 || contador==0){
        regInsertados="ERROR AL INSERTAR REGISTRO";

    }else{
        regInsertados=regInsertados+contador;
    }
    return regInsertados;
}

    public Materia consultarMateria(String codigoMat){

    String[] id={codigoMat};

    Cursor cursor = db.query("materia",camposMateria,"codmateria = ?",id,null,null,null);
    if(cursor.moveToFirst()){
    Materia materia = new Materia();
    materia.setCodmateria(cursor.getString(0));
    materia.setNommateria(cursor.getString(1));
    materia.setUnidadesval(cursor.getString(2));
    return materia;
    }else{
        return null;
    }

}

    public String eliminarMateria(Materia materia){
    String regAfectados="filas afectadas= ";
    int contador=0;
    if (verificarIntegridad(materia,4)) {
        contador+=db.delete("nota", "codmateria='"+materia.getCodmateria()+"'", null);
    }
    contador+=db.delete("materia", "codmateria='"+materia.getCodmateria() + "'", null);
    regAfectados += contador;
    return regAfectados;
}

    public String actualizarMateria(Materia materia){
        if(verificarIntegridad(materia, 6)){
            String[] id = {materia.getCodmateria()};
            ContentValues cv = new ContentValues();
            cv.put("nommateria", materia.getNommateria());
            cv.put("unidadesval", materia.getUnidadesval());
            db.update("materia", cv, "codmateria = ?", id);
            return "Registro Actualizado Correctamente";
        }else{
            return "Registro con carnet " + materia.getCodmateria() + " no existe";
        }

    }

//********************************************CRUD NOTA******************************************************************
    public String insertar(Nota nota){

    String regInsertados="Registro Insertado Nº= ";
    long contador=0;
    if(verificarIntegridad(nota,1))
    {
        ContentValues notas = new ContentValues();
        notas.put("carnet", nota.getCarnet());
        notas.put("codmateria", nota.getCodamateria());
        notas.put("ciclo", nota.getCiclo());
        notas.put("notafinal", nota.getNotafinal());
        contador=db.insert("nota", null, notas);
        if(contador==-1 || contador==0)
        {
            regInsertados= "Error al Insertar el registro, Registro Duplicado. Verificar inserción";
        }
        else {
            regInsertados=regInsertados+contador;
        }
    }
    else
    {
        regInsertados= "Error al Insertar el registro, No Existe Alumno o Materia. Verificar ";
    }


    return regInsertados;

}
    public String eliminarNot(Nota nota){
        String regAfectados="filas afectadas= ";
        int contador=0;
        String where="carnet='"+nota.getCarnet()+"'";
        where=where+" AND codmateria='"+nota.getCodamateria()+"'";
        where=where+" AND ciclo="+nota.getCiclo();
        contador+=db.delete("nota", where, null);
        regAfectados+=contador;
        return regAfectados;
    }
    public Nota consultarNota(String carnet, String codmateria, String ciclo){

        String[] id = {carnet, codmateria, ciclo};
        Cursor cursor = db.query("nota", camposNota, "carnet = ? AND codmateria = ? AND ciclo = ?", id, null, null, null);
        if(cursor.moveToFirst()){
            Nota nota = new Nota();
            nota.setCarnet(cursor.getString(0));
            nota.setCodamateria(cursor.getString(1));
            nota.setCiclo(cursor.getString(2));
            nota.setNotafinal(cursor.getDouble(3));
            return nota;
        }else{
            return null;
        }
    }
    public String actualizar(Nota nota){

        if(verificarIntegridad(nota, 2)){
            String[] id = {nota.getCodamateria(), nota.getCarnet(), nota.getCiclo()};
            ContentValues cv = new ContentValues();
            cv.put("notafinal", nota.getNotafinal());
            db.update("nota", cv, "codmateria = ? AND carnet = ? AND ciclo = ?", id);
            return "Registro Actualizado Correctamente";
        }else{
            return "Registro no Existe";
        }


    }
//*************************************************CONSULTAS ESPECIALES**************************************************
    public String consulta1 (String carnet){
        String[] id = {carnet};
        String [] consulta = {"count(carnet)"};
        String tempo;
        Cursor cursor = db.query("nota",consulta,"carnet=?",id,null,null,null);

        if(cursor.moveToFirst()){
            tempo=cursor.getString(0);
            return tempo;
        }else{
         return null;
        }
    }

    public Alumno consulta2 (String carnet){
        String[] id = {carnet};
        String[]campos_cons2 = new String [] {"alumno.carnet","nombre","apellido","sexo", "matganadas","sum(notafinal) total1","max(notafinal) total2","min(notafinal) total3 "};
        Cursor cursor = db.query("alumno,nota", campos_cons2, "alumno.carnet=nota.carnet and nota.carnet = ?", id, null, null, null);

        if(cursor.moveToFirst()){
            Alumno alumno = new Alumno();
            alumno.setCarnet(cursor.getString(0));
            alumno.setNombre(cursor.getString(1));
            alumno.setApellido(cursor.getString(2));
            alumno.setSexo(cursor.getString(3));
            alumno.setMatganadas(cursor.getInt(4));
            alumno.setTotal1(cursor.getInt(5));
            alumno.setTotal2(cursor.getDouble(6));
            alumno.setTotal3(cursor.getDouble(7));
            return alumno;
        }else{
            return null;
        }
    }

    public Materia consulta3(String codmateria){

        String[] id = {codmateria};
        String[]campos_cons3 = new String [] {"materia.codmateria","materia.nommateria","count(notafinal) total "};
        Cursor cursor = db.query("nota,materia", campos_cons3, "materia.codmateria=nota.codmateria and nota.codmateria = ?", id, null, null, null);
        if(cursor.moveToFirst()){
            Materia materia = new Materia();
            materia.setCodmateria(cursor.getString(0));
            materia.setNommateria(cursor.getString(1));
            materia.setCantidad_materias(cursor.getDouble(2));


            return materia;
        }else{
            return null;
        }
    }

    public Cursor consulta4(){
        Cursor    cursor = db.rawQuery("SELECT  * FROM materia ", null);
        return cursor;
    }






    //********************************************VERIFICADOR DE INTEGRIDAD**************************************************
private boolean verificarIntegridad(Object dato, int relacion) throws SQLException{

    switch(relacion){

        case 1:
        {
            //verificar que al insertar nota exista carnet del alumno y el codigo de materia
            Nota nota = (Nota)dato;
            String[] id1 = {nota.getCarnet()};
            String[] id2 = {nota.getCodamateria()};
            Cursor cursor1 = db.query("alumno", null, "carnet = ?", id1, null, null, null);
            Cursor cursor2 = db.query("materia", null, "codmateria = ?", id2, null, null, null);
            if(cursor1.moveToFirst() && cursor2.moveToFirst()){
                //Se encontraron datos
                return true;
            }
            return false;

        }

        case 2:
        {
            //verificar que al modificar nota exista carnet del alumno, el codigo de materia y el ciclo
            Nota nota1 = (Nota)dato;
            String[] ids = {nota1.getCarnet(), nota1.getCodamateria(), nota1.getCiclo()};
            abrir();
            Cursor c = db.query("nota", null, "carnet = ? AND codmateria = ? AND ciclo = ?", ids, null, null, null);
            if(c.moveToFirst()){
                //Se encontraron datos
                return true;
            }
            return false;
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

        case 4: {

            Materia materia = (Materia)dato;
            Cursor cmat=db.query(true, "nota", new String[] {"codmateria" }, "codmateria='"+materia.getCodmateria()+"'",null, null, null, null, null);
            if(cmat.moveToFirst())
                return true;
            else
                return false;
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
            Materia materia2 = (Materia)dato;
            String[] idm = {materia2.getCodmateria()};
            abrir();
            Cursor cm = db.query("materia", null, "codmateria = ?", idm, null, null, null);
            if(cm.moveToFirst()){
                //Se encontro Materia
                return true;
            }
            return false;

        }

        default:
            return false;

    }
}}
