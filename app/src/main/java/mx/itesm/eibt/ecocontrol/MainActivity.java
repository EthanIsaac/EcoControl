package mx.itesm.eibt.ecocontrol;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private DataBase db;
    private Button ingresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        crearObjetos();
        cargarDatos();
    }

    private void cargarDatos() {
        db.insertarEnUsuario("Ethan", "1234");
        db.insertarEnFabrica("Bimbo Naucalpan", "Presion de aire: 30mg\nPPM: 100", "bimbo.png");
        db.insertarEnFabrica("Bimbo Azcapotzalco", "Presion de aire: 20mg\nPPM: 85", "bimbo.png");
        db.insertarEnUs_Fab("Ethan",1);
        db.insertarEnUs_Fab("Ethan",2);
        db.insertarEnRegistro(1,29,60);
        db.insertarEnRegistro(2,18,72);
    }

    private void crearObjetos() {
        db = new DataBase(getApplicationContext());
        ingresar = findViewById(R.id.ingresarButton);
        ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre_usuario = ((EditText)findViewById(R.id.usuarioEditText)).getText().toString();
                String contraseña_usuario = ((EditText)findViewById(R.id.contraseñaEditText)).getText().toString();
                Cursor usuario = db.buscarUsuario(nombre_usuario);
                if(usuario!=null)
                {
                    if(usuario.getString(1).equals(contraseña_usuario))
                    {
                        cargarFabricas(nombre_usuario);
                    }
                    else
                    {
                        mostrarError("Contraseña Incorrecta");
                    }
                }
                else
                {
                    mostrarError("Usuario Incorrecto");
                }
            }
        });
    }

    private void cargarFabricas(String usuario) {
        Intent seleccionFabrica = new Intent(this, SeleccionFabrica.class);
        seleccionFabrica.putExtra("usuario", usuario);
        startActivity(seleccionFabrica);
    }

    private void mostrarError(String s) {
        Toast.makeText(this, s,

                Toast.LENGTH_SHORT).show();
    }
}
