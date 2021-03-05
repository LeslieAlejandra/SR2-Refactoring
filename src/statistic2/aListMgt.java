package statistic2;
public abstract class aListMgt extends List
{
    public List m_Lista;
    public aListMgt Manejo;

	//IVM 08/03/2003 
	//quite void, es constructor de la clase
    public aListMgt()
    {
    }
    abstract public void ordena(context_ctx ctx);
}

