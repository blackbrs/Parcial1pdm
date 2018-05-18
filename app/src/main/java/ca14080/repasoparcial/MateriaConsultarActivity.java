package ca14080.repasoparcial;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MateriaConsultarActivity extends Activity {
    ControlBDCarnet helper;
    EditText editCodmateria;
    EditText editNommateria;
    EditText editUnidadesval;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        helper = new ControlBDCarnet(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materia_consultar);
        editCodmateria =(EditText)findViewById(R.id.edtCodmateria);
        editNommateria = (EditText)findViewById(R.id.edtNombremat);
        editUnidadesval = (EditText)findViewById(R.id.edtUnidadesval);
    }
    public void consultarMateria (View v ){
        helper.abrir();
        Materia materia = helper.consultarMateria(editCodmateria.getText().toString());
        if(materia==null){
            Toast.makeText(this,"La materia "+editCodmateria.getText().toString()+"No se encontro",Toast.LENGTH_SHORT).show();

        }else{
            editNommateria.setText(materia.getNommateria());
            editUnidadesval.setText(materia.getUnidadesval());
        }
    }
}
