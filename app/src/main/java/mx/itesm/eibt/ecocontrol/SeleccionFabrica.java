package mx.itesm.eibt.ecocontrol;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SeleccionFabrica extends AppCompatActivity {

    private DataBase db;
    private LinearLayout contenedor;
    private String nombre_usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccion_fabrica);
        crearObjetos();
        cargarFabricas();
    }

    private void cargarFabricas() {
        Cursor fabricas = db.buscarFabricas(nombre_usuario);
        if(fabricas!=null)
        {
            do
            {
                final int id = fabricas.getInt(0);
                final String nombre = fabricas.getString(1);
                LinearLayout l = new LinearLayout(this);
                l.setOrientation(LinearLayout.HORIZONTAL);
                LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,300);
                l.setLayoutParams(lparams);
                l.setGravity(Gravity.CENTER);
                ImageButton fab = new ImageButton(this);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(300,300);

                fab.setLayoutParams(params);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    fab.setBackground(null);
                }
                fab.setImageResource(R.drawable.bimbo);
                fab.setScaleType(ImageView.ScaleType.FIT_START);
                fab.setAdjustViewBounds(true);

                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        cargarFabrica(id, nombre);
                    }
                });
                l.addView(fab);
                TextView nombreFab = new TextView(this);
                nombreFab.setText(nombre);
                nombreFab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        cargarFabrica(id, nombre);
                    }
                });
                l.addView(nombreFab);
                contenedor.addView(l);
            }while(fabricas.moveToNext());
        }
        else
        {
            mostrarError("No existen fábricas asociadas a este usuario");
            finish();
        }
    }

    private void cargarFabrica(int id, String nombre) {
        Intent fabrica = new Intent(this,MostrarDatos.class);
        fabrica.putExtra("id", id);
        fabrica.putExtra("nombre", nombre);
        fabrica.putExtra("usuario", nombre_usuario);
        startActivity(fabrica);
    }

    private void mostrarError(String s) {
        Toast.makeText(this, s,

                Toast.LENGTH_SHORT).show();
    }

    private void crearObjetos() {
        contenedor = findViewById(R.id.contenedorLinearLayout);
        db = new DataBase(getApplicationContext());
        nombre_usuario = getIntent().getStringExtra("usuario");
    }
}
