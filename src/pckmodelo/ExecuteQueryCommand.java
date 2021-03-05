/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pckmodelo;

import pckmodelo.BaseDatos;
import pckmodelo.Conexion;

/**
 * @version 1.0
 * @created 15-ago-2017 01:50:54 p.m.
 */

/**
 *
 * @author RenéSS,OliviaGFD, JavierSS
 */
public class ExecuteQueryCommand extends Command{
    private Conexion con;
    private String query;
    private BaseDatos receptor;
    
    public ExecuteQueryCommand(Conexion con,BaseDatos receptor,String query)
    {
        this.receptor=receptor;
        this.con=con;
        this.query=query;
    }
    
    @Override public void execute()
    {
        con.abrirConexion();
        System.out.println(query);
        receptor.executeQuery(con, query);
        con.cerrarConexion();
    }
}
