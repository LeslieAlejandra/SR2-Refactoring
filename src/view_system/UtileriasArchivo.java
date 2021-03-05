package view_system;
/* Clase: UtileriasArchivo
 Autor: Manuel Vald�s Marrero
 Fecha: 19 Enero 2004
 Descripci�n: Permite abrir un archivo o varios archivos, tambi�n copiar archivos. */

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class UtileriasArchivo {
    /* static File[] abrirArchivos(String titulo, String[] extensiones, String descripcion, File[] archivoSeleccionado)
     Recibe el titulo del di�logo, un arreglo de cadenas con las extensiones de los archivos (sin el punto), una descripci�n de esos archivos, y un arreglo de archivos para seleccionar inicialmente, que puede ser nulo. 
     Regresa un arreglo con los archivos que fueron seleccionados, se debe validar si existen con la funci�n isFile(), ya que se pudo escribir un nombre incorrecto*/

    static ArrayList<File> listaArchivos = new ArrayList();
    static ArrayList<File> listaArchivosoCarpetas = new ArrayList();
    static File archivo[] = new File[0];

    public static File[] abrirArchivos(String titulo, String[] extensiones, String descripcion, File[] archivoSeleccionado) {

        JFileChooser abrirArch = new JFileChooser();
        FiltroArchivos filtro = new FiltroArchivos();

        for (int i = 0; i < extensiones.length; i++) {
            filtro.addExtension(extensiones[i]);
        }

        filtro.setDescription(descripcion);
        abrirArch.setMultiSelectionEnabled(true);
        abrirArch.setDialogTitle(titulo);
        abrirArch.setFileFilter(filtro);
        abrirArch.setMultiSelectionEnabled(true);
        abrirArch.setSelectedFiles(archivoSeleccionado);
        abrirArch.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        int valor = abrirArch.showOpenDialog(null);
        try 
        {
            if (valor == JFileChooser.APPROVE_OPTION) 
            {
                listaArchivosoCarpetas.addAll(Arrays.asList(abrirArch.getSelectedFiles()));
                
                for (File carpetaOdocumento : listaArchivosoCarpetas) 
                {
                    buscarArchivos(carpetaOdocumento);
                }
            }
            archivo = listaArchivos.toArray(archivo);
        } 
        catch (Exception e) 
        {
            System.out.println(e);
        }
        return archivo;
    }
    
    

    private static void buscarArchivos(File carpeta) {
        File[] archivos = null;
        if (carpeta.isDirectory()) 
        {
            archivos = carpeta.listFiles();
        } 
        else 
        {
            File[] aux = new File[1];
            aux[0] = carpeta;
            archivos = aux;
        }

        if (archivos != null || archivos.length > 0) 
        {
            for (File archivo : archivos) 
            {
                if (archivo.isDirectory()) 
                {
                    buscarArchivos(archivo);
                } 
                else 
                {
                    if (archivo.getPath().endsWith(".java")) 
                    {
                        listaArchivos.add(archivo);
                    }
                }
            }

        }

    }

    /* static File abrirArchivo(String titulo, String[] extensiones, String descripcion, File archivoSeleccionado)
     Recibe el titulo del di�logo, un arreglo de cadenas con las extensiones de los archivos (sin el punto), una descripci�n de esos archivos, y un archivo para seleccionar inicialmente, que puede ser nulo. 
     Regresa un tipo File con el archivo que fue seleccionado, se debe validar si existe con la funci�n isFile(), ya que se pudo escribir un nombre incorrecto*/
    
    public static File abrirArchivo(String titulo, String[] extensiones, String descripcion, File archivoSeleccionado) {
        File archivo = null;
        JFileChooser abrirArch = new JFileChooser();
        FiltroArchivos filtro = new FiltroArchivos();
        for (int i = 0; i < extensiones.length; i++) {
            filtro.addExtension(extensiones[i]);
        }
        filtro.setDescription(descripcion);
        abrirArch.setDialogTitle(titulo);
        abrirArch.setFileFilter(filtro);
        abrirArch.setSelectedFile(archivoSeleccionado);
//                abrirArch.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        int valor = abrirArch.showOpenDialog(null);
        if (valor == JFileChooser.APPROVE_OPTION) {
            archivo = abrirArch.getSelectedFile();
        }
        return archivo;
    }
    /* static boolean copiarArchivo(String origen, String destino)
     Recibe el nombre del archivo origen con la ruta completa, y el nombre del archivo destino donde se har� la copia, con todo y ruta. 
     Regresa un tipo File con el archivo destino, o null si ocurri� un error. Si ya exist�a un archivo con ese nombre se sobreescribir�. */

    public static File copiarArchivo(String origen, String destino) {
        int i;
        FileInputStream archOrigen = null;
        FileOutputStream archDestino = null;
        // Abrir archivo Origen
        try {
            archOrigen = new FileInputStream(origen);
        } catch (FileNotFoundException e) {
            try {
                archOrigen.close();
                archDestino.close();
            } catch (IOException e1) {
                return null;
            }
            return null;
        }
        // Abrir archivo Destino
        try {
            archDestino = new FileOutputStream(destino);
        } catch (FileNotFoundException e) {
            try {
                archOrigen.close();
                archDestino.close();
            } catch (IOException e1) {
                return null;
            }
            return null;
        }
        // Copia el archivo byte por byte
        try {
            do {
                i = archOrigen.read();
                if (i != -1) {
                    archDestino.write(i);
                }
            } while (i != -1);
        } catch (IOException e) {
            try {
                archOrigen.close();
                archDestino.close();
            } catch (IOException e1) {
                return null;
            }
            return null;
        }
        try {
            archOrigen.close();
            archDestino.close();
        } catch (IOException e1) {
            return null;
        }
        return new File(destino);
    }
}
