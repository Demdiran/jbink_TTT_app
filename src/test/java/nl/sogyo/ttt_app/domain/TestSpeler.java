package nl.sogyo.ttt_app.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class TestSpeler{

    @Test
    public void TestSpelerHeeftRating(){
        Speler speler = new Speler();
        int rating = speler.getRating();
        assertEquals(0, rating);
    }

}