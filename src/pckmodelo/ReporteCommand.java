/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pckmodelo;

import pckmodelo.aReporte;

/**
 * @version 1.0
 * @created 15-ago-2017 01:50:54 p.m.
 */

/**
 *
 * @author RenéSS,OliviaGFD, JavierSS
 */
public class ReporteCommand extends Command{
    private aReporte receptor; 
    public ReporteCommand(aReporte receptor)
     {
         super();
         this.receptor=receptor;
     }
     @Override public void execute()
     {
        receptor.mostrar();
     }

}
