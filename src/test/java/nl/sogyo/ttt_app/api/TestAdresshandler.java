package nl.sogyo.ttt_app.api;


import org.junit.jupiter.api.Test;

import LocationIq.ApiException;
import nl.sogyo.ttt_app.domain.Adress;


public class TestAdresshandler{
    // @Test
    // public void TestDistanceCalculation(){
    //     Adresshandler distanceCalculator = new Adresshandler();
    //     Adress adress1 = new Adress();
    //     adress1.setStreet("De Warande");
    //     adress1.setStreetnumber("18");
    //     adress1.setCity("Middelburg");
    //     adress1.setPostalcode("4333LD");
    //     Adress adress2 = new Adress();
    //     adress2.setStreet("budapestlaan");
    //     adress2.setStreetnumber("4");
    //     adress2.setPostalcode("3584CD");
    //     double distance = distanceCalculator.calculateDistance(adress1, adress2);
    //     System.out.println(distance);
    //     assert(Math.abs(124 - distance) < 1);
    // }

    @Test
    public void TestCheckadressTrue()throws ApiException{
        Adress adress = new Adress();
        adress.setCity("Driebergen");
        adress.setPostalcode("3972XZ");
        adress.setStreet("De Warande");
        adress.setStreetnumber("18");

        Adresshandler adresshandler = new Adresshandler();
        try {
            assert(adresshandler.checkAdressMatchesPostalcode(adress));            
        }
        catch (ApiException e) {
            System.err.println("Exception when calling SearchApi#search");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
    }

    @Test
    public void TestCheckadressFalse()throws ApiException{
        Adress adress = new Adress();
        adress.setCity("Driebergen");
        adress.setPostalcode("3972XY");
        adress.setStreet("De Warande");
        adress.setStreetnumber("18");

        Adresshandler adresshandler = new Adresshandler();
        try {
            assert(!adresshandler.checkAdressMatchesPostalcode(adress));            
        }
        catch (ApiException e) {
            System.err.println("Exception when calling SearchApi#search");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
    }
}