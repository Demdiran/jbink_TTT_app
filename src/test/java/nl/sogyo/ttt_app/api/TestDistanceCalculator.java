package nl.sogyo.ttt_app.api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.hibernate.Session;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import nl.sogyo.ttt_app.domain.*;

public class TestDistanceCalculator{
    @Test
    public void TestDistanceCalculation(){
        DistanceCalculator distanceCalculator = new DistanceCalculator();
        double distance = distanceCalculator.calculateDistance("De warande, 18", "budapestlaan 4");
        System.out.println(distance);
        assert(Math.abs(124200 - distance) < 10);
    }
}