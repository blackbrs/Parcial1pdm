package ca14080.repasoparcial;
        import android.app.Activity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.EditText;
        import android.widget.Toast;

public class AlumnoInsertarActivity extends Activity {
 ControlBDCarnet helper;
 EditText editCarnet;
 EditText editNombre;
 EditText editApellido;
 EditText editsexo;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumno_insertar);
        helper = new ControlBDCarnet(this);
        editCarnet = (EditText) findViewById(R.id.editCarnet);
        editNombre = (EditText) findViewById(R.id.editNombre);
        editApellido = (EditText) findViewById(R.id.editApellido);
        editsexo = (EditText) findViewById(R.id.editSexo);
    }

    public void insertarAlumno (View v) {
        String carnet=editCarnet.getText().toString();
        String nombre=editNombre.getText().toString();
        String apellido=editApellido.getText().toString();
        String sexo=editsexo.getText().toString();
        String regInsertados;
        Alumno alumno = new Alumno();
        alumno.setCarnet(carnet);
        alumno.setNombre(nombre);
        alumno.setApellido(apellido);
        alumno.setSexo(sexo);
        alumno.setMatganadas(0);
        helper.abrir();
        regInsertados = helper.insertar(alumno);
        helper.cerrar();
        Toast.makeText(this,regInsertados,Toast.LENGTH_SHORT).show();
        editCarnet.setText("");
        editNombre.setText("");
        editApellido.setText("");
        editsexo.setText("");

    }
}
