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
public class Instancia {

    private Clase tipo;
    private String id;

    public Instancia(Clase tipo, String id) {
        this.tipo = tipo;
        this.id = id;
    }

    public Clase getTipo() {
        return tipo;
    }

    public void setTipo(Clase tipo) {
        this.tipo = tipo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
