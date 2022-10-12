package model;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Calendar;

public class LeadTo {
    String firstName;
    String lastName;
    double annualRevenue;
    String phone;
    String street;
    String postalCode;
    String city;
    String country;
    XMLGregorianCalendar creationDate;
    String company;
    String state;

    public LeadTo(String fn,String ln,double ar,String ph,String st,String pc,String ci,String co,XMLGregorianCalendar da,String comp,String sta){
        firstName=fn;
        lastName=ln;
        annualRevenue=ar;
        phone=ph;
        street=st;
        postalCode=pc;
        city=ci;
        country=co;
        creationDate=da;
        company=comp;
        state=sta;
    }

    public String getFirstName(){
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public double getAnnualRevenue() {
        return annualRevenue;
    }

    public String getStreet() {
        return street;
    }

    public XMLGregorianCalendar getCreationDate() {
        return creationDate;
    }

    public String getCity() {
        return city;
    }

    public String getCompany() {
        return company;
    }

    public String getCountry() {
        return country;
    }

    public String getState() {
        return state;
    }

    public String toString(){
        String res="";
        res+="[name: "+firstName+" "+lastName+", ";
        res+="phone: "+phone+", ";
        res+="postalCode: "+postalCode+", ";
        res+="revenue: "+annualRevenue+", ";
        res+="street: "+street+", ";
        res+="city: "+city+", ";
        res+="country: "+country+", ";
        res+="state: "+state+", ";
        res+="company: "+company+", ";
        res+="date créa: "+ creationDate.toString()+"]";
        return res;
    }
}
