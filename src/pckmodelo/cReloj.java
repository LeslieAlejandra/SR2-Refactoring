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

public class cReloj extends aReloj
{
    public cReloj()
    {
        super();
        cambiarEstado(EdoActivoR.Instancia());
    }
    
    @Override public void desactivar()
    {
        estadoR.desactivar(this);
        estado="inactivo";
        tiempoF=new cTiempo();
    }
    
    @Override public void activar()
    {
        estadoR.activar(this);
        estado="activo";
        tiempoI=new cTiempo();
    }
    
    @Override protected void cambiarEstado(aEstadoR edo)
    {
        estadoR=edo;
    }
    
    @Override public int getTiempo()
    {
        if(tiempoF!=null && tiempoI!=null){
            if((tiempoF.calcular_min()-tiempoI.calcular_min())==0)
                return 1;
            else 
                return tiempoF.calcular_min()-tiempoI.calcular_min();
        }
        return 1;
         
    }
    
    @Override public String getEstado()
    {
        return estado;
    }
}
