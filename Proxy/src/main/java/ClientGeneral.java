import model.LeadTo;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.ArrayList;

public class ClientGeneral implements Proxy{

    ClientSalesForce salesClient;
    ClientIntern internClient;

    public ClientGeneral(){
        salesClient=new ClientSalesForce();
        internClient=new ClientIntern();
    }
    @Override
    public ArrayList<LeadTo> findLeads(double min, double max, String state) {
        ArrayList<LeadTo>res=new ArrayList<>();
        res.addAll(internClient.findLeads(min,max,state));
        res.addAll(salesClient.findLeads(min,max,state));
        return res;
    }

    @Override
    public ArrayList<LeadTo> findLeadsByDate(XMLGregorianCalendar start, XMLGregorianCalendar end) {
        ArrayList<LeadTo>res=new ArrayList<>();
        res.addAll(internClient.findLeadsByDate(start,end));
        res.addAll(salesClient.findLeadsByDate(start,end));
        return res;
    }
}
