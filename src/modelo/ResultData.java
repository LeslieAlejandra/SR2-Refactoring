package modelo;

import java.util.ArrayList;

public class ResultData {

	public static ResultData resultData;
	public String nombre;
	public String Cnoc;
	public String Nfv;
	public String Nfno;
	public String Result;
	public String coherencia;
	public String totalMetodos;
	public String getTotalMetodos() {
		return totalMetodos;
	}

	public void setTotalMetodos(String totalMetodos) {
		this.totalMetodos = totalMetodos;
	}

	public ArrayList<VDino> lista;
	public String rutaArchivos;
			
	private ResultData() {}

	public static ResultData getSingletonInstance() {
		if (resultData == null) {
			resultData = new ResultData();
		} else {
			System.out.println(
					"No se puede crear el objeto porque ya existe un objeto de la clase SoyUnico");
		}

		return resultData;
	}

	public static ResultData getResultData() {
		return resultData;
	}

	public static void setResultData(ResultData resultData) {
		ResultData.resultData = resultData;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCnoc() {
		return Cnoc;
	}

	public void setCnoc(String cnoc) {
		Cnoc = cnoc;
	}

	public String getNfv() {
		return Nfv;
	}

	public void setNfv(String nfv) {
		Nfv = nfv;
	}

	public String getNfno() {
		return Nfno;
	}

	public void setNfno(String nfno) {
		Nfno = nfno;
	}

	public String getResult() {
		return Result;
	}

	public void setResult(String result) {
		Result = result;
	}

	public ArrayList<VDino> getLista() {
		return lista;
	}

	public void setLista(ArrayList<VDino> lista) {
		this.lista = lista;
	}

	public String getRutaArchivos() {
		return rutaArchivos;
	}

	public void setRutaArchivos(String rutaArchivos) {
		this.rutaArchivos = rutaArchivos;
	}

	public String getCoherencia() {
		return coherencia;
	}

	public void setCoherencia(String coherencia) {
		this.coherencia = coherencia;
	}
	
	

}
