package ca14080.repasoparcial;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ConsultaEspecial3Activity extends Activity {

    ControlBDCarnet helper;
    EditText editCodmateria;
    EditText editNomMateria;
    EditText editTotal4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_especial3);
        helper = new ControlBDCarnet(this);
        editCodmateria = (EditText) findViewById(R.id.editCodmateria);
        editNomMateria = (EditText) findViewById(R.id.editNommateria);
        editTotal4 = (EditText) findViewById(R.id.editTotal4);
    }


    public void consultaN3(View v) {

        helper.abrir();
        Materia materia = helper.consulta3(editCodmateria.getText().toString());

        helper.cerrar();
        if(materia == null)
            Toast.makeText(this, "Materia con codigo " + editCodmateria.getText().toString() +
                    " no encontrado", Toast.LENGTH_LONG).show();
        else{
            editNomMateria.setText(materia.getNommateria());
            editTotal4.setText(String.valueOf(materia.getCantidad_materias()));



        }
    }
}
