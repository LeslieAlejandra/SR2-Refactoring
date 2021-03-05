package pckmodelo;

/**
 * @version 1.0
 * @created 15-ago-2017 01:50:54 p.m.
 */

/**
 *
 * @author RenéSS,OliviaGFD, JavierSS
 */

public abstract class aReporte {
	protected Conexion con;
        protected String nombreRpt;

	public aReporte(Conexion con,String nombreRpt){
            this.con=con;
            this.nombreRpt=nombreRpt;
	}
	public abstract void mostrar();
}