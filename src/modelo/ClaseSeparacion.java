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
public class ClaseSeparacion {

    private Clase claseOriginal;
    private ArrayList<ConjuntoMetodos> conjuntos;
    private ArrayList<MetodoProblematico> metodosProblematicos;
    private ArrayList<Clase> clasesIntermedias;
    private ArrayList<Clase> clasesDerivadas;

    public ClaseSeparacion(Clase claseOriginal) {
        this.claseOriginal = claseOriginal;
        conjuntos = new ArrayList<>();
        metodosProblematicos = new ArrayList<>();
        clasesIntermedias = new ArrayList<>();
        clasesDerivadas = new ArrayList<>();
    }

    public Clase getClaseOriginal() {
        return claseOriginal;
    }

    public void setClaseOriginal(Clase claseOriginal) {
        this.claseOriginal = claseOriginal;
    }

    public ArrayList<MetodoProblematico> getMetodosProblematicos() {
        return metodosProblematicos;
    }

    public ArrayList<Metodo> getMetodosProblematicosMetodo() {
        ArrayList<Metodo> aux = new ArrayList<>();
        for (MetodoProblematico met : metodosProblematicos) {
            Metodo metodo;
            metodo = met.getMetodo();
            aux.add(metodo);
        }
        return aux;
    }

    public ArrayList<Metodo> getMetodosNoAbstractos() {
        ArrayList<Metodo> aux = new ArrayList<>();
        for (Metodo met : claseOriginal.getMetodos()) {
            if (met.getMetodoCompleto().contains("{")) {
                aux.add(met);
            }
        }
        return aux;
    }

    public ArrayList<Metodo> getMetodosProblematicosAbstractos() {
        ArrayList<Metodo> aux = new ArrayList<>();
        for (MetodoProblematico met : metodosProblematicos) {
            Metodo metodo;
            metodo = met.getMetodo();
            metodo.setMetodoCompletoAux(metodo.getMetodoCompleto());
            if (claseOriginal.isAbstracta()) {
                metodo.setModificador(metodo.getModificador() + " abstract");
            }
            metodo.setMetodoCompleto(metodo.getModificador() + " " + met.getMetodo().getTipoRetorno() + " " + met.getMetodo().getNombre() + met.getMetodo().getParametrosLinea() + ";");
            aux.add(metodo);
        }
        return aux;
    }

    public void setMetodosProblematicos(ArrayList<MetodoProblematico> metodosProblematicos) {
        this.metodosProblematicos = metodosProblematicos;
    }

    public void addMetodosProblematicos(Clase implementador, ArrayList<Metodo> metP) {
        for (Metodo metodoARevisar : metP) {
            boolean encontrado = false;
            for (MetodoProblematico metodoP : metodosProblematicos) {
                String metodoC1 = metodoP.getMetodo().getMetodoCompleto().replaceAll("\r", "").replaceAll(" ", "").replaceAll("\t", "").replaceAll("\n", "");
                String metodoC2 = metodoARevisar.getMetodoCompleto().replaceAll("\r", "").replaceAll(" ", "").replaceAll("\t", "").replaceAll("\n", "");
                if (metodoC1.equals(metodoC2)) {
                    metodoP.addImplementador(implementador);
                    encontrado = true;
                    break;
                }
            }
            if (!encontrado) {
                MetodoProblematico mp = new MetodoProblematico(metodoARevisar, implementador);
                metodosProblematicos.add(mp);
            }
        }
    }
    public ArrayList<ConjuntoMetodos> getConjuntos() {
        return conjuntos;
    }

    public void setConjuntos(ArrayList<ConjuntoMetodos> conjuntos) {
        this.conjuntos = conjuntos;
    }

    public void addConjunto(ConjuntoMetodos conjunto) {
        this.conjuntos.add(conjunto);
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

    public ArrayList<Clase> getClasesDerivadas() {
        return clasesDerivadas;
    }

    public void setClasesDerivadas(ArrayList<Clase> clasesDerivadas) {
        this.clasesDerivadas = clasesDerivadas;
    }

    public void addClaseDerivada(Clase claseDerivada) {
        this.clasesDerivadas.add(claseDerivada);
    }
}
