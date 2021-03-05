/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.ArrayList;
import java.util.Comparator;

/**
 *
 * @author Padilla
 */
public class MetodoProblematico {

    private Metodo metodo;
    private ArrayList<Clase> implementadores;
    private boolean ocupado;

    public MetodoProblematico(Metodo metodo, Clase implementador) {
        implementadores = new ArrayList<>();
        this.metodo = metodo;
        this.implementadores.add(implementador);
        ocupado = false;
    }

    public Metodo getMetodo() {
        return metodo;
    }

    public void setMetodo(Metodo metodo) {
        this.metodo = metodo;
    }

    public ArrayList<Clase> getImplementadores() {
        implementadores.sort(Comparator.comparing((clase) -> clase.getNombre()));
        return implementadores;
    }

    public void setImplementadores(ArrayList<Clase> implementadores) {
        this.implementadores = implementadores;
    }

    public void addImplementador(Clase implementador) {
        this.implementadores.add(implementador);
    }

    public boolean isOcupado() {
        return ocupado;
    }

    public void setOcupado(boolean ocupado) {
        this.ocupado = ocupado;
    }

}
