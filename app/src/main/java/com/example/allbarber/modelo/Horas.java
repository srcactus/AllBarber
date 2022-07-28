package com.example.allbarber.modelo;

public class Horas {

    private String id;
    private String hora;

    public Horas(String id, String hora) {
        this.id = id;
        this.hora = hora;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    @Override
    public String toString() {
        return hora;

    }
}
