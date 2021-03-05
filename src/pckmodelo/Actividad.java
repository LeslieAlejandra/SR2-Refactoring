/**
 *
 * @author RenéSS,OliviaGFD, JavierSS
 */

package pckmodelo;

public abstract class Actividad extends EntidadPSP {
	public String comentario;
	public String estado;
        public String subcat;
	public int id;
        public int id_proyecto=0;
	public int tiempo;
	protected aEstadoA estadoA;

	public Actividad()
        {
            comentario="";
            estado="";
            subcat="";
            id=0;
            tiempo=0;
            estadoA=null;
        }
	protected void cambiarEstado(aEstadoA edo){}
	public abstract void cancelar();
	public abstract void cerrarsesion();
	public abstract void iniciar();
	public abstract void interrumpir();
	public abstract void reanudar();
	@Override public String registrar(int opc)
        {
            return "";
	}
	public abstract void suspender();
	public abstract void terminar();
}


