package modelo;


import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Padilla
 */
public class ClaseTemporal {

    private String nombre;
    private ArrayList<String> hijos;

    public ClaseTemporal(String nombre) {
        this.nombre = nombre;
        hijos = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ArrayList<String> getHijos() {
        return hijos;
    }

    public void setHijos(ArrayList<String> hijos) {
        this.hijos = hijos;
    }

    public void addHijo(String hijo) {
        this.hijos.add(hijo);
    }

}
