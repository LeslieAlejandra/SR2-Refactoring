package view_system;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import analizadorjava.Antlr;
import analizadorjava.JavaLexer;
import analizadorjava.JavaParser;
import analizadorjava.JavaParserBaseListener;
import logica.SeparacionInterfazLogica;
import modelo.Clase;
import modelo.ResultData;

/* Clase: CommandMetodoSeparacionAnalizar
 Autor: Manuel Valdes Marrero
 Fecha: 11 Junio 2004
 Descripcion: Comando del Boton Analizar para el frame MetodoSeparacion */
public class CommandMetodoSeparacionAnalizar extends CommandMetodoSeparacion {
	/* CommandMetodoSeparacionAnalizar(FrameMetodoSeparacion frame)
	 Constructor que recibe un frame de tipo MetodoSeparacion, al que modificar con el comando. */
	public CommandMetodoSeparacionAnalizar(FrameMetodoSeparacion frame){
		this.frame=frame;
	}
	/* void ejecutar()
	 Ejecucion del comando.
	 No regresa ni recibe nada.*/
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
			
			SeparacionInterfazLogica sil = new SeparacionInterfazLogica(clases);
			
			sil.generarCodigoRefactorizado();
        } catch (Exception ex) {
            Logger.getLogger(Antlr.class.getName()).log(Level.SEVERE, null, ex);
        }
		
	}
}
