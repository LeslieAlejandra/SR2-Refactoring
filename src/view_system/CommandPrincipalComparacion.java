package view_system;
/* Clase: CommandPrincipalComparacion
 Autor: Manuel Valdes Marrero
 Fecha: 21 Julio 2003
 Descripción: Menu Comparar Archivos para el frame principal */
import java.beans.PropertyVetoException;

import modelo.ResultData;
public class CommandPrincipalComparacion extends CommandPrincipal {
	/* CommandPrincipalComparacion(Principal frame)
	 Constructor que recibe el frame principal, al que modificar con el comando. */
	public CommandPrincipalComparacion(principal_view frame){
		this.frame=frame;
	}
	/* void ejecutar()
	 Ejecución del comando.
	 No regresa ni recibe nada.*/
	public void ejecutar(){
		boolean band=false;
		if (frame.frameComparacion.isClosed())
			band=true;
		ResultData resultData = ResultData.getSingletonInstance();
		
		frame.frameComparacion=FrameComparacion.instanciar(frame.archivosFuente,frame.archivosOriginales);
		if (band==true)
			frame.desktop.add(frame.frameComparacion);
		frame.frameComparacion.setVisible(true);
		try {
			frame.frameComparacion.setIcon(false);
			frame.frameComparacion.moveToFront();
			frame.frameComparacion.setSelected(true);
		} catch (PropertyVetoException e1) {
			// nada
		}
	}
}
