package Proxy;

import model.LeadTo;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.ArrayList;


public interface Proxy {

    ArrayList<LeadTo> findLeads(Double min, Double max, String state);

    public ArrayList<model.LeadTo> findLeadsByDate(XMLGregorianCalendar start, XMLGregorianCalendar end);
}
