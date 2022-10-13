package Proxy;

import Proxy.Proxy;
import model.LeadTo;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
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
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TimeZone;

import static java.lang.System.out;

public class ClientSalesForce implements Proxy {
    private String key;

    public ClientSalesForce(){
        connect();
    }

    private void connect() {

        NameValuePair[] data={
                new BasicNameValuePair("grant_type","password"),
                new BasicNameValuePair("client_id","3MVG9t0sl2P.pByrs.jGfyp11wt7Fiy9Yn3u25AIkaXJpYI.p2QXil_ABYdhTo7DLO7M5IAG5CEXorcpihO8V"),
                new BasicNameValuePair("client_secret","A4C48893CC6CBD865B2DF2183D9760E266A40161225D0E1ADD0EB87BE73E25A7"),
                new BasicNameValuePair("username","antonin@project.com"),
                new BasicNameValuePair("password","SuperProject1")
        };
        HttpPost request = new HttpPost("https://login.salesforce.com/services/oauth2/token");

        request.addHeader("Content-Type", "application/x-www-form-urlencoded");
        try {
            request.setEntity(new UrlEncodedFormEntity(Arrays.asList(data)));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(request)) {

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result = EntityUtils.toString(entity);
                JSONObject jsonObject = null;

                jsonObject = (JSONObject) new JSONTokener(result).nextValue();
                key = jsonObject.getString("access_token");


            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        catch (JSONException jsonException) {
            jsonException.printStackTrace();
        }

        requete();
    }

    @Override
    public ArrayList<LeadTo> findLeads(double min, double max, String state) {
        ArrayList<LeadTo> res=new ArrayList<>();
        return res;
    }

    @Override
    public ArrayList<LeadTo> findLeadsByDate(XMLGregorianCalendar start, XMLGregorianCalendar end) {
        ArrayList<LeadTo> res=new ArrayList<>();
        return res;
    }

    public void requete(){
        String uri="https://archiproject-dev-ed.my.salesforce.com/services/data/v45.0//query?";
        //requete sur toutes les informations demander des leads
        uri+="q=Select+Id+,+FirstName+,+LastName+,+AnnualRevenue+,+Phone+,+Street+,+PostalCode+,+City+,+Country+,+Company+,+CreatedDate+,+State+From+Lead";
        HttpGet request = new HttpGet(uri);

        request.addHeader("Content-Type", "application/x-www-form-urlencoded");
        request.addHeader("Authorization","Bearer "+key);
        request.addHeader("Accept","application/xml");
        request.addHeader("X-PrettyPrint", "1");

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(request)) {

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result = EntityUtils.toString(entity);
                out.println(result);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
            NodeList list=doc.getElementsByTagName("records");
            for(int i=0;i< list.getLength();i++){
                Node node=list.item(i);
                if(node.getNodeType()==Node.ELEMENT_NODE){
                    Element elementLead=(Element) node;
                    String firstName,lastName,phone,revenueS,street = "",postalCode="",city="",country,dateCrea,company,state="";
                    firstName=elementLead.getElementsByTagName("FirstName").item(0).getTextContent();
                    lastName=elementLead.getElementsByTagName("LastName").item(0).getTextContent();

                    double revenue=0;
                    if(elementLead.getElementsByTagName("AnnualRevenue").getLength()>0){
                        revenueS=elementLead.getElementsByTagName("AnnualRevenue").item(0).getTextContent();
                        revenue= Double.valueOf(revenueS);
                    }
                    phone=elementLead.getElementsByTagName("Phone").item(0).getTextContent();
                    if(elementLead.getElementsByTagName("Street").getLength()>0){
                        street=elementLead.getElementsByTagName("Street").item(0).getTextContent();
                    }
                    if(elementLead.getElementsByTagName("PostalCode").getLength()>0){
                        postalCode=elementLead.getElementsByTagName("PostalCode").item(0).getTextContent();
                    }if(elementLead.getElementsByTagName("City").getLength()>0){
                        city=elementLead.getElementsByTagName("City").item(0).getTextContent();
                    }
                    country=elementLead.getElementsByTagName("Country").item(0).getTextContent();
                    dateCrea=elementLead.getElementsByTagName("CreatedDate").item(0).getTextContent();
                    company=elementLead.getElementsByTagName("Company").item(0).getTextContent();
                    if(elementLead.getElementsByTagName("State").getLength()>0){
                        state=elementLead.getElementsByTagName("State").item(0).getTextContent();
                    }
                    String []splitDate=dateCrea.split("T");
                    String []splitDate2=splitDate[0].split("-");
                    XMLGregorianCalendar date= DatatypeFactory.newDefaultInstance().newXMLGregorianCalendarDate(Integer.valueOf(splitDate2[0]),Integer.valueOf(splitDate2[1]),Integer.valueOf(splitDate2[2]), TimeZone.SHORT);;

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
