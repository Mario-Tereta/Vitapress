package com.example.vitalpreesoficial;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vitalpreesoficial.db.DBVitalPress;

public class Registro2Activity extends AppCompatActivity {

    // [MODIFICACIÓN] Conexión a la base de datos VitalPress
    private DBVitalPress dbVitalPress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro2);

        // [MODIFICACIÓN] Inicialización de la base de datos
        dbVitalPress = new DBVitalPress(this);

        // Botón Registrarse
        Button btnRegistrarse = findViewById(R.id.btn_registrarse);
        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Aquí iría la lógica de registro
                // Por ahora regresa al login
                Intent intent = new Intent(Registro2Activity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Botón Regresar
        Button btnRegresar = findViewById(R.id.btn_regresar);
        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Regresar a la pantalla de login
                finish();
            }
        });

        // Cálculo automático del IMC
        EditText editPeso = findViewById(R.id.edit_peso);
        EditText editEstatura = findViewById(R.id.edit_estatura);
        TextView txtIMC = findViewById(R.id.txt_imc);

        // Listeners para calcular IMC automáticamente
        View.OnFocusChangeListener imcCalculator = new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    calcularIMC(editPeso, editEstatura, txtIMC);
                }
            }
        };

        editPeso.setOnFocusChangeListener(imcCalculator);
        editEstatura.setOnFocusChangeListener(imcCalculator);
    }

    private void calcularIMC(EditText editPeso, EditText editEstatura, TextView txtIMC) {
        try {
            String pesoStr = editPeso.getText().toString();
            String estaturaStr = editEstatura.getText().toString();

            if (!pesoStr.isEmpty() && !estaturaStr.isEmpty()) {
                double peso = Double.parseDouble(pesoStr);
                double estatura = Double.parseDouble(estaturaStr);

                if (estatura > 0) {
                    double imc = peso / (estatura * estatura);
                    txtIMC.setText(String.format("%.2f", imc));
                }
            }
        } catch (NumberFormatException e) {
            // Error en el formato de números
        }
    }
}
