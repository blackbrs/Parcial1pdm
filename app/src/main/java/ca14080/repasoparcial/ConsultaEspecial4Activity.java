package ca14080.repasoparcial;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.ResourceBundle;

public class ConsultaEspecial4Activity extends Activity {

    ControlBDCarnet helper;
    EditText editNomMateria;
    EditText editTotal4;
    Spinner spinner1;
    public Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_especial4);
        helper = new ControlBDCarnet(this);

        editNomMateria = (EditText) findViewById(R.id.editNommateria);
        editTotal4 = (EditText) findViewById(R.id.editTotal4);

        ArrayList<String> my_array = new ArrayList<String>();
        my_array = getMateriaValues();
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        ArrayAdapter my_Adapter = new ArrayAdapter(this, R.layout.spinnerrow, my_array);
        spinner1.setAdapter(my_Adapter);
    }

    public ArrayList<String> getMateriaValues() {

        ArrayList<String> my_array = new ArrayList<String>();
        try {
            helper.abrir();
            Cursor allrows = helper.consulta4();
            if (allrows.moveToFirst()) {
                do {

                    my_array.add(allrows.getString(0));

                } while (allrows.moveToNext());
            }
            allrows.close();
            helper.cerrar();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error encountered.",
                    Toast.LENGTH_LONG);
        }
        return my_array;
    }

    public void consultaN4(View v) {

        helper.abrir();
        Materia materia = helper.consulta3(spinner1.getSelectedItem().toString());
        helper.cerrar();
        if (materia == null)
            Toast.makeText(this, "Materia con codigo " + spinner1.getSelectedItem().toString() +
                    " no encontrado", Toast.LENGTH_LONG).show();
        else {
            editNomMateria.setText(materia.getNommateria());
            editTotal4.setText(String.valueOf(materia.getCantidad_materias()));


        }
    }
}


