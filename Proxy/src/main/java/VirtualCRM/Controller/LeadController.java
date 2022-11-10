package VirtualCRM.Controller;

import VirtualCRM.Service.LeadService;
import model.LeadTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
public class LeadController {
    @Autowired
    private LeadService leadService;

    @GetMapping("/findLeads")
    public ResponseEntity<List<LeadTo>> findLeads(@RequestParam(value = "min",defaultValue = "0") double min,
                                                  @RequestParam(value = "max",defaultValue = "0")double max,
                                                  @RequestParam(value = "state",defaultValue = "")String state){
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type","application/xml");

        return ResponseEntity.ok().headers(responseHeaders).body(leadService.findLeads(min,max,state));
    }

    @GetMapping("/findLeadsByDate")
    public ResponseEntity<List<LeadTo>> findLeadsByDate(@RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date start,
                                                        @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date end){
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type","application/xml");

        return ResponseEntity.ok().headers(responseHeaders).body(leadService.findLeadsByDate(start,end));
    }


}
