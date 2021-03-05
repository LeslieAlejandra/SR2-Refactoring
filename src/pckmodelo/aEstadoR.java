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

public class aEstadoR {
    public aEstadoR(){}
    protected void cambiarEdo(aReloj reloj, aEstadoR edo)
    {
        reloj.cambiarEstado(edo);
    }
    public void activar(aReloj reloj){}
    public void desactivar(aReloj reloj){}
}
