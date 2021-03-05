/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pckmodelo;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * @version 1.0
 * @created 15-ago-2017 01:50:54 p.m.
 */

/**
 *
 * @author RenéSS,OliviaGFD, JavierSS
 */

public class cTiempo extends aTiempo{
    protected Calendar time= new GregorianCalendar();
    
    public cTiempo()
    {
        super();
        this.hora=time.get(Calendar.HOUR_OF_DAY);
        this.minuto=time.get(Calendar.MINUTE);
    }
    
    @Override public int calcular_min()
    {
        return (hora*60)+ minuto;
    }
 }
