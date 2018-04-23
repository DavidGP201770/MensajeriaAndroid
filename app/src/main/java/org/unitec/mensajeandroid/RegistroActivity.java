package org.unitec.mensajeandroid;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import android.Manifest;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;


public class RegistroActivity extends AppCompatActivity {

    LocationManager locationManager;
    double longitudeGPS, latitudeGPS;
    Posicion pos = new Posicion();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);


        //BOTON REGISTRAR Y VARIABLES
        Button botonregistrar;

        botonregistrar = (Button) findViewById(R.id.darregistro);

        //ACCION BOTON

        botonregistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TAREA DE REGISTRAR
                TareaRegistrar tareaRegistrar = new TareaRegistrar();
                tareaRegistrar.execute(null, null, null);

            }
        });

        //FIN BOTON REGISTRAR


        // TAREA_REGISTRAR DECLARACION
    }

    public class TareaRegistrar extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {

            super.onPostExecute(s);

        }

        @Override
        protected String doInBackground(String... strings) {

            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

            // DEMAS

            EditText nicknametxt;
            EditText mailtxt;
            EditText passwordnametxt;

            nicknametxt = (EditText) findViewById(R.id.nickname);
            mailtxt = (EditText) findViewById(R.id.mail);
            passwordnametxt = (EditText) findViewById(R.id.password);

            Usuario usu = new Usuario();


            usu.getNickname(nicknametxt.getText().toString());
            usu.getMail(mailtxt.getText().toString());
            usu.getPassword(passwordnametxt.getText().toString());
            usu.setMensajes(null);
            usu.setPosicion(pos);

            String resultado = restTemplate.postForObject("http://jc-unitec.herokuapp.com/api/mensajito", usu, String.class);

            ObjectMapper mapper = new ObjectMapper();
            try {
                Estatus estatus = mapper.readValue(resultado, Estatus.class);
                Toast.makeText(getApplicationContext(), "USUARIO REGISTRADO", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                System.out.println("ERROR " + e.getMessage());
            }

            return null;
        }
    }

    // FIN TAREA

    //INICIO UBICACION

    private boolean checkLocation() {
        if (!isLocationEnabled())
            showAlert();
        return isLocationEnabled();
    }

    private void showAlert() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Enable Location")
                .setMessage("Su ubicación esta desactivada.\npor favor active su ubicación " +
                        "usa esta app")
                .setPositiveButton("Configuración de ubicación", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(myIntent);
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    }
                });
        dialog.show();
    }

    public boolean isLocationEnabled() {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    public final LocationListener ubic = new LocationListener() {
        public void onLocationChanged(Location location) {

            pos.getLat(latitudeGPS = pos.getLat(location.getLatitude()));
            pos.getLon(longitudeGPS = pos.getLon(location.getLongitude()));
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }

    };
}

