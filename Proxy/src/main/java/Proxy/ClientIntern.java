package Proxy;

import Proxy.Proxy;
import model.LeadTo;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;
import Utils.Utils;

import static java.lang.System.out;

public class ClientIntern implements Proxy {

    private String callSoapService(String soapRequest) {
        try {
            String url = "http://localhost:8080/ws";
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type","text/xml; charset=utf-8");
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(soapRequest);
            wr.flush();
            wr.close();
            String responseStatus = con.getResponseMessage();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            con.disconnect();

            String finalvalue= response.toString();
            //out.println(finalvalue);

            return finalvalue;
        }
        catch (Exception e) {
            return e.getMessage();
        }
    }

    @Override
    public ArrayList<LeadTo> findLeads(Double min, Double max, String state) {
        String xml="<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"\n" +
                "                  xmlns:gs=\"http://spring.io/guides/gs-producing-web-service\">\n" +
                "    <soapenv:Header/>\n" +
                "    <soapenv:Body>\n" +
                "        <gs:findLeadsRequest>\n" +
                "            <gs:state>"+state+"</gs:state>\n" +
                "            <gs:minRevenue>"+min+"</gs:minRevenue>\n" +
                "            <gs:maxRevenue>"+max+"</gs:maxRevenue>\n" +
                "        </gs:findLeadsRequest>\n" +
                "    </soapenv:Body>\n" +
                "</soapenv:Envelope>";
        String response=callSoapService(xml);
        ArrayList<LeadTo> res=getListLead(response);
        return res;
    }

    @Override
    public ArrayList<LeadTo> findLeadsByDate(Date start, Date end) {

        String xml="<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"\n" +
                "                  xmlns:gs=\"http://spring.io/guides/gs-producing-web-service\">\n" +
                "    <soapenv:Header/>\n" +
                "    <soapenv:Body>\n" +
                "        <gs:findLeadsByDateRequest>\n" +
                "            <gs:startDate>"+Utils.getStringDate(start)+"</gs:startDate>\n" +
                "            <gs:endDate>"+Utils.getStringDate(end)+"</gs:endDate>\n" +
                "        </gs:findLeadsByDateRequest>\n" +
                "    </soapenv:Body>\n" +
                "</soapenv:Envelope>";
        String response=callSoapService(xml);
        ArrayList<LeadTo> res=getListLead(response);

        return res;
    }

    private static ArrayList<LeadTo> getListLead(String response) {
        ArrayList<LeadTo> res=new ArrayList<>();
        DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();
        try{
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            DocumentBuilder db= dbf.newDocumentBuilder();
            InputSource is=new InputSource((new StringReader(response)));
            Document doc=db.parse(is);

            doc.getDocumentElement().normalize();
            NodeList list=doc.getElementsByTagName("ns2:leads");
            for(int i=0;i< list.getLength();i++){
                Node node=list.item(i);
                if(node.getNodeType()==Node.ELEMENT_NODE){
                    Element elementLead=(Element) node;
                    String firstName=elementLead.getElementsByTagName("ns2:firstName").item(0).getTextContent();
                    String lastName=elementLead.getElementsByTagName("ns2:lastName").item(0).getTextContent();
                    double revenue= Double.valueOf(elementLead.getElementsByTagName("ns2:annualRevenue").item(0).getTextContent());
                    String phone=elementLead.getElementsByTagName("ns2:phone").item(0).getTextContent();
                    String street=elementLead.getElementsByTagName("ns2:street").item(0).getTextContent();
                    String postalCode=elementLead.getElementsByTagName("ns2:postalCode").item(0).getTextContent();
                    String city=elementLead.getElementsByTagName("ns2:city").item(0).getTextContent();
                    String country=elementLead.getElementsByTagName("ns2:country").item(0).getTextContent();
                    String dateCrea=elementLead.getElementsByTagName("ns2:creationDate").item(0).getTextContent();
                    String company=elementLead.getElementsByTagName("ns2:company").item(0).getTextContent();
                    String state=elementLead.getElementsByTagName("ns2:state").item(0).getTextContent();

                    String []splitDate=dateCrea.split("-");
                    XMLGregorianCalendar date= DatatypeFactory.newDefaultInstance().newXMLGregorianCalendarDate(Integer.valueOf(splitDate[0]),Integer.valueOf(splitDate[1]),Integer.valueOf(splitDate[2].substring(0,splitDate[2].length()-1)), TimeZone.SHORT);;

                    LeadTo lead=new LeadTo(firstName,lastName,revenue,phone,street,postalCode,city,country,date,company,state);
                    res.add(lead);
                }
            }
        }catch (ParserConfigurationException | SAXException | IOException e){
            e.printStackTrace();
        }
        return res;
    }
}
