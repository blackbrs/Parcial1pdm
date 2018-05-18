package ca14080.repasoparcial;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AlumnoConsultarActivity extends Activity {
    ControlBDCarnet helper;
    EditText editCarnet;
    EditText editNombre;
    EditText editApellido;
    EditText editsexo;
    EditText editMatganadas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumno_consultar);
        helper = new ControlBDCarnet(this);
        editCarnet = (EditText) findViewById(R.id.editCarnet);
        editNombre = (EditText) findViewById(R.id.editNombre);
        editApellido = (EditText) findViewById(R.id.editApellido);
        editsexo = (EditText) findViewById(R.id.editSexo);
        editMatganadas = (EditText) findViewById(R.id.edtMatganadas);
    }

    public void consultarAlumno(View v){
        helper.abrir();
        Alumno alumno = helper.ConsultarAlumno(editCarnet.getText().toString());
        if(alumno==null){
            Toast.makeText(this,"Alumno con carnet" + editCarnet.getText().toString()+"No encontrado",Toast.LENGTH_LONG).show();
        }else{
             editNombre.setText(alumno.getNombre());
             editApellido.setText(alumno.getApellido());
             editsexo.setText(alumno.getSexo());
             editMatganadas.setText(String.valueOf(alumno.getMatganadas()));
        }
    }
}
