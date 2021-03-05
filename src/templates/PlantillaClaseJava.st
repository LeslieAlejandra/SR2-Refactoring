package codigorefactorizado;

<clase.imports :{ import | <import>}; separator="\n">

<clase.modificador> <tipoclase> <clase.nombre> <herencia> <papa.nombre>{
    <clase.variablesLinea :{ variable | <variable>}; separator="\n">
    <clase.constructores :{ constructor |<constructor>}; separator="\n">

    <clase.metodos :{ metodo |  <metodo.metodoCompleto>}; separator="\n\n">

}
