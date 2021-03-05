package prueba2ref;


/**
 * @author Pablo Padilla
 * @version 1.0
 * @created 26-mar.-2019 07:09:59 p. m.
 */
public class CContexto extends  Clista {

	public CStrategy m_CStrategy;

	public CContexto(){

	}

	public void finalize() throws Throwable {
		super.finalize();
	}

}