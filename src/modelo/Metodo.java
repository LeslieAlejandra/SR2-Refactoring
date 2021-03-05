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
public class Metodo implements Cloneable {

    private String modificador;
    private String tipoRetorno;
    private String nombre;
    private ArrayList<Parametro> parametros;
    private String parametrosLinea;
    private ArrayList<Variable> variables;
    private ArrayList<Instancia> instancias;
    private ArrayList<String> llamadas;
    private Clase claseContenedora;
    private boolean abstracto;
    private String cuerpo;
    private String metodoCompleto;
    private String metodoCompletoAux;

    public Metodo() {

    }

    public Metodo(String modificador, String tipoRetorno, String nombre, Clase claseContenedora) {
        this.modificador = modificador;
        this.tipoRetorno = tipoRetorno;
        this.nombre = nombre;
        this.claseContenedora = claseContenedora;
        parametros = new ArrayList<>();
        variables = new ArrayList<>();
        instancias = new ArrayList<>();
        llamadas = new ArrayList<>();

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ArrayList<String> getLlamadas() {
        return llamadas;
    }

    public void setLlamadas(ArrayList<String> llamadas) {
        this.llamadas = llamadas;
    }

    public void addParametro(Parametro parametro) {
        this.parametros.add(parametro);
    }

    public void addVariable(Variable variable) {
        this.variables.add(variable);
    }

    public void addLlamada(String llamada) {
        this.llamadas.add(llamada);
    }

    public void addInstancia(Instancia instancia) {
        this.instancias.add(instancia);
    }

    public ArrayList<Parametro> getParametros() {
        return parametros;
    }

    public void setParametros(ArrayList<Parametro> parametros) {
        this.parametros = parametros;
    }

    public String getParametrosLinea() {
        return parametrosLinea;
    }

    public void setParametrosLinea(String parametrosLinea) {
        this.parametrosLinea = parametrosLinea;
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

    public String getModificador() {
        return modificador;
    }

    public void setModificador(String modificador) {
        this.modificador = modificador;
    }

    public String getTipoRetorno() {
        return tipoRetorno;
    }

    public void setTipoRetorno(String tipoRetorno) {
        this.tipoRetorno = tipoRetorno;
    }

    public Clase getClaseContenedora() {
        return claseContenedora;
    }

    public void setClaseContenedora(Clase claseContenedora) {
        this.claseContenedora = claseContenedora;
    }

    public boolean isAbstracto() {
        return abstracto;
    }

    public void setAbstracto(boolean abstracto) {
        this.abstracto = abstracto;
    }

    public String getCuerpo() {
        return cuerpo;
    }

    public void setCuerpo(String cuerpo) {
        this.cuerpo = cuerpo;
    }

    public String getMetodoCompleto() {
        return metodoCompleto;
    }

    public void setMetodoCompleto(String metodoCompleto) {
        this.metodoCompleto = metodoCompleto;
    }

    public String getMetodoCompletoAux() {
        return metodoCompletoAux;
    }

    public void setMetodoCompletoAux(String metodoCompletoAux) {
        this.metodoCompletoAux = metodoCompletoAux;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {

        return super.clone();
    }
}
