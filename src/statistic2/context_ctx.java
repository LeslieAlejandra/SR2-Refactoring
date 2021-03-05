//clase principal para ejecucion de marco de componentes
//de aplicaciones estadisticas
package statistic2;
//import java.io.*;
//import java.lang.*;
//import java.lang.Integer;
import java.util.StringTokenizer;
//import java.lang.Math;
//import java.text.DecimalFormat;
public class context_ctx 
{
	//constructor
	public context_ctx()
	{
	}
	
	//var to fill list before execute some calculation method
	private boolean bListFilled;
	
	//Vars to save results
	double resultadofin = 0;
    double resultadoy = 0;
	double dbSumX = 0;	//Sum result of X
    double resultsumay = 0; 
    double dbAriMeanX = 0; //Arithmetic mean result of X
	double resultMediay = 0;
	double Sumax = 0;
	double Sumay = 0;
	double dbAriMeanXTEMP = 0;  
	double Mediay = 0;
	double dbStdDev = 0;  // calculo de la desviacion estandar
	double reglineal=0;
	double sumaR=0.0;
	double sumy= 0.0;
	double sum1=0.0;
	double sum4=0.0;
	double sum5=0.0;
	double raiz=0.0;
	double Rango=0.0;
	double corr=0;
	double t=0;
	double t1=0;
	double totalf=1.0;
	float limSup=0;
	float limInf=0;
	int n=0;
	int S=10;
	int Nsegs=20;
	double Er=0.0;
	double errorPorcentaje=0.1;
	int gl=0;
	int N1=1;
	int opcion =0;
	float est1=0;
	double est=0;
	double B1;
	double B0;
	int matriz=6;
	float[][]mat=new float[4][4];
	float[] zetas= new float[4];
	float[] betas=new float[4];
	float estw=650;
	float estx=3000;
	float esty=155;
	List m_list= new List();
	List zlista= new List();
	List list_items= new List();
	List listlimits= new List();
	cElement apt= m_list.getL();
	cElement apt1=zlista.getL();
	List listaorden;
	List listaord;


	//metodos de escritura de valores
    public void setResultado(double resultado)
	{  
     	resultadofin = resultado;
	}
    public void setResultadoy(double resultado)
	{ 	
		resultadoy =resultado; 
	}
    public void setSumax(double dbXRes)
	{	
		dbSumX = dbXRes;
	}
    public void setSumay(double resultadoy)
    {
    	resultsumay = resultadoy; 
    }
    public void setAriMeanX(double dbXRes)
    {	
    	dbAriMeanX = dbXRes; 
    }
    public void setMediay(double resultadoy)
    {	
    	resultMediay =resultadoy; 
    }
	
	
	//metodos para lectura de resultado
    public double getResultado()
	{ 
		return resultadofin;
	}
    public double getResultadoy()
	{
		return resultadoy; 
	}
    public double getSumax()
    {	
    	return dbSumX; 
    }
    public double getSumay()
    {
		return resultsumay; 
	}
    public double getMediax()
    {
		return dbAriMeanX; 
	}
    public double getMediay()
    {
		return resultMediay; 
	}
	
	// this method must be executed before use any calc
	public double ariMeanCalc()
	{
		if (bListFilled)
		{
			//polimorfismo de ptr
			context_ctx ctx=this;
			//aStatistic ptr;
			//cAriMean val= new cAriMean();
			//ptr toma forma de cMedia()
			//ptr = val;
			//ptr.interfaceAlg2(ctx);
		}
		//if the list isn�t filled, then return 0	
		return dbAriMeanX;
	}
	
	public double stdDevCalc()
	{
		if (bListFilled)
		{	
			//aStatistic ptr;
	   		//cDev val2= new cDev();
	   		//ptr toma forma de cDesviacion()
	   		//ptr = val2;
	   		//ptr.interfaceAlg3(this);
		}
		return dbStdDev;
	}
	
	public double sumCalc()
	{
		if (bListFilled)
		{
			//polimorfismo de ptr.
			//aStatistic ptr;
			//cSum obj1= new cSum();
		
			//ptr toma forma de cSuma()
			//ptr = obj1;
			//ptr.interfaceAlg1(this);
		}
		//if the list isn�t filled, then return 0
		return dbSumX;
	}
	
	public void CalculaCorrelacion()
	{
		//polimorfismo de ptr
		//aStatistic ptr;
		//cCorrelation obj10= new cCorrelation();
		//ptr toma forma de cCorrelacion
		//ptr = obj10;
		//ptr.algoInterfaz10(this);
		corr= getResultado();
		System.out.println("valor de correlacion = "+corr);
	}
	
	//inicio del metodo principal
	//IVM 11/Sep/2003 se modifico para leer linea de String del Servicio Web
 	public void addToList(String strLine)
    {
    	String stLine;
        String strNum1 = "";
        String strNum2 = "";
        float flNx=0;
        float flNy=0;
        try
        {
            	//Saves in list
            	StringTokenizer strTNum = new StringTokenizer(strLine);
                strNum1 = strTNum.nextToken();
                flNx = (Float.valueOf(strNum1)).floatValue();
                strNum2 = strTNum.nextToken();
                flNy = (Float.valueOf(strNum2)).floatValue();
                cElement node = new cElement(flNx,flNy);

				//saves data in list
                m_list.add(node);
                n=n+1;
                //Verifies that some data is in the list
				bListFilled = true;
		}
	    catch (Exception e)
	    {
	    	System.out.println( "Error"+ e);
		}
		//Once this method is finished and the list is filled, 
		//then we can call to any calculation method
   	}
	
}
