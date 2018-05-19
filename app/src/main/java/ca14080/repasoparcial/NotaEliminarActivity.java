package ca14080.repasoparcial;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class NotaEliminarActivity extends Activity {


    EditText editCarnet,editCodmateria,editCiclo;
    ControlBDCarnet controlhelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nota_eliminar);
        controlhelper=new ControlBDCarnet(this);
        editCarnet=(EditText)findViewById(R.id.editC);
        editCodmateria=(EditText)findViewById(R.id.editCo);
        editCiclo=(EditText)findViewById(R.id.editCi);
    }
    public void eliminarNota(View v){
        String regEliminadas;
        Nota nota=new Nota();
        nota.setCarnet(editCarnet.getText().toString());
        nota.setCodamateria(editCodmateria.getText().toString());
        nota.setCiclo(editCiclo.getText().toString());
        controlhelper.abrir();
        regEliminadas=controlhelper.eliminarNot(nota);
        controlhelper.cerrar();
        Toast.makeText(this, regEliminadas, Toast.LENGTH_SHORT).show();
        editCarnet.setText("");
        editCodmateria.setText("");
        editCiclo.setText("");
    }
}
