/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 *
 * @author s124392
 */
public class DataAdapter {
    private static final DateFormat formatter = 
            new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
    
    public DataAdapter() {}
    
    /**
     * From a Date to a XMLGregorianCalendar.
     * @param date
     * @return
     * @throws DatatypeConfigurationException 
     */
    public XMLGregorianCalendar dateToGreg(Date date)
            throws DatatypeConfigurationException {
        GregorianCalendar c = new GregorianCalendar();
        c.setTime(date);
        return DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
    }
    
    /**
     * From a XMLGregorianCalendar to a Date.
     * @param xgc
     * @return 
     */
    public Date gregToDate(XMLGregorianCalendar xgc) {
        if(xgc == null) {
            return null;
        }
        return xgc.toGregorianCalendar().getTime();
    }
    
    /**
     * From a Date to a String.
     * @param date
     * @return 
     */
    public String dateToString(Date date) {
        return formatter.format(date);
    }
    
    /**
     * From a String to a Date.
     * @param string
     * @return
     * @throws ParseException 
     */
    public Date stringToDate(String string) throws ParseException {
        return formatter.parse(string);
    }
}
