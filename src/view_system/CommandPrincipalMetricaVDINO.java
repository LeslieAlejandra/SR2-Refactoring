package view_system;
/* Clase: CommandPrincipalMetricaDINO
 Autor: Manuel Valdés Marrero
 Fecha: 22 Enero 2004
 Descripción: Menu Calcular Métrica V-DINO para el frame principal */
import java.beans.PropertyVetoException;

public class CommandPrincipalMetricaVDINO extends CommandPrincipal {
	/* CommandPrincipalMetricaDINO(Principal frame)
	 Constructor que recibe el frame principal, al que modificar con el comando. */
	public CommandPrincipalMetricaVDINO(principal_view frame){
		this.frame=frame;
	}
	/* void ejecutar()
	 Ejecución del comando.
	 No regresa ni recibe nada.*/
	public void ejecutar(){
		boolean band=false;
		if (frame.frameMetricaVDINO.isClosed())
			band=true;
		frame.frameMetricaVDINO=FrameMetricaVDINO.instanciar(frame.archivosOriginales);
		if (band==true)
			frame.desktop.add(frame.frameMetricaVDINO);
		frame.frameMetricaVDINO.setVisible(true);
		try {
			frame.frameMetricaVDINO.setIcon(false);
			frame.frameMetricaVDINO.moveToFront();
			frame.frameMetricaVDINO.setSelected(true);
		} catch (PropertyVetoException e1) {
			// nada
		}
	}
}
