package pckmodelo;

/**
 * @version 1.0
 * @created 15-ago-2017 01:50:54 p.m.
 */

/**
 *
 * @author RenéSS,OliviaGFD, JavierSS
 */

public class DesarrolloAct_Cot extends aEstadoA 
{
	private static aEstadoA instancia=null;

	public static aEstadoA Instancia()
        {
            if(instancia==null)
                instancia= new DesarrolloAct_Cot();
            return instancia;
	}
        
        @Override public void iniciar(Actividad actividad)
        {
            actividad.estado="D";
        }

	@Override public void cancelar(Actividad actividad)
        {
            actividad.estado="C";
	}

	@Override public void cerrarsesion(Actividad actividad)
        {
            cambiarEstado(actividad,PendienteAct_Cot.Instancia());
            actividad.estado="P";
	}

	@Override public void interrumpir(Actividad actividad)
        {
            cambiarEstado(actividad,PendienteAct_Cot.Instancia());
            actividad.estado="P";
	}

	@Override public void suspender(Actividad actividad)
        {
            cambiarEstado(actividad,PendienteAct_Cot.Instancia());
            actividad.estado="P";
	}

	@Override public void terminar(Actividad actividad)
        {
            actividad.estado="T";
	}

   }