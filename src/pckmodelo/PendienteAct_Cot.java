package pckmodelo;

/**
 * @version 1.0
 * @created 15-ago-2017 01:50:54 p.m.
 */

/**
 *
 * @author RenéSS,OliviaGFD, JavierSS
 */

public class PendienteAct_Cot extends aEstadoA {

	private static aEstadoA instancia=null;

	public static aEstadoA Instancia()
        {
            if(instancia==null)
                instancia= new PendienteAct_Cot();
            return instancia;
	}
        
	@Override public void cancelar(Actividad actividad)
        {
            actividad.estado="C";
	}
        
	@Override public void cerrarsesion(Actividad actividad){
            cambiarEstado(actividad,PendienteAct_Cot.Instancia());
            actividad.estado="P";
	}

	
	@Override public void reanudar(Actividad actividad){
            cambiarEstado(actividad,DesarrolloAct_Cot.Instancia());
            actividad.estado="D";
	}

}