package ca14080.repasoparcial;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AlumnoModificarActivity extends Activity {

    ControlBDCarnet helper;
    EditText editCarnet;
    EditText editNombre;
    EditText editApellido;
    EditText editSexo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumno_modificar);
        helper = new ControlBDCarnet(this);
        editCarnet = (EditText)findViewById(R.id.editCarnet);
        editNombre = (EditText)findViewById(R.id.editNombre);
        editApellido = (EditText)findViewById(R.id.editApellido);
        editSexo = (EditText)findViewById(R.id.editSexo);
    }

    public void actulizarAlumno(View v){
        Alumno alumno = new Alumno();
        alumno.setCarnet(editCarnet.getText().toString());
        alumno.setNombre(editNombre.getText().toString());
        alumno.setApellido(editApellido.getText().toString());
        alumno.setSexo(editSexo.getText().toString());
        helper.abrir();
        String estado = helper.actualizar(alumno);
        helper.cerrar();
        Toast.makeText(this,estado,Toast.LENGTH_SHORT).show();
        editCarnet.setText("");
        editNombre.setText("");
        editApellido.setText("");
        editSexo.setText("");

    }
}
