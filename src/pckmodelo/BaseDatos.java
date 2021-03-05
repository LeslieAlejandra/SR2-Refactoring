/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pckmodelo;
import java.sql.*;
/**
 * @version 1.0
 * @created 15-ago-2017 01:50:54 p.m.
 */

/**
 *
 * @author RenéSS,OliviaGFD, JavierSS
 */
public abstract class BaseDatos {
    public ResultSet dataSet;
    protected String mensaje;
    public int filas;
    public BaseDatos()
    {
        dataSet=null;
        mensaje=new String("");
    }
    public abstract void executeQuery(Conexion con,String query);
    public abstract void selectQuery(Conexion con,String query);
    public abstract String getMensaje();
    public abstract int getId();
    public abstract void setId(int id);
}
