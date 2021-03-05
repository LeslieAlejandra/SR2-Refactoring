package pckmodelo;

/**
 * @version 1.0
 * @created 15-ago-2017 01:50:54 p.m.
 */
/**
 *
 * @author RenéSS,OliviaGFD, JavierSS
 */
import java.util.HashMap;
import java.io.InputStream;

public class ReporteJasper extends aReporte {

    public ReporteJasper(Conexion con, String nombreRpt) {
        super(con, nombreRpt);
    }

    @Override
    public void mostrar() {
        try {
            String reportname = nombreRpt;
            InputStream fis = this.getClass().getClassLoader().getResourceAsStream(reportname);
            con.abrirConexion();
            /* JasperPrint print = JasperFillManager.fillReport(fis, new HashMap(), con.con);
            JasperViewer view = new JasperViewer(print, false);
            view.setVisible(true);*/

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (con.getAbierto()) {
                con.cerrarConexion();
            }
        }
    }
}
