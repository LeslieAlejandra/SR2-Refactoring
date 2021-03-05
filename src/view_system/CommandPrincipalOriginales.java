package view_system;
/* Clase: CommandPrincipalOriginales
 Autor: Manuel Vald�s Marrero
 Fecha: 19 Enero 2004
 Descripci�n: Menu Seleccionar Archivos Originales para el frame principal */
import javax.swing.*;

import modelo.ResultData;

import java.io.*;

public class CommandPrincipalOriginales extends CommandPrincipal {
	private principal_view frame;
	/* CommandPrincipalOriginales(Principal frame)
	 Constructor que recibe el frame principal, al que modificar� con el comando. */
	public CommandPrincipalOriginales(principal_view frame){
		this.frame=frame;
	}
	/* void ejecutar()
	 Ejecución del comando.
	 No regresa ni recibe nada.*/
	public void ejecutar(){
		//String[] extension={"h","hpp","c","cpp"};
		String[] extension={"java"};
		File[] temp;
		File[] temp1=new File[1];
		if (frame.archivosOriginales != null) 
                {
			if (frame.archivosOriginales.length>=1)
                        {
                            temp1[0]=frame.archivosOriginales[0];
			}
			else
                        {
                            temp1=null;
			}
		}
		else
                {
			temp1=null;
		}
		temp=UtileriasArchivo.abrirArchivos("Seleccionar Archivos Originales",extension,"Archivos Java",temp1);
		boolean validos=true;
		if (temp!=null){
			for (int i=0;i<temp.length;i++){
				if (temp[i].isFile()==false){
					validos=false;
					break;
				}
			}
		}if (validos==true && temp!=null){
			frame.archivosOriginales=temp;
			frame.archivosFuente=null;
			if (JOptionPane.showConfirmDialog(null,"Desea crear copias de seguridad de sus archivos fuente?","Copia de Seguridad",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_OPTION){
				copiaSeguridad();
			}
			frame.actualizar();
			//Borrar Bases de Datos
		}
	
		else{
			JOptionPane.showMessageDialog(null,"No seleccion� archivos v�lidos.\nSiguen activos los archivos anteriores","Error de Apertura",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/* void copiaSeguridad()
	 Crea un directorio llamado 'Fuente' a partir del directorio donde est�n los archivos seleccionados. Hace una copia en este directorio de los archivos abstractas. 
	 No regresa ni recibe nada.*/
	private void copiaSeguridad(){
		File directorio = new File(frame.archivosOriginales[0].getParent()+File.separator+"Fuente");
		directorio.mkdir();
		frame.archivosFuente=new File[frame.archivosOriginales.length];
		for (int i=0;i<frame.archivosOriginales.length;i++){
			String nombre1 = frame.archivosOriginales[i].getAbsolutePath();
			String nombre2 = frame.archivosOriginales[i].getParent()+File.separator+"Fuente"+File.separator+frame.archivosOriginales[i].getName();
			frame.archivosFuente[i]=UtileriasArchivo.copiarArchivo(nombre1,nombre2);
			if (frame.archivosFuente[i]==null){
				JOptionPane.showMessageDialog(null,"No se Pudieron Realizar las Copias de Seguridad en el Directorio\n'"+directorio.getAbsolutePath()+"'","Copia de Seguridad",JOptionPane.ERROR_MESSAGE);
				frame.archivosFuente=null;
				return;
			}
		}
		ResultData resultData = ResultData.getSingletonInstance();
		resultData.setRutaArchivos(directorio.getAbsolutePath());
		JOptionPane.showMessageDialog(null,"Copias de Seguridad Realizadas con Exito en el Directorio\n'"+directorio.getAbsolutePath()+"'","Copia de Seguridad",JOptionPane.INFORMATION_MESSAGE);
	}
	
}
