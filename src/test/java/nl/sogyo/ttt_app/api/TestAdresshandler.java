package nl.sogyo.ttt_app.api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import nl.sogyo.ttt_app.domain.Adress;


public class TestAdresshandler{
    @Test
    public void TestDistanceCalculation(){
        Adresshandler distanceCalculator = new Adresshandler();
        Adress adress1 = new Adress();
        adress1.setStreet("De Warande");
        adress1.setStreetnumber(18);
        adress1.setCity("Middelburg");
        adress1.setPostalcode("4333LD");
        Adress adress2 = new Adress();
        adress2.setStreet("budapestlaan");
        adress2.setStreetnumber(4);
        adress2.setPostalcode("3584CD");
        double distance = distanceCalculator.calculateDistance(adress1, adress2);
        System.out.println(distance);
        assert(Math.abs(124 - distance) < 1);
    }
}