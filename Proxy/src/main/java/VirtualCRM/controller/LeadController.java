package VirtualCRM.controller;

import VirtualCRM.service.LeadService;
import model.LeadTo;
import model.RequestDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import static java.lang.System.out;

@RestController
public class LeadController {
    @Autowired
    private LeadService leadService;

    @GetMapping("/findLeads")
    public ResponseEntity<List<LeadTo>> findLeads(@RequestParam(value = "min",defaultValue = "0") double min,
                                                  @RequestParam(value = "max",defaultValue = "0")double max,
                                                  @RequestParam(value = "state",defaultValue = "")String state){
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type","application/json");
        responseHeaders.set("Access-Control-Allow-Origin","*");

        return ResponseEntity.ok().headers(responseHeaders).body(leadService.findLeads(min,max,state));
    }

    @GetMapping("/findLeadsByDate")
    public ResponseEntity<List<LeadTo>> findLeadsByDate(@RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date start,
                                                        @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date end){
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type","application/xml");
        responseHeaders.set("Access-Control-Allow-Origin","*");
        XMLGregorianCalendar xmlstart = null;
        XMLGregorianCalendar xmlend = null;

        out.println(start);
        out.println(end);
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(start);
        GregorianCalendar gc2 = new GregorianCalendar();
        gc2.setTime(end);

        try {
            xmlstart = DatatypeFactory.newInstance()
                    .newXMLGregorianCalendar();
            xmlend = DatatypeFactory.newInstance()
                    .newXMLGregorianCalendar();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        out.println(xmlstart);
        out.println(xmlend);

        return ResponseEntity.ok().headers(responseHeaders).body(leadService.findLeadsByDate(xmlstart,xmlend));
    }


}
