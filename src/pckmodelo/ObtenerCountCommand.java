/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pckmodelo;
import javax.swing.JOptionPane;
import pckmodelo.*;

/**
 * @version 1.0
 * @created 15-ago-2017 01:50:54 p.m.
 */

/**
 *
 * @author RenéSS,OliviaGFD, JavierSS
 */
public class ObtenerCountCommand extends Command {
    private Conexion con;
    private String query;
    private BaseDatos receptor;
    
     
    public ObtenerCountCommand(Conexion con,BaseDatos receptor,String query)
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
                receptor.filas=(new Integer(receptor.dataSet.getString(1)));
            }
            receptor.dataSet.close();
        }
        catch(Exception ex){}
        con.cerrarConexion();
    }
}
