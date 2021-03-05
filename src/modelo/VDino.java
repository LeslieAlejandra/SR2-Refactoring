package modelo;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Padilla
 */
public class VDino {

    private Clase clase;
    private int cnoc, nfv, nfno;

    public VDino(Clase clase, int cnoc, int nfv, int nfno) {
        this.clase = clase;
        this.cnoc = cnoc;
        this.nfv = nfv;
        this.nfno = nfno;
    }

    public Clase getClase() {
        return clase;
    }

    public void setClase(Clase clase) {
        this.clase = clase;
    }

    public int getCnoc() {
        return cnoc;
    }

    public void setCnoc(int cnoc) {
        this.cnoc = cnoc;
    }

    public int getNfv() {
        return nfv;
    }

    public void setNfv(int nfv) {
        this.nfv = nfv;
    }

    public int getNfno() {
        return nfno;
    }

    public void setNfno(int nfno) {
        this.nfno = nfno;
    }

    public double getResultado() {
        double res = (double) nfno / ((double) cnoc * (double) nfv);
        return res;
    }
}
