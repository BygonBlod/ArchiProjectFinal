package VirtualCRM.Repository;

import Proxy.VirtualCRM;
import model.LeadTo;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Repository
public class LeadRepository {

    public List<LeadTo> findLeads(double min,double max,String state){
        List<LeadTo> res=new ArrayList<>();
        res=VirtualCRM.getInstance().findLeads(min,max,state);
        return res;
    }

    public List<LeadTo> findLeadsByDate(Date start, Date end){
        List<LeadTo> res=new ArrayList<>();
        res=VirtualCRM.getInstance().findLeadsByDate(start,end);
        return res;
    }
}
