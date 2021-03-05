package view_system;
/* Clase: CommandComparacionFuentes
 Autor: Manuel Valdes Marrero
 Fecha: 21 Julio 2003
 Descripcion: Comando de la Lista Fuentes para el frame Comparacion */
public class CommandComparacionFuentes extends CommandComparacion {
	/* CommandComparacionFuentes(FrameComparacion frame)
	 Constructor que recibe un frame de tipo comparacion, al que modificar con el comando. */
	public CommandComparacionFuentes(FrameComparacion frame){
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
		nombre=String.valueOf(frame.fuentes.getSelectedItem());
		frame.ruta1=frame.archivosFuente[frame.fuentes.getSelectedIndex()].getAbsolutePath();
		frame.titulo1.setText("Archivo Original "+nombre);
		frame.lineasNuevas.setText("");
		frame.lineasEliminadas.setText("");
		frame.lineasReuso.setText("");
		frame.lineasBase.setText("");
		frame.lineasArchivo1=UtileriasContador.mostrarLineas(frame.archivosFuente[frame.fuentes.getSelectedIndex()]);
		frame.presentarArea(frame.lineasArchivo1,frame.contenido1);
	}
}
