import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;


import javax.xml.XMLConstants;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Scanner;

import static java.lang.System.out;

public class Main {
    public static boolean quit;

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        while(!quit){
            showMenu();
            boolean goodChoice=false;
            int choice=0;
            while(!goodChoice){
                String str = sc.nextLine();
                try{
                    choice=Integer.valueOf(str);
                    goodChoice=true;
                }catch (Exception e){
                    out.println("le choix n'est pas un nombre ");
                }
            }
            menuSelect(choice);
        }
    }

    public static void showMenu(){
        out.println("Menu: (Une fois votre choix écrit appuyer sur entrée)");
        out.println("0: requête sur le salaire annuel et l'Etat");
        out.println("1: requête sur les dates");
        out.println("2: quitter ");
    }

    public static void menuSelect(int select){
        switch(select){
            case 0:
                requestRevenue();
                break;
            case 1:
                requestDate();
                break;
            case 2:
                quit=true;
                break;
            default:out.println("votre choix n'existe pas");
        }
    }

    public static void requestRevenue(){
        boolean test=false;
        Scanner sc = new Scanner(System.in);
        out.println("choississez un revenue min ");
        double min=0;
        double max=0;
        String state="";
        while(!test){
            String str = sc.nextLine();
            try {
                min=Double.valueOf(str);
                test=true;
            }catch (Exception e){
                out.println("le min doit être un nombre");
            }
        }
        test=false;
        out.println("choississez un revenue max");
        while(!test){
            String str = sc.nextLine();
            try {
                max=Double.valueOf(str);
                test=true;
            }catch (Exception e){
                out.println("le max doit être un nombre");
            }
        }
        out.println("choississez un Etat, si vous ne pas mettre Etat ne mettez rien");
        state=sc.nextLine();
        String uri="http://localhost:9000/findLeads?min="+min+"&max="+max;
        if(state!=""){
            uri+="&state="+state;
        }
        requestWithURI(uri);
    }

    private static void requestDate() {
        String start="";
        String end="";
        boolean test=false;
        Scanner sc = new Scanner(System.in);
        out.println("donnez la date de début de la recherche au format yyyy-mm-dd");
        while(!test){
            String str = sc.nextLine();
            if (str.matches("\\d{4}-\\d{2}-\\d{2}")) {
                start=str;
                test=true;
            }
            else{
                out.println("le format de la date doit être yyyy-mm-dd");
            }
        }
        test=false;
        out.println("donnez la date de fin de la recherche au format yyyy-mm-dd");
        while(!test){
            String str = sc.nextLine();
            if (str.matches("\\d{4}-\\d{2}-\\d{2}")) {
                end=str;
                test=true;
            }
            else{
                out.println("le format de la date doit être yyyy-mm-dd");
            }
        }

        String uri="http://localhost:9000/findLeadsByDate?start="+start+"&end="+end;
        requestWithURI(uri);
    }

    public static void requestWithURI(String uri){

        HttpGet request = new HttpGet(uri);
        out.println("envoie de la requête attendez quelques instants");
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(request)) {

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result = EntityUtils.toString(entity);
                prettyPrint(result);
            }
        }catch(HttpHostConnectException http){
            out.println("la connexion avec l'API VirtualCRM n'a pas pu être faite ");
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void prettyPrint(String xmlString) {
        try {
            Source xmlInput = new StreamSource(new StringReader(xmlString));
            StringWriter stringWriter = new StringWriter();
            StreamResult xmlOutput = new StreamResult(stringWriter);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            transformerFactory.setAttribute("indent-number", 2);
            transformerFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
            transformerFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, "");
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(xmlInput, xmlOutput);
            out.println("affichege du résultat");
            out.println(xmlOutput.getWriter().toString());
        } catch (Exception e) {
            out.println("la réponse du serveur n'est pas un xml lisible");
        }
    }
}
