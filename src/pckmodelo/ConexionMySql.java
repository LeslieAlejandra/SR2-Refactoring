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

public class ConexionMySql extends Conexion {
    public ConexionMySql(String bd_psp,String user,String password){
        super(bd_psp,user,password);
    }
    
    @Override public void abrirConexion(){
     try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + bd_psp + "?user=" + "root");
            abierto= true;
         }
        catch(SQLException ex){
            System.out.println("Error al abrir la conexión");
            System.out.println("SQLException: "+ ex.getMessage().toString());
            System.out.println("SQLErrorcode: "+ ex.getErrorCode());
            System.out.println("SQLState: "+ ex.getSQLState().toString());
        }
        catch(Exception ex1){}
    }
    @Override public void cerrarConexion(){
      try{
            con.close();
            abierto=false;
        }
        catch(SQLException ex){
            System.out.println("Error al cerrar la conexión");
            System.out.println("SQLException: "+ ex.getMessage().toString());
            System.out.println("SQLErrorcode: "+ ex.getErrorCode());
            System.out.println("SQLState: "+ ex.getSQLState().toString());
        }
    }
    
    @Override public boolean getAbierto(){
        return super.getAbierto();
    }

}
