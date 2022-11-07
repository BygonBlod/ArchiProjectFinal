package model;

import lombok.Data;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;

import static java.lang.System.out;

@Data
public class GeographicPointTo {
    double latitude;
    double longitude;

    public GeographicPointTo(String street, String city, String country, String postalCode) {
        latitude=0;
        longitude=0;
        search(street,city,country,postalCode);
    }

    private void search(String street,String city,String country,String postalCode){

        if(!street.equals("") || !city.equals("") || !country.equals("") || !postalCode.equals("") ) {
            String uri="https://nominatim.openstreetmap.org/search?adressdetails=1&";
            uri+="city="+city+"&";
            uri+="country="+country+"&";
            uri+="postalcode="+postalCode+"&";
            uri+="street="+change(street)+"&";
            uri+="format=json&limit=1";
            HttpGet request = new HttpGet(uri);

            try (CloseableHttpClient httpClient = HttpClients.createDefault();
                 CloseableHttpResponse response = httpClient.execute(request)) {

                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String result = EntityUtils.toString(entity);
                    result=result.substring(1,result.length()-1);
                    out.println("test:"+result);
                    if(!result.equals("")){
                        JSONObject jsonObject = null;

                        jsonObject = (JSONObject) new JSONTokener(result).nextValue();
                        longitude = Double.parseDouble(jsonObject.getString("lon"));
                        latitude = Double.parseDouble(jsonObject.getString("lat"));
                    }

                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    private String change(String street) {
        String s="";
        s=street.replaceAll(" ", "+");
        return s;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
