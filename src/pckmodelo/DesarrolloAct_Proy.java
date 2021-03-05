package pckmodelo;

/**
 * @version 1.0
 * @created 15-ago-2017 01:50:54 p.m.
 */

/**
 *
 * @author RenéSS,OliviaGFD, JavierSS
 */

public class DesarrolloAct_Proy extends aEstadoA {

	public static aEstadoA instancia=null;

	public static aEstadoA Instancia(){
            if(instancia==null)
                instancia= new DesarrolloAct_Proy();
            return instancia;
	}
        
	/**
	 * 
	 * @param a
	 */
	@Override public void cancelar(Actividad actividad){
            actividad.estado="C";
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
	@Override public void iniciar(Actividad actividad){
            actividad.estado="D";
	}

	/**
	 * 
	 * @param a
	 */
	@Override public void interrumpir(Actividad actividad){
            cambiarEstado(actividad,PendienteAct_Proy.Instancia());
            actividad.estado="P";
	}

	/**
	 * 
	 * @param a
	 */
	@Override public void suspender(Actividad actividad){
            cambiarEstado(actividad,PendienteAct_Proy.Instancia());
            actividad.estado="P";
	}

	/**
	 * 
	 * @param a
	 */
	@Override public void terminar(Actividad actividad){
            actividad.estado="NT";
	}

}