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
public class Interfaz {

    private String paquete;
    private String nombre;
    private ArrayList<Variable> variables;
    private ArrayList<Instancia> instancias;
    private ArrayList<Metodo> metodos;
    private boolean main;
    private boolean statica;
    private ArrayList<String> llamadas;
    private ArrayList<String> hijos;
    private String papaExtends;
    private ArrayList<String> papasImplements;
    private boolean interfaz;
    private boolean abstracta;
}
