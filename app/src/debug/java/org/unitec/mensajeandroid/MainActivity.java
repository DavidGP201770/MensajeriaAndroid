package org.unitec.mensajeandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //AQUI GENERAMOS LA NAVEGACHION :V
        //NAVEGACION DE REGISTRO

        findViewById(R.id.registrarse).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),RegistroActivity.class);
                startActivity(i);
            }
        });

        findViewById(R.id.enviar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),MensajeActivity.class);
                startActivity(i);
            }
        });

    }
}
