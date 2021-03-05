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
public class ConjuntoMetodos {

    private Metodo metodoRefactorizado;
    private ArrayList<MetodoProblematico> metodosConjunto;
    private ArrayList<Clase> implementadoresUnificados;

    public ConjuntoMetodos() {
        metodosConjunto = new ArrayList<>();
        implementadoresUnificados = new ArrayList<>();
    }

    public Metodo getMetodoRefactorizado() {
        return metodoRefactorizado;
    }

    public void setMetodoRefactorizado(Metodo metodoRefactorizado) {
        this.metodoRefactorizado = metodoRefactorizado;
    }

    public ArrayList<MetodoProblematico> getMetodosConjunto() {
        return metodosConjunto;
    }

    public void setMetodosConjunto(ArrayList<MetodoProblematico> metodosConjunto) {
        this.metodosConjunto = metodosConjunto;
    }

    public void addMetodoAConjunto(MetodoProblematico metodo) {
        this.metodosConjunto.add(metodo);
    }

    public ArrayList<Clase> getImplementadoresUnificados() {
        return implementadoresUnificados;
    }

    public void setImplementadoresUnificados(ArrayList<Clase> implementadoresUnificados) {
        this.implementadoresUnificados = implementadoresUnificados;
    }

    public void addImplementadorUnificado(Clase implementador) {
        this.implementadoresUnificados.add(implementador);
    }

    public void unificarImplementadores() {

        for (MetodoProblematico mc : metodosConjunto) {
            for (Clase implementador : mc.getImplementadores()) {
                boolean flag = false;
                for (Clase implementadorLista : implementadoresUnificados) {
                    if (implementador.equals(implementadorLista)) {
                        flag = true;
                    }
                }
                if (!flag) {
                    implementadoresUnificados.add(implementador);
                }
            }
        }
        implementadoresUnificados.sort(Comparator.comparing((clase) -> clase.getNombre()));
    }
}
