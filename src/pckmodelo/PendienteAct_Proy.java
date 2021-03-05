package pckmodelo;

/**
 * @version 1.0
 * @created 15-ago-2017 01:50:54 p.m.
 */

/**
 *
 * @author RenéSS,OliviaGFD, JavierSS
 */

public class PendienteAct_Proy extends aEstadoA {

	private static aEstadoA instancia=null;

	public static aEstadoA Instancia()
        {
            if(instancia==null)
                instancia= new PendienteAct_Proy();
            return instancia;
	}

	/**
	 * 
	 * @param a
	 */
	@Override public void cerrarsesion(Actividad actividad){
            cambiarEstado(actividad,PendienteAct_Proy.Instancia());
            actividad.estado="P";
	}

	/**
	 * 
	 * @param a
	 */
	@Override public void reanudar(Actividad actividad){
            cambiarEstado(actividad,DesarrolloAct_Proy.Instancia());
            actividad.estado="D";
	}

}