package com.unichile.app.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private static final String PREF_NAME = "UniChileSession";
    private static final String KEY_LOGGED = "isLoggedIn";
    private static final String KEY_ID = "estudianteId";
    private static final String KEY_NOMBRE = "nombre";
    private static final String KEY_APELLIDO = "apellido";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_CARRERA = "carrera";
    private static final String KEY_SEMESTRE = "semestre";

    private final SharedPreferences prefs;
    private final SharedPreferences.Editor editor;

    public SessionManager(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    public void createSession(int id, String nombre, String apellido, String email, String carrera, int semestre) {
        editor.putBoolean(KEY_LOGGED, true);
        editor.putInt(KEY_ID, id);
        editor.putString(KEY_NOMBRE, nombre);
        editor.putString(KEY_APELLIDO, apellido);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_CARRERA, carrera);
        editor.putInt(KEY_SEMESTRE, semestre);
        editor.apply();
    }

    public boolean isLoggedIn()      { return prefs.getBoolean(KEY_LOGGED, false); }
    public int    getEstudianteId()  { return prefs.getInt(KEY_ID, -1); }
    public String getNombre()        { return prefs.getString(KEY_NOMBRE, ""); }
    public String getApellido()      { return prefs.getString(KEY_APELLIDO, ""); }
    public String getNombreCompleto(){ return getNombre() + " " + getApellido(); }
    public String getEmail()         { return prefs.getString(KEY_EMAIL, ""); }
    public String getCarrera()       { return prefs.getString(KEY_CARRERA, ""); }
    public int    getSemestre()      { return prefs.getInt(KEY_SEMESTRE, 1); }

    public void logout() {
        editor.clear();
        editor.apply();
    }
}
