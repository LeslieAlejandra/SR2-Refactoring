package view_system;
/* Clase: CommandComparacionOriginales
 Autor: Manuel Valdes Marrero
 Fecha: 21 Julio 2003
 Descripcion: Comando de la Lista Originales para el frame Comparacion */
public class CommandComparacionOriginales extends CommandComparacion {
	/* CommandComparacionOriginales(FrameComparacion frame)
	 Constructor que recibe un frame de tipo comparacion, al que modificar con el comando. */
	public CommandComparacionOriginales(FrameComparacion frame){
		this.frame=frame;
	}
	/* void ejecutar()
	 Ejecucion del comando.
	 No regresa ni recibe nada.*/
	public void ejecutar(){
		String nombre;
		if (frame.fuentes.isEnabled() && frame.originales.isEnabled()){
			frame.compararButton.setEnabled(true);
		}
		nombre=String.valueOf(frame.originales.getSelectedItem());
		frame.ruta2=frame.archivosOriginales[frame.originales.getSelectedIndex()].getAbsolutePath();
		frame.titulo2.setText("Archivo Refactorizado "+nombre);
		frame.lineasNuevas.setText("");
		frame.lineasEliminadas.setText("");
		frame.lineasReuso.setText("");
		frame.lineasBase.setText("");
		frame.lineasArchivo2=UtileriasContador.mostrarLineas(frame.archivosOriginales[frame.originales.getSelectedIndex()]);
		frame.presentarArea(frame.lineasArchivo2,frame.contenido2);
	}
}
