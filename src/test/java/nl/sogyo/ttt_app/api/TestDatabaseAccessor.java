package nl.sogyo.ttt_app.api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import nl.sogyo.ttt_app.domain.*;
public class TestDatabaseAccessor{

    @Test
    public void TestGetPlayerByID(){
        DatabaseAccessor databaseAccessor = new DatabaseAccessor();
        Player testplayer = databaseAccessor.getPlayer(1);
        assertEquals("testplayer", testplayer.getName());
        assertEquals(800, testplayer.getRating());
    }
}