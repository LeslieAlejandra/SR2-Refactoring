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
public class PapaConjuntoInterfaz {

    private Clase papa;
    private ArrayList<ConjuntoInterfaz> listaClasesIntermedias;

    public Clase getPapa() {
        return papa;
    }

    public void setPapa(Clase papa) {
        this.papa = papa;
    }

    public ArrayList<ConjuntoInterfaz> getListaClasesIntermedias() {
        return listaClasesIntermedias;
    }

    public void setListaClasesIntermedias(ArrayList<ConjuntoInterfaz> listaClasesIntermedias) {
        this.listaClasesIntermedias = listaClasesIntermedias;
    }
}
