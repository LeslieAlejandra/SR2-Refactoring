/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.ArrayList;

/**
 *
 * @author Padilla
 */
public class ConjuntoInterfaz {

    private Clase interfaz;
    private ArrayList<Clase> clasesIntermedias;
    private int contadorNombres;

    public ConjuntoInterfaz() {
        clasesIntermedias = new ArrayList<>();
        contadorNombres = 1;
    }

    public Clase getInterfaz() {
        return interfaz;
    }

    public void setInterfaz(Clase interfaz) {
        this.interfaz = interfaz;
    }

    public ArrayList<Clase> getClasesIntermedias() {
        return clasesIntermedias;
    }

    public void setClasesIntermedias(ArrayList<Clase> clasesIntermedias) {
        this.clasesIntermedias = clasesIntermedias;
    }

    public void addClaseIntermedia(Clase claseIntermedia) {
        this.clasesIntermedias.add(claseIntermedia);
    }

}
