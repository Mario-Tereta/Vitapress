package com.example.vitalpreesoficial;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class AjustesActivity extends AppCompatActivity {

    private ImageView circuloFotoPerfil;
    private Button btnCambiarPerfil;
    private ActivityResultLauncher<Intent> seleccionarImagenLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);

        // Inicializar vistas
        circuloFotoPerfil = findViewById(R.id.circulo_foto_perfil);
        btnCambiarPerfil = findViewById(R.id.btn_cambiar_perfil);

        // Configurar launcher para seleccionar imagen
        configurarSelectorImagen();

        // Configurar clic en foto de perfil Y en botón cambiar
        circuloFotoPerfil.setOnClickListener(v -> mostrarOpcionesCambiarFoto());
        btnCambiarPerfil.setOnClickListener(v -> mostrarOpcionesCambiarFoto());

        // Botón Regresar
        Button btnRegresar = findViewById(R.id.btn_regresar_ajustes);
        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Regresar al menú principal
            }
        });

        // Botón Cerrar Sesión
        Button btnCerrarSesion = findViewById(R.id.btn_cerrar_sesion);
        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mostrar diálogo de confirmación para cerrar sesión
                new AlertDialog.Builder(AjustesActivity.this)
                    .setTitle("Cerrar Sesión")
                    .setMessage("¿Estás seguro de que quieres cerrar sesión?")
                    .setPositiveButton("Sí", (dialog, which) -> {
                        // Cerrar sesión y regresar al login
                        Intent intent = new Intent(AjustesActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    })
                    .setNegativeButton("Cancelar", null)
                    .show();
            }
        });
    }

    private void configurarSelectorImagen() {
        seleccionarImagenLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Uri imageUri = result.getData().getData();
                    if (imageUri != null) {
                        // Establecer la imagen seleccionada en el círculo de perfil
                        circuloFotoPerfil.setImageURI(imageUri);
                        circuloFotoPerfil.setScaleType(ImageView.ScaleType.CENTER_CROP);

                        Toast.makeText(this, "Foto de perfil actualizada", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        );
    }

    private void mostrarOpcionesCambiarFoto() {
        new AlertDialog.Builder(this)
            .setTitle("Cambiar foto de perfil")
            .setMessage("Selecciona una opción:")
            .setPositiveButton("Galería", (dialog, which) -> {
                // Abrir galería para seleccionar imagen
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                seleccionarImagenLauncher.launch(intent);
            })
            .setNegativeButton("Cancelar", null)
            .setNeutralButton("Quitar foto", (dialog, which) -> {
                // Restaurar imagen por defecto (círculo gris)
                circuloFotoPerfil.setImageResource(0);
                circuloFotoPerfil.setBackgroundResource(R.drawable.circulo_perfil);
                Toast.makeText(this, "Foto de perfil eliminada", Toast.LENGTH_SHORT).show();
            })
            .show();
    }
}
