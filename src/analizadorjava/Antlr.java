package analizadorjava;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import logica.*;
import modelo.Clase;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

/**
 *
 * @author Padilla
 */
public class Antlr {

    public static void main(String[] args) {
        JavaParserBaseListener listener = new JavaParserBaseListener();
        try (Stream<Path> paths = Files.walk(Paths.get("C:\\Users\\Pablo Padilla\\Documents\\worskpace-new\\SR2 Refactoring Java\\src\\codigorefactorizado"))) 
        {
            paths
                    .filter(Files::isRegularFile)
                    .forEach((file) -> {
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

            //CoherenciaLogica cl = new CoherenciaLogica(clases);
            //VDinoLogica vdl = new VDinoLogica(clases);
            SeparacionInterfazLogica sil = new SeparacionInterfazLogica(clases);

            sil.generarCodigoRefactorizado();
            //cl.calcular();
            //vdl.calcular();
        } catch (Exception ex) {
            Logger.getLogger(Antlr.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
