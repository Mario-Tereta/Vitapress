package com.example.vitalpreesoficial.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

// Clase de base de datos central para VitalPress
public class DBVitalPress extends SQLiteOpenHelper {
    // Nombre y versión de la base de datos
    private static final String DATABASE_NAME = "vitalpress.db";
    private static final int DATABASE_VERSION = 1;

    public DBVitalPress(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crear tablas necesarias aquí
        db.execSQL("CREATE TABLE IF NOT EXISTS usuario (id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, correo TEXT, edad INTEGER, estatura REAL, peso REAL, contrasena TEXT)");
        // Puedes agregar más tablas según necesidades
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Manejar actualizaciones de esquema
        db.execSQL("DROP TABLE IF EXISTS usuario");
        onCreate(db);
    }

    // Método para insertar usuario
    public long insertUsuario(String nombre, String correo, int edad, double estatura, double peso, String contrasena) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombre", nombre);
        values.put("correo", correo);
        values.put("edad", edad);
        values.put("estatura", estatura);
        values.put("peso", peso);
        values.put("contrasena", contrasena);
        return db.insert("usuario", null, values);
    }

    // Método para actualizar usuario por id
    public int updateUsuario(int id, String nombre, String correo, int edad, double estatura, double peso, String contrasena) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombre", nombre);
        values.put("correo", correo);
        values.put("edad", edad);
        values.put("estatura", estatura);
        values.put("peso", peso);
        values.put("contrasena", contrasena);
        return db.update("usuario", values, "id=?", new String[]{String.valueOf(id)});
    }

    // Método para exportar todos los usuarios
    public List<String> exportarUsuarios() {
        List<String> usuarios = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM usuario", null);
        if (cursor.moveToFirst()) {
            do {
                String datos = "ID: " + cursor.getInt(0) + ", Nombre: " + cursor.getString(1) + ", Correo: " + cursor.getString(2) + ", Edad: " + cursor.getInt(3) + ", Estatura: " + cursor.getDouble(4) + ", Peso: " + cursor.getDouble(5);
                usuarios.add(datos);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return usuarios;
    }
}
