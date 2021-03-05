/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pckmodelo;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @version 1.0
 * @created 15-ago-2017 01:50:54 p.m.
 */

/**
 *
 * @author RenéSS,OliviaGFD, JavierSS
 */

public class BaseDatosMySql extends BaseDatos 
{
    private int id=0;
    public BaseDatosMySql()
    {
        super();
    }
    
    @Override public void selectQuery(Conexion con,String query)
    {
        try
        {
            Statement st= con.con.createStatement();
            System.out.println("ejecutando query" + con + query);
            dataSet=st.executeQuery(query);
        }
        catch(SQLException ex)
        {
            mensaje=ex.getErrorCode()+ ex.getMessage();
        }
    }
    
    @Override public void executeQuery(Conexion con,String query)
    {
        try
        {
            Statement st= con.con.createStatement();
            st.execute(query);
            mensaje="Operación ejecutada con éxito";
        }
        catch(SQLException ex)
        {
            mensaje=ex.getErrorCode()+ ex.getMessage();
        }
    }
    
    @Override public String getMensaje()
    {
        return mensaje;
    }
    
    @Override public int getId()
    {
         return id;
    }
    
    @Override public void setId(int id)
    {
        this.id=id;
    }

}
