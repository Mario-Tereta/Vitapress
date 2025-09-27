package com.example.vitalpreesoficial;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.app.Activity;
import android.provider.OpenableColumns;
import android.database.Cursor;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import org.json.JSONObject;
import android.graphics.pdf.PdfDocument;
import android.content.Context;
import com.example.vitalpreesoficial.db.DBVitalPress;
import java.util.List;

public class SeccionDatosActivity extends AppCompatActivity {

    // [MODIFICACIÓN] API Key y endpoint de Google Cloud Document AI
    private static final String DOCUMENT_AI_API_KEY = "aqui";
    private static final String DOCUMENT_AI_ENDPOINT = "https://documentai.googleapis.com/v1/projects/TU_PROJECT_ID/locations/TU_LOCATION/processors/TU_PROCESSOR_ID:process";
    private static final int PICK_FILE_REQUEST_CODE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seccion_datos);

        // Botón Exportar PDF
        Button btnExportarPDF = findViewById(R.id.btn_exportar_pdf);
        btnExportarPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // [MODIFICACIÓN] Exportar datos a PDF y procesar con Document AI
                exportarDatosConDocumentAI();
            }
        });

        // Botón Importar Archivo
        Button btnImportarArchivo = findViewById(R.id.btn_importar_archivo);
        btnImportarArchivo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // [MODIFICACIÓN] Importar archivo y procesar con Document AI
                seleccionarArchivo();
            }
        });

        // Botón Enviar Info al Correo
        Button btnEnviarCorreo = findViewById(R.id.btn_enviar_correo);
        btnEnviarCorreo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lógica para enviar por correo
            }
        });

        // Botón Pendiente
        Button btnPendiente = findViewById(R.id.btn_pendiente_datos);
        btnPendiente.setOnClickListener(v -> {
            android.content.Intent intent = new android.content.Intent(SeccionDatosActivity.this, PendienteActivity.class);
            startActivity(intent);
        });

        // Botón Recomendaciones
        Button btnRecomendaciones = findViewById(R.id.btn_recomendaciones_datos);
        btnRecomendaciones.setOnClickListener(v -> {
            android.content.Intent intent = new android.content.Intent(SeccionDatosActivity.this, RecomendacionesActivity.class);
            startActivity(intent);
        });

        // Botón Regresar
        Button btnRegresar = findViewById(R.id.btn_regresar_datos);
        btnRegresar.setOnClickListener(v -> finish());
    }

    // [MODIFICACIÓN] Exportar datos a PDF y procesar con Document AI
    private void exportarDatosConDocumentAI() {
        DBVitalPress dbVitalPress = new DBVitalPress(this);
        List<String> usuarios = dbVitalPress.exportarUsuarios();
        if (usuarios.isEmpty()) {
            Toast.makeText(this, "No hay datos para exportar", Toast.LENGTH_SHORT).show();
            return;
        }
        // Generar PDF con los datos
        PdfDocument pdfDocument = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(300, 600, 1).create();
        PdfDocument.Page page = pdfDocument.startPage(pageInfo);
        int y = 25;
        for (String usuario : usuarios) {
            page.getCanvas().drawText(usuario, 10, y, new android.graphics.Paint());
            y += 25;
        }
        pdfDocument.finishPage(page);
        // Guardar PDF en almacenamiento interno
        File pdfFile = new File(getFilesDir(), "usuarios_exportados.pdf");
        try (FileOutputStream fos = new FileOutputStream(pdfFile)) {
            pdfDocument.writeTo(fos);
            pdfDocument.close();
            // Enviar PDF a Document AI
            procesarArchivoConDocumentAI(Uri.fromFile(pdfFile));
        } catch (IOException e) {
            Toast.makeText(this, "Error al generar PDF: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    // [MODIFICACIÓN] Seleccionar archivo para importar y procesar con Document AI
    private void seleccionarArchivo() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        startActivityForResult(Intent.createChooser(intent, "Selecciona un archivo PDF"), PICK_FILE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_FILE_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            Uri uri = data.getData();
            if (uri != null) {
                procesarArchivoConDocumentAI(uri);
            }
        }
    }

    // [MODIFICACIÓN] Procesar archivo PDF con Document AI
    private void procesarArchivoConDocumentAI(Uri uri) {
        Toast.makeText(this, "Procesando archivo con Document AI...", Toast.LENGTH_SHORT).show();
        new AsyncTask<Uri, Void, String>() {
            @Override
            protected String doInBackground(Uri... uris) {
                try {
                    InputStream inputStream = getContentResolver().openInputStream(uris[0]);
                    byte[] fileBytes = new byte[inputStream.available()];
                    inputStream.read(fileBytes);
                    inputStream.close();

                    URL url = new URL(DOCUMENT_AI_ENDPOINT + "?key=" + DOCUMENT_AI_API_KEY);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/pdf");
                    conn.setDoOutput(true);
                    OutputStream os = conn.getOutputStream();
                    os.write(fileBytes);
                    os.flush();
                    os.close();

                    int responseCode = conn.getResponseCode();
                    Scanner scanner;
                    if (responseCode == 200) {
                        scanner = new Scanner(conn.getInputStream());
                    } else {
                        scanner = new Scanner(conn.getErrorStream());
                    }
                    StringBuilder response = new StringBuilder();
                    while (scanner.hasNext()) {
                        response.append(scanner.nextLine());
                    }
                    scanner.close();

                    if (responseCode == 200) {
                        JSONObject jsonResponse = new JSONObject(response.toString());
                        return "Procesado correctamente: " + jsonResponse.toString();
                    } else {
                        return "Error al procesar con Document AI: " + response.toString();
                    }
                } catch (Exception e) {
                    return "Error de conexión: " + e.getMessage();
                }
            }
            @Override
            protected void onPostExecute(String result) {
                Toast.makeText(SeccionDatosActivity.this, result, Toast.LENGTH_LONG).show();
            }
        }.execute(uri);
    }
}
