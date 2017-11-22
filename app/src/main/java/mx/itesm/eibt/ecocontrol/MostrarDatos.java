package mx.itesm.eibt.ecocontrol;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class MostrarDatos extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private int id;
    private String nombre;
    private String usuario;
    private LinearLayout container;
    private PrimeThread p;
    private boolean real = false;
    private ImageView grafica_co2;
    private ImageView grafica_presion;
    private int tiempo;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_datos);
        crearObjetos();
        cargarTiempoReal();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void crearObjetos() {
        id = getIntent().getIntExtra("id",0);
        nombre = getIntent().getStringExtra("nombre");
        usuario = getIntent().getStringExtra("usuario");
        container = findViewById(R.id.containerDatos);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            real = false;
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mostrar_datos, menu);
        TextView nom = findViewById(R.id.nombreFabricaTextView);
        nom.setText(nombre);
        TextView usu = findViewById(R.id.usuarioTextView);
        usu.setText(usuario);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_real) {
            // Handle the camera action
            cargarTiempoReal();
        } else if (id == R.id.nav_fecha) {
            DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    cargarFechaEspecífica();
                }
            });
            newFragment.show(getSupportFragmentManager(), "datePicker");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void cargarFechaEspecífica() {
        real = false;
        container.removeAllViews();
        TextView grafica_co2_titulo = new TextView(this);
        grafica_co2_titulo.setText("Emisiones de CO2");
        grafica_co2_titulo.setTextSize(30);
        grafica_co2_titulo.setTextColor(Color.WHITE);
        grafica_co2_titulo.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        grafica_co2_titulo.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textParams.setMargins(0,20,0,20);
        grafica_co2_titulo.setLayoutParams(textParams);
        container.addView(grafica_co2_titulo);
        ImageView grafica_co2 = new ImageView(this);
        int i = ThreadLocalRandom.current().nextInt(0,5);
        switch(i)
        {
            case 0:
                grafica_co2.setImageResource(R.drawable.co2_static_0);
                break;
            case 1:
                grafica_co2.setImageResource(R.drawable.co2_static_1);
                break;
            case 2:
                grafica_co2.setImageResource(R.drawable.co2_static_2);
                break;
            case 3:
                grafica_co2.setImageResource(R.drawable.co2_static_3);
                break;
            case 4:
                grafica_co2.setImageResource(R.drawable.co2_static_4);
                break;
        }
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,600);
        params.setMargins(20,20,20,20);
        grafica_co2.setLayoutParams(params);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            grafica_co2.setBackground(null);
        }
        grafica_co2.setScaleType(ImageView.ScaleType.FIT_START);
        grafica_co2.setAdjustViewBounds(true);
        container.addView(grafica_co2);
        TextView maxCO2 = new TextView(this);
        maxCO2.setText("Emisión mínima de CO2: " + ThreadLocalRandom.current().nextInt(20,40));
        maxCO2.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        maxCO2.setLayoutParams(textParams);
        TextView minCO2 = new TextView(this);
        minCO2.setText("Emisión máxima de CO2: " + ThreadLocalRandom.current().nextInt(50,70));
        minCO2.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        minCO2.setLayoutParams(textParams);
        TextView promCO2 = new TextView(this);
        promCO2.setText("Emisión promedio de CO2: " + ThreadLocalRandom.current().nextInt(40,50));
        promCO2.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        promCO2.setLayoutParams(textParams);
        container.addView(maxCO2);
        container.addView(minCO2);
        container.addView(promCO2);
        TextView grafica_presion_titulo = new TextView(this);
        grafica_presion_titulo.setText("Niveles de presión");
        grafica_presion_titulo.setTextSize(30);
        grafica_presion_titulo.setTextColor(Color.WHITE);
        grafica_presion_titulo.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        grafica_presion_titulo.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        textParams.setMargins(0,20,0,20);
        grafica_presion_titulo.setLayoutParams(textParams);
        container.addView(grafica_presion_titulo);
        ImageView grafica_presion = new ImageView(this);
        switch(i)
        {
            case 0:
                grafica_presion.setImageResource(R.drawable.presion_static_0);
                break;
            case 1:
                grafica_presion.setImageResource(R.drawable.presion_static_1);
                break;
            case 2:
                grafica_presion.setImageResource(R.drawable.presion_static_2);
                break;
            case 3:
                grafica_presion.setImageResource(R.drawable.presion_static_3);
                break;
            case 4:
                grafica_presion.setImageResource(R.drawable.presion_static_4);
                break;
        }

        grafica_presion.setLayoutParams(params);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            grafica_presion.setBackground(null);
        }
        grafica_presion.setScaleType(ImageView.ScaleType.FIT_START);
        grafica_presion.setAdjustViewBounds(true);
        container.addView(grafica_presion);
        TextView maxPresion = new TextView(this);
        maxPresion.setText("Presión mínima: " + ThreadLocalRandom.current().nextInt(20,40));
        maxPresion.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        maxPresion.setLayoutParams(textParams);
        TextView minPresion = new TextView(this);
        minPresion.setText("Presión máxima: " + ThreadLocalRandom.current().nextInt(50,70));
        minPresion.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        minPresion.setLayoutParams(textParams);
        TextView promPresion = new TextView(this);
        promPresion.setText("Presión promedio: " + ThreadLocalRandom.current().nextInt(40,50));
        promPresion.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        promPresion.setLayoutParams(textParams);
        container.addView(maxPresion);
        container.addView(minPresion);
        container.addView(promPresion);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void cargarTiempoReal() {
        tiempo = 0;
        real = true;
        container.removeAllViews();
        TextView grafica_co2_titulo = new TextView(this);
        grafica_co2_titulo.setText("Emisiones de CO2");
        grafica_co2_titulo.setTextSize(30);
        grafica_co2_titulo.setTextColor(Color.WHITE);
        grafica_co2_titulo.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        grafica_co2_titulo.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textParams.setMargins(0,20,0,20);
        grafica_co2_titulo.setLayoutParams(textParams);
        container.addView(grafica_co2_titulo);
        grafica_co2 = new ImageView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,600);
        params.setMargins(20,20,20,20);
        grafica_co2.setLayoutParams(params);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            grafica_co2.setBackground(null);
        }
        grafica_co2.setScaleType(ImageView.ScaleType.FIT_START);
        grafica_co2.setAdjustViewBounds(true);
        container.addView(grafica_co2);
        TextView maxCO2 = new TextView(this);
        maxCO2.setText("Emisión mínima de CO2: " + ThreadLocalRandom.current().nextInt(20,40));
        maxCO2.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        maxCO2.setLayoutParams(textParams);
        TextView minCO2 = new TextView(this);
        minCO2.setText("Emisión máxima de CO2: " + ThreadLocalRandom.current().nextInt(50,70));
        minCO2.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        minCO2.setLayoutParams(textParams);
        TextView promCO2 = new TextView(this);
        promCO2.setText("Emisión promedio de CO2: " + ThreadLocalRandom.current().nextInt(40,50));
        promCO2.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        promCO2.setLayoutParams(textParams);
        container.addView(maxCO2);
        container.addView(minCO2);
        container.addView(promCO2);
        TextView grafica_presion_titulo = new TextView(this);
        grafica_presion_titulo.setText("Niveles de presión");
        grafica_presion_titulo.setTextSize(30);
        grafica_presion_titulo.setTextColor(Color.WHITE);
        grafica_presion_titulo.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        grafica_presion_titulo.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        textParams.setMargins(0,20,0,20);
        grafica_presion_titulo.setLayoutParams(textParams);
        container.addView(grafica_presion_titulo);
        grafica_presion = new ImageView(this);
        grafica_presion.setLayoutParams(params);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            grafica_presion.setBackground(null);
        }
        grafica_presion.setScaleType(ImageView.ScaleType.FIT_START);
        grafica_presion.setAdjustViewBounds(true);
        container.addView(grafica_presion);
        TextView maxPresion = new TextView(this);
        maxPresion.setText("Presión mínima: " + ThreadLocalRandom.current().nextInt(20,40));
        maxPresion.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        maxPresion.setLayoutParams(textParams);
        TextView minPresion = new TextView(this);
        minPresion.setText("Presión máxima: " + ThreadLocalRandom.current().nextInt(50,70));
        minPresion.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        minPresion.setLayoutParams(textParams);
        TextView promPresion = new TextView(this);
        promPresion.setText("Presión promedio: " + ThreadLocalRandom.current().nextInt(40,50));
        promPresion.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        promPresion.setLayoutParams(textParams);
        container.addView(maxPresion);
        container.addView(minPresion);
        container.addView(promPresion);
        p = new PrimeThread(143);
        p.start();

    }
    class PrimeThread extends Thread {
        long minPrime;
        PrimeThread(long minPrime) {
            this.minPrime = minPrime;
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
        public void run() {
            while(real)
            {
                runOnUiThread(new Runnable() {
                    public void run() {
                        cambiarGraficas();
                    }
                });
                tiempo=(tiempo+1)%25;
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void cambiarGraficas() {
        switch(tiempo)
        {
            case 0:
                grafica_presion.setImageResource(R.drawable.presion_0);
                grafica_co2.setImageResource(R.drawable.co2_0);
                break;
            case 1:
                grafica_presion.setImageResource(R.drawable.presion_1);
                grafica_co2.setImageResource(R.drawable.co2_1);
                break;
            case 2:
                grafica_presion.setImageResource(R.drawable.presion_2);
                grafica_co2.setImageResource(R.drawable.co2_2);
                break;
            case 3:
                grafica_presion.setImageResource(R.drawable.presion_3);
                grafica_co2.setImageResource(R.drawable.co2_3);
                break;
            case 4:
                grafica_presion.setImageResource(R.drawable.presion_4);
                grafica_co2.setImageResource(R.drawable.co2_4);
                break;
            case 5:
                grafica_presion.setImageResource(R.drawable.presion_5);
                grafica_co2.setImageResource(R.drawable.co2_5);
                break;
            case 6:
                grafica_presion.setImageResource(R.drawable.presion_6);
                grafica_co2.setImageResource(R.drawable.co2_6);
                break;
            case 7:
                grafica_presion.setImageResource(R.drawable.presion_7);
                grafica_co2.setImageResource(R.drawable.co2_7);
                break;
            case 8:
                grafica_presion.setImageResource(R.drawable.presion_8);
                grafica_co2.setImageResource(R.drawable.co2_8);
                break;
            case 9:
                grafica_presion.setImageResource(R.drawable.presion_9);
                grafica_co2.setImageResource(R.drawable.co2_9);
                break;
            case 10:
                grafica_presion.setImageResource(R.drawable.presion_10);
                grafica_co2.setImageResource(R.drawable.co2_10);
                break;
            case 11:
                grafica_presion.setImageResource(R.drawable.presion_11);
                grafica_co2.setImageResource(R.drawable.co2_11);
                break;
            case 12:
                grafica_presion.setImageResource(R.drawable.presion_12);
                grafica_co2.setImageResource(R.drawable.co2_12);
                break;
            case 13:
                grafica_presion.setImageResource(R.drawable.presion_13);
                grafica_co2.setImageResource(R.drawable.co2_13);
                break;
            case 14:
                grafica_presion.setImageResource(R.drawable.presion_14);
                grafica_co2.setImageResource(R.drawable.co2_14);
                break;
            case 15:
                grafica_presion.setImageResource(R.drawable.presion_15);
                grafica_co2.setImageResource(R.drawable.co2_15);
                break;
            case 16:
                grafica_presion.setImageResource(R.drawable.presion_16);
                grafica_co2.setImageResource(R.drawable.co2_16);
                break;
            case 17:
                grafica_presion.setImageResource(R.drawable.presion_17);
                grafica_co2.setImageResource(R.drawable.co2_17);
                break;
            case 18:
                grafica_presion.setImageResource(R.drawable.presion_18);
                grafica_co2.setImageResource(R.drawable.co2_18);
                break;
            case 19:
                grafica_presion.setImageResource(R.drawable.presion_19);
                grafica_co2.setImageResource(R.drawable.co2_19);
                break;
            case 20:
                grafica_presion.setImageResource(R.drawable.presion_20);
                grafica_co2.setImageResource(R.drawable.co2_20);
                break;
            case 21:
                grafica_presion.setImageResource(R.drawable.presion_21);
                grafica_co2.setImageResource(R.drawable.co2_21);
                break;
            case 22:
                grafica_presion.setImageResource(R.drawable.presion_22);
                grafica_co2.setImageResource(R.drawable.co2_22);
                break;
            case 23:
                grafica_presion.setImageResource(R.drawable.presion_23);
                grafica_co2.setImageResource(R.drawable.co2_23);
                break;
            case 24:
                grafica_presion.setImageResource(R.drawable.presion_24);
                grafica_co2.setImageResource(R.drawable.co2_24);
                break;
        }
    }
    public static class DatePickerFragment extends DialogFragment {

        private DatePickerDialog.OnDateSetListener listener;

        public static DatePickerFragment newInstance(DatePickerDialog.OnDateSetListener listener) {
            DatePickerFragment fragment = new DatePickerFragment();
            fragment.setListener(listener);
            return fragment;
        }

        public void setListener(DatePickerDialog.OnDateSetListener listener) {
            this.listener = listener;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), listener, year, month, day);
        }

    }
}
