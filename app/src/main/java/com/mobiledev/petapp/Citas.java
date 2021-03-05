package com.mobiledev.petapp;

public class Citas {

    String tipo_mascota;
    String contrasena;
    String telefono;
    String email;
    String mascota;

    public Citas() {
    }

    public Citas(String tipo_mascota, String contrasena, String telefono, String email,String mascota) {
        this.tipo_mascota = tipo_mascota;
        this.contrasena = contrasena;
        this.telefono = telefono;
        this.email = email;
        this.mascota = mascota;
    }

    public String getTipo_mascota() {
        return tipo_mascota;
    }

    public void setTipo_mascota(String tipo_mascota) {
        this.tipo_mascota = tipo_mascota;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
