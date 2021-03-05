/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import modelo.Clase;
import modelo.Metodo;
import modelo.Parametro;
import modelo.ResultData;
import modelo.Variable;

/**
 *
 * @author Padilla
 */
public class CoherenciaLogica {

    private ArrayList<Clase> clases;
    private double totalSecuencia;//n
    private double totalMetodos;//m

    public CoherenciaLogica(ArrayList<Clase> clases) {
        this.clases = clases;
        this.totalSecuencia = 0;
        this.totalMetodos = 0;
    }

    public double calcular() {
        for (Clase clase : clases) {
            if (clase.isMain()) {
                for (String llamada : clase.getMetodos().get(0).getLlamadas()) {//LLAMADAS DEL MAIN
                    ArrayList<String> llamadaEnLista = getListaLlamadas(llamada);
                    Clase claseDePrimerElemento;
                    if (llamadaEnLista.get(0).contains("(") && llamadaEnLista.get(0).contains(")")) {//EL ITEM ES METODO
                        claseDePrimerElemento = getClase(getMetodo(clase, llamadaEnLista.get(0)).getTipoRetorno());
                    } else {//EL ITEM ES VARIABLE O ES CLASE ESTATICA
                        claseDePrimerElemento = getClaseDeVariable(clase, clase.getMetodos().get(0), llamadaEnLista.get(0));
                    }
                    recorrerLlamada(claseDePrimerElemento, clase.getMetodos().get(0), llamadaEnLista);
                }
                break;
            }
        }
        
        
        
        ResultData resultData = ResultData.getSingletonInstance();
        
        double valorCoherencia = totalSecuencia / totalMetodos;
        String newValCoherencia = String.format("%.1f", valorCoherencia);
        
        resultData.setCoherencia(newValCoherencia);
        resultData.setTotalMetodos(Double.toString(totalMetodos));
        
        System.out.println("Coherencia: " + totalSecuencia / totalMetodos);
        return totalSecuencia / totalMetodos;
    }

    private ArrayList<String> getListaLlamadas(String llamada) {
        ArrayList<String> listaLlamada = new ArrayList<>(Arrays.asList(llamada.split("\\.")));
        if (listaLlamada.isEmpty()) {
            listaLlamada.add(llamada);
        }
        return listaLlamada;
    }

    private void recorrerLlamada(Clase clase, Metodo metodo, ArrayList<String> llamada) {
        ArrayList<String> items = llamada;

        if (items.size() > 0) {//Es una llamada en cadena
            String itemActual = items.get(0);

            if (itemActual.contains("(") && itemActual.contains(")")) {//EL ITEM ES METODO
                totalSecuencia++;
                Metodo metAux = getMetodo(clase, itemActual);
                totalMetodos += metAux.getClaseContenedora().getMetodos().size();
                for (String llam : metAux.getLlamadas()) {
                    recorrerLlamada(metAux.getClaseContenedora(), metAux, getListaLlamadas(llam));
                }
                items.remove(0);
                recorrerLlamada(getClase(metAux.getTipoRetorno()), metAux, items);
            } else {//EL ITEM ES VARIABLE O ES CLASE ESTATICA
                Clase res = getClaseDeVariable(clase, metodo, itemActual);
                items.remove(0);
                recorrerLlamada(res, null, items);
            }
        }
    }

    private Metodo getMetodo(Clase clase, String met) {
        String nombreMetodo = met.substring(0, met.indexOf("("));
        for (Metodo metodo : clase.getMetodos()) {
            if (metodo.getNombre().equals(nombreMetodo)) {
                return metodo;
            }
        }
        return getMetodoHeredado(clase, met);
    }

    private Metodo getMetodoHeredado(Clase clase, String met) {
        Metodo res = null;
        if (clase == null) {
            return res;
        }
        Clase papaExtends = getClase(clase.getPapaExtends());
        if (papaExtends != null) {
            for (Metodo metodo : papaExtends.getMetodos()) {
                if (met.equals(metodo.getNombre())) {
                    res = metodo;
                }
            }
        }

        if (res == null) {
            List<Clase> papasImplements = new ArrayList<>();
            for (String c : clase.getPapasImplements()) {
                Clase claseAux = getClase(c);
                papasImplements.add(claseAux);
                for (Metodo metodo : claseAux.getMetodos()) {
                    if (met.equals(metodo.getNombre())) {
                        res = metodo;
                    }
                }
            }
            if (res == null) {
                try {
                    res = getMetodoHeredado(getClase(papaExtends.getPapaExtends()), met);
                } catch (NullPointerException e) {
                }

                if (res == null) {
                    try {
                        for (String c : papaExtends.getPapasImplements()) {
                            res = getMetodoHeredado(getClase(c), met);
                        }
                    } catch (NullPointerException e) {
                    }

                    if (res == null) {
                        try {
                            for (Clase c : papasImplements) {
                                res = getMetodoHeredado(c, met);
                            }
                        } catch (NullPointerException e) {
                        }
                    }
                }
            }
        }
        return res;
    }

    private Clase getClase(String nombre) {
        if (!nombre.equals("")) {
            for (Clase clase : clases) {
                if (clase.getNombre().equalsIgnoreCase(nombre)) {
                    return clase;
                }
            }
        }
        return null;
    }

    private Clase getClaseDeVariable(Clase clase, Metodo metodo, String item) {
        Clase claseRepresentada = null;
        Parametro localParametro = null;
        Variable varLocal = null;
        if (metodo != null) {
            localParametro = getParametroSiLocal(metodo, item);
            if (localParametro != null) {
                claseRepresentada = getClase(localParametro.getTipo());
            } else {
                varLocal = getVariableSiLocal(metodo, item);
                if (varLocal != null) {
                    claseRepresentada = getClase(varLocal.getTipo());
                }
            }
        }
        if (varLocal == null && localParametro == null) {
            Variable varClase = getVariableSiClase(clase, item);
            if (varClase != null) {
                claseRepresentada = getClase(varClase.getTipo());
            } else {
                Clase claseEstatica = getClaseSiEstatica(item);
                if (claseEstatica != null) {
                    claseRepresentada = claseEstatica;
                } else {
                    Clase claseVariableHeredada = getClaseDeVariableHeredada(clase, item);
                    if (claseVariableHeredada != null) {
                        claseRepresentada = claseVariableHeredada;
                    }
                }
            }
        }

        return claseRepresentada;
    }

    private Parametro getParametroSiLocal(Metodo metodo, String variable) {
        for (Parametro par : metodo.getParametros()) {
            if (par.getId().equals(variable)) {
                return par;
            }
        }
        return null;
    }

    private Variable getVariableSiLocal(Metodo metodo, String variable) {
        for (Variable var : metodo.getVariables()) {
            if (var.getId().equals(variable)) {
                return var;
            }
        }
        return null;
    }

    private Variable getVariableSiClase(Clase clase, String variable) {
        for (Variable var : clase.getVariables()) {
            if (var.getId().equals(variable)) {
                return var;
            }
        }
        return null;
    }

    private Clase getClaseSiEstatica(String var) {
        for (Clase clase : clases) {
            if (clase.isStatica() && clase.getNombre().equals(var)) {
                return clase;
            }
        }
        return null;
    }

    private Clase getClaseDeVariableHeredada(Clase clase, String var) {
        Clase res = null;
        if (clase == null) {
            return res;
        }
        Clase papaExtends = getClase(clase.getPapaExtends());
        if (papaExtends != null) {
            for (Variable variable : papaExtends.getVariables()) {
                if (var.equals(variable.getId())) {
                    res = getClase(variable.getTipo());
                }
            }
        }

        if (res == null) {
            List<Clase> papasImplements = new ArrayList<>();
            for (String c : clase.getPapasImplements()) {
                Clase claseAux = getClase(c);
                papasImplements.add(claseAux);
                for (Variable variable : claseAux.getVariables()) {
                    if (var.equals(variable.getId())) {
                        res = getClase(variable.getTipo());
                    }
                }
            }
            if (res == null) {
                res = getClaseDeVariableHeredada(getClase(papaExtends.getPapaExtends()), var);
                if (res == null) {
                    for (String c : papaExtends.getPapasImplements()) {
                        res = getClaseDeVariableHeredada(getClase(c), var);
                    }

                    if (res == null) {
                        for (Clase c : papasImplements) {
                            res = getClaseDeVariableHeredada(c, var);
                        }
                    }
                }
            }
        }
        return res;
    }

}
