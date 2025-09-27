package com.example.vitalpreesoficial;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class InicioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        // Botón Iniciar Sesión
        Button btnIniciarSesion = findViewById(R.id.btn_iniciar_sesion_inicio);
        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ir al menú principal
                Intent intent = new Intent(InicioActivity.this, MenuPrincipalActivity.class);
                startActivity(intent);
            }
        });

        // Botón Regresar
        Button btnRegresar = findViewById(R.id.btn_regresar_inicio);
        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Regresar al login
                finish();
            }
        });
    }
}
