package Proxy;

import model.LeadTo;

import java.util.ArrayList;
import java.util.Date;

import static java.lang.System.out;

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
        out.println("requete sur le revenu "+min+" : "+max+" : "+state);
        ArrayList<LeadTo>res=new ArrayList<>();
        for(Proxy client:proxyList){
            out.println("appel à un proxy");
            res.addAll(client.findLeads(min,max,state));
        }
        out.println(res);
        return res;
    }

    @Override
    public ArrayList<LeadTo> findLeadsByDate(Date start, Date end) {
        out.println("requete sur la date "+start.toString()+" : "+end.toString());
        ArrayList<LeadTo>res=new ArrayList<>();
        for(Proxy client:proxyList){
            res.addAll(client.findLeadsByDate(start,end));
        }
        out.println(res);
        return res;
    }
}
