package RSS;

import com.rometools.rome.feed.rss.Channel;
import com.rometools.rome.feed.rss.Description;
import com.rometools.rome.feed.rss.Item;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.feed.AbstractRssFeedView;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.XMLConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.lang.System.out;

@Component
public class RssFeedView extends AbstractRssFeedView {

    @Override
    protected void buildFeedMetadata(Map<String, Object> model,
                                     Channel feed, HttpServletRequest request) {
        feed.setTitle("cliant ajouter depuis moins de 24H");
        feed.setDescription("");
        feed.setLink("http:9001/rss");
    }

    @Override
    protected List<Item> buildFeedItems(Map<String, Object> model,
                                        HttpServletRequest request, HttpServletResponse response) {
        return getLeadTo();
    }

    private List<Item> getLeadTo(){
        List<Item> itemsList=new ArrayList<Item>() ;
        String result=requestDate();
        itemsList=parseToItem(result);
        return itemsList;
    }

    private List<Item> parseToItem(String result) {
        List<Item> itemsList=new ArrayList<Item>() ;
        DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();
        try{
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            DocumentBuilder db= dbf.newDocumentBuilder();
            InputSource is=new InputSource((new StringReader(result)));
            Document doc=db.parse(is);

            doc.getDocumentElement().normalize();
            NodeList list=doc.getElementsByTagName("item");
            for(int i=0;i< list.getLength();i++){
                Node node=list.item(i);
                if(node.getNodeType()==Node.ELEMENT_NODE){
                    Element elementLead=(Element) node;
                    String firstName,lastName,phone,revenueS,street = "",postalCode="",city="",country,dateCrea,company,state="";
                    firstName=elementLead.getElementsByTagName("firstName").item(0).getTextContent();
                    lastName=elementLead.getElementsByTagName("lastName").item(0).getTextContent();

                    double revenue=0;
                    if(elementLead.getElementsByTagName("annualRevenue").getLength()>0){
                        revenueS=elementLead.getElementsByTagName("annualRevenue").item(0).getTextContent();
                        revenue= Double.valueOf(revenueS);
                    }
                    phone=elementLead.getElementsByTagName("phone").item(0).getTextContent();
                    if(elementLead.getElementsByTagName("street").getLength()>0){
                        street=elementLead.getElementsByTagName("street").item(0).getTextContent();
                    }
                    if(elementLead.getElementsByTagName("postalCode").getLength()>0){
                        postalCode=elementLead.getElementsByTagName("postalCode").item(0).getTextContent();
                    }if(elementLead.getElementsByTagName("city").getLength()>0){
                        city=elementLead.getElementsByTagName("city").item(0).getTextContent();
                    }
                    country=elementLead.getElementsByTagName("country").item(0).getTextContent();
                    dateCrea=elementLead.getElementsByTagName("creationDate").item(0).getTextContent();
                    company=elementLead.getElementsByTagName("company").item(0).getTextContent();
                    if(elementLead.getElementsByTagName("state").getLength()>0){
                        state=elementLead.getElementsByTagName("state").item(0).getTextContent();
                    }
                    String []splitDate=dateCrea.split("T");
                    String []splitDate2=splitDate[0].split("-");
                    XMLGregorianCalendar date= DatatypeFactory.newDefaultInstance().newXMLGregorianCalendarDate(Integer.valueOf(splitDate2[0]),Integer.valueOf(splitDate2[1]),Integer.valueOf(splitDate2[2]), TimeZone.SHORT);;
                    Item item=new Item();
                    item.setTitle(firstName+" "+lastName+" - "+company);
                    Description desc=new Description();
                    String description="firstname:"+firstName+"-lastname:"+lastName+"-annualRevenue:"+revenue+"-phone:"+phone+"-street:"+street+"-postalCode:"+postalCode+"-city:"+city+"-country:"+country+"-creationDate:"+date.toString()+"-company:"+company+"-state:"+state;
                    desc.setValue(description);
                    item.setDescription(desc);
                    item.setPubDate(toDate(date));
                    itemsList.add(item);

                }
            }
        }catch (ParserConfigurationException | SAXException | IOException e){
            e.printStackTrace();
        }
        return itemsList;
    }

    private static String requestDate(){
        String res="";
        Date d=new Date();
        long nowms = d.getTime();
        long differencems = 1 * 24 * 60 * 60 * 1000;
        long thenms = nowms - differencems;
        Date d2 = new Date(thenms);
        SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd");
        String start=dt1.format(d2);
        String end=dt1.format(d);
        String uri="http://localhost:9000/findLeadsByDate?start="+start+"&end="+end;

        HttpGet request = new HttpGet(uri);

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(request)) {

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result = EntityUtils.toString(entity);
                out.println(result);
                res=result;

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
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
