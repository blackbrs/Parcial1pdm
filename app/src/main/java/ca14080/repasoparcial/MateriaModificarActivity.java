package ca14080.repasoparcial;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MateriaModificarActivity extends Activity {


    ControlBDCarnet helper;
    EditText editCodmateria;
    EditText editNommateria;
    EditText editUnidadesval;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materia_modificar);
        helper = new ControlBDCarnet(this);
        editCodmateria = (EditText) findViewById(R.id.editCodmateria);
        editNommateria = (EditText) findViewById(R.id.editNommateria);
        editUnidadesval = (EditText) findViewById(R.id.editUnidadesval);
    }
    public void actualizarMateria(View v) {
        Materia materia = new Materia();
        materia.setCodmateria(editCodmateria.getText().toString());
        materia.setNommateria(editNommateria.getText().toString());
        materia.setUnidadesval(editUnidadesval.getText().toString());

        helper.abrir();
        String estado = helper.actualizarMateria(materia);
        helper.cerrar();

        Toast.makeText(this, estado, Toast.LENGTH_SHORT).show();

    }

}
