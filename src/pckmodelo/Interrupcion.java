package pckmodelo;

/**
 * @version 1.0
 * @created 15-ago-2017 01:50:54 p.m.
 */

/**
 *
 * @author RenéSS,OliviaGFD, JavierSS
 */

public class Interrupcion extends EntidadPSP {
	public String descripcion;
	public int id;
	protected int tiempo;
        protected Actividad actividad;
	
	public Interrupcion(Actividad a){
            descripcion="";
            id=0;
            tiempo=0;
            actividad=a;
	}
        
       @Override public String registrar(int opc)
        {
	    String cadena= new String();
            String fecha= new String();
            fecha= "DATE_FORMAT(NOW(),'%e/%m/%y %H:%i')";
            switch (opc) {
            case 1://hace el insert
                cadena= "insert into interrupcion(Id_actividad,Descripcion,Fecha) values (" + actividad.id + ",'" + descripcion +  "', " + fecha + ")";
                break;
            case 2://hace un update
                cadena= "update interrupcion set Tiempo= (Tiempo + " + tiempo + ") where Id_actividad=" + actividad.id + " and Id_interrupcion=" + id;
                break;
            case 3://hace un delete
                cadena= "delete from interrupcion where Id_actividad=" + actividad.id + " and Id_interrupcion=" + id;
                break;
            }
            return cadena;
	}

	public void setTiempo(int tiempo){
            this.tiempo=tiempo;
	}


}