package pckmodelo;

/**
 * @version 1.0
 * @created 15-ago-2017 01:50:54 p.m.
 */

/**
 *
 * @author RenéSS,OliviaGFD, JavierSS
 */

public class aEstadoA 
{
    public aEstadoA(){}
    protected void cambiarEstado(Actividad actividad,aEstadoA edo)
    {
        actividad.cambiarEstado(edo);
    }
    public void cancelar(Actividad actividad){}
    public void cerrarsesion(Actividad actividad){}
    public void iniciar(Actividad actividad){}
    public void interrumpir(Actividad actividad){}
    public void reanudar(Actividad actividad){}
    public void suspender(Actividad actividad){}
    public void terminar(Actividad actividad){}
	
}