package statistic2;

public class cElement 
{
	private cElement next;
    private cElement ant;
    float valx;
    float valy;
    float valz;
    //private aED m_aED;
    private List lista;
    public cElement (float x)
	{
		this(x,0);
	}
    public cElement (float val, float val1, float val2)
    {
    	valx=val;
        valy=val1;
        valz=val2;
        next= null;
        ant = null;
	}
    public cElement (float val, float val1)
    {
		valx=val;
	    valy=val1;
	    next= null;
	    ant = null;
	}
	//escritura de los elementos en el nodo
    public void setNumero(float valor )
	{
		valx=valor;
	}
    public void setNumeros(float valorx, float valory)
	{
		valx=valorx;
		valy=valory;
	}
	//pone valor en nodo
    public void setNumeros(float valorx, float valory, float valorz) 
    {
    	valx=valorx;
        valy=valory;
        valz=valorz;
	}
	//apunta al atributo next al elemento que recibe como parametro
    public void apunta(cElement pt)
    {
    	next=pt;
	}
	//regresa el valor x, y, y z respectivamente del nodo
	public float getNumerox()
    {
    	return valx;
	}
    public float getNumeroy()
    {
    	return valy;
	}
    public float getNumeroz()
	{
    	return valz;
	}
	//regresa el valor siguiente
    public cElement getNext()
    {
    	return next;
	}
	// regresa el atributo ant
	public cElement getAnt()
    {
    	return ant;
	}
}
//termina clase cElemento
