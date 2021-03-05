package view_system;
/* Clase: FrameMetricaDINO
 Autor: Manuel Vald�s Marrero
 Fecha: 22 Enero 2004
 Descripci�n: frame interno para calcular la m�trica V-DINO (Vald�s - Dependencia por Interfaces que No se Ocupan)*/
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;



import java.io.*;

public class FrameMetricaCoherencia extends FrameInterno{
	private JLabel etiqueta;
	public JTextArea recomendacion;
	private JScrollPane recomendacionScroll;
	public JButton analisisButton;
	public JButton calcularButton;
	public JTextField clasesDerivadas;
	public JTextField valorHerencia;
	
	private static FrameMetricaCoherencia instancia=null;
	/* static FrameMetricaDINO instanciar(File[] archivos, BaseDatos base)
	 Funci�n para instanciar la clase, recibe la base de datos y archivos Originales. 
	 Regresa una instancia de la pantalla, usando Singleton.
	 Si no est� creada la pantalla la crea, si ya est� solo la env�a.*/	
	public static FrameMetricaCoherencia instanciar(File[] archivos){
		if (instancia==null){
			instancia=new FrameMetricaCoherencia(archivos);
		}
		if (instancia.isClosed()){
			instancia=new FrameMetricaCoherencia(archivos);
		}
		return instancia;
	}
	
	private FrameMetricaCoherencia(File[] archivos){
		this.archivosOriginales=archivos;
		
		
		
		setTitle("M�trica para calcular coherencia (Padilla - Abstracciones incorrectas en una clase)");
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
		analisisButton=new JButton("Calcular Coherencia");
		analisisButton.setBounds(202,20,220,30);
		analisisButton.addActionListener(this);
		analisisButton.setEnabled(true);
		getContentPane().add(analisisButton);
		etiqueta=new JLabel("Número de Clases Derivadas NOC :");
		etiqueta.setBounds(12,175,230,20);
		getContentPane().add(etiqueta);
		clasesDerivadas=new JTextField();
		clasesDerivadas.setBounds(232,180,60,20);
		clasesDerivadas.setEditable(false);
		getContentPane().add(clasesDerivadas);
		etiqueta=new JLabel("Profundidad de Herencia :");
		etiqueta.setBounds(12,205,230,20);
		getContentPane().add(etiqueta);
		valorHerencia = new JTextField();
		valorHerencia.setBounds(232,210,60,20);
		valorHerencia.setEditable(false);
		getContentPane().add(valorHerencia);

		setResizable(false);
		setClosable(true);
		setMaximizable(true);
		setIconifiable(true);
		setLocation(120,120);
		setVisible(false);
	}
	
	
	/* void actionPerformed(ActionEvent e)
	 responde a los eventos 'e' de los botones y las listas desplegables.  
	 No regresa nada.*/
	public void actionPerformed(ActionEvent e) {
//		if (e.getSource().equals(abstractas)==true){
//			//comando=new CommandMetricaDINOClase(this);
//			comando.ejecutar();
//		}
		if (e.getSource().equals(analisisButton)==true){
			// Llamar al Analisis Sintáctico
			comando=new CommandMetricaCoherenciaAnalizar(this);
			comando.ejecutar();			
		}
	}	
}
