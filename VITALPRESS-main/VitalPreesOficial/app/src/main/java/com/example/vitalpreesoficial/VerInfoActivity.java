package com.example.vitalpreesoficial;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vitalpreesoficial.db.DBVitalPress;

public class VerInfoActivity extends AppCompatActivity {

    // [MODIFICACIÓN] Conexión a la base de datos VitalPress
    private DBVitalPress dbVitalPress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_info);

        // [MODIFICACIÓN] Inicialización de la base de datos
        dbVitalPress = new DBVitalPress(this);

        // Cargar información del usuario (simulada)
        cargarInformacionUsuario();

        // Botón Regresar
        Button btnRegresar = findViewById(R.id.btn_regresar_ver_info);
        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void cargarInformacionUsuario() {
        // Aquí se cargaría la información real del usuario
        // Por ahora mostramos datos de ejemplo

        TextView txtNombre = findViewById(R.id.txt_info_nombre);
        TextView txtCorreo = findViewById(R.id.txt_info_correo);
        TextView txtEdad = findViewById(R.id.txt_info_edad);
        TextView txtEstatura = findViewById(R.id.txt_info_estatura);
        TextView txtPeso = findViewById(R.id.txt_info_peso);
        TextView txtIMC = findViewById(R.id.txt_info_imc);

        // Datos de ejemplo
        txtNombre.setText("Juan Pérez");
        txtCorreo.setText("juan.perez@email.com");
        txtEdad.setText("35 años");
        txtEstatura.setText("1.75 m");
        txtPeso.setText("70 kg");
        txtIMC.setText("22.86");
    }
}
