package Proxy;

import model.LeadTo;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.ArrayList;

public class FabricClient implements Proxy {

    ArrayList<Proxy> proxyList=new ArrayList<>();

    public FabricClient(){
        ClientSalesForce salesClient=new ClientSalesForce();
        ClientIntern internClient=new ClientIntern();

        proxyList.add(salesClient);
        proxyList.add(internClient);
    }
    @Override
    public ArrayList<LeadTo> findLeads(double min, double max, String state) {
        ArrayList<LeadTo>res=new ArrayList<>();
        for(Proxy client:proxyList){
            res.addAll(client.findLeads(min,max,state));
        }
        return res;
    }

    @Override
    public ArrayList<LeadTo> findLeadsByDate(XMLGregorianCalendar start, XMLGregorianCalendar end) {
        ArrayList<LeadTo>res=new ArrayList<>();
        for(Proxy client:proxyList){
            res.addAll(client.findLeadsByDate(start,end));
        }
        return res;
    }
}
