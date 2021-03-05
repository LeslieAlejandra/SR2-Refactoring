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

public class CbCategoriaCatCommand extends Command {
    private Conexion con;
    private String query;
    private BaseDatos receptor;
    private javax.swing.JComboBox combo1;
    private String campo;
    
    public CbCategoriaCatCommand(Conexion con,BaseDatos receptor,String query,javax.swing.JComboBox combo, String campo)
    {
           this.receptor=receptor;
           this.con=con;
           this.query=query;
           combo1=combo;
           this.campo=campo;
     
    }
    
    @Override public void execute()
    {
       
        con.abrirConexion();
        receptor.selectQuery(con, query);
        try{
            if (campo.equals("Id_categoras, Descripcion")){
            while(receptor.dataSet.next()){
                combo1.addItem(receptor.dataSet.getString("Id_categorias") + "   -" + receptor.dataSet.getString("Descripcion"));
            }
            }
             if (campo.equals("Id_categoria")){
            while(receptor.dataSet.next()){
                combo1.addItem(receptor.dataSet.getString("Id_categorias"));
            }    
            receptor.dataSet.close();
        }
        }
        catch(Exception ex){}
        con.cerrarConexion();
    }
}