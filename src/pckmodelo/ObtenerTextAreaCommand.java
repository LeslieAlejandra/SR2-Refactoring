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
public class ObtenerTextAreaCommand extends Command {
    private Conexion con;
    private String query;
    private BaseDatos bd;
    private javax.swing.JTextArea receptor;
    
     
    public ObtenerTextAreaCommand(Conexion con,BaseDatos bd,String query,javax.swing.JTextArea receptor)
    {
           this.bd=bd;
           this.con=con;
           this.query=query;
           this.receptor=receptor;
    }
    
    @Override public void execute()
    {
        con.abrirConexion();
        bd.selectQuery(con, query);
        try{
            while(bd.dataSet.next()){
                receptor.setText(bd.dataSet.getString(1));
            }
            bd.dataSet.close();
        }
        catch(Exception ex){}
        con.cerrarConexion();
    }
}
