/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebacoherencia;

/**
 *
 * @author Padilla
 */
public class Prueba1 {

    private Prueba2 var;

    public Prueba1() {
        var = new Prueba2();
    }

    public void metodoPrueba1() {
        var.metodoPrueba2();
    }
}
