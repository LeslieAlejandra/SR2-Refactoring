package pckmodelo;

/**
 * @version 1.0
 * @created 15-ago-2017 01:50:54 p.m.
 */

/**
 *
 * @author RenéSS,OliviaGFD, JavierSS
 */

public class Proyecto extends EntidadPSP {

	public String estado;
	public int id;
	public String nombre;
	
        public Proyecto(){
            this.estado="";
            id=0;
            this.nombre="";
	}

	public Proyecto(String nombre,String estado){
            this.estado=estado;
            id=0;
            this.nombre=nombre;
	}

	
	/**
	 * 
	 * @param opc
	 */
    @Override public String registrar(int opc){
            String cadena= new String();
            String fecha= new String();
            fecha= "DATE_FORMAT(NOW(),'%e/%m/%y %H:%i')";
            switch (opc) {
            case 1://hace el insert
                cadena= "insert into proyecto(Id_estado,Id_usuario,Nombre,Fecha) values ('" + estado + "','" + Usuario.id + "','" + nombre + "', " + fecha + ")";
                break;
            case 2://hace un update
                cadena= "update proyecto set Id_estado= '" + estado + "' where Id_proyecto=" + id + " and Id_usuario=" + Usuario.id;
                break;
            case 3://hace un delete
                cadena= "delete from proyecto where Id_proyecto=" + id + " and Id_usuario=" + Usuario.id;
                break;
            }
            return cadena;
	}

	
}