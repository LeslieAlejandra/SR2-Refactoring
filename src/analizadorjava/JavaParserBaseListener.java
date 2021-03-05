package analizadorjava;

// Generated from JavaParser.g4 by ANTLR 4.7.1
import modelo.Metodo;
import modelo.Instancia;
import modelo.Variable;
import modelo.ClaseTemporal;
import modelo.Parametro;
import modelo.Clase;
import java.util.ArrayList;
import java.util.List;
import analizadorjava.JavaParser.ClassBodyDeclarationContext;
import analizadorjava.JavaParser.ClassOrInterfaceModifierContext;
import analizadorjava.JavaParser.InterfaceBodyDeclarationContext;
import analizadorjava.JavaParser.TypeDeclarationContext;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.misc.Interval;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

/**
 * This class provides an empty implementation of {@link JavaParserListener},
 * which can be extended to create a listener which only needs to handle a
 * subset of the available methods.
 */
public class JavaParserBaseListener implements JavaParserListener {

    private String auxModificador;
    private ArrayList<Clase> clases;
    private ArrayList<ClaseTemporal> clasesEHijos;
    private boolean clase;
    private boolean constructor;
    private boolean metodo;
    private String paqueteAux;

    private ArrayList<String> importsTemporal;

    public JavaParserBaseListener() {
        this.auxModificador = "";
        this.clase = false;
        this.constructor = false;
        this.metodo = false;
        this.clases = new ArrayList<>();
        this.clasesEHijos = new ArrayList<>();
        this.paqueteAux = "";
        this.importsTemporal = new ArrayList<>();
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    public ArrayList<Clase> finalizaLectura() {

        for (Clase clase1 : clases) {
            for (Variable variable : clase1.getVariables()) {
                for (Clase clase : clases) {
                    if (variable.getTipo().equals(clase.getNombre())) {
                        clase1.addInstancia(new Instancia(clase, variable.getId()));
                    }
                }
            }
            ArrayList<String> llamadasClase = new ArrayList<>();
            for (String llamada : clase1.getLlamadas()) {//ELIMINA METODOS QUE NO SON CREADOS POR USUARIO EN VARIABLES DE CLASE

                boolean esMiMetodo = false;
                for (Clase clase : clases) {
                    String[] llamadaItems = llamada.split("\\.");
                    String item;
                    if (llamadaItems.length > 0) {
                        item = llamadaItems[llamadaItems.length - 1];
                    } else {
                        item = llamada;
                    }
                    int index = item.indexOf("(");
                    if (index != -1) {
                        item = item.substring(0, index);
                        for (Metodo metodo2 : clase.getMetodos()) {
                            if (item.equals(metodo2.getNombre())) {
                                esMiMetodo = true;
                                break;
                            }
                        }
                    }

                    if (esMiMetodo) {
                        llamadasClase.add(llamada);
                        break;
                    }
                }
            }
            clase1.setLlamadas(llamadasClase);
            for (Metodo metodo : clase1.getMetodos()) {
                for (Variable variable : metodo.getVariables()) {
                    for (Clase clase : clases) {
                        if (variable.getTipo().equals(clase.getNombre())) {
                            metodo.addInstancia(new Instancia(clase, variable.getId()));
                        }
                    }
                }
                ArrayList<String> llamadas = new ArrayList<>();
                for (String llamada : metodo.getLlamadas()) {//ELIMINA METODOS QUE NO SON CREADOS POR USUARIO

                    boolean esMiMetodo = false;
                    for (Clase clase : clases) {
                        String[] llamadaItems = llamada.split("\\.");
                        String item;
                        if (llamadaItems.length > 0) {
                            item = llamadaItems[llamadaItems.length - 1];
                        } else {
                            item = llamada;
                        }
                        int index = item.indexOf("(");
                        if (index != -1) {
                            item = item.substring(0, index);
                            for (Metodo metodo2 : clase.getMetodos()) {

                                if (item.equals(metodo2.getNombre())) {
                                    esMiMetodo = true;
                                    break;
                                }
                            }
                        }

                        if (esMiMetodo) {
                            llamadas.add(llamada);
                            break;
                        }
                    }
                }
                metodo.setLlamadas(llamadas);
            }

        }
        for (Clase clase : clases) {//Pone hijos a todas las clases
            for (ClaseTemporal ct : clasesEHijos) {
                if (clase.getNombre().equals(ct.getNombre())) {
                    clase.setHijos(ct.getHijos());// NO CORROBORA PAQUETES
                    break;
                }
            }
        }

        return clases;
    }

    private void addHijo(String clase, String hijo) {
        boolean found = false;
        for (ClaseTemporal ct : clasesEHijos) {
            if (ct.getNombre().equals(clase)) {
                ct.addHijo(hijo);
                found = true;
            }
        }
        if (!found) {
            ClaseTemporal ct = new ClaseTemporal(clase);
            ct.addHijo(hijo);
            clasesEHijos.add(ct);
        }
    }

    @Override
    public void enterCompilationUnit(JavaParser.CompilationUnitContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void exitCompilationUnit(JavaParser.CompilationUnitContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void enterPackageDeclaration(JavaParser.PackageDeclarationContext ctx) {
        if (ctx.start == null || ctx.stop == null || ctx.start.getStartIndex() < 0 || ctx.stop.getStopIndex() < 0) {
            paqueteAux = ctx.getText(); // Fallback
        } else {
            paqueteAux = ctx.start.getInputStream().getText(Interval.of(ctx.start.getStartIndex(), ctx.stop.getStopIndex()));
        }
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void exitPackageDeclaration(JavaParser.PackageDeclarationContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void enterImportDeclaration(JavaParser.ImportDeclarationContext ctx) {
        if (ctx.start == null || ctx.stop == null || ctx.start.getStartIndex() < 0 || ctx.stop.getStopIndex() < 0) {
            importsTemporal.add(ctx.getText()); // Fallback
        } else {
            importsTemporal.add(ctx.start.getInputStream().getText(Interval.of(ctx.start.getStartIndex(), ctx.stop.getStopIndex())));
        }
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void exitImportDeclaration(JavaParser.ImportDeclarationContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void enterTypeDeclaration(JavaParser.TypeDeclarationContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void exitTypeDeclaration(JavaParser.TypeDeclarationContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void enterModifier(JavaParser.ModifierContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void exitModifier(JavaParser.ModifierContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void enterClassOrInterfaceModifier(JavaParser.ClassOrInterfaceModifierContext ctx) {
        auxModificador = ctx.getText();
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void exitClassOrInterfaceModifier(JavaParser.ClassOrInterfaceModifierContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void enterVariableModifier(JavaParser.VariableModifierContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void exitVariableModifier(JavaParser.VariableModifierContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void enterClassDeclaration(JavaParser.ClassDeclarationContext ctx) {
        Clase cl = new Clase(ctx.IDENTIFIER().getText());
        cl.setImports(importsTemporal);
        importsTemporal = new ArrayList<>();

        TypeDeclarationContext tdc = (TypeDeclarationContext) ctx.parent;
        List<ClassOrInterfaceModifierContext> listaAux = tdc.classOrInterfaceModifier();
        String modificadores = "";
        for (ClassOrInterfaceModifierContext item : listaAux) {
            if (!modificadores.equals("")) {
                modificadores += " ";
            }
            modificadores += item.getText();
        }
        cl.setModificador(modificadores);
        try {
            JavaParser.ClassBodyDeclarationContext jmc = ((JavaParser.ClassBodyDeclarationContext) ctx.getParent().getParent());

            for (JavaParser.ModifierContext mc : jmc.modifier()) {
                if (mc.getText().equals("static")) {
                    cl.setStatica(true);
                }
            }
        } catch (ClassCastException e) {

        }
        if (auxModificador.equals("abstract")) {
            cl.setAbstracta(true);
        }
        try {
            String nombreClasePapa = ctx.typeType().classOrInterfaceType().IDENTIFIER(ctx.typeType().classOrInterfaceType().IDENTIFIER().size() - 1).getText();
            addHijo(nombreClasePapa, cl.getNombre());
            cl.setPapaExtends(nombreClasePapa);
        } catch (NullPointerException e) {

        }
        try {
            String[] tipos = ctx.typeList().getText().split(",");
            if (tipos.length > 0) {
                for (String tipo : tipos) {
                    addHijo(tipo, cl.getNombre());
                    cl.addPapaImplements(tipo);
                }
            } else {
                addHijo(ctx.typeList().getText(), cl.getNombre());
                cl.addPapaImplements(ctx.typeList().getText());
            }
        } catch (NullPointerException e) {

        }
        cl.setPaquete(paqueteAux);
        clases.add(cl);
        clase = true;

    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void exitClassDeclaration(JavaParser.ClassDeclarationContext ctx) {
        clase = false;
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void enterTypeParameters(JavaParser.TypeParametersContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void exitTypeParameters(JavaParser.TypeParametersContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void enterTypeParameter(JavaParser.TypeParameterContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void exitTypeParameter(JavaParser.TypeParameterContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void enterTypeBound(JavaParser.TypeBoundContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void exitTypeBound(JavaParser.TypeBoundContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void enterEnumDeclaration(JavaParser.EnumDeclarationContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void exitEnumDeclaration(JavaParser.EnumDeclarationContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void enterEnumConstants(JavaParser.EnumConstantsContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void exitEnumConstants(JavaParser.EnumConstantsContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void enterEnumConstant(JavaParser.EnumConstantContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void exitEnumConstant(JavaParser.EnumConstantContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void enterEnumBodyDeclarations(JavaParser.EnumBodyDeclarationsContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void exitEnumBodyDeclarations(JavaParser.EnumBodyDeclarationsContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void enterInterfaceDeclaration(JavaParser.InterfaceDeclarationContext ctx) {
        Clase cl = new Clase(ctx.IDENTIFIER().getText());
        cl.setInterfaz(true);
        cl.setImports(importsTemporal);
        importsTemporal = new ArrayList<>();

        TypeDeclarationContext tdc = (TypeDeclarationContext) ctx.parent;
        List<ClassOrInterfaceModifierContext> listaAux = tdc.classOrInterfaceModifier();
        String modificadores = "";
        for (ClassOrInterfaceModifierContext item : listaAux) {
            if (!modificadores.equals("")) {
                modificadores += " ";
            }
            modificadores += item.getText();
        }
        cl.setModificador(modificadores);
        try {
            JavaParser.ClassBodyDeclarationContext jmc = ((JavaParser.ClassBodyDeclarationContext) ctx.getParent().getParent());

            for (JavaParser.ModifierContext mc : jmc.modifier()) {
                if (mc.getText().equals("static")) {
                    cl.setStatica(true);
                }
            }
        } catch (ClassCastException e) {

        }
        try {
            String[] tipos = ctx.typeList().getText().split(",");
            if (tipos.length > 0) {
                for (String tipo : tipos) {
                    addHijo(tipo, cl.getNombre());
                    cl.addPapaImplements(tipo);
                }
            } else {
                addHijo(ctx.typeList().getText(), cl.getNombre());
                cl.addPapaImplements(ctx.typeList().getText());
            }
        } catch (NullPointerException e) {

        }
        cl.setPaquete(paqueteAux);
        clases.add(cl);
        clase = true;
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void exitInterfaceDeclaration(JavaParser.InterfaceDeclarationContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void enterClassBody(JavaParser.ClassBodyContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void exitClassBody(JavaParser.ClassBodyContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void enterInterfaceBody(JavaParser.InterfaceBodyContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void exitInterfaceBody(JavaParser.InterfaceBodyContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void enterClassBodyDeclaration(JavaParser.ClassBodyDeclarationContext ctx) {

    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void exitClassBodyDeclaration(JavaParser.ClassBodyDeclarationContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void enterMemberDeclaration(JavaParser.MemberDeclarationContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void exitMemberDeclaration(JavaParser.MemberDeclarationContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void enterMethodDeclaration(JavaParser.MethodDeclarationContext ctx) {

        Clase clase = clases.get(clases.size() - 1);
        if (ctx.IDENTIFIER().getText().equals("main") && ctx.formalParameters().getText().contains("String") && ctx.formalParameters().getText().contains("[]")) {
            clase.setMain(true);
        }
        Metodo metodoAInsertar = new Metodo(auxModificador, ctx.typeTypeOrVoid().getText(), ctx.IDENTIFIER().getText(), clase);
        metodoAInsertar.setCuerpo(ctx.methodBody().getText());
        if (ctx.methodBody().getText().equals(";")) {
            metodoAInsertar.setAbstracto(true);
        }

        ClassBodyDeclarationContext cAux = (ClassBodyDeclarationContext) ctx.parent.parent;
        if (cAux.start == null || cAux.stop == null || cAux.start.getStartIndex() < 0 || cAux.stop.getStopIndex() < 0) {
            metodoAInsertar.setMetodoCompleto(cAux.getText()); // Fallback
        } else {
            metodoAInsertar.setMetodoCompleto(cAux.start.getInputStream().getText(Interval.of(cAux.start.getStartIndex(), cAux.stop.getStopIndex())));
        }
        metodoAInsertar.setParametrosLinea(ctx.formalParameters().getText());
        clase.addMetodo(metodoAInsertar);
        try {
            List<JavaParser.FormalParameterContext> lista = ctx.formalParameters().formalParameterList().formalParameter();
            for (JavaParser.FormalParameterContext item : lista) {
                Parametro parametro = new Parametro(item.variableDeclaratorId().IDENTIFIER().getText(), item.typeType().getText());
                clases.get(clases.size() - 1).getMetodos().get(clases.get(clases.size() - 1).getMetodos().size() - 1).addParametro(parametro);
            }

        } catch (NullPointerException e) {

        }

        metodo = true;
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void exitMethodDeclaration(JavaParser.MethodDeclarationContext ctx) {
        metodo = false;
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void enterMethodBody(JavaParser.MethodBodyContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void exitMethodBody(JavaParser.MethodBodyContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void enterTypeTypeOrVoid(JavaParser.TypeTypeOrVoidContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void exitTypeTypeOrVoid(JavaParser.TypeTypeOrVoidContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void enterGenericMethodDeclaration(JavaParser.GenericMethodDeclarationContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void exitGenericMethodDeclaration(JavaParser.GenericMethodDeclarationContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void enterGenericConstructorDeclaration(JavaParser.GenericConstructorDeclarationContext ctx) {
        constructor = true;
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void exitGenericConstructorDeclaration(JavaParser.GenericConstructorDeclarationContext ctx) {
        constructor = false;
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void enterConstructorDeclaration(JavaParser.ConstructorDeclarationContext ctx) {
        constructor = true;
        ClassBodyDeclarationContext cAux = (ClassBodyDeclarationContext) ctx.parent.parent;
        if (cAux.start == null || cAux.stop == null || cAux.start.getStartIndex() < 0 || cAux.stop.getStopIndex() < 0) {
            clases.get(clases.size() - 1).addConstructor(cAux.getText()); // Fallback
        } else {
            clases.get(clases.size() - 1).addConstructor(cAux.start.getInputStream().getText(Interval.of(cAux.start.getStartIndex(), cAux.stop.getStopIndex())));
        }
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void exitConstructorDeclaration(JavaParser.ConstructorDeclarationContext ctx) {
        constructor = true;
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void enterFieldDeclaration(JavaParser.FieldDeclarationContext ctx) {
        ClassBodyDeclarationContext cAux = (ClassBodyDeclarationContext) ctx.parent.parent;
        if (cAux.start == null || cAux.stop == null || cAux.start.getStartIndex() < 0 || cAux.stop.getStopIndex() < 0) {
            clases.get(clases.size() - 1).addVariableLinea(cAux.getText()); // Fallback
        } else {
            clases.get(clases.size() - 1).addVariableLinea(cAux.start.getInputStream().getText(Interval.of(cAux.start.getStartIndex(), cAux.stop.getStopIndex())));
        }

        List<JavaParser.VariableDeclaratorContext> lista = ctx.variableDeclarators().variableDeclarator();
        for (JavaParser.VariableDeclaratorContext item : lista) {
            Variable variable = new Variable(item.variableDeclaratorId().IDENTIFIER().getText(), ctx.typeType().getText());
            clases.get(clases.size() - 1).addVariable(variable);
        }
        try {
            JavaParser.ClassBodyDeclarationContext jmc = ((JavaParser.ClassBodyDeclarationContext) ctx.getParent().getParent());
            for (JavaParser.ModifierContext mc : jmc.modifier()) {
                if (mc.getText().equals("static")) {
                    clases.get(clases.size() - 1).setStatica(true);
                }
            }
        } catch (NullPointerException ex) {

        }
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void exitFieldDeclaration(JavaParser.FieldDeclarationContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void enterInterfaceBodyDeclaration(JavaParser.InterfaceBodyDeclarationContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void exitInterfaceBodyDeclaration(JavaParser.InterfaceBodyDeclarationContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void enterInterfaceMemberDeclaration(JavaParser.InterfaceMemberDeclarationContext ctx) {

    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void exitInterfaceMemberDeclaration(JavaParser.InterfaceMemberDeclarationContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void enterConstDeclaration(JavaParser.ConstDeclarationContext ctx) {
        List<JavaParser.ConstantDeclaratorContext> lista = ctx.constantDeclarator();
        for (JavaParser.ConstantDeclaratorContext item : lista) {
            Variable variable = new Variable(item.IDENTIFIER().getText(), ctx.typeType().getText());
            clases.get(clases.size() - 1).addVariable(variable);
        }
        try {
            JavaParser.ClassBodyDeclarationContext jmc = ((JavaParser.ClassBodyDeclarationContext) ctx.getParent().getParent());
            for (JavaParser.ModifierContext mc : jmc.modifier()) {
                if (mc.getText().equals("static")) {
                    clases.get(clases.size() - 1).setStatica(true);
                }
            }
        } catch (Exception ex) {

        }
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void exitConstDeclaration(JavaParser.ConstDeclarationContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void enterConstantDeclarator(JavaParser.ConstantDeclaratorContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void exitConstantDeclarator(JavaParser.ConstantDeclaratorContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void enterInterfaceMethodDeclaration(JavaParser.InterfaceMethodDeclarationContext ctx) {

        Clase clase = clases.get(clases.size() - 1);
        Metodo metodoAInsertar = new Metodo(auxModificador, ctx.typeTypeOrVoid().getText(), ctx.IDENTIFIER().getText(), clase);
        metodoAInsertar.setAbstracto(true);
        metodoAInsertar.setCuerpo(ctx.methodBody().getText());

        InterfaceBodyDeclarationContext cAux = (InterfaceBodyDeclarationContext) ctx.parent.parent;
        if (cAux.start == null || cAux.stop == null || cAux.start.getStartIndex() < 0 || cAux.stop.getStopIndex() < 0) {
            metodoAInsertar.setMetodoCompleto(cAux.getText()); // Fallback
        } else {
            metodoAInsertar.setMetodoCompleto(cAux.start.getInputStream().getText(Interval.of(cAux.start.getStartIndex(), cAux.stop.getStopIndex())));
        }

        clase.addMetodo(metodoAInsertar);
        try {
            List<JavaParser.FormalParameterContext> lista = ctx.formalParameters().formalParameterList().formalParameter();
            for (JavaParser.FormalParameterContext item : lista) {
                Parametro parametro = new Parametro(item.variableDeclaratorId().IDENTIFIER().getText(), item.typeType().getText());
                clases.get(clases.size() - 1).getMetodos().get(clases.get(clases.size() - 1).getMetodos().size() - 1).addParametro(parametro);
            }
        } catch (NullPointerException e) {

        }

    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void exitInterfaceMethodDeclaration(JavaParser.InterfaceMethodDeclarationContext ctx) {
        metodo = false;
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void enterInterfaceMethodModifier(JavaParser.InterfaceMethodModifierContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void exitInterfaceMethodModifier(JavaParser.InterfaceMethodModifierContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void enterGenericInterfaceMethodDeclaration(JavaParser.GenericInterfaceMethodDeclarationContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void exitGenericInterfaceMethodDeclaration(JavaParser.GenericInterfaceMethodDeclarationContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void enterVariableDeclarators(JavaParser.VariableDeclaratorsContext ctx) {

    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void exitVariableDeclarators(JavaParser.VariableDeclaratorsContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void enterVariableDeclarator(JavaParser.VariableDeclaratorContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void exitVariableDeclarator(JavaParser.VariableDeclaratorContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void enterVariableDeclaratorId(JavaParser.VariableDeclaratorIdContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void exitVariableDeclaratorId(JavaParser.VariableDeclaratorIdContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void enterVariableInitializer(JavaParser.VariableInitializerContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void exitVariableInitializer(JavaParser.VariableInitializerContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void enterArrayInitializer(JavaParser.ArrayInitializerContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void exitArrayInitializer(JavaParser.ArrayInitializerContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void enterClassOrInterfaceType(JavaParser.ClassOrInterfaceTypeContext ctx) {

        //System.out.println(ctx.getRuleIndex() + " - " + ctx.getText());
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void exitClassOrInterfaceType(JavaParser.ClassOrInterfaceTypeContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void enterTypeArgument(JavaParser.TypeArgumentContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void exitTypeArgument(JavaParser.TypeArgumentContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void enterQualifiedNameList(JavaParser.QualifiedNameListContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void exitQualifiedNameList(JavaParser.QualifiedNameListContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void enterFormalParameters(JavaParser.FormalParametersContext ctx) {

    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void exitFormalParameters(JavaParser.FormalParametersContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void enterFormalParameterList(JavaParser.FormalParameterListContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void exitFormalParameterList(JavaParser.FormalParameterListContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void enterFormalParameter(JavaParser.FormalParameterContext ctx) {

    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void exitFormalParameter(JavaParser.FormalParameterContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void enterLastFormalParameter(JavaParser.LastFormalParameterContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void exitLastFormalParameter(JavaParser.LastFormalParameterContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void enterQualifiedName(JavaParser.QualifiedNameContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void exitQualifiedName(JavaParser.QualifiedNameContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void enterLiteral(JavaParser.LiteralContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void exitLiteral(JavaParser.LiteralContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void enterIntegerLiteral(JavaParser.IntegerLiteralContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void exitIntegerLiteral(JavaParser.IntegerLiteralContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void enterFloatLiteral(JavaParser.FloatLiteralContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void exitFloatLiteral(JavaParser.FloatLiteralContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void enterAnnotation(JavaParser.AnnotationContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void exitAnnotation(JavaParser.AnnotationContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void enterElementValuePairs(JavaParser.ElementValuePairsContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void exitElementValuePairs(JavaParser.ElementValuePairsContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void enterElementValuePair(JavaParser.ElementValuePairContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void exitElementValuePair(JavaParser.ElementValuePairContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void enterElementValue(JavaParser.ElementValueContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void exitElementValue(JavaParser.ElementValueContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void enterElementValueArrayInitializer(JavaParser.ElementValueArrayInitializerContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void exitElementValueArrayInitializer(JavaParser.ElementValueArrayInitializerContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void enterAnnotationTypeDeclaration(JavaParser.AnnotationTypeDeclarationContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void exitAnnotationTypeDeclaration(JavaParser.AnnotationTypeDeclarationContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void enterAnnotationTypeBody(JavaParser.AnnotationTypeBodyContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void exitAnnotationTypeBody(JavaParser.AnnotationTypeBodyContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void enterAnnotationTypeElementDeclaration(JavaParser.AnnotationTypeElementDeclarationContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void exitAnnotationTypeElementDeclaration(JavaParser.AnnotationTypeElementDeclarationContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void enterAnnotationTypeElementRest(JavaParser.AnnotationTypeElementRestContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void exitAnnotationTypeElementRest(JavaParser.AnnotationTypeElementRestContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void enterAnnotationMethodOrConstantRest(JavaParser.AnnotationMethodOrConstantRestContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void exitAnnotationMethodOrConstantRest(JavaParser.AnnotationMethodOrConstantRestContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void enterAnnotationMethodRest(JavaParser.AnnotationMethodRestContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void exitAnnotationMethodRest(JavaParser.AnnotationMethodRestContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void enterAnnotationConstantRest(JavaParser.AnnotationConstantRestContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void exitAnnotationConstantRest(JavaParser.AnnotationConstantRestContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void enterDefaultValue(JavaParser.DefaultValueContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void exitDefaultValue(JavaParser.DefaultValueContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void enterBlock(JavaParser.BlockContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void exitBlock(JavaParser.BlockContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void enterBlockStatement(JavaParser.BlockStatementContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void exitBlockStatement(JavaParser.BlockStatementContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void enterLocalVariableDeclaration(JavaParser.LocalVariableDeclarationContext ctx) {
        List<JavaParser.VariableDeclaratorContext> lista = ctx.variableDeclarators().variableDeclarator();
        for (JavaParser.VariableDeclaratorContext item : lista) {
            Variable variable = new Variable(item.variableDeclaratorId().IDENTIFIER().getText(), ctx.typeType().getText());

            if (!constructor) {
                clases.get(clases.size() - 1).getMetodos().get(clases.get(clases.size() - 1).getMetodos().size() - 1).addVariable(variable);
            }

        }
        //System.out.println(ctx.getRuleIndex() + " - " + ctx.getText());
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void exitLocalVariableDeclaration(JavaParser.LocalVariableDeclarationContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void enterLocalTypeDeclaration(JavaParser.LocalTypeDeclarationContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void exitLocalTypeDeclaration(JavaParser.LocalTypeDeclarationContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void enterStatement(JavaParser.StatementContext ctx) {
        //System.out.println(ctx.getText());
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void exitStatement(JavaParser.StatementContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void enterCatchClause(JavaParser.CatchClauseContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void exitCatchClause(JavaParser.CatchClauseContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void enterCatchType(JavaParser.CatchTypeContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void exitCatchType(JavaParser.CatchTypeContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void enterFinallyBlock(JavaParser.FinallyBlockContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void exitFinallyBlock(JavaParser.FinallyBlockContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void enterResourceSpecification(JavaParser.ResourceSpecificationContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void exitResourceSpecification(JavaParser.ResourceSpecificationContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void enterResources(JavaParser.ResourcesContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void exitResources(JavaParser.ResourcesContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void enterResource(JavaParser.ResourceContext ctx) {

    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void exitResource(JavaParser.ResourceContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void enterSwitchBlockStatementGroup(JavaParser.SwitchBlockStatementGroupContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void exitSwitchBlockStatementGroup(JavaParser.SwitchBlockStatementGroupContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void enterSwitchLabel(JavaParser.SwitchLabelContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void exitSwitchLabel(JavaParser.SwitchLabelContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void enterForControl(JavaParser.ForControlContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void exitForControl(JavaParser.ForControlContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void enterForInit(JavaParser.ForInitContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void exitForInit(JavaParser.ForInitContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void enterEnhancedForControl(JavaParser.EnhancedForControlContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void exitEnhancedForControl(JavaParser.EnhancedForControlContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void enterParExpression(JavaParser.ParExpressionContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void exitParExpression(JavaParser.ParExpressionContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void enterExpressionList(JavaParser.ExpressionListContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void exitExpressionList(JavaParser.ExpressionListContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void enterMethodCall(JavaParser.MethodCallContext ctx) {
        if (clase && !constructor && !metodo) {
            clases.get(clases.size() - 1).addLlamada(ctx.parent.getText());
        } else if (clase && !constructor && metodo) {
            clases.get(clases.size() - 1).getMetodos().get(clases.get(clases.size() - 1).getMetodos().size() - 1).addLlamada(ctx.parent.getText());
        }
        //System.out.println(ctx.parent.parent.getRuleIndex() + " - " + ctx.getText());
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void exitMethodCall(JavaParser.MethodCallContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void enterExpression(JavaParser.ExpressionContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void exitExpression(JavaParser.ExpressionContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void enterLambdaExpression(JavaParser.LambdaExpressionContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void exitLambdaExpression(JavaParser.LambdaExpressionContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void enterLambdaParameters(JavaParser.LambdaParametersContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void exitLambdaParameters(JavaParser.LambdaParametersContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void enterLambdaBody(JavaParser.LambdaBodyContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void exitLambdaBody(JavaParser.LambdaBodyContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void enterPrimary(JavaParser.PrimaryContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void exitPrimary(JavaParser.PrimaryContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void enterClassType(JavaParser.ClassTypeContext ctx) {

    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void exitClassType(JavaParser.ClassTypeContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void enterCreator(JavaParser.CreatorContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void exitCreator(JavaParser.CreatorContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void enterCreatedName(JavaParser.CreatedNameContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void exitCreatedName(JavaParser.CreatedNameContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void enterInnerCreator(JavaParser.InnerCreatorContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void exitInnerCreator(JavaParser.InnerCreatorContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void enterArrayCreatorRest(JavaParser.ArrayCreatorRestContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void exitArrayCreatorRest(JavaParser.ArrayCreatorRestContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void enterClassCreatorRest(JavaParser.ClassCreatorRestContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void exitClassCreatorRest(JavaParser.ClassCreatorRestContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void enterExplicitGenericInvocation(JavaParser.ExplicitGenericInvocationContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void exitExplicitGenericInvocation(JavaParser.ExplicitGenericInvocationContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void enterTypeArgumentsOrDiamond(JavaParser.TypeArgumentsOrDiamondContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void exitTypeArgumentsOrDiamond(JavaParser.TypeArgumentsOrDiamondContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void enterNonWildcardTypeArgumentsOrDiamond(JavaParser.NonWildcardTypeArgumentsOrDiamondContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void exitNonWildcardTypeArgumentsOrDiamond(JavaParser.NonWildcardTypeArgumentsOrDiamondContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void enterNonWildcardTypeArguments(JavaParser.NonWildcardTypeArgumentsContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void exitNonWildcardTypeArguments(JavaParser.NonWildcardTypeArgumentsContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void enterTypeList(JavaParser.TypeListContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void exitTypeList(JavaParser.TypeListContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void enterTypeType(JavaParser.TypeTypeContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void exitTypeType(JavaParser.TypeTypeContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void enterPrimitiveType(JavaParser.PrimitiveTypeContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void exitPrimitiveType(JavaParser.PrimitiveTypeContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void enterTypeArguments(JavaParser.TypeArgumentsContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void exitTypeArguments(JavaParser.TypeArgumentsContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void enterSuperSuffix(JavaParser.SuperSuffixContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void exitSuperSuffix(JavaParser.SuperSuffixContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void enterExplicitGenericInvocationSuffix(JavaParser.ExplicitGenericInvocationSuffixContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void exitExplicitGenericInvocationSuffix(JavaParser.ExplicitGenericInvocationSuffixContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void enterArguments(JavaParser.ArgumentsContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void exitArguments(JavaParser.ArgumentsContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void enterEveryRule(ParserRuleContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void exitEveryRule(ParserRuleContext ctx) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void visitTerminal(TerminalNode node) {
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The default implementation does nothing.</p>
     */
    @Override
    public void visitErrorNode(ErrorNode node) {
    }
}
