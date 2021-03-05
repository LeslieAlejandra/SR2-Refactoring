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
public class tblSubCategoriaCommand extends Command {

    private Conexion con;
    private String query;
    private BaseDatos receptor;
    private javax.swing.JTable tabla1;
    private int r;

    public tblSubCategoriaCommand(Conexion con, BaseDatos receptor, String query, javax.swing.JTable tabla, int rows) {
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
        try {
            model = new Modelo(r, 3);
            Object[] headers = new Object[3];
            setHeaders(headers);
            model.setColumnIdentifiers(headers);
            while (receptor.dataSet.next()) {
                tabla1.setModel(model);
                tabla1.setValueAt(receptor.dataSet.getString("Id_subcategorias"), i, 0);
                tabla1.setValueAt(receptor.dataSet.getString("Id_categorias"), i, 1);
                tabla1.setValueAt(receptor.dataSet.getString("Descripcion"), i, 2);
                i++;
            }
            receptor.dataSet.close();
        } catch (Exception ex) {
        }
        con.cerrarConexion();
    }

    private void setHeaders(Object[] headers) {
        headers[0] = "Id_Subcategoria";
        headers[1] = "Id_Categoria";
        headers[2] = "Descripcion";

    }

}
