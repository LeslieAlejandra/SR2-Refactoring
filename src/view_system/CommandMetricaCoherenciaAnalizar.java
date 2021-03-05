package view_system;
/* Clase: CommandMetricaDINOAnalizar
 Autor: Manuel Vald�s Marrero
 Fecha: 10 Abril 2004
 Descripci�n: Comando del Bot�n Analizar para el frame MetricaDINO. */
import javax.swing.JOptionPane;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import analizadorjava.Antlr;
import analizadorjava.JavaLexer;
import analizadorjava.JavaParser;
import analizadorjava.JavaParserBaseListener;
import logica.CoherenciaLogica;
import logica.VDinoLogica;
import modelo.Clase;
import modelo.ResultData;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;



public class CommandMetricaCoherenciaAnalizar extends CommandMetricaCoherencia {

	/* CommandMetricaDINOAnalizar(FrameMetricaDINO frame)
	 Constructor que recibe un frame de tipo MetricaDINO, al que modificar� con el comando. */
	public CommandMetricaCoherenciaAnalizar(FrameMetricaCoherencia frame){
		this.frame=frame;
	}
	/* void ejecutar()
	 Ejecuci�n del comando.
	 No regresa ni recibe nada. */
	public void ejecutar(){
		ResultData resultData = ResultData.getSingletonInstance();
		
		JavaParserBaseListener listener = new JavaParserBaseListener();
		String rutaArchivos = resultData.getRutaArchivos();
		try (Stream<Path> paths = Files.walk(Paths.get(rutaArchivos))) {
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
			
			CoherenciaLogica cl = new CoherenciaLogica(clases);
						
			cl.calcular();
			
			frame.clasesDerivadas.setText(resultData.getTotalMetodos());
			frame.valorHerencia.setText(resultData.getCoherencia());
			
//			int tamanioLista = resultData.getLista().size();
//			
//			for(int valor = 0; valor < tamanioLista ; valor ++) {
//				System.out.println("Entro");
//				frame.abstractas.addItem(resultData.getLista().get(valor).getClase().getNombre());
//			}
				

			
			
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
