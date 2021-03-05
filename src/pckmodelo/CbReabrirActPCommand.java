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

public class CbReabrirActPCommand extends Command {
    private Conexion con;
    private String query;
    private BaseDatos receptor;
    private javax.swing.JComboBox combo1;
    
    public CbReabrirActPCommand(Conexion con,BaseDatos receptor,String query,javax.swing.JComboBox combo)
    {
           this.receptor=receptor;
           this.con=con;
           this.query=query;
           combo1=combo;
     
    }
    @Override public void execute()
    {
        con.abrirConexion();
        receptor.selectQuery(con, query);
        try{
            while(receptor.dataSet.next()){
                combo1.addItem(receptor.dataSet.getString("orden")+ ") " + receptor.dataSet.getString("Descripcion") + " _"+ receptor.dataSet.getString("Id_subcategorias"));
            }
            receptor.dataSet.close();
        }
        catch(Exception ex){}
        con.cerrarConexion();
    }
}
