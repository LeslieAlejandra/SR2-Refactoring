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
public class Clase implements Cloneable {

    private String paquete;
    private ArrayList<String> imports;
    private String modificador;
    private String nombre;
    private String nombreRefactorizado;
    private ArrayList<String> variablesLinea;
    private ArrayList<Variable> variables;
    private ArrayList<Instancia> instancias;
    private ArrayList<String> constructores;
    private ArrayList<Metodo> metodos;
    private ArrayList<Metodo> metodosNoUtilizados;
    private boolean main;
    private boolean statica;
    private ArrayList<String> llamadas;
    private ArrayList<String> hijos;
    private ArrayList<Clase> hijosClase;
    private String papaExtends;
    private ArrayList<String> papasImplements;
    private String papasLinea;
    private boolean interfaz;
    private boolean abstracta;
    private int contadorSeparaciones;

    public Clase(String nombre) {
        imports = new ArrayList<>();
        modificador = "";
        this.nombre = nombre;
        this.nombreRefactorizado = "";
        variablesLinea = new ArrayList<>();
        variables = new ArrayList<>();
        instancias = new ArrayList<>();
        constructores = new ArrayList<>();
        metodos = new ArrayList<>();
        metodosNoUtilizados = new ArrayList<>();
        llamadas = new ArrayList<>();
        hijos = new ArrayList<>();
        hijosClase = new ArrayList<>();
        papasImplements = new ArrayList<>();
        papaExtends = "";
        contadorSeparaciones = 1;
    }

    public ArrayList<String> getImports() {
        return imports;
    }

    public void setImports(ArrayList<String> imports) {
        this.imports = imports;
    }

    public String getModificador() {
        return modificador;
    }

    public void setModificador(String modificador) {
        this.modificador = modificador;
    }

    public void addImport(String imp) {
        this.imports.add(imp);
    }

    public String getPaquete() {
        return paquete;
    }

    public void setPaquete(String paquete) {
        this.paquete = paquete;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombreRefactorizado() {
        return nombreRefactorizado;
    }

    public void setNombreRefactorizado(String nombreRefactorizado) {
        this.nombreRefactorizado = nombreRefactorizado;
    }

    public ArrayList<Metodo> getMetodos() {
        return metodos;
    }

    public void setMetodos(ArrayList<Metodo> metodos) {
        this.metodos = metodos;
    }

    public void addMetodo(Metodo metodo) {
        this.metodos.add(metodo);
    }

    public void addMetodos(ArrayList<Metodo> metodos) {
        this.metodos.addAll(metodos);
    }

    public void addMetodosFinal(ArrayList<Metodo> metodos) {
        this.metodos.addAll(metodos);
    }

    public ArrayList<Metodo> getMetodosNoUtilizados() {
        return metodosNoUtilizados;
    }

    public void setMetodosNoUtilizados(ArrayList<Metodo> metodosNoUtilizados) {
        this.metodosNoUtilizados = metodosNoUtilizados;
    }

    public void addMetodoNoUtilizado(Metodo metodoNoUtilizado) {
        this.metodosNoUtilizados.add(metodoNoUtilizado);
    }

    public boolean isMain() {
        return main;
    }

    public void setMain(boolean main) {
        this.main = main;
    }

    public void addInstancia(Instancia instancia) {
        this.instancias.add(instancia);
    }

    public ArrayList<String> getConstructores() {
        return constructores;
    }

    public void setConstructores(ArrayList<String> constructores) {
        this.constructores = constructores;
    }

    public void addConstructor(String constructor) {
        this.constructores.add(constructor);
    }

    public ArrayList<String> getVariablesLinea() {
        return variablesLinea;
    }

    public void setVariablesLinea(ArrayList<String> variablesLinea) {
        this.variablesLinea = variablesLinea;
    }

    public void addVariableLinea(String variableLinea) {
        this.variablesLinea.add(variableLinea);
    }

    public void addVariable(Variable variable) {
        this.variables.add(variable);
    }

    public ArrayList<Variable> getVariables() {
        return variables;
    }

    public void setVariables(ArrayList<Variable> variables) {
        this.variables = variables;
    }

    public ArrayList<Instancia> getInstancias() {
        return instancias;
    }

    public void setInstancias(ArrayList<Instancia> instancias) {
        this.instancias = instancias;
    }

    public ArrayList<String> getLlamadas() {
        return llamadas;
    }

    public void setLlamadas(ArrayList<String> llamadas) {
        this.llamadas = llamadas;
    }

    public void addLlamada(String llamada) {
        this.llamadas.add(llamada);
    }

    public boolean isStatica() {
        return statica;
    }

    public void setStatica(boolean statica) {
        this.statica = statica;
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

    public ArrayList<Clase> getHijosClase() {
        return hijosClase;
    }

    public void setHijosClase(ArrayList<Clase> hijosClase) {
        this.hijosClase = hijosClase;
    }

    public void addHijoClase(Clase hijoClase) {
        this.hijosClase.add(hijoClase);
    }

    public String getPapaExtends() {
        return papaExtends;
    }

    public void setPapaExtends(String papaExtends) {
        this.papaExtends = papaExtends;
    }

    public ArrayList<String> getPapasImplements() {
        return papasImplements;
    }

    public void setPapasImplements(ArrayList<String> papasImplements) {
        this.papasImplements = papasImplements;
    }

    public void addPapaImplements(String papaImplements) {
        this.papasImplements.add(papaImplements);
    }

    public String getPapasLinea() {
        return papasLinea;
    }

    public void setPapasLinea(String papasLinea) {
        this.papasLinea = papasLinea;
    }

    public boolean isInterfaz() {
        return interfaz;
    }

    public void setInterfaz(boolean interfaz) {
        this.interfaz = interfaz;
    }

    public boolean isAbstracta() {
        return abstracta;
    }

    public void setAbstracta(boolean abstracta) {
        this.abstracta = abstracta;
    }

    public ArrayList<Metodo> getMetodosAbstractos() {
        ArrayList<Metodo> aux = new ArrayList<>();
        for (Metodo metodo : metodos) {
            if (metodo.isAbstracto()) {
                aux.add(metodo);
            }
        }
        return aux;
    }

    public void removeMetodosHeredados(Clase papa) {
        ArrayList<Metodo> metodosAux = new ArrayList<>();
        for (Metodo metodo : metodos) {
            boolean flag = false;
            for (Metodo mPapa : papa.getMetodosAbstractos()) {
                if ((mPapa.getNombre().equalsIgnoreCase(metodo.getNombre()) && mPapa.getTipoRetorno().equalsIgnoreCase(metodo.getTipoRetorno()) && mPapa.getParametros().equals(metodo.getParametros()))) {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                metodosAux.add(metodo);
            }
        }
        metodos = metodosAux;
    }

    public int getContadorSeparaciones() {
        return contadorSeparaciones;
    }

    public void setContadorSeparaciones(int contadorSeparaciones) {
        this.contadorSeparaciones = contadorSeparaciones;
    }

    public void incrementarContadorSeparaciones() {
        this.contadorSeparaciones++;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {

        return super.clone();
    }
}
