package VirtualCRM.service;

import VirtualCRM.Repository.LeadRepository;
import model.LeadTo;
import model.RequestDate;
import model.RequestRevenue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.List;

@Service
public class LeadService {

    @Autowired
    private LeadRepository leadRepository;

    public List<LeadTo> findLeads(double min,double max,String state){
        return leadRepository.findLeads(min,max,state);
    }
    public List<LeadTo> findLeadsByDate(XMLGregorianCalendar start, XMLGregorianCalendar end){
        return leadRepository.findLeadsByDate(start, end);
    }
}
