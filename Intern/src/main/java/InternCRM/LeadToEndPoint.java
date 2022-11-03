package InternCRM;

import io.spring.guides.gs_producing_web_service.FindLeadsByDateRequest;
import io.spring.guides.gs_producing_web_service.FindLeadsRequest;
import io.spring.guides.gs_producing_web_service.FindLeadsResponse;
import io.spring.guides.gs_producing_web_service.LeadTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;

@Endpoint
public class LeadToEndPoint {
    private static final String NAMESPACE_URI = "http://spring.io/guides/gs-producing-web-service";
    private LeadsToRepository leadsRepo;

    @Autowired
    public LeadToEndPoint(LeadsToRepository repo){
        this.leadsRepo=repo;
    }

    @PayloadRoot(namespace = NAMESPACE_URI,localPart = "findLeadsRequest")
    @ResponsePayload
    public FindLeadsResponse findsLeads(@RequestPayload FindLeadsRequest request){
        FindLeadsResponse response=new FindLeadsResponse();
        List<LeadTo> leads=leadsRepo.findLeads(request.getMinRevenue(),request.getMaxRevenue(),request.getState());
        for(LeadTo lead:leads) {
            response.getLeads().add(lead);
        }
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI,localPart = "findLeadsByDateRequest")
    @ResponsePayload
    public FindLeadsResponse findsLeadsByDate(@RequestPayload FindLeadsByDateRequest request){
        FindLeadsResponse response=new FindLeadsResponse();
        List<LeadTo> leads=leadsRepo.findLeadsByDate(request.getStartDate(),request.getEndDate());
        for(LeadTo lead:leads) {
            response.getLeads().add(lead);
        }
        return response;
    }

}
