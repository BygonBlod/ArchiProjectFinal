package Proxy;

import model.LeadTo;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.ArrayList;
import java.util.Date;

public class VirtualCRM implements Proxy {

    ArrayList<Proxy> proxyList=new ArrayList<>();
    private static VirtualCRM singleton;

    private VirtualCRM(){
        ClientSalesForce salesClient=new ClientSalesForce();
        ClientIntern internClient=new ClientIntern();

        proxyList.add(salesClient);
        proxyList.add(internClient);
    }

    public static VirtualCRM getInstance(){
        if(singleton==null){
            singleton=new VirtualCRM();
        }
        return singleton;
    }
    @Override
    public ArrayList<LeadTo> findLeads(Double min, Double max, String state) {
        ArrayList<LeadTo>res=new ArrayList<>();
        for(Proxy client:proxyList){
            res.addAll(client.findLeads(min,max,state));
        }
        return res;
    }

    @Override
    public ArrayList<LeadTo> findLeadsByDate(Date start, Date end) {
        ArrayList<LeadTo>res=new ArrayList<>();
        for(Proxy client:proxyList){
            res.addAll(client.findLeadsByDate(start,end));
        }
        return res;
    }
}
