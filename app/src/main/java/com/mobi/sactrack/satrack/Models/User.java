package com.mobi.sactrack.satrack.Models;

import io.realm.RealmObject;

/**
 * Modelo para trabajar con Realm
 */
public class User extends RealmObject {

    private String id;
    private String nombre;
    private String usuario;

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

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

}
