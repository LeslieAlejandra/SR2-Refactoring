/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.util.ArrayList;
import java.util.List;

import modelo.Clase;
import modelo.Metodo;
import modelo.ResultData;
import modelo.VDino;


/**
 *
 * @author Padilla
 */
public class VDinoLogica {

    private ArrayList<Clase> clases;

    public VDinoLogica(ArrayList<Clase> clases) {
        this.clases = clases;
    }


    public List<VDino> calcular() {
        ArrayList<VDino> lista = new ArrayList<>();
        for (Clase clase : clases) {
            int cnoc = 0, nfv = 0, nfno = 0;
            if (clase.isAbstracta() || clase.isInterfaz()) {

                cnoc = clase.getHijos().size();
                nfv = clase.getMetodosAbstractos().size();
                nfno = getMetodosNoUtilizados(clase).size();
                
                ResultData resultData = ResultData.getSingletonInstance();
                
                
                
                VDino aux = new VDino(clase, cnoc, nfv, nfno);
                resultData.setNombre(aux.getClase().getNombre());
                resultData.setCnoc(Integer.toString(aux.getCnoc()));
                resultData.setNfv(Integer.toString(aux.getNfv()));
                resultData.setNfno(Integer.toString(aux.getNfno()));
                String newValResult = String.format("%.5f", aux.getResultado());
                resultData.setResult(newValResult);
                
                System.out.println("Clase: " + aux.getClase().getNombre());
                System.out.println("       C-NOC: " + aux.getCnoc());
                System.out.println("       NFV: " + aux.getNfv());
                System.out.println("       NFNO: " + aux.getNfno());
                System.out.println("       V-DINO: " + aux.getResultado());

                lista.add(aux);
                resultData.setLista(lista);
            }
        }
        return lista;
    }

    private ArrayList<Metodo> getMetodosNoUtilizados(Clase clase) {
        ArrayList<Metodo> metodosNoUtilizados = new ArrayList<>();
        for (String h : clase.getHijos()) {
            Clase hijo = getClase(h);
            for (Metodo metodoPapa : clase.getMetodos()) {
                if (metodoPapa.isAbstracto()) {
                    for (Metodo metodoHijo : hijo.getMetodos()) {
                        if (metodoPapa.getNombre().equals(metodoHijo.getNombre())) {
                            //CHECAR QUE EL CUERPO ESTE O

                            String tAux = metodoHijo.getTipoRetorno();
                            if (metodoHijo.getCuerpo().equals("{}")) {
                                metodosNoUtilizados.add(metodoHijo);
                            } else if (metodoHijo.getCuerpo().equals("{return0;}") && (tAux.equals("byte") || tAux.equals("short") || tAux.equals("int") || tAux.equals("long") || tAux.equals("float") || tAux.equals("double") || tAux.equals("char"))) {
                                metodosNoUtilizados.add(metodoHijo);
                            } else if (tAux.equals("boolean") && metodoHijo.getCuerpo().equals("{returnfalse;}")) {
                                metodosNoUtilizados.add(metodoHijo);
                            } else if (tAux.equals("String") && metodoHijo.getCuerpo().equals("{return\"\";}")) {
                                metodosNoUtilizados.add(metodoHijo);
                            } else if (metodoHijo.getCuerpo().equals("{returnnull;}")) {
                                metodosNoUtilizados.add(metodoHijo);
                            }
                        }
                    }
                }
            }
        }
        return metodosNoUtilizados;
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
}
