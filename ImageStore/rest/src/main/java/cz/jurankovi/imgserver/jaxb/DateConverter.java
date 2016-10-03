package cz.jurankovi.imgserver.jaxb;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.bind.DatatypeConverter;

public class DateConverter {
    public static Date parseDate(String s) {
        return DatatypeConverter.parseDate(s).getTime();
    }

    public static String printDate(Date date) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        return DatatypeConverter.printDate(cal);
    }
}
