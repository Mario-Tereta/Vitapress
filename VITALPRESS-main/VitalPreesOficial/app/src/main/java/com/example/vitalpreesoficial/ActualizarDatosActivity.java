package com.example.vitalpreesoficial;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vitalpreesoficial.db.DBVitalPress;

public class ActualizarDatosActivity extends AppCompatActivity {

    // [MODIFICACIÓN] Conexión a la base de datos VitalPress
    private DBVitalPress dbVitalPress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_datos);

        // [MODIFICACIÓN] Inicialización de la base de datos
        dbVitalPress = new DBVitalPress(this);

        // Botón Actualizar
        Button btnActualizar = findViewById(R.id.btn_actualizar);
        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editNombre = findViewById(R.id.edit_nombre_apellido_actualizar);
                EditText editCorreo = findViewById(R.id.edit_correo_actualizar);
                EditText editEdad = findViewById(R.id.edit_edad_actualizar);
                EditText editEstatura = findViewById(R.id.edit_estatura_actualizar);
                EditText editPeso = findViewById(R.id.edit_peso_actualizar);
                EditText editContrasena = findViewById(R.id.edit_contrasena_actualizar);

                String nombre = editNombre.getText().toString();
                String correo = editCorreo.getText().toString();
                int edad = editEdad.getText().toString().isEmpty() ? 0 : Integer.parseInt(editEdad.getText().toString());
                double estatura = editEstatura.getText().toString().isEmpty() ? 0 : Double.parseDouble(editEstatura.getText().toString());
                double peso = editPeso.getText().toString().isEmpty() ? 0 : Double.parseDouble(editPeso.getText().toString());
                String contrasena = editContrasena.getText().toString();

                dbVitalPress.insertUsuario(nombre, correo, edad, estatura, peso, contrasena);
            }
        });

        // Botón Regresar
        Button btnRegresar = findViewById(R.id.btn_regresar_actualizar);
        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Cálculo automático del IMC al actualizar datos
        EditText editPeso = findViewById(R.id.edit_peso_actualizar);
        EditText editEstatura = findViewById(R.id.edit_estatura_actualizar);
        TextView txtIMC = findViewById(R.id.txt_imc_actualizar);

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
