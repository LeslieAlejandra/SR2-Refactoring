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
public class Prueba2 {

    public String metodoPrueba2() {
        Prueba3 p3 = new Prueba3();
        p3.metodoPrueba1();
        return "llamadaMetodoPrueba2!";
    }

    public void metodoPrueba22() {

    }

    public void metodoPrueba222() {

    }
}
