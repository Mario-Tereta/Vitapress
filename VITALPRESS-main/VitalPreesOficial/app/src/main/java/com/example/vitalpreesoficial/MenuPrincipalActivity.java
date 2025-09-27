package com.example.vitalpreesoficial;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MenuPrincipalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        // Botones arriba del corazón
        Button btnChatIA = findViewById(R.id.btn_chat_ia);
        Button btnSeccionDatos = findViewById(R.id.btn_seccion_datos);
        Button btnRecordatorio = findViewById(R.id.btn_recordatorio);

        // Botones debajo del corazón
        Button btnPendiente = findViewById(R.id.btn_pendiente);
        Button btnRecomendaciones = findViewById(R.id.btn_recomendaciones);
        Button btnActualizarDatos = findViewById(R.id.btn_actualizar_datos);
        Button btnVerInfo = findViewById(R.id.btn_ver_info);

        // Icono de ajustes
        ImageView iconoAjustes = findViewById(R.id.icono_ajustes);

        // Configurar listeners
        btnChatIA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuPrincipalActivity.this, ChatIAActivity.class);
                startActivity(intent);
            }
        });

        btnSeccionDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuPrincipalActivity.this, SeccionDatosActivity.class);
                startActivity(intent);
            }
        });

        btnRecordatorio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuPrincipalActivity.this, RecordatorioActivity.class);
                startActivity(intent);
            }
        });

        btnPendiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuPrincipalActivity.this, PendienteActivity.class);
                startActivity(intent);
            }
        });

        btnRecomendaciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuPrincipalActivity.this, RecomendacionesActivity.class);
                startActivity(intent);
            }
        });

        btnActualizarDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuPrincipalActivity.this, ActualizarDatosActivity.class);
                startActivity(intent);
            }
        });

        btnVerInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuPrincipalActivity.this, VerInfoActivity.class);
                startActivity(intent);
            }
        });

        iconoAjustes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuPrincipalActivity.this, AjustesActivity.class);
                startActivity(intent);
            }
        });
    }
}
