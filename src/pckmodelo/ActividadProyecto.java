
/**
 * @version 1.0
 * @created 15-ago-2017 01:50:54 p.m.
 */

/**
 *
 * @author RenéSS,OliviaGFD, JavierSS
 */
package pckmodelo;


public class ActividadProyecto extends Actividad {
        
        
	public ActividadProyecto(int id_proyecto)
        {
            super();
            cambiarEstado(DesarrolloAct_Proy.Instancia());
            this.id_proyecto=id_proyecto;
	}

	@Override protected void cambiarEstado(aEstadoA edo)
        {
            estadoA=edo;
	}

	@Override public void cancelar()
        {
            estadoA.cancelar(this);
	}

	@Override public void cerrarsesion()
        {
            estadoA.cerrarsesion(this);
	}

	@Override public void iniciar()
        {
            estadoA.iniciar(this);
	}

	@Override public void interrumpir()
        {
            estadoA.interrumpir(this);
	}

	@Override public void reanudar()
        {
            estadoA.reanudar(this);
	}

	@Override public String registrar(int opc)
        {
	    String cadena= new String();
            String fecha= new String();
            fecha= "DATE_FORMAT(NOW(),'%e/%m/%y %H:%i')";
            switch (opc) {
            case 1://hace el insert
                cadena= "insert into actividad(Id_proyecto,Id_subcategorias,Id_estado,Id_usuario,Comentario,FechaI) values (" + id_proyecto + "," + new Integer(subcat) + ",'" + estado + "'," + Usuario.id + ",'" + comentario + "', " + fecha + ")";
                break;
            case 2://hace un update
                cadena= "update actividad set Id_estado= '" + estado + "',Tiempo= (Tiempo + " + tiempo + "),FechaF= " + fecha + ",Comentario='" + comentario + "' where Id_actividad=" + id + " and Id_usuario=" + Usuario.id + " and Id_proyecto=" + id_proyecto;
                break;
            case 3://hace un delete
                cadena= "delete from actividad where Id_actividad=" + id + " and Id_usuario=" + Usuario.id + " and Id_proyecto=" + id_proyecto;
                break;
            }
            return cadena;
	}

	@Override public void suspender()
        {
            estadoA.suspender(this);
	}

	@Override public void terminar()
        {
            estadoA.terminar(this);
	}

}