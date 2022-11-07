package InternCRM;

import io.spring.guides.gs_producing_web_service.LeadTo;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import static java.lang.System.out;


@Component
public class LeadsToRepository {
    private ArrayList<LeadTo> leads=new ArrayList<LeadTo>();

    @PostConstruct
    public  void initData() throws DatatypeConfigurationException {

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

        //-------
        LeadTo lead3=new LeadTo();
        lead3.setFirstName("Johny");
        lead3.setLastName("Hyria");
        lead3.setAnnualRevenue(200000.0);
        lead3.setPhone("0780626515");
        lead3.setStreet("2 boulevard lavoisier");
        lead3.setPostalCode("49000");
        lead3.setCity("Angers");
        lead3.setCountry("France");
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
        XMLGregorianCalendar now = datatypeFactory.newXMLGregorianCalendar(gregorianCalendar);
        now.setTimezone(TimeZone.SHORT);
        lead3.setCreationDate(now);
        lead3.setCompany("la fuite");
        lead3.setState("ND");
        leads.add(lead3);
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
        long nowms = endDate.getTime();
        long differencems = 1 * 24 * 60 * 60 * 1000;
        long thenms = nowms + differencems;
        Date endDate2=new Date(thenms);
        ArrayList<LeadTo> res=new ArrayList<>();
        if( startDate.compareTo(endDate)<=0);{
            for(LeadTo lead:leads){
                 Date date= toDate(lead.getCreationDate());
                 out.println(startDate.toString()+" "+date.toString()+" "+endDate2.toString());
                 out.println(startDate.compareTo(date)+" - "+date.compareTo(endDate2));
                 if(startDate.compareTo(date)<=0 && date.compareTo(endDate2)<=0){
                    res.add(lead);
                }
            }
        }
        out.println("---------");
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
