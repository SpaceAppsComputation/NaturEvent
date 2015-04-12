package com.example.asabater.pruebasmaps;

import android.text.Editable;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

/**
 * Created by Alberto on 11/04/2015.
 */
public class Perfil{

    private String nombre;
    private String description;
    private LatLng ubicacion;
    private String pathImg;
    private int likes;


    public Perfil (String nombre, LatLng ubicacion, String description, String pathImg, int likes) {
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.description = description;
        this.pathImg = pathImg;
        this.likes = likes;
    }



    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setUbicacion(LatLng ubicacion) {
        this.ubicacion = ubicacion;
    }

    public void setPathImg(String pathImg) {
        this.pathImg = pathImg;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public LatLng getUbicacion() {
        return ubicacion;
    }

    public String getNombre() {
        return nombre;
    }

    public String getPathImg() {
        return pathImg;
    }

    public int getLikes() {
        return likes;
    }
}
