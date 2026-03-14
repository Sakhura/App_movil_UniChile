package com.unichile.app.models;

public class Estudiante {
    private int id;
    private String nombre, apellido, email, carrera, rut;
    private int semestre;

    public Estudiante(int id, String nombre, String apellido, String email,
                      String carrera, int semestre, String rut) {
        this.id = id; this.nombre = nombre; this.apellido = apellido;
        this.email = email; this.carrera = carrera;
        this.semestre = semestre; this.rut = rut;
    }

    public int    getId()       { return id; }
    public String getNombre()   { return nombre; }
    public String getApellido() { return apellido; }
    public String getEmail()    { return email; }
    public String getCarrera()  { return carrera; }
    public int    getSemestre() { return semestre; }
    public String getRut()      { return rut; }
    public String getNombreCompleto() { return nombre + " " + apellido; }
}
