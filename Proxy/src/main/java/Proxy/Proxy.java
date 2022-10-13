package Proxy;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.ArrayList;


public interface Proxy {
    public ArrayList<model.LeadTo> findLeads(double min, double max, String state);
    public ArrayList<model.LeadTo> findLeadsByDate(XMLGregorianCalendar start, XMLGregorianCalendar end);
}
