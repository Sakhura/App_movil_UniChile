package com.unichile.app.models;

public class Nota {
    private int id, materiaId, porcentaje;
    private String evaluacion, fecha;
    private double valor;

    public Nota(int id, int materiaId, String evaluacion,
                double valor, int porcentaje, String fecha) {
        this.id = id; this.materiaId = materiaId; this.evaluacion = evaluacion;
        this.valor = valor; this.porcentaje = porcentaje; this.fecha = fecha;
    }

    public int    getId()          { return id; }
    public int    getMateriaId()   { return materiaId; }
    public String getEvaluacion()  { return evaluacion; }
    public double getValor()       { return valor; }
    public int    getPorcentaje()  { return porcentaje; }
    public String getFecha()       { return fecha; }
}
