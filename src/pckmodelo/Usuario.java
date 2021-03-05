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

public class Usuario{
    public static int id;
    public static String nombre;
    public static String perfil;

    public Usuario() {
        super();
    }

    public static String registrar(int opc) 
    {
        String cadena= new String();
        String fecha= new String();
        fecha= "DATE_FORMAT(NOW(),'%e/%m/%y %H:%i')";
        switch (opc) {
            case 1://hace el insert
                cadena= "insert into usuario(Nombre,Id_perfil,Fecha) values ('" + nombre + "','" + perfil + "'," + fecha + ")";
                break;
            case 2://hace un update
                cadena= "update usuario set Nombre='" + nombre + "',Id_perfil='" + perfil + "' ,Fecha= " + fecha + " where Id_usuario=" + id;
                break;
            case 3://hace un delete
                cadena= "delete from usuario where Id_usuario=" + id;
                break;
        }
        return cadena;
    }
    
    public static String getNombre()
    {
        return nombre;
    }
    
    public static String getPerfil()
    {
        return perfil;
    }
    
    public static void setNombre(String nombre1)
    {
        nombre=nombre1;
    }
    public static void setPerfil(String perfil1)
    {
        perfil=perfil1;
    }
    
}
