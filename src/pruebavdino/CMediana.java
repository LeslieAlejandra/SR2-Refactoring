/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebavdino;

/**
 *
 * @author Padilla
 */
public class CMediana implements CStrategy {

    private double mediana;

    @Override
    public double getResultados() {
        return mediana;
    }

    @Override
    public void ordena() {

    }

    @Override
    public void calcula() {
        System.out.println("Algoritmo estadístico correspondiente");
    }
}
