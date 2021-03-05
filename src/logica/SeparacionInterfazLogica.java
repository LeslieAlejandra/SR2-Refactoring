/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Clase;
import modelo.ClaseSeparacion;
import modelo.ConjuntoMetodos;
import modelo.Metodo;
import modelo.MetodoProblematico;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STRawGroupDir;
import org.stringtemplate.v4.StringRenderer;

/**
 *
 * @author Padilla
 */
public class SeparacionInterfazLogica {

    private ArrayList<Clase> clases;

    public SeparacionInterfazLogica(ArrayList<Clase> clases) {
        this.clases = clases;
    }

    private ArrayList<ClaseSeparacion> separacionDeInterfaz() throws CloneNotSupportedException {
        ArrayList<ClaseSeparacion> clasesFinales = new ArrayList<>();
        for (Clase clase : clases) {
            if (clase.isAbstracta() || clase.isInterfaz()) {
                ClaseSeparacion aux = new ClaseSeparacion(clase);
                clase.getHijos().stream().map((h) -> getClase(h)).forEach((hijo) -> {
                    ArrayList<Metodo> metodosNoUtilizadosEnHijo = getMetodosNoUtilizadosHijo(clase, hijo);

                    aux.addMetodosProblematicos(hijo, metodosNoUtilizadosEnHijo);
                    hijo.setMetodosNoUtilizados(metodosNoUtilizadosEnHijo);
                    clase.addHijoClase(hijo);
                });
                if (aux.getMetodosProblematicos().size() > 0) {
                    clasesFinales.add(aux);
                }
            }
        }
        generarConjuntos(clasesFinales);

        generarRefactorizacion(clasesFinales);

        for (ClaseSeparacion cs : clasesFinales) {
            for (Clase hijo : cs.getClaseOriginal().getHijosClase()) {
                refactorizarHijos(cs, hijo);

                for (Clase ci : cs.getClasesIntermedias()) {
                    if (incluyeMetodos(hijo.getMetodos(), ci.getMetodosAbstractos())) {
                        ci.addHijoClase(hijo);
                    }
                }
            }
        }
        return clasesFinales;
    }

    public void refactorizarHijos(ClaseSeparacion padreAnterior, Clase hijo) {
        ArrayList<Metodo> listaMetodos = new ArrayList<>();
        for (Metodo metodo : hijo.getMetodos()) {
            boolean flag = false;
            for (Metodo mNoUtilizado : hijo.getMetodosNoUtilizados()) {
                if (metodosSonIguales(metodo, mNoUtilizado)) {
                    flag = true;
                    break;
                }
            }
            if (flag) {
                for (Metodo aux : padreAnterior.getMetodosProblematicosMetodo()) {
                    if (metodosSonIguales(aux, metodo)) {

                        try {
                            metodo = (Metodo) metodo.clone();
                        } catch (CloneNotSupportedException ex) {
                            Logger.getLogger(SeparacionInterfazLogica.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        metodo.setMetodoCompleto(aux.getMetodoCompletoAux());
                        listaMetodos.add(metodo);
                        break;
                    }
                }
            } else {
                listaMetodos.add(metodo);
            }
        }
        hijo.setMetodos(listaMetodos);
    }

    private void generarRefactorizacion(ArrayList<ClaseSeparacion> clasesFinales) {
        for (ClaseSeparacion cs : clasesFinales) {
            int contador1 = 1;
            ArrayList<Metodo> metodosNoAbstractos = cs.getMetodosNoAbstractos();
            cs.getClaseOriginal().setMetodos(cs.getMetodosProblematicosAbstractos());

            int contadorClasesIntermedias = 1;
            cs.setClasesIntermedias(crearClasesIntermedias(cs.getConjuntos()));
            for (ConjuntoMetodos cm : cs.getConjuntos()) {
                Metodo metodoNuevo = new Metodo();
                Metodo mAux = cm.getMetodosConjunto().get(0).getMetodo();
                String nombreMetodoNuevo = "algoritmoInterfaz";
                if (cs.getClaseOriginal().isAbstracta()) {
                    mAux.setModificador(mAux.getModificador() + " abstract");
                }
                String metodoStr = mAux.getModificador() + " " + mAux.getTipoRetorno() + " " + nombreMetodoNuevo + contador1++ + mAux.getParametrosLinea() + ";";
                metodoNuevo.setMetodoCompleto(metodoStr);

                cs.getClaseOriginal().addMetodo(metodoNuevo);
                ////////
            }
            cs.getClaseOriginal().addMetodosFinal(metodosNoAbstractos);
        }
    }

    private boolean incluyeMetodos(ArrayList<Metodo> mHijos, ArrayList<Metodo> mPapa) {

        for (Metodo metodoPapa : mPapa) {
            boolean flag = false;
            for (Metodo mHijo : mHijos) {
                if (metodosSonIguales(mHijo, metodoPapa)) {
                    flag = true;
                    break;
                }

            }
            if (!flag) {
                return false;
            }

        }

        return true;
    }

    private boolean metodosSonIguales(Metodo m1, Metodo m2) {
        return m1.getTipoRetorno().equals(m2.getTipoRetorno()) && m1.getNombre().equals(m2.getNombre()) && m1.getParametrosLinea().equals(m2.getParametrosLinea());
    }

    public ArrayList<Clase> crearClasesIntermedias(ArrayList<ConjuntoMetodos> conjuntos) {
        ArrayList<Clase> listaClasesIntermedias = new ArrayList<>();
        int contador = 1;
        ConjuntoMetodos cm = conjuntos.get(0);
        int contador2 = 1;
        for (MetodoProblematico mp : cm.getMetodosConjunto()) {
            Clase claseIntermedia = new Clase("Algor" + contador++);
            claseIntermedia.setAbstracta(true);
            //claseIntermedia.setPaquete(ccs.getClaseOriginal().getPaquete());
            claseIntermedia.setModificador("public abstract");
            claseIntermedia.addMetodos(crearParMetodosClaseIntermedia("algoritmoInterfaz", contador2, mp));
            conjuntos.stream().filter((cm2) -> (!cm.equals(cm2))).forEachOrdered((cm2) -> {
                int contador3 = contador2;
                for (MetodoProblematico mp2 : cm2.getMetodosConjunto()) {

                    if (mp2.getImplementadores().equals(mp.getImplementadores())) {
                        claseIntermedia.addMetodos(crearParMetodosClaseIntermedia("algoritmoInterfaz", ++contador3, mp2));
                    }
                }
            });
            listaClasesIntermedias.add(claseIntermedia);
        }
        return listaClasesIntermedias;
    }

    public ArrayList<Metodo> crearParMetodosClaseIntermedia(String nombre, int contador, MetodoProblematico mp) {
        ArrayList<Metodo> lista = new ArrayList<>();
        Metodo mConjunto = mp.getMetodo();
        lista.add(mConjunto);
        Metodo met = new Metodo();
        met.setTipoRetorno(mConjunto.getTipoRetorno());
        met.setNombre(mConjunto.getNombre());
        met.setParametrosLinea(mConjunto.getParametrosLinea());
        met.setMetodoCompleto("public abstract " + mConjunto.getTipoRetorno() + " " + mConjunto.getNombre() + mConjunto.getParametrosLinea() + ";");
        met.setAbstracto(true);
        lista.add(met);

        String metodoStrClaseI;
        if (!mConjunto.getTipoRetorno().equals("void")) {
            metodoStrClaseI = "@Override\npublic " + mConjunto.getTipoRetorno() + " " + nombre + (contador) + mConjunto.getParametrosLinea() + "{\n\treturn " + mConjunto.getNombre() + mConjunto.getParametrosLinea() + ";\n}";
        } else {
            metodoStrClaseI = "@Override\npublic " + mConjunto.getTipoRetorno() + " " + nombre + (contador) + mConjunto.getParametrosLinea() + "{\n\t" + mConjunto.getNombre() + mConjunto.getParametrosLinea() + ";\n}";
        }
        mConjunto.setMetodoCompleto(metodoStrClaseI);
        return lista;
    }

    public void generarConjuntos(ArrayList<ClaseSeparacion> clasesFinales) {
        ArrayList<MetodoProblematico> metodosPAux;
        for (ClaseSeparacion clase : clasesFinales) {
            metodosPAux = new ArrayList<>();
            while (clase.getMetodosProblematicos().size() > 1) {
                MetodoProblematico mAComparar = clase.getMetodosProblematicos().get(0);
                ConjuntoMetodos cm = new ConjuntoMetodos();
                cm.addMetodoAConjunto(mAComparar);

                for (int i = 1; i < clase.getMetodosProblematicos().size(); i++) {
                    MetodoProblematico item = clase.getMetodosProblematicos().get(i);
                    ArrayList<Clase> implComparar = item.getImplementadores();
                    boolean flag = false;
                    for (MetodoProblematico mp : cm.getMetodosConjunto()) {
                        for (Clase cImp : mp.getImplementadores()) {
                            boolean flagE = false;
                            for (Clase cImp2 : implComparar) {
                                if (cImp.getNombre().equals(cImp2.getNombre())) {
                                    flagE = true;
                                    break;
                                }
                            }
                            if (flagE) {
                                flag = true;
                            } else {
                                flag = false;
                                break;
                            }
                        }
                    }

                    Metodo metAux = mAComparar.getMetodo();
                    Metodo itemAux = item.getMetodo();
                    if (metAux.getTipoRetorno().equals(itemAux.getTipoRetorno()) && metAux.getParametrosLinea().equals(itemAux.getParametrosLinea())) {
                        if (!flag && !item.isOcupado()) {
                            cm.addMetodoAConjunto(item);
                            item.setOcupado(true);
                        }
                    }
                }
                if (cm.getMetodosConjunto().size() > 1) {
                    clase.addConjunto(cm);
                } else {
                    if (!clase.getMetodosProblematicos().get(0).isOcupado()) {
                        metodosPAux.add(clase.getMetodosProblematicos().get(0));
                    }
                }
                clase.getMetodosProblematicos().remove(0);
            }
            clase.setMetodosProblematicos(metodosPAux);
        }
    }

    private ArrayList<Metodo> getMetodosNoUtilizadosHijo(Clase papa, Clase hijo) {
        ArrayList<Metodo> metodosNoUtilizados = new ArrayList<>();
        for (Metodo metodoPapa : papa.getMetodos()) {
            if (metodoPapa.isAbstracto()) {
                for (Metodo metodoHijo : hijo.getMetodos()) {
                    if (metodoPapa.getNombre().equals(metodoHijo.getNombre())) {
                        if (esMetodoNoUtilizado(metodoHijo.getTipoRetorno(), metodoHijo.getCuerpo())) {
                            metodosNoUtilizados.add(metodoHijo);
                        }
                    }
                }
            }
        }

        return metodosNoUtilizados;
    }

    private boolean esMetodoNoUtilizado(String tipoRetorno, String cuerpo) {
        //CHECAR QUE EL CUERPO ESTE VACÃ�O
        if (cuerpo.equals("{}")) {
            return true;
        } else if (cuerpo.equals("{return0;}") && (tipoRetorno.equals("byte") || tipoRetorno.equals("short") || tipoRetorno.equals("int") || tipoRetorno.equals("long") || tipoRetorno.equals("float") || tipoRetorno.equals("double") || tipoRetorno.equals("char"))) {
            return true;
        } else if (tipoRetorno.equals("boolean") && cuerpo.equals("{returnfalse;}")) {
            return true;
        } else if (tipoRetorno.equals("String") && cuerpo.equals("{return\"\";}")) {
            return true;
        } else if (cuerpo.equals("{returnnull;}")) {
            return true;
        }
        return false;
    }

    private ArrayList<Metodo> getMetodosUtilizados(Clase papa, Clase hijo) {

        ArrayList<Metodo> metodosUtilizados = new ArrayList<>();
        for (Metodo metodoPapa : papa.getMetodos()) {
            if (metodoPapa.isAbstracto()) {
                for (Metodo metodoHijo : hijo.getMetodos()) {
                    if (metodoPapa.getNombre().equals(metodoHijo.getNombre())) {
                        //CHECAR QUE EL CUERPO ESTE VACÃ�O
                        if (!esMetodoNoUtilizado(metodoHijo.getTipoRetorno(), metodoHijo.getCuerpo())) {
                            metodosUtilizados.add(metodoHijo);
                        }
                    }
                }
            }
        }
        return metodosUtilizados;
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

    public void generarCodigoRefactorizado() {
        try {
            ArrayList<ClaseSeparacion> conjuntos = separacionDeInterfaz();
            generarClasesAbstractas(conjuntos);
            generarClasesIntermedias(conjuntos);
            generarImplementaciones(conjuntos);
        } catch (IOException ex) {
            Logger.getLogger(SeparacionInterfazLogica.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(SeparacionInterfazLogica.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void generarClasesAbstractas(ArrayList<ClaseSeparacion> conjuntos) throws IOException {
        STGroup g = new STRawGroupDir("templates");
        g.registerRenderer(String.class, new StringRenderer());

//        String ruta = "C:\\Users\\Pablo Padilla\\Documents\\worskpace-new\\SR2 Refactoring Java\\src\\codigorefactorizado";
        String ruta = "C:\\Users\\Orlando\\Documents\\Maestría\\Tesis\\SR2-Pablo\\SR2\\SR2 Refactoring Java\\src\\codigorefactorizado";
        File file;
        for (ClaseSeparacion conjunto : conjuntos) {
            Clase clase = conjunto.getClaseOriginal();

            ST st = g.getInstanceOf("PlantillaClaseJava");

            Clase papa = new Clase("");
            if (!clase.getPapaExtends().isEmpty()) {
                papa = getClase(clase.getPapaExtends());
            } else if (!clase.getPapasImplements().isEmpty()) {
                papa = new Clase(clase.getPapasLinea());
                papa.setInterfaz(true);
            }

            if (papa.isInterfaz()) {
                st.add("herencia", "implements");
            } else if (papa.isAbstracta()) {
                st.add("herencia", "extends");
            } else {
                st.add("herencia", "");
            }
            st.add("papa", papa);

            if (conjunto.getClaseOriginal().isInterfaz()) {
                st.add("tipoclase", "interface");
            } else {
                st.add("tipoclase", "class");
            }
            st.add("clase", clase);

            file = new File(ruta + "\\" + clase.getNombre() + ".java");
            FileWriter w = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(w);
            PrintWriter wr = new PrintWriter(bw);
            wr.write(st.render());
            wr.close();
            bw.close();
        }
    }

    private void generarClasesIntermedias(ArrayList<ClaseSeparacion> conjuntos) throws IOException {

        STGroup g = new STRawGroupDir("templates");
        g.registerRenderer(String.class, new StringRenderer());

        String ruta = "C:\\Users\\Orlando\\Documents\\Maestría\\Tesis\\SR2-Pablo\\SR2\\SR2 Refactoring Java\\src\\codigorefactorizado";
        File file;
        for (ClaseSeparacion conjunto : conjuntos) {
            for (Clase claseIntermedia : conjunto.getClasesIntermedias()) {
                ST st = g.getInstanceOf("PlantillaClaseJava");
                st.add("clase", claseIntermedia);
                Clase papa = conjunto.getClaseOriginal();
                if (papa.isInterfaz()) {
                    st.add("herencia", "implements");
                } else if (papa.isAbstracta()) {
                    st.add("herencia", "extends");
                }
                if (claseIntermedia.isInterfaz()) {
                    st.add("tipoclase", "interface");
                } else {
                    st.add("tipoclase", "class");
                }
                st.add("papa", papa);
                file = new File(ruta + "\\" + claseIntermedia.getNombre() + ".java");
                FileWriter w = new FileWriter(file);
                BufferedWriter bw = new BufferedWriter(w);
                PrintWriter wr = new PrintWriter(bw);
                wr.write(st.render());
                wr.close();
                bw.close();
            }
        }
    }

    private void generarImplementaciones(ArrayList<ClaseSeparacion> conjuntos) throws IOException {

        STGroup g = new STRawGroupDir("templates");
        g.registerRenderer(String.class, new StringRenderer());

        String ruta = "C:\\Users\\Orlando\\Documents\\Maestría\\Tesis\\SR2-Pablo\\SR2\\SR2 Refactoring Java\\src\\codigorefactorizado";
        File file;
        for (ClaseSeparacion conjunto : conjuntos) {
            for (Clase claseIntermedia : conjunto.getClasesIntermedias()) {
                for (Clase implementacion : claseIntermedia.getHijosClase()) {
                    ST st = g.getInstanceOf("PlantillaClaseJava");
                    st.add("clase", implementacion);
                    Clase papa = claseIntermedia;
                    if (papa.isInterfaz()) {
                        st.add("herencia", "implements");
                    } else if (papa.isAbstracta()) {
                        st.add("herencia", "extends");
                    }
                    if (implementacion.isInterfaz()) {
                        st.add("tipoclase", "interface");
                    } else {
                        st.add("tipoclase", "class");
                    }
                    st.add("papa", papa);
                    file = new File(ruta + "\\" + implementacion.getNombre() + ".java");
                    FileWriter w = new FileWriter(file);
                    BufferedWriter bw = new BufferedWriter(w);
                    PrintWriter wr = new PrintWriter(bw);
                    wr.write(st.render());
                    wr.close();
                    bw.close();
                }
            }
        }
    }
}
