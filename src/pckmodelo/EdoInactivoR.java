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

public class EdoInactivoR extends aEstadoR
{
    private static aEstadoR instancia=null;
    public static aEstadoR Instancia()
    {
        if(instancia==null)
            instancia= new EdoInactivoR();
        return instancia;
    }
    
    @Override public void activar(aReloj reloj)
    {
        cambiarEdo(reloj,EdoActivoR.Instancia());
    }
    
     @Override public void desactivar(aReloj reloj)
    {
        cambiarEdo(reloj,EdoInactivoR.Instancia());
    }

}
