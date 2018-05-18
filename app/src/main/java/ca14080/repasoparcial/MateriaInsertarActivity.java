package ca14080.repasoparcial;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MateriaInsertarActivity extends Activity {

    ControlBDCarnet helper;
    EditText editCodmateria;
    EditText editNommateria;
    EditText editUnidadesval;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materia_insertar);
        helper = new ControlBDCarnet(this);
        editCodmateria = (EditText)findViewById(R.id.edtCodmateria);
        editNommateria = (EditText)findViewById(R.id.edtNombremat);
        editUnidadesval = (EditText)findViewById(R.id.edtUnidadesval);
    }

    public void InsertarMateria(View v){
        String codigo = editCodmateria.getText().toString();
        String nombre =editNommateria.getText().toString();
        String unidades=editUnidadesval.getText().toString();
        String regInsertados;
        Materia materia = new Materia();
        materia.setCodmateria(codigo);
        materia.setNommateria(nombre);
        materia.setUnidadesval(unidades);
        helper.abrir();
        regInsertados = helper.insertar(materia);
        helper.cerrar();
        Toast.makeText(this,regInsertados,Toast.LENGTH_SHORT).show();}

}
