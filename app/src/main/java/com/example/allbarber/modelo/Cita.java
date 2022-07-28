package com.example.allbarber.modelo;

public class Cita {

    private String id;
    private String idCliente;
    private String nombreBarberia;
    private String servicio;
    private String nombreBarbero;
    private String nombreCliente;
    private String hora;
    private String fecha;

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombreBarberia() {
        return nombreBarberia;
    }

    public void setNombreBarberia(String nombreBarberia) {
        this.nombreBarberia = nombreBarberia;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public String getNombreBarbero() {
        return nombreBarbero;
    }

    public void setNombreBarbero(String nombreBarbero) {
        this.nombreBarbero = nombreBarbero;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    @Override
    public String toString() {
        return "\n"+"Barberia: "+nombreBarberia+" \n "+servicio+" \n "+hora+"  "+fecha+" \n "+"Se atiende con: "+nombreBarbero+"\n";
    }
}
