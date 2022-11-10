package Proxy;

import model.LeadTo;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.ArrayList;
import java.util.Date;


public interface Proxy {

    public ArrayList<LeadTo> findLeads(Double min, Double max, String state);

    public ArrayList<model.LeadTo> findLeadsByDate(Date start, Date end);
}
