package view_system;
/* Clase: FrameMetricaDINO
 Autor: Manuel Valdes Marrero
 Fecha: 22 Enero 2004
 Descripción: frame interno para calcular la métrica V-DINO (Valdés - Dependencia por Interfaces que No se Ocupan)*/
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

import modelo.ResultData;

import java.io.*;

public class FrameMetricaVDINO extends FrameInterno{
	private JLabel etiqueta;
	public JTextArea recomendacion;
	private JScrollPane recomendacionScroll;
	public JButton analisisButton;
	public JTextField valorCNOC;
	public JTextField valorNFV;
	public JTextField valorNFNO;
	public JTextField valorVDINO;
	public JComboBox abstractas;
	
	private static FrameMetricaVDINO instancia=null;
	private int valoractual;
	/* static FrameMetricaDINO instanciar(File[] archivos, BaseDatos base)
	 Función para instanciar la clase, recibe la base de datos y archivos Originales. 
	 Regresa una instancia de la pantalla, usando Singleton.
	 Si no está creada la pantalla la crea, si ya está solo la envía.*/	
	public static FrameMetricaVDINO instanciar(File[] archivos){
		if (instancia==null){
			instancia=new FrameMetricaVDINO(archivos);
		}
		if (instancia.isClosed()){
			instancia=new FrameMetricaVDINO(archivos);
		}
		return instancia;
	}
	
	private FrameMetricaVDINO(File[] archivos){
		this.archivosOriginales=archivos;
		
		setTitle("Métrica V-DINO (Padilla - Dependencia por Interfaces que No se Ocupan)");
		setSize(594, 367);
		getContentPane().setLayout(null);

		etiqueta=new JLabel("Recomendación: ");
		etiqueta.setBounds(302,150,280,20);
		getContentPane().add(etiqueta);
		recomendacion = new JTextArea();
		recomendacion.setEditable(false);
		recomendacion.setFont(new Font("courier",Font.PLAIN,12));
		recomendacionScroll=new JScrollPane(recomendacion);
		recomendacionScroll.setBounds(302,175,260,120);
		getContentPane().add(recomendacionScroll);
		analisisButton=new JButton("Realizar Análisis");
		analisisButton.setBounds(232,20,120,30);
		analisisButton.addActionListener(this);
		analisisButton.setEnabled(true);
		getContentPane().add(analisisButton);
		etiqueta=new JLabel("Número de Clases Derivadas C-NOC :");
		etiqueta.setBounds(12,175,230,20);
		getContentPane().add(etiqueta);
		valorCNOC=new JTextField();
		valorCNOC.setBounds(232,180,60,20);
		valorCNOC.setEditable(false);
		getContentPane().add(valorCNOC);
		etiqueta=new JLabel("Número de Funciones Virtuales Puras NFV :");
		etiqueta.setBounds(12,205,230,20);
		getContentPane().add(etiqueta);
		valorNFV = new JTextField();
		valorNFV.setBounds(232,210,60,20);
		valorNFV.setEditable(false);
		getContentPane().add(valorNFV);
		etiqueta=new JLabel("Número de Funciones No Ocupadas NFNO :");
		etiqueta.setBounds(12,235,230,20);
		getContentPane().add(etiqueta);
		valorNFNO=new JTextField();
		valorNFNO.setBounds(232,240,60,20);
		valorNFNO.setEditable(false);
		getContentPane().add(valorNFNO);
		etiqueta=new JLabel("Valor de la M�trica V-DINO :");
		etiqueta.setBounds(12,265,230,20);
		getContentPane().add(etiqueta);
		valorVDINO = new JTextField();
		valorVDINO.setBounds(202,270,90,20);
		valorVDINO.setEditable(false);
		getContentPane().add(valorVDINO);
		etiqueta=new JLabel("Clases Abstractas :");
		etiqueta.setBounds(172,95,130,30);
		getContentPane().add(etiqueta);
		abstractas=new JComboBox();
		abstractas.setBounds(302,100,150,20);
		abstractas.addActionListener(this);
		abstractas.setEditable(false);
		abstractas.setEnabled(true);
		getContentPane().add(abstractas);

		setResizable(false);
		setClosable(true);
		setMaximizable(true);
		setIconifiable(true);
		setLocation(0,0);
		setVisible(false);
	}
		
	
	/* void presentarRecomendacion(float valor, JTextArea area)
	 Recibe un n�mero flotante y un cuadro de Area de texto, para colocar la recomendaci�n.
	 No regresa nada. */	
	
	/* void actionPerformed(ActionEvent e)
	 responde a los eventos 'e' de los botones y las listas desplegables.  
	 No regresa nada.*/
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource().equals(analisisButton)==true){
			// Llamar al Analisis Sint�ctico
			comando=new CommandMetricaVDINOAnalizar(this);
			comando.ejecutar();			
			//desplegarClasesAbstractas();
		}
	 JComboBox cb = (JComboBox)e.getSource();
	 valoractual = cb.getSelectedIndex();
	
	 //Asignamos valor a las cajas de texto
	 ResultData resultData = ResultData.getSingletonInstance();
	 
	 valorCNOC.setText(Integer.toString(resultData.getLista().get(valoractual).getCnoc()));
	 valorNFV.setText(Integer.toString(resultData.getLista().get(valoractual).getNfv()));
	 valorNFNO.setText(Integer.toString(resultData.getLista().get(valoractual).getNfno()));
	 String newValResult = String.format("%.5f", resultData.getLista().get(valoractual).getResultado());
	 valorVDINO.setText(newValResult);
	 
	 StringBuffer texto=new StringBuffer("");
	 
	 if (resultData.getLista().get(valoractual).getResultado()==0) {
			texto.append("La Clase " + resultData.getLista().get(valoractual).getClase().getNombre() + " no tiene el problema de Dependencia de Interfaces.");
			texto.append("\n\n");
			texto.append("No tiene sentido ejecutar el método de Refactorización por Separación de Interfaces");
			texto.append("\nsobre esta clase, no se producir ningún cambio en la arquitectura de clases.");
			texto.append("\n");
		}
		else {
			if (resultData.getLista().get(valoractual).getResultado()<0.5){
				texto.append("La Clase " + resultData.getLista().get(valoractual).getClase().getNombre() + " tiene el problema de Dependencia de Interfaces.");
				texto.append("\n\n");
				texto.append("El problema que tiene esta clase no es grave.");
				texto.append("\nEs altamente probable que las interfaces no sean mutuamente excluyentes.");
				texto.append("\nNo se recomienda ejecutar el método de Refactorización por Separación de Interfaces");
				texto.append("\nsobre esta clase, es poco probable que se produzca algún cambio en la arquitectura de clases.");
				texto.append("\n");
			}
			else{
				texto.append("La Clase " + resultData.getLista().get(valoractual).getClase().getNombre() + " tiene el problema de Dependencia de Interfaces.");
				texto.append("\n\n");
				texto.append("El problema que tiene esta clase es grave.");
				texto.append("\nEs altamente probable que las interfaces sean mutuamente excluyentes.");
				texto.append("\nSe recomienda ejecutar el método de Refactorización por Separación de Interfaces");
				texto.append("\nsobre esta clase, es muy probable que se produzcan mejoras sobre la arquitectura de clases.");
				texto.append("\n");
			}
		}
	 recomendacion.setText(texto.toString());
	 
	 
	 
	}	
}
