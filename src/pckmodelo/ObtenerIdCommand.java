/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pckmodelo;
import pckmodelo.*;

/**
 * @version 1.0
 * @created 15-ago-2017 01:50:54 p.m.
 */

/**
 *
 * @author RenéSS,OliviaGFD, JavierSS
 */
public class ObtenerIdCommand extends Command {
    private Conexion con;
    private String query;
    private BaseDatos receptor;
     
    public ObtenerIdCommand(Conexion con,BaseDatos receptor,String query)
    {
           this.receptor=receptor;
           this.con=con;
           this.query=query;
    }
    
    @Override public void execute()
    {
        con.abrirConexion();
        receptor.selectQuery(con, query);
        try{
            while(receptor.dataSet.next()){
                receptor.setId(receptor.dataSet.getInt(1));
            }
            receptor.dataSet.close();
        }
        catch(Exception ex){}
        con.cerrarConexion();
    }
}
