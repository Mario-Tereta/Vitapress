package com.example.vitalpreesoficial;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Botón Iniciar Sesión
        Button btnIniciarSesion = findViewById(R.id.btn_iniciar_sesion);
        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ir a pantalla de Inicio (post-login)
                Intent intent = new Intent(MainActivity.this, InicioActivity.class);
                startActivity(intent);
            }
        });

        // Texto "Registrarse" clickeable
        TextView txtRegistrarse = findViewById(R.id.txt_registrarse);
        txtRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ir a pantalla de registro
                Intent intent = new Intent(MainActivity.this, Registro2Activity.class);
                startActivity(intent);
            }
        });
    }
}