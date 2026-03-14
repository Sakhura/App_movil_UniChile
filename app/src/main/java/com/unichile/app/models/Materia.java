package com.unichile.app.models;

public class Materia {
    private int id, creditos;
    private String nombre, profesor, sala, dia, horaInicio, horaFin, color;

    public Materia(int id, String nombre, String profesor, String sala,
                   String dia, String horaInicio, String horaFin,
                   String color, int creditos) {
        this.id = id; this.nombre = nombre; this.profesor = profesor;
        this.sala = sala; this.dia = dia; this.horaInicio = horaInicio;
        this.horaFin = horaFin; this.color = color; this.creditos = creditos;
    }

    public int    getId()         { return id; }
    public String getNombre()     { return nombre; }
    public String getProfesor()   { return profesor; }
    public String getSala()       { return sala; }
    public String getDia()        { return dia; }
    public String getHoraInicio() { return horaInicio; }
    public String getHoraFin()    { return horaFin; }
    public String getColor()      { return color; }
    public int    getCreditos()   { return creditos; }
}
