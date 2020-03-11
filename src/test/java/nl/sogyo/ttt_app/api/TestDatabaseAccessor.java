package nl.sogyo.ttt_app.api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import nl.sogyo.ttt_app.domain.*;
public class TestDatabaseAccessor{

    // @Test
    // public void TestGetPlayerByID(){
    //     DatabaseAccessor databaseAccessor = new DatabaseAccessor();
    //     Player testplayerin = new Player();
    //     testplayerin.setRating(800);
    //     testplayerin.setName("testplayer1");
    //     testplayerin.setID(1);
    //     databaseAccessor.mergePlayer(testplayerin);
    //     Player testplayer = databaseAccessor.getPlayer(1);
    //     assertEquals("testplayer1", testplayer.getName());
    //     assertEquals(800, testplayer.getRating());
    //     databaseAccessor.removePlayer(1);
    // }

    // @Test
    // public void TestManyToMany(){
    //     DatabaseAccessor databaseAccessor = new DatabaseAccessor();
    //     Player testplayer1 = new Player();
    //     testplayer1.setRating(800);
    //     testplayer1.setName("testplayer1");
    //     Player testplayer2 = new Player();
    //     testplayer2.setRating(1000);
    //     testplayer2.setName("testplayer2");
    //     Tournament testTournament1 = new Tournament();
    //     testTournament1.setName("testtournament1");
    //     Tournament testTournament2 = new Tournament();
    //     testTournament2.setName("testtournament2");

    //     testplayer1.signUpForTournament(testTournament1);
    //     testplayer1.signUpForTournament(testTournament2);
    //     testplayer2.signUpForTournament(testTournament1);

    //     databaseAccessor.persistPlayer(testplayer1);
    //     databaseAccessor.persistPlayer(testplayer2);
    // }
}