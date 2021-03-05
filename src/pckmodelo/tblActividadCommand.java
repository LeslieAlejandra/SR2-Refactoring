/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pckmodelo;
import javax.swing.table.*;
import pckmodelo.*;

/**
 * @version 1.0
 * @created 15-ago-2017 01:50:54 p.m.
 */

/**
 *
 * @author RenéSS,OliviaGFD, JavierSS
 */
public class tblActividadCommand extends Command {
    private Conexion con;
    private String query;
    private BaseDatos receptor;
    private javax.swing.JTable tabla1;
    private int r;
        
    public tblActividadCommand(Conexion con,BaseDatos receptor,String query,javax.swing.JTable tabla,int rows)
    {
           this.receptor=receptor;
           this.con=con;
           this.query=query;
           tabla1=tabla;
           this.r=rows;
    }
    
    @Override public void execute()
    {
        DefaultTableModel model; 
        int i=0;
        con.abrirConexion();
        receptor.selectQuery(con, query);
        try{
            model = new Modelo(r,5);
            Object[] headers= new Object [5];
            setHeaders(headers);
            model.setColumnIdentifiers(headers);
            while(receptor.dataSet.next()){
                tabla1.setModel(model);
                tabla1.setValueAt(receptor.dataSet.getString("Id_actividad"), i, 0);
                tabla1.setValueAt(receptor.dataSet.getString("Comentario"), i, 1);
                tabla1.setValueAt(receptor.dataSet.getString("FechaI"), i, 2);
                tabla1.setValueAt(receptor.dataSet.getString("FechaF"), i, 3);
                tabla1.setValueAt(receptor.dataSet.getString("Tiempo"), i, 4);
                i++;
            }
            receptor.dataSet.close();
          
        }
        catch(Exception ex){}
        con.cerrarConexion();
    }
    
    private void setHeaders(Object [] headers)
    {
            headers[0]="Actividad";
            headers[1]="Comentario";
            headers[2]="Fecha inicio";
            headers[3]="Fecha fin";
            headers[4]="Tiempo";
    }
}
