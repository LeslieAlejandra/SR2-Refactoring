package view_system;
/* Clase: UtileriasContador
 Autor: Manuel Valdes Marrero
 Fecha: 20 Julio 2003
 Descripcion: Funciones para obtener líneas de un archivo, contar diversos tipos de lineas */
import java.io.*;

public class UtileriasContador {
	/* static String[] mostrarLineas(File archivo)
	 Recibe un archivo, para descomponerlo por lineas. 
	 Regresa un arreglo con las lineas del archivo en cada renglón del arreglo*/
	public static String[] mostrarLineas(File archivo){
		String[] lineas=null;
		String cadena;
		int contador=0;
		int indice=0;
		try{
			BufferedReader lectura = new BufferedReader(new FileReader(archivo));
			while (lectura.readLine()!=null){
				contador++;
			}
			lectura.close();
			lineas=new String[contador];
			lectura = new BufferedReader(new FileReader(archivo));
			while ((cadena=lectura.readLine())!=null){
				lineas[indice]=cadena;
				indice++;
			}
			lectura.close();
			return lineas;
		}
		catch(Exception e){
			return null;
		}
	}
	/* static int contarLineas(String[] archivo)
	 Recibe un arreglo de cadenas, para identificar cuales son lineas de codigo, eliminando comentarios y llaves. 
	 Regresa el numero de lineas que son consideradas código. */	
	public static int contarLineas(String[] archivo){
		int contadorLinea=0;
		int contadorVacia=0;
		int contadorCom=0;
		int contadorLlave=0;
		int opcion;
		int indice=0;
		boolean bandCom=false;
		String cadena;
		try{
			while (indice<archivo.length){
				cadena=archivo[indice];
				cadena=quitaCaracter(' ',cadena);
				cadena=quitaCaracter('\t',cadena);
				opcion=0;
				if (cadena.length()==0 && opcion==0)
					opcion=1;
				else {
					if (cadena.length()==1 && (cadena.charAt(0)=='}' || cadena.charAt(0)=='{') && opcion==0)
						opcion=4;
					if ((cadena.charAt(0)!='/') && (bandCom==false) && opcion==0)
						opcion=2;
					if ((cadena.charAt(0)=='/')&&(cadena.charAt(1)=='/') && opcion==0)
						opcion=3;
					if ((cadena.charAt(0)=='/')&&(cadena.charAt(1)=='*') && opcion==0) {
						bandCom=true;
						opcion=3;
					}
					if ((bandCom==true)&&(cadena.charAt(cadena.length()-1)!='/'))
						opcion=3;
					if ((bandCom==true)&&(cadena.charAt(cadena.length()-1)=='/')&&(cadena.charAt(cadena.length()-2)=='*')) {
						bandCom=false;
						opcion=3;
					}
				}
				switch (opcion){
				case 1:
					contadorVacia++;
					break;
				case 2:
					contadorLinea++;
					break;
				case 3:
					contadorCom++;
					break;
				case 4:
					contadorLlave++;
					break;
				}
				indice++;
			}
			return contadorLinea;
		}
		catch(Exception e){
			return 0;
		}
	}
	/* static int contarLineasReuso(String[] archivo1, String[] archivo2)
	 Recibe dos arreglos de cadenas, para contar las líneas de reuso, sin considerar comentarios, espacios o llaves.
	 Se consideran líneas de reuso cuando no se modifica ninguna línea entre los dos arreglos.
	 Regresa el número de líneas reusadas, ya sea cero o la longitud de los arreglos. */
	public static int contarLineasReuso(String[] archivo1, String[] archivo2){
		String[] lineas1;
		String[] lineas2;
		lineas1=quitaBasura(archivo1);
		lineas2=quitaBasura(archivo2);
		if (lineas1.length!=lineas2.length)
			return 0;
		boolean hayCambios=false;
		for (int i=0;i<lineas1.length;i++){
			if (lineas1[i].compareToIgnoreCase(lineas2[i])!=0){
				hayCambios=true;
				break;
			}
		}
		if (hayCambios==false)
			return lineas1.length;
		else
			return 0;
	}
	/* static int contarLineasEliminadas(String[] archivo1, String[] archivo2)
	 Recibe dos arreglos de cadenas, para contar las líneas eliminadas, sin considerar comentarios, espacios o llaves.
	 Se consideran líneas eliminadas cuando aparecen en el primer arreglo pero no en el segundo.
	 Regresa el número de líneas eliminadas. */
	public static int contarLineasEliminadas(String[] archivo1, String[] archivo2){
		String[] lineas1;
		String[] lineas2;
		lineas1=quitaBasura(archivo1);
		lineas2=quitaBasura(archivo2);
		int lineasEliminadas=0;
		int apuntador1;
		int apuntador2;
		int indiceEncontrado=0;
		String codigo1;
		String codigo2;
		boolean encontrada;
		for (apuntador1=0;apuntador1<lineas1.length;apuntador1++){
			codigo1=lineas1[apuntador1];
			encontrada=false;
			for (apuntador2=indiceEncontrado;apuntador2<lineas2.length;apuntador2++){
				codigo2=lineas2[apuntador2];
				if (codigo1.compareTo(codigo2)==0){
					encontrada=true;
					indiceEncontrado=apuntador2+1;
					break;
				}
			}
			if (encontrada==false){
				lineasEliminadas++;
			}
		}
		return lineasEliminadas;
	}
	/* static int contarLineasNuevas(String[] archivo1, String[] archivo2)
	 Recibe dos arreglos de cadenas, para contar las líneas nuevas, sin considerar comentarios, espacios o llaves.
	 Se consideran líneas nuevas cuando aparecen en el segundo arreglo pero no en el primero.
	 Regresa el número de líneas nuevas. */
	public static int contarLineasNuevas(String[] archivo1, String[] archivo2){
		int lineasNuevas=0;
		lineasNuevas=contarLineasEliminadas(archivo2, archivo1);
		return lineasNuevas;
	}
	/* static String quitaCaracter(char caracter,String cadena)
	 * Elimina el caracter de la cadena.
	 * Regresa la cadena. */
	public static String quitaCaracter(char caracter,String cadena){
		StringBuffer cad=new StringBuffer("");
		for (int i=0;i<cadena.length();i++){
			if (cadena.charAt(i)!=caracter){
				cad.append(cadena.charAt(i));
			}
		}
		return (cad.toString());
	}
	/* static String quitaCaracterExtremo(char caracter,String cadena)
	 * Elimina el caracter de la cadena al inicio y fin de cadena.
	 * Regresa la cadena. */
	public static String quitaCaracterExtremo(char caracter,String cadena){
		StringBuffer cad=new StringBuffer("");
		boolean bandera=false;
		int contFinal=cadena.length()-1;
		for (int i=cadena.length()-1;i>=0;i--){
			if (cadena.charAt(i)!=caracter){
				contFinal=i;
				break;
			}
		}
		for (int i=0;i<=contFinal;i++){
			if (bandera==false){
				if (cadena.charAt(i)!=caracter){
					cad.append(cadena.charAt(i));
					bandera=true;
				}
			}
			else{
				cad.append(cadena.charAt(i));
			}
		}
		return (cad.toString());
	}
	
	private static String[] quitaBasura(String[] archivo){
		String[] archivoFinal;
		int longitud; 
		int opcion;
		int contador=0;
		int indice=0;
		boolean bandCom=false;
		String cadena;
		longitud=contarLineas(archivo);
		archivoFinal=new String[longitud];
		while (indice<archivo.length){
			cadena=archivo[indice];
			cadena=quitaCaracter(' ',cadena);
			cadena=quitaCaracter('\t',cadena);
			opcion=0;
			if (cadena.length()==0 && opcion==0)
				opcion=1;
			else {
				if (cadena.length()==1 && (cadena.charAt(0)=='}' || cadena.charAt(0)=='{') && opcion==0)
					opcion=4;
				if ((cadena.charAt(0)!='/') && (bandCom==false) && opcion==0)
					opcion=2;
				if ((cadena.charAt(0)=='/')&&(cadena.charAt(1)=='/') && opcion==0)
					opcion=3;
				if ((cadena.charAt(0)=='/')&&(cadena.charAt(1)=='*') && opcion==0) {
					bandCom=true;
					opcion=3;
				}
				if ((bandCom==true)&&(cadena.charAt(cadena.length()-1)!='/'))
					opcion=3;
				if ((bandCom==true)&&(cadena.charAt(cadena.length()-1)=='/')&&(cadena.charAt(cadena.length()-2)=='*')) {
					bandCom=false;
					opcion=3;
				}
			}
			switch (opcion){
			case 2:
				archivoFinal[contador]=archivo[indice];
				archivoFinal[contador]=quitaCaracter(' ',archivoFinal[contador]);
				archivoFinal[contador]=quitaCaracter('\t',archivoFinal[contador]);
				contador++;
				break;
			}
			indice++;
		}
		return archivoFinal;
	}
	/* static int isCodigo(String linea)
	 Recibe una cadena, para identificar si es línea de código. 
	 Regresa 1 si es considerada línea de código, 2 si es línea vacía, 3 si es comentario simple y 4 si el comentario continúa. */	
	public static int isCodigo(String linea, int codigo){
		int opcion;
		boolean bandCom=false;
		String cadena;
		if (codigo==4){
			bandCom=true;
		}
		cadena=linea;
		cadena=quitaCaracterExtremo(' ',cadena);
		cadena=quitaCaracterExtremo('\t',cadena);
		opcion=0;
		if (cadena.length()==0 && opcion==0)
			opcion=2; // línea vacía
		else {
			if ((cadena.charAt(0)!='/') && (bandCom==false) && opcion==0) {
				opcion=1; // línea de código
			}
			if ((cadena.charAt(0)=='/')&&(cadena.charAt(1)=='/') && opcion==0) {
				opcion=3; // comentario simple
			}
			if ((cadena.charAt(0)=='/')&&(cadena.charAt(1)=='*') && opcion==0) {
				opcion=4; // inicio comentario compuesto
			}
			if ((bandCom==true)&&(cadena.charAt(cadena.length()-1)!='/')) {
				opcion=4; // continuación comentario compuesto 
			}
			if ((bandCom==true)&&(cadena.charAt(cadena.length()-1)=='/')&&(cadena.charAt(cadena.length()-2)=='*')) {
				opcion=3; // fin comentario compuesto
			}
		}
		return opcion;
	}
}
