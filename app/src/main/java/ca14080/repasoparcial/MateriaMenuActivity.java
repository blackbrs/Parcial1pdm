package ca14080.repasoparcial;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MateriaMenuActivity extends ListActivity {

    String[] menu = {"Agregar Materia", "Eliminar Materia", "Consultar Materia", "Modificar Materia"};
    String [] activity={"MateriaInsertarActivity","MateriaEliminarActivity","MateriaConsultarActivity","MateriaModificarActivity"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menu));
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
