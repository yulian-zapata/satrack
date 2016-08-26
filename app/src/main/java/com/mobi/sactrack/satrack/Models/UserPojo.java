package com.mobi.sactrack.satrack.Models;

/**
 * Modelo para trabajar con retrofit
 */

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

public class UserPojo {

    @SerializedName("Id")
    private String id;
    @SerializedName("Nombre")
    private String nombre;
    @SerializedName("Usuario")
    private String usuario;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * @return The id
     */
    @SerializedName("Id")
    public String getId() {
        return id;
    }

    /**
     * @param id The Id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return The nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre The Nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return The usuario
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * @param usuario The Usuario
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
}