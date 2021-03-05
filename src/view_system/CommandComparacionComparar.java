package view_system;
/* Clase: CommandComparacionComparar
 Autor: Manuel Valdes Marrero
 Fecha: 21 Julio 2003
 Descripcion: Comando Comparar Archivos para el frame Comparacion */
public class CommandComparacionComparar extends CommandComparacion {
	/* CommandComparacionComparar(FrameComparacion frame)
	 Constructor que recibe un frame de tipo comparacion, al que modificar� con el comando. */
	public CommandComparacionComparar(FrameComparacion frame){
		this.frame=frame;
	}
	/* void ejecutar()
	 Ejecuci�n del comando.
	 No regresa ni recibe nada.*/
	public void ejecutar(){
		int valorLOC1=UtileriasContador.contarLineas(frame.lineasArchivo1);
		int valorLOC2=UtileriasContador.contarLineas(frame.lineasArchivo2);
		int valorNuevas=0;
		valorNuevas=UtileriasContador.contarLineasNuevas(frame.lineasArchivo1,frame.lineasArchivo2);
		int valorEliminadas=0;
		valorEliminadas=UtileriasContador.contarLineasEliminadas(frame.lineasArchivo1,frame.lineasArchivo2);
		int valorReuso=0;
		valorReuso=UtileriasContador.contarLineasReuso(frame.lineasArchivo1,frame.lineasArchivo2);
		int valorBase=0;
		if (valorNuevas<=valorEliminadas){
			valorBase=valorLOC2-valorNuevas-valorReuso;
			valorEliminadas=valorLOC1-valorReuso-valorBase;
		}
		else{
			valorBase=valorLOC1-valorEliminadas-valorReuso;
			valorNuevas=valorLOC2-valorReuso-valorBase;
		}
		frame.lineasNuevas.setText(String.valueOf(valorNuevas));
		frame.lineasEliminadas.setText(String.valueOf(valorEliminadas));
		frame.lineasReuso.setText(String.valueOf(valorReuso));
		frame.lineasBase.setText(String.valueOf(valorBase));
	}
}
