package InternCRM;

import io.spring.guides.gs_producing_web_service.LeadTo;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import static java.lang.System.out;


@Component
public class LeadsToRepository {
    private ArrayList<LeadTo> leads=new ArrayList<LeadTo>();

    @PostConstruct
    public  void initData(){

        LeadTo lead1=new LeadTo();
        lead1.setFirstName("Antonin");
        lead1.setLastName("Hunault");
        lead1.setAnnualRevenue(200000.0);
        lead1.setPhone("0780626515");
        lead1.setStreet("2 boulevard lavoisier");
        lead1.setPostalCode("49000");
        lead1.setCity("Angers");
        lead1.setCountry("France");
        XMLGregorianCalendar newDate= DatatypeFactory.newDefaultInstance().newXMLGregorianCalendarDate(2020,02,11, TimeZone.SHORT);
        lead1.setCreationDate(newDate);
        lead1.setCompany("la fuite");
        lead1.setState("ND");
        leads.add(lead1);

        //------
        lead1=new LeadTo();
        lead1.setFirstName("Salami");
        lead1.setLastName("Renault");
        lead1.setAnnualRevenue(20000.0);
        lead1.setPhone("078062655");
        lead1.setStreet("2 boulevard lavoisier");
        lead1.setPostalCode("49000");
        lead1.setCity("Angers");
        lead1.setCountry("France");
        newDate= DatatypeFactory.newDefaultInstance().newXMLGregorianCalendarDate(2020,03,11, TimeZone.SHORT);
        lead1.setCreationDate(newDate);
        lead1.setCompany("la fuite");
        lead1.setState("ND");
        leads.add(lead1);
    }

    public ArrayList<LeadTo> findLeads(double min, double max, String state){
        Assert.notNull(min,"min doit être un nombre");
        Assert.notNull(max,"max doit être un nombre");
        Assert.notNull(state,"state doit être un Etat");
        ArrayList<LeadTo> res=new ArrayList<>();
        for(LeadTo lead:leads){
            double rev=lead.getAnnualRevenue();
            if(rev>=min && rev<=max && state.equals(lead.getState())){
                res.add(lead);
            }
        }
        return res;
    }

    public ArrayList<LeadTo> findLeadsByDate(XMLGregorianCalendar start, XMLGregorianCalendar end){
        Assert.notNull(start,"min doit être une date");
        Assert.notNull(end,"max doit être une date");
        Date startDate=toDate(start);
        Date endDate=toDate(end);
        ArrayList<LeadTo> res=new ArrayList<>();
        if( startDate.compareTo(endDate)<=0);{
            for(LeadTo lead:leads){
                 Date date= toDate(lead.getCreationDate());
                 if(startDate.compareTo(date)<=0 && date.compareTo(endDate)<=0){
                    res.add(lead);
                }
            }
        }
        return res;
    }

    public static Date toDate(XMLGregorianCalendar calendar)
    {
        if (calendar == null) {
            return null;
        }
        return calendar.toGregorianCalendar().getTime();
    }


}
