package nl.sogyo.ttt_app.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
class TestPlayer{

    @Test
    public void TestPlayerHasRating(){
        Player player = new Player();
        int rating = player.getRating();
        assertEquals(800, rating);
    }

}