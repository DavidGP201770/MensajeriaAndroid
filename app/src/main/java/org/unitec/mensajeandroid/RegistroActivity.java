package org.unitec.mensajeandroid;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class RegistroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);




        //BOTON REGISTRAR
        Button botonregistrar;

        botonregistrar = (Button) findViewById(R.id.darregistro);
        botonregistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TAREA DE REGISTRAR
                TareaRegistrar tareaRegistrar = new TareaRegistrar();
                tareaRegistrar.execute(null,null,null);




            }
        });

        //FIN BOTON REGISTRAR

    }

    public class TareaRegistrar extends AsyncTask<String, String, String>{
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
            Usuario usu=new Usuario();

            usu.getNickname();

            String resultado = restTemplate.postForObject("https://jc-moguito.herokuapp.com/api/mensajito", mensajito, String.class);

            ObjectMapper mapper=new ObjectMapper();
            try {
                Estatus estatus=mapper.readValue(resultado, Estatus.class);
                System.out.println("ESTATUS DEL SERVICIO: "+estatus.isSuccess());
            } catch (Exception e) {
                System.out.println("ERROR "+e.getMessage());
            }

            return null;
        }
    }

}
