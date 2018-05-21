package ca14080.repasoparcial;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class menuprincipalActivity extends ListActivity {

    String [] menu={"Alumno","Materia","Notas","Consulta Especial 1","Consulta Especial 2","Consulta Especial 3","Consulta Especial 4"};
    String [] activity={"AlumnoMenuActivity","MateriaMenuActivity","NotaMenuActivity","ConsultaEspecial1Activity","ConsultaEspecial2Activity","ConsultaEspecial3Activity","ConsultaEspecial4Activity"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setListAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, menu));
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id){
        super.onListItemClick(l, v, position, id);

            String nombreValue=activity[position];
            try{
                Class<?>
                        clase=Class.forName("ca14080.repasoparcial."+nombreValue);
                Intent inte = new Intent(this,clase);
                this.startActivity(inte);
            }catch(ClassNotFoundException e){
                e.printStackTrace();
            }

    }
}

