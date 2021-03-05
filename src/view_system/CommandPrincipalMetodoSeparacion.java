package view_system;
/* Clase: CommandPrincipalMetodoSeparacion
 Autor: Manuel Valdés Marrero
 Fecha: 22 Enero 2004
 Descripción: Menu Método de Separación de Interfaces para el frame principal */
import java.beans.PropertyVetoException;

public class CommandPrincipalMetodoSeparacion extends CommandPrincipal {
	/* CommandPrincipalMetodoSeparacion(Principal frame)
	 Constructor que recibe el frame principal, al que modificar con el comando. */
	public CommandPrincipalMetodoSeparacion(principal_view frame){
		this.frame=frame;
	}
	/* void ejecutar()
	 Ejecución del comando.
	 No regresa ni recibe nada.*/
	public void ejecutar(){
		boolean band=false;
		if (frame.frameMetodoSeparacion.isClosed())
			band=true;
		frame.frameMetodoSeparacion=FrameMetodoSeparacion.instanciar(frame.archivosOriginales);
		if (band==true)
			frame.desktop.add(frame.frameMetodoSeparacion);
		frame.frameMetodoSeparacion.setVisible(true);
		try {
			frame.frameMetodoSeparacion.setIcon(false);
			frame.frameMetodoSeparacion.moveToFront();
			frame.frameMetodoSeparacion.setSelected(true);
		} catch (PropertyVetoException e1) {
			// nada
		}
	}
}
