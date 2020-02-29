package com.example.cavfar.Users;

import java.io.Serializable;

public class Usuario implements Serializable {
    private String email ;
    private String nombre ;
    private String usuario ;
    private String token;

    public Usuario(String email, String nombre, String usuario, String token) {
        this.email = email;
        this.nombre = nombre;
        this.usuario = usuario;
        this.token = token;
    }

    //Getters y Setters


    public String getToken() {
        return token;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre()
    {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUsuario() {
        return usuario;
    }
    public void setUsuario(String apellidos) {
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return nombre + ": " + usuario ;
    }
}
