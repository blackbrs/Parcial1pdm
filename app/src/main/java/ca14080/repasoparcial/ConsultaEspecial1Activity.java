package ca14080.repasoparcial;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ConsultaEspecial1Activity extends Activity {
    ControlBDCarnet helper;
    EditText editCarnet;
    EditText editMatinscritas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_especial1);
        helper = new ControlBDCarnet(this);
        editCarnet = (EditText)findViewById(R.id.edtCarnet);
        editMatinscritas = (EditText)findViewById(R.id.edtMatinscritas);
    }

    public void consultaN1(View v){
        helper.abrir();
        String mat_ins = helper.consulta1(editCarnet.getText().toString());
        helper.cerrar();

        if(mat_ins == null){
            Toast.makeText(this,"alumno con carnet"+editCarnet.getText().toString()+"No fue encontrado",Toast.LENGTH_SHORT).show();
        }else{
        editMatinscritas.setText(String.valueOf(mat_ins));
        }
    }
}
