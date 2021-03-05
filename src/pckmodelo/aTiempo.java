/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pckmodelo;

/**
 * @version 1.0
 * @created 15-ago-2017 01:50:54 p.m.
 */

/**
 *
 * @author RenéSS,OliviaGFD, JavierSS
 */

public abstract class aTiempo {
    protected int hora;
    protected int minuto;
    public aTiempo()
    {
        hora=0;
        minuto=0;
    }
    public abstract int calcular_min();
}
