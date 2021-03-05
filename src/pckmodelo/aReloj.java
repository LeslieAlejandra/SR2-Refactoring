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

public abstract class aReloj {
    protected String estado;
    protected aTiempo tiempoI;
    protected aTiempo tiempoF;
    protected aEstadoR estadoR;

    public aReloj(){
        estado="";
        tiempoI=null;
        tiempoF=null;
        estadoR=null;
    }
    public abstract void desactivar();
    public abstract void activar();
    protected abstract void cambiarEstado(aEstadoR edo);
    public abstract int getTiempo();
    public abstract String getEstado();
}
