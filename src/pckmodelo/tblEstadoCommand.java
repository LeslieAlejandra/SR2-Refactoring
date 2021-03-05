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
public class tblEstadoCommand extends Command {

    private Conexion con;
    private String query;
    private BaseDatos receptor;
    private javax.swing.JTable tabla1;
    private int r;

    public tblEstadoCommand(Conexion con, BaseDatos receptor, String query, javax.swing.JTable tabla, int rows) {
        this.receptor = receptor;
        this.con = con;
        this.query = query;
        tabla1 = tabla;
        this.r = rows;
    }

    @Override
    public void execute() {
        DefaultTableModel model;
        int i = 0;
        con.abrirConexion();
        receptor.selectQuery(con, query);
        //int f;
        try {
            model = new Modelo(r, 2);
            Object[] headers = new Object[2];
            setHeaders(headers);
            model.setColumnIdentifiers(headers);
            while (receptor.dataSet.next()) {
                tabla1.setModel(model);
                tabla1.setValueAt(receptor.dataSet.getString("Id_estado"), i, 0);
                tabla1.setValueAt(receptor.dataSet.getString("Descripcion"), i, 1);
                i++;
            }
            receptor.dataSet.close();
        } catch (Exception ex) {
        }
        //tabla1.setColumnSelectionInterval(0, 2);
        //f=tabla1.getSelectedRow();

        con.cerrarConexion();
    }

    private void setHeaders(Object[] headers) {
        headers[0] = "Id_Estado";
        headers[1] = "Descripcion";
    }
}
