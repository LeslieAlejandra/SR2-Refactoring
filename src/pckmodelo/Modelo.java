/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pckmodelo;

/**
 * @version 1.0
 * @created 15-ago-2017 01:50:54 p.m.
 */

/**
 *
 * @author RenéSS,OliviaGFD, JavierSS
 */

import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Itzel
 */
public class Modelo extends DefaultTableModel
{
    public Modelo(int r,int c)
    {
        super(r,c);
    }
    @Override public boolean isCellEditable (int row, int column)
   {
       // Aquí devolvemos true o false según queramos que una celda
       // identificada por fila,columna (row,column), sea o no editable
       return false;
   }
}
