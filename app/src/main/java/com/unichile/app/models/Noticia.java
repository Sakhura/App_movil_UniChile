package com.unichile.app.models;

public class Noticia {
    private int id;
    private String titulo, descripcion, fecha, categoria;

    public Noticia(int id, String titulo, String descripcion, String fecha, String categoria) {
        this.id = id; this.titulo = titulo; this.descripcion = descripcion;
        this.fecha = fecha; this.categoria = categoria;
    }

    public int    getId()          { return id; }
    public String getTitulo()      { return titulo; }
    public String getDescripcion() { return descripcion; }
    public String getFecha()       { return fecha; }
    public String getCategoria()   { return categoria; }
}
