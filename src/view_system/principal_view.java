package view_system;

import MetodoReduccionHerencia.FrameMetodoPlantilla;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

//import java.awt.GraphicsConfiguration;
//import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.io.File;
import MetodoReduccionHerencia.*;
import MetodoRefactorizarCalificador.FrameMetodoRefactorizarCalificador;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import javax.swing.JLabel;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class principal_view extends JFrame implements ActionListener 
{
	
	private static final long serialVersionUID = 1L;
	
	public JDesktopPane desktop;
        private JLabel label;
	private final JMenuBar menuBar;
	public JMenu menuArchivo;
        private FrameMetodoPlantilla framePlantilla;
        private FrameMetodoRefactorizarCalificador frameMRcalificadores;
        private FrameMetricaFHI frameFHI;
        private FrameMetricaFHIJ frameFHIJ;
        private FrameMetricaFHIAC frameFHIAC;
        private FrameMetricaFFC frameFFC;
        private FrameMetricaFMFAC frameFMFAC;
	private CommandPrincipalOriginales comando;
	private CommandPrincipalMetricaVDINO comandoVDINO;
	private CommandPrincipalMetricaCoherencia comandoCoherencia;
	private CommandPrincipalMetodoSeparacion comandoSeparacion;
	private CommandPrincipalComparacion comandoComparacion;
	private JMenuItem menuArchivoSeleccionarFuente;
	private JMenuItem menuArchivoCambiarSesion;
	private JMenuItem menuArchivoSalir;
	public  JMenu menuMetrica;
	private JMenuItem menuMetricaDINO;
	private JMenuItem menuMetricaCOF;
	private JMenuItem menuMetricaCoherencia;
        ////////////////////////////////////////
        private JMenuItem menuMetricaFHI;
        private JMenuItem menuMetricaFHIJ;
        private JMenuItem menuMetricaFHIAC;
        private JMenuItem menuMetricaFFC;
        private JMenuItem menuMetricaFMFAC;
        ////////////////////////////////////////
	public  JMenu menuMetodo;
	private JMenuItem menuMetodoSeparacion;
	private JMenuItem menuMetodoAdaptacion;
	private JMenuItem menuMetodoMediador;
        private JMenuItem menuMetodoPlantilla;
        private JMenuItem menuMetodoMRcalificadores;
	public  JMenu menuComparar;
	private JMenuItem menuCompararArchivos;
	public  JMenu menuUsuarios;
	private JMenuItem menuUsuariosUsuarios;
	private JMenuItem menuUsuariosAccesos;
	public  JMenu menuAyuda;
	private JMenuItem menuAyudaAcerca;
	
	public File[] archivosFuente;
	public File[] archivosOriginales;
	
	public FrameInterno frameMetricaVDINO;
	public FrameInterno frameMetricaCoherencia;
	public FrameInterno frameMetodoSeparacion;
	public FrameInterno frameComparacion;
	
	public File[] archivosFuentetes;

	private Object Antlr;

	public static void main(String[] args) 
        {
		// TODO Auto-generated method stub
		principal_view aplicacion=new principal_view();
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) 
        {
		// TODO Auto-generated method stub
		
		//Menu seleccionar archivo
		if (e.getSource().equals(menuArchivoSeleccionarFuente)==true){
			comando=new CommandPrincipalOriginales(this);
			comando.ejecutar();
		}
		if (archivosOriginales != null)
                {
			if (e.getSource().equals(menuMetricaDINO)==true && archivosOriginales != null){
				comandoVDINO=new CommandPrincipalMetricaVDINO(this);
				comandoVDINO.ejecutar();
				try 
                                {
					frameMetricaCoherencia.setClosed(true);
					frameMetodoSeparacion.setClosed(true);
					frameComparacion.setClosed(true);
				} catch (PropertyVetoException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
			if (e.getSource().equals(menuCompararArchivos)==true){
				comandoComparacion=new CommandPrincipalComparacion(this);
				comandoComparacion.ejecutar();
				try {
					frameMetricaCoherencia.setClosed(true);
					frameMetodoSeparacion.setClosed(true);
					frameMetricaVDINO.setClosed(true);
				} catch (PropertyVetoException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
			if (e.getSource().equals(menuMetricaCoherencia)==true && archivosOriginales != null){
				comandoCoherencia=new CommandPrincipalMetricaCoherencia(this);
				comandoCoherencia.ejecutar();
				try {
					frameMetricaVDINO.setClosed(true);
					frameMetodoSeparacion.setClosed(true);
					frameComparacion.setClosed(true);
				} catch (PropertyVetoException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
			if (e.getSource().equals(menuMetodoSeparacion)==true && archivosOriginales != null)
                        {
                            comandoSeparacion=new CommandPrincipalMetodoSeparacion(this);
                            comandoSeparacion.ejecutar();
                            try 
                            {
                                    frameMetricaVDINO.setClosed(true);
                                    frameMetricaCoherencia.setClosed(true);
                                    frameComparacion.setClosed(true);
                            } catch (PropertyVetoException e1) {
                                    // TODO Auto-generated catch block
                                    e1.printStackTrace();
                            }
			}
		}else {
			JOptionPane.showMessageDialog(null,"Se necesita seleccionar los archivos, para continuar","Error", JOptionPane.INFORMATION_MESSAGE);
		}
		//Menu Salir
                
                if (e.getSource().equals(menuMetodoPlantilla)==true && archivosOriginales != null)
                {
                    framePlantilla = new FrameMetodoPlantilla(archivosOriginales);
                    cerrarFrames();
                    framePlantilla.show();
                    desktop.add(framePlantilla);
                }
                
                if (e.getSource().equals(menuMetodoMRcalificadores)==true)
                {
                    try {
                        frameMRcalificadores = new FrameMetodoRefactorizarCalificador(archivosOriginales);
                    } catch (IOException ex) {
                        Logger.getLogger(principal_view.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    cerrarFrames();
                    frameMRcalificadores.show();
                    desktop.add(frameMRcalificadores);
                }
                
                if (e.getSource().equals(menuMetricaFHI)==true && archivosOriginales != null)
                {
                    cerrarFrames();
                    frameFHI = new FrameMetricaFHI(archivosOriginales);
                    frameFHI.show();
                    desktop.add(frameFHI);
                }
                
                if (e.getSource().equals(menuMetricaFHIJ)==true && archivosOriginales != null)
                {
                    cerrarFrames();
                    frameFHIJ = new FrameMetricaFHIJ(archivosOriginales);
                    frameFHIJ.show();
                    desktop.add(frameFHIJ);
                }
                
                if (e.getSource().equals(menuMetricaFHIAC)==true && archivosOriginales != null)
                {
                    cerrarFrames();
                    frameFHIAC = new FrameMetricaFHIAC(archivosOriginales);
                    frameFHIAC.show();
                    desktop.add(frameFHIAC);
                }
                
                if (e.getSource().equals(menuMetricaFFC)==true && archivosOriginales != null)
                {
                    cerrarFrames();
                    frameFFC = new FrameMetricaFFC(archivosOriginales);
                    frameFFC.show();
                    desktop.add(frameFFC);
                }
                
                if (e.getSource().equals(menuMetricaFMFAC)==true && archivosOriginales != null)
                {
                    cerrarFrames();
                    frameFMFAC = new FrameMetricaFMFAC(archivosOriginales);
                    frameFMFAC.show();
                    desktop.add(frameFMFAC);
                }
                
		if (e.getSource().equals(menuArchivoSalir)==true)
                {
			salir();
		}
	}
        
        private void cerrarFrames()
        {
            try 
            {
                if(frameFHI != null)
                {
                    frameFHI.dispose();
                }
                if(frameFHIJ != null)
                {
                    frameFHIJ.setVisible(false);
                    frameFHIJ.dispose();
                }
                if(frameFHIAC != null)
                {
                    frameFHIAC.setVisible(false);
                    frameFHIAC.dispose();
                }
                if(frameFFC != null)
                {
                    frameFFC.setVisible(false);
                    frameFFC.dispose();
                }
                if(frameFMFAC != null)
                {
                    frameFMFAC.setVisible(false);
                    frameFMFAC.dispose();
                }
                if(framePlantilla != null)
                {
                    framePlantilla.setVisible(false);
                    framePlantilla.dispose();
                }
                if(frameMRcalificadores != null)
                {
                    frameMRcalificadores.setVisible(false);
                    frameMRcalificadores.dispose();
                }
            } 
            catch (Exception e) 
            {
                System.out.println(e);
            }
        }
	
	public principal_view() 
        {
		archivosFuente=null;
		archivosOriginales=null;
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			//nada
		}
		
		setTitle("Métodos de Refactorización de Frameworks");
		setSize(800, 640);
		
		
		desktop = new javax.swing.JDesktopPane() 
                {
                    private  final Image IMG=new ImageIcon(getClass().getResource("/Imagenes/fondo3.jpg")).getImage();
                    public void paintChildren(Graphics g)
                    {
                        g.drawImage(IMG, 0, 0, getWidth(), getHeight(), this);
                        super.paintChildren(g);
                    }
                };
                
		menuBar=new JMenuBar();
		menuArchivo=new JMenu("Archivo");
		menuArchivoSeleccionarFuente=new JMenuItem("Seleccionar Archivos Originales");
		menuArchivoSeleccionarFuente.addActionListener(this);
		menuArchivoSalir=new JMenuItem("Salir");
		menuArchivoSalir.addActionListener(this);
		menuMetrica=new JMenu("Métricas");
		menuMetricaDINO=new JMenuItem("Calcular Métrica V-DINO");
		menuMetricaDINO.addActionListener(this);
		menuMetricaCoherencia=new JMenuItem("Calcular Métrica Coherencia");
		menuMetricaCoherencia.addActionListener(this);
                
                menuMetricaFHI = new JMenuItem("Calcular Métrica FHI");
                menuMetricaFHI.addActionListener(this);
                menuMetricaFHIJ = new JMenuItem("Calcular Métrica FHIJ");
                menuMetricaFHIJ.addActionListener(this);
                menuMetricaFHIAC = new JMenuItem("Calcular Métrica FHIAC");
                menuMetricaFHIAC.addActionListener(this);
                menuMetricaFFC = new JMenuItem("Calcular Métrica FFC");
                menuMetricaFFC.addActionListener(this);
                menuMetricaFMFAC = new JMenuItem("Calcular Métrica FMFAC");
                menuMetricaFMFAC.addActionListener(this);
                
		menuMetodo=new JMenu("Métodos de Refactorización");
		menuMetodoSeparacion=new JMenuItem("Método de Separación de Interfaces");
		menuMetodoSeparacion.addActionListener(this);
                
               menuMetodoPlantilla = new JMenuItem("Método de Reducción de Herencia de Implementación");
               menuMetodoPlantilla.addActionListener(this);
               
               menuMetodoMRcalificadores = new JMenuItem("Método de Refactorización de Calificadores de Alcance");
               menuMetodoMRcalificadores.addActionListener(this);
                
		menuComparar=new JMenu("Comparación");
		menuCompararArchivos=new JMenuItem("Comparación de Archivos");
		menuCompararArchivos.addActionListener(this);
		menuUsuarios=new JMenu("Usuarios");
		menuUsuariosUsuarios=new JMenuItem("Usuarios");
		menuUsuariosUsuarios.addActionListener(this);
		menuUsuariosAccesos=new JMenuItem("Tipos de Accesos");
		menuUsuariosAccesos.addActionListener(this);
		menuAyuda=new JMenu("Ayuda");
		menuAyudaAcerca=new JMenuItem("Acerca de...");
		menuAyudaAcerca.addActionListener(this);
		
		getContentPane().add(desktop,BorderLayout.CENTER);

		menuArchivo.add(menuArchivoSeleccionarFuente);
		menuArchivo.add(menuArchivoSalir);
		menuMetrica.add(menuMetricaDINO);
		menuMetrica.add(menuMetricaCoherencia);
                
                
                menuMetrica.add(menuMetricaFHI);
                menuMetrica.add(menuMetricaFHIJ);
                menuMetrica.add(menuMetricaFHIAC);
                menuMetrica.add(menuMetricaFFC);
                menuMetrica.add(menuMetricaFMFAC);
                
		menuMetodo.add(menuMetodoPlantilla);
                menuMetodo.add(menuMetodoMRcalificadores);
                
		menuMetodo.add(menuMetodoSeparacion);
		menuComparar.add(menuCompararArchivos);
		menuUsuarios.add(menuUsuariosUsuarios);
		menuUsuarios.add(menuUsuariosAccesos);
		menuAyuda.add(menuAyudaAcerca);
		menuBar.add(menuArchivo);
		menuBar.add(menuMetrica);
		menuBar.add(menuMetodo);
		menuBar.add(menuComparar);
		menuBar.add(menuUsuarios);
		menuBar.add(menuAyuda);
		setJMenuBar(menuBar);
		//solo el menu de métrica;
		
		setResizable(false);		
		//setLocation(0,0);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		setVisible(true);
		show();
		
		frameMetricaVDINO = FrameMetricaVDINO.instanciar(archivosOriginales);
		desktop.add(frameMetricaVDINO);
		frameMetricaCoherencia=FrameMetricaCoherencia.instanciar(archivosOriginales);
		desktop.add(frameMetricaCoherencia);
		frameMetodoSeparacion=FrameMetodoSeparacion.instanciar(archivosOriginales);
		desktop.add(frameMetodoSeparacion);
		frameComparacion=FrameComparacion.instanciar(archivosFuente,archivosOriginales);
		desktop.add(frameComparacion);
		
		archivosFuentetes = archivosOriginales;
	}
	
	/* void actualizar()
	 Debe ser invocado al abrir archivos. Cierra todas las pantallas y las vuelve a cargar con los cambios.
	 No regresa ni recibe nada. */
	public void actualizar()
        {
		try {
			frameMetricaVDINO.setClosed(true);
			frameMetricaCoherencia.setClosable(true);
			frameMetodoSeparacion.setClosable(true);
			frameComparacion.setClosable(true);
		} catch (PropertyVetoException e1) {
			return;
		}
		archivosFuentetes = archivosOriginales;
                
		frameMetricaVDINO = FrameMetricaVDINO.instanciar(archivosOriginales);
		desktop.add(frameMetricaVDINO);
		
	}
	
	private void salir(){
		System.exit(0);
	}
}
