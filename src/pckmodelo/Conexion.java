/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @version 1.0
 * @created 15-ago-2017 01:50:54 p.m.
 */

/**
 *
 * @author RenéSS,OliviaGFD, JavierSS
 */

package pckmodelo;
import java.sql.*;


public abstract class Conexion {
    protected String bd_psp;
   // protected String " ";
    protected String user;
    protected boolean abierto;
    public Connection con;
    
     public Conexion(String bd,String user,String password){
        this.bd_psp=bd;
        //this.password=password;
        this.user=user;
        abierto=false;
        con=null;
    }
    public abstract void abrirConexion();
    public abstract void cerrarConexion();    
    public boolean getAbierto(){
        return abierto;
    }

}
