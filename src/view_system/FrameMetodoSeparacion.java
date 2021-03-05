package view_system;
/* Clase: FrameMetodoSeparacion
 Autor: Manuel Vald�s Marrero
 Fecha: 3 Febrero 2004
 Descripci�n: frame interno para realizar el m�todo de Separaci�n de Interfaces*/
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import analizadorjava.Antlr;
import analizadorjava.JavaLexer;
import analizadorjava.JavaParser;
import analizadorjava.JavaParserBaseListener;
import logica.VDinoLogica;
import modelo.Clase;
import modelo.ResultData;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class FrameMetodoSeparacion extends FrameInterno{
	private JLabel etiqueta;
	public JTextArea bitacora;
	private JScrollPane bitacoraScroll;
	public JButton analisisButton;
	public JButton metodoButton;
	public JComboBox abstractas;
	public String[] lineasArchivo;
	public String ruta;
	
	private static FrameMetodoSeparacion instancia=null;
	private int tamanioLista;
	/* static FrameMetodoSeparacion instanciar(File[] archivos, BaseDatos base)
	 Funci�n para instanciar la clase, recibe los archivos originales en archivos. Tambi�n recibe la base de datos.
	 Regresa una instancia de la pantalla, usando Singleton.
	 Si no est� creada la pantalla la crea, si ya est� solo la env�a.*/	
	public static FrameMetodoSeparacion instanciar(File[] archivos){
		if (instancia==null){
			instancia=new FrameMetodoSeparacion(archivos);
		}
		if (instancia.isClosed()){
			instancia=new FrameMetodoSeparacion(archivos);
		}
		return instancia;
	}
	
	private FrameMetodoSeparacion(File[] archivos){
		archivosOriginales=archivos;
		
		setTitle("M�todo de Separaci�n de Interfaces");
		setSize(594, 367);
		getContentPane().setLayout(null);

//		etiqueta=new JLabel("Bit�cora: ");
//		etiqueta.setBounds(22,150,280,20);
//		getContentPane().add(etiqueta);
//		bitacora = new JTextArea();
//		bitacora.setEditable(false);
//		bitacora.setFont(new Font("courier",Font.PLAIN,12));
//		bitacoraScroll=new JScrollPane(bitacora);
//		bitacoraScroll.setBounds(22,175,520,120);
//		getContentPane().add(bitacoraScroll);
		analisisButton=new JButton("Analizar C�digo");
		analisisButton.setBounds(232,20,120,30);
		analisisButton.addActionListener(this);
		analisisButton.setEnabled(true);
		getContentPane().add(analisisButton);
		etiqueta=new JLabel("Clases Abstractas :");
		etiqueta.setBounds(172,95,130,30);
		getContentPane().add(etiqueta);
		abstractas=new JComboBox();
		abstractas.setBounds(302,100,150,20);
		abstractas.addActionListener(this);
		abstractas.setEditable(false);
		abstractas.setEnabled(true);
		getContentPane().add(abstractas);
//		metodoButton=new JButton("Ejecutar M�todo");
//		metodoButton.setBounds(462,95,120,30);
//		metodoButton.addActionListener(this);
//		metodoButton.setEnabled(false);
//		getContentPane().add(metodoButton);

		setResizable(false);
		setClosable(true);
		setMaximizable(true);
		setIconifiable(true);
		setLocation(0,0);
		setVisible(false);
	}


	
	/* void actionPerformed(ActionEvent e)
	 responde a los eventos 'e' de los botones y las listas desplegables.  
	 No regresa nada.*/
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(metodoButton)==true){
			comando=new CommandMetodoSeparacionAnalizar(this);
			comando.ejecutar();
			
		}
		if (e.getSource().equals(analisisButton)==true){
			// Llamar al An�lisis Sint�ctico
			comando=new CommandMetodoSeparacionAnalizar(this);
			comando.ejecutar();
			
			ResultData resultData = ResultData.getSingletonInstance();
			
			JavaParserBaseListener listener = new JavaParserBaseListener();
			String rutaArchivos = resultData.getRutaArchivos();
//                        try (Stream<Path> paths = Files.walk(Paths.get("C:\\Users\\Pablo Padilla\\Documents\\worskpace-new\\SR2 Refactoring Java\\src\\codigorefactorizado"))) {
			try (Stream<Path> paths = Files.walk(Paths.get("C:\\Users\\Orlando\\Documents\\Maestría\\Tesis\\SR2-Pablo\\SR2\\SR2 Refactoring Java\\src\\codigorefactorizado"))) {
				paths.filter(Files::isRegularFile).forEach((file) -> {
					if (file.getFileName().toString().contains(".java")) {
						CharStream charStream;
						try {
							charStream = CharStreams.fromFileName(file.toString());
							JavaLexer lexer = new JavaLexer(charStream);
							CommonTokenStream commonTokenStream = new CommonTokenStream(lexer);
							JavaParser parser = new JavaParser(commonTokenStream);
							ParseTreeWalker walker = new ParseTreeWalker();
							RuleContext rc = parser.compilationUnit();
							walker.walk(listener, rc);
						} catch (IOException ex) {
							Logger.getLogger(Antlr.class.getName()).log(Level.SEVERE, null, ex);
						}
					}
				});

				ArrayList<Clase> clases = listener.finalizaLectura();
				
				VDinoLogica vdl = new VDinoLogica(clases);
							
				vdl.calcular();
				
				tamanioLista = resultData.getLista().size();
				
				for(int valor = 0; valor < tamanioLista ; valor ++) {
					abstractas.addItem(resultData.getLista().get(valor).getClase().getNombre());
				}
				
				
			

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}	
}
}