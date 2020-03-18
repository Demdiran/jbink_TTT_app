package nl.sogyo.ttt_app.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import nl.sogyo.ttt_app.api.IStorable;

@Entity(name = "adress")
@Table(name = "adresses")
public class Adress implements IStorable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "adress_ID")
    private int adress_ID;
    @Column(name = "street")
    private String street;
    @Column(name = "city")
    private String city;
    @Column(name = "streetnumber")
    private int streetnumber;
    @Column(name = "postalcode")
    private String postalcode;

    public int getAdress_ID() {
        return adress_ID;
    }
    public String getCity() {
        return city;
    }
    public int getStreetnumber() {
        return streetnumber;
    }
    public String getPostalcode() {
        return postalcode;
    }
    public String getStreet() {
        return street;
    }
    public void setAdress_ID(int adress_ID) {
        this.adress_ID = adress_ID;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public void setStreetnumber(int streetnumber) {
        this.streetnumber = streetnumber;
    }
    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }
    public void setStreet(String street) {
        this.street = street;
    }
}