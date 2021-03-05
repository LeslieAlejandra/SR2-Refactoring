package view_system;
/* Clase: FrameComparacion
 Autor: Manuel Vald�s Marrero
 Fecha: 22 Enero 2004
 Descripci�n: frame interno para comparar las lineas de un archivo fuente sin modificar y uno original modificado*/
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.io.*;

public class FrameComparacion extends FrameInterno{
	public JLabel titulo1;
	public JLabel titulo2;
	private JLabel etiqueta;
	public JTextArea contenido1;
	private JScrollPane contenido1Scroll;
	public JTextArea contenido2;
	private JScrollPane contenido2Scroll;
	public JButton compararButton;
	public JTextField lineasBase;
	public JTextField lineasReuso;
	public JTextField lineasNuevas;
	public JTextField lineasEliminadas;
	public JComboBox fuentes;
	public JComboBox originales;
	public String[] lineasArchivo1;
	public String[] lineasArchivo2;
	public String ruta1;
	public String ruta2;
	
	private static FrameComparacion instancia=null;
	/* static FrameComparacion instanciar(File[] archivos1, File[] archivos2)
	 Función para instanciar la clase, recibe los archivos abstractas en archivos1, y los originales en archivos2. 
	 Regresa una instancia de la pantalla, usando Singleton.
	 Si no está creada la pantalla la crea, si ya está solo la envía.*/	
	public static FrameComparacion instanciar(File[] archivos1, File[] archivos2){
		
		if (instancia==null){
			instancia=new FrameComparacion(archivos1,archivos2);
		}
		if (instancia.isClosed()){
			instancia=new FrameComparacion(archivos1,archivos2);
		}
		return instancia;
	}
	
	private FrameComparacion(File[] archivos1, File[] archivos2){
		archivosFuente=archivos1;
		archivosOriginales=archivos2;
		
		setTitle("Comparacion de Archivos");
		setSize(794, 587);
		getContentPane().setLayout(null);

		titulo1=new JLabel("Archivo Fuente");
		titulo1.setBounds(12,0,280,20);
		getContentPane().add(titulo1);
		contenido1 = new JTextArea();
		contenido1.setEditable(false);
		contenido1.setFont(new Font("courier",Font.PLAIN,12));
		contenido1Scroll=new JScrollPane(contenido1);
		contenido1Scroll.setBounds(12,25,380,370);
		getContentPane().add(contenido1Scroll);
		titulo2=new JLabel("Archivo Refactorizado");
		titulo2.setBounds(402,0,280,20);
		getContentPane().add(titulo2);
		contenido2 = new JTextArea();
		contenido2.setEditable(false);
		contenido2.setFont(new Font("courier",Font.PLAIN,12));
		contenido2Scroll=new JScrollPane(contenido2);
		contenido2Scroll.setBounds(402,25,380,370);
		getContentPane().add(contenido2Scroll);
		compararButton=new JButton("Comparar Líneas");
		compararButton.setBounds(22,400,120,30);
		compararButton.addActionListener(this);
		compararButton.setEnabled(false);
		getContentPane().add(compararButton);		
		etiqueta=new JLabel("Fuentes :");
		etiqueta.setBounds(22,435,130,30);
		getContentPane().add(etiqueta);
		fuentes=new JComboBox();
		fuentes.setBounds(130,440,250,20);
		fuentes.addActionListener(this);
		fuentes.setEditable(false);
		fuentes.setEnabled(false);
		getContentPane().add(fuentes);
		etiqueta=new JLabel("Refactorizados :");
		etiqueta.setBounds(22,465,130,20);
		getContentPane().add(etiqueta);
		originales = new JComboBox();
		originales.setEditable(false);
		originales.setEnabled(false);
		originales.addActionListener(this);
		originales.setBounds(130,470,250,20);
		getContentPane().add(originales);
		etiqueta=new JLabel("L�neas Base :");
		etiqueta.setBounds(22,495,130,20);
		getContentPane().add(etiqueta);
		lineasBase=new JTextField();
		lineasBase.setBounds(130,500,150,20);
		lineasBase.setEditable(false);
		getContentPane().add(lineasBase);
		etiqueta=new JLabel("L�neas Reuso :");
		etiqueta.setBounds(302,495,130,30);
		getContentPane().add(etiqueta);
		lineasReuso = new JTextField();
		lineasReuso.setBounds(400,500,150,20);
		lineasReuso.setEditable(false);
		getContentPane().add(lineasReuso);
		etiqueta=new JLabel("L�neas Nuevas :");
		etiqueta.setBounds(22,525,130,20);
		getContentPane().add(etiqueta);
		lineasNuevas=new JTextField();
		lineasNuevas.setBounds(130,530,150,20);
		lineasNuevas.setEditable(false);
		getContentPane().add(lineasNuevas);
		etiqueta=new JLabel("L�neas Eliminadas :");
		etiqueta.setBounds(302,525,130,30);
		getContentPane().add(etiqueta);
		lineasEliminadas = new JTextField();
		lineasEliminadas.setBounds(400,530,150,20);
		lineasEliminadas.setEditable(false);
		getContentPane().add(lineasEliminadas);

		setResizable(false);
		setClosable(true);
		setMaximizable(true);
		setIconifiable(true);
		setLocation(0,0);
		setVisible(false);
		desplegarListasCuadro();
	}

	private void desplegarListasCuadro(){
		if (archivosFuente!=null){
			fuentes.setEnabled(true);
			for (int i=0;i<archivosFuente.length;i++){
				fuentes.addItem(archivosFuente[i].getName());
			}
		}
		else
			fuentes.setEnabled(false);
		if (archivosOriginales!=null){
			originales.setEnabled(true);
			for (int i=0;i<archivosOriginales.length;i++){
				originales.addItem(archivosOriginales[i].getName());
			}
		}
		else
			originales.setEnabled(false);
	}
	/* void presentarArea(String[] lineas, JTextArea area)
	 Recibe un arreglo de cadenas y un cuadro de Area de texto, para insertar las cadenas en el componente.
	 No regresa nada. */	
	public void presentarArea(String[] lineas, JTextArea area){
		StringBuffer texto=new StringBuffer("");
		if (lineas!=null){
			for (int i=0;i<lineas.length;i++){
				texto.append(lineas[i]);
				texto.append("\n");
			}
		}
		area.setText(texto.toString());
	}
	/* void actionPerformed(ActionEvent e)
	 responde a los eventos 'e' de los botones y las listas desplegables.  
	 No regresa nada.*/
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(fuentes)==true){
			comando=new CommandComparacionFuentes(this);
			comando.ejecutar();
		}
		if (e.getSource().equals(originales)==true){
			comando=new CommandComparacionOriginales(this);
			comando.ejecutar();
		}
		if (e.getSource().equals(compararButton)==true){
			comando=new CommandComparacionComparar(this);
			comando.ejecutar();
		}
	}	
}
