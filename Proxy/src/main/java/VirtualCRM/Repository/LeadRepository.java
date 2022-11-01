package VirtualCRM.Repository;

import Proxy.VirtualCRM;
import model.LeadTo;
import model.RequestDate;
import model.RequestRevenue;
import org.springframework.stereotype.Repository;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.out;

@Repository
public class LeadRepository {

    public List<LeadTo> findLeads(double min,double max,String state){
        List<LeadTo> res=new ArrayList<>();
        res=VirtualCRM.getInstance().findLeads(min,max,state);
        return res;
    }

    public List<LeadTo> findLeadsByDate(XMLGregorianCalendar start,XMLGregorianCalendar end){
        List<LeadTo> res=new ArrayList<>();
        res=VirtualCRM.getInstance().findLeadsByDate(start,end);
        out.println("test leads by date");
        return res;
    }
}
