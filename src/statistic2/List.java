package statistic2;

public class List
{
	private cElement L;
    private cElement C;
    public cElement m_cElemento;
    public aListMgt m_aManejoLista;
    //public void listaLigada()
    //{
    //}
	public List()
	{
		L = null;
		C = null;
	}
  	public void ordenacion1(context_ctx ctx)
    {
	    context_ctx ctx1=ctx;
		cBuble objeto = new cBuble();
        objeto.ordena(ctx1);
    }
  	public void ordenacion(context_ctx ctx)
    {
	    context_ctx ctx1=ctx;
		cBubleXY objeto = new cBubleXY();
        objeto.ordena(ctx1);
    }

	
	
	// obtiene la ref al elemento de la lista
   	public cElement getL()
    {
    	return L;
	}
   	public cElement getC ()
    {
    	return C;
    }


	//checa si lista esta vacia
    public boolean isEmpty ()
	{
    	return ( L==null);
	}
    public void add ( cElement apt)
    {
    	if ( L==null)
        {
        	L=apt;
			C=apt;
		}
        else
        {
        	C.apunta(apt);
            C=apt;
		}
	}
    public cElement proximo(cElement val)
	{
    	if(val!=null)
		{
        	val=val.getNext();
		}
		return val;
	}
}