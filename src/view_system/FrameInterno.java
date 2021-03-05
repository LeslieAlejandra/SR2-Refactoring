package view_system;
/* Clase: FrameInterno
 Autor: Manuel Valdes Marrero
 Fecha: 19 Julio 2003
 Descripcion: Abstracta para manejar los eventos y funciones de los frames internos*/
import java.awt.event.ActionListener;
import javax.swing.JInternalFrame;
import java.io.*;

public abstract class FrameInterno extends JInternalFrame implements ActionListener {
	public principal_view framePrincipal;
	public Command comando;
	public File[] archivosFuente;
	public File[] archivosOriginales;
}
