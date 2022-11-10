package VirtualCRM.Service;

import VirtualCRM.Repository.LeadRepository;
import model.LeadTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class LeadService {

    @Autowired
    private LeadRepository leadRepository;

    public List<LeadTo> findLeads(double min,double max,String state){
        return leadRepository.findLeads(min,max,state);
    }
    public List<LeadTo> findLeadsByDate(Date start, Date end){
        return leadRepository.findLeadsByDate(start, end);
    }
}
