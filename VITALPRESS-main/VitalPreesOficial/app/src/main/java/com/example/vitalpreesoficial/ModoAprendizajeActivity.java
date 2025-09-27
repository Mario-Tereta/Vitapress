package com.example.vitalpreesoficial;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class ModoAprendizajeActivity extends AppCompatActivity {

    private TextView txtRespuestaIA;
    private EditText editPregunta;
    private ScrollView scrollRespuestas;

    // [MODIFICACIÓN] API Key de OpenAI (debe ser reemplazada por una válida y segura)
    private static final String OPENAI_API_KEY = "Aqui_va_tu_api_key";
    private static final String OPENAI_ENDPOINT = "https://api.openai.com/v1/chat/completions";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modo_aprendizaje);

        // Referencias
        txtRespuestaIA = findViewById(R.id.txt_respuesta_ia);
        editPregunta = findViewById(R.id.edit_pregunta);
        scrollRespuestas = findViewById(R.id.scroll_respuestas);

        // Botón Enviar Pregunta
        Button btnEnviar = findViewById(R.id.btn_enviar_pregunta);
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pregunta = editPregunta.getText().toString().trim();
                if (!pregunta.isEmpty()) {
                    procesarPregunta(pregunta);
                    editPregunta.setText("");
                }
            }
        });

        // Botón Limpiar Chat
        Button btnLimpiar = findViewById(R.id.btn_limpiar_chat);
        btnLimpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtRespuestaIA.setText("Bienvenido al Modo Aprendizaje de VitalPress IA.\n\nHaz una pregunta sobre salud cardiovascular, presión arterial o el uso de la aplicación.");
                scrollRespuestas.scrollTo(0, 0);
            }
        });

        // Botón Regresar
        Button btnRegresar = findViewById(R.id.btn_regresar_aprendizaje);
        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Inicializar mensaje de bienvenida
        txtRespuestaIA.setText("Bienvenido al Modo Aprendizaje de VitalPress IA.\n\nHaz una pregunta sobre salud cardiovascular, presión arterial o el uso de la aplicación.");
    }

    // [MODIFICACIÓN] Procesar pregunta usando OpenAI GPT-4.1
    private void procesarPregunta(String pregunta) {
        txtRespuestaIA.setText("Consultando a la IA...");
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                try {
                    URL url = new URL(OPENAI_ENDPOINT);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Authorization", "Bearer " + OPENAI_API_KEY);
                    conn.setRequestProperty("Content-Type", "application/json");
                    conn.setDoOutput(true);

                    JSONObject jsonBody = new JSONObject();
                    jsonBody.put("model", "gpt-4-1106-preview");
                    jsonBody.put("messages", new org.json.JSONArray()
                        .put(new JSONObject().put("role", "system").put("content", "Eres un asistente experto en salud cardiovascular y uso de la app VitalPress."))
                        .put(new JSONObject().put("role", "user").put("content", pregunta))
                    );
                    String body = jsonBody.toString();
                    OutputStream os = conn.getOutputStream();
                    os.write(body.getBytes());
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
                        String respuesta = jsonResponse.getJSONArray("choices")
                                .getJSONObject(0)
                                .getJSONObject("message")
                                .getString("content");
                        return respuesta.trim();
                    } else {
                        return "Error al consultar la IA: " + response.toString();
                    }
                } catch (Exception e) {
                    return "Error de conexión: " + e.getMessage();
                }
            }
            @Override
            protected void onPostExecute(String result) {
                txtRespuestaIA.setText(result);
                scrollRespuestas.post(() -> scrollRespuestas.fullScroll(View.FOCUS_DOWN));
            }
        }.execute(pregunta);
    }
}
