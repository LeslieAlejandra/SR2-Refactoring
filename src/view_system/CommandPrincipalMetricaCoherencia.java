package view_system;
/* Clase: CommandPrincipalMetricaDINO
 Autor: Manuel Valdés Marrero
 Fecha: 22 Enero 2004
 Descripción: Menu Calcular Métrica V-DINO para el frame principal */
import java.beans.PropertyVetoException;

public class CommandPrincipalMetricaCoherencia extends CommandPrincipal {
	/* CommandPrincipalMetricaDINO(Principal frame)
	 Constructor que recibe el frame principal, al que modificar con el comando. */
	public CommandPrincipalMetricaCoherencia(principal_view frame){
		this.frame=frame;
	}
	/* void ejecutar()
	 Ejecución del comando.
	 No regresa ni recibe nada.*/
	public void ejecutar(){
		boolean band=false;
		if (frame.frameMetricaCoherencia.isClosed())
			band=true;
		frame.frameMetricaCoherencia=FrameMetricaCoherencia.instanciar(frame.archivosOriginales);
		if (band==true)
			frame.desktop.add(frame.frameMetricaCoherencia);
		frame.frameMetricaCoherencia.setVisible(true);
		try {
			frame.frameMetricaCoherencia.setIcon(false);
			frame.frameMetricaCoherencia.moveToFront();
			frame.frameMetricaCoherencia.setSelected(true);
		} catch (PropertyVetoException e1) {
			// nada
		}
	}
}
