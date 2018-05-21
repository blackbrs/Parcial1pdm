package ca14080.repasoparcial;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ConsultaEspecial2Activity extends Activity {
    ControlBDCarnet helper;
    EditText editCarnet;
    EditText editNombre;
    EditText editApellido;
    EditText editSexo;
    EditText editMatganadas;
    EditText editTotal1;
    EditText editTotal2;
    EditText editTotal3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_especial2);

        helper = new ControlBDCarnet(this);

        editCarnet = (EditText) findViewById(R.id.editCarnet);
        editNombre = (EditText) findViewById(R.id.editNombre);
        editApellido = (EditText) findViewById(R.id.editApellido);
        editSexo = (EditText) findViewById(R.id.editSexo);
        editMatganadas = (EditText) findViewById(R.id.editMatganadas);
        editTotal1 = (EditText) findViewById(R.id.editTotal1);
        editTotal2 = (EditText) findViewById(R.id.editTotal2);
        editTotal3 = (EditText) findViewById(R.id.editTotal3);
    }

    public void consultaN2(View v) {

        helper.abrir();
        Alumno alumno2 = helper.consulta2(editCarnet.getText().toString());

        helper.cerrar();
        if(alumno2 == null)
            Toast.makeText(this, "Alumno con carnet " + editCarnet.getText().toString() +
                    " no encontrado", Toast.LENGTH_LONG).show();
        else{
            editNombre.setText(alumno2.getNombre());
            editApellido.setText(alumno2.getApellido());
            editSexo.setText(alumno2.getSexo());
            editMatganadas.setText(String.valueOf(alumno2.getMatganadas()));
            editTotal1.setText(String.valueOf(alumno2.getTotal1()));
            editTotal2.setText(String.valueOf(alumno2.getTotal2()));
            editTotal3.setText(String.valueOf(alumno2.getTotal3()));


        }
    }
}
