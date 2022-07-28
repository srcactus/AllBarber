package com.example.allbarber.modelo;



public class Barberias{

    private String id;
    private String nombre;
    private String telefono;
    private String descripcion;



    public Barberias(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;

    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return nombre;

    }
}
