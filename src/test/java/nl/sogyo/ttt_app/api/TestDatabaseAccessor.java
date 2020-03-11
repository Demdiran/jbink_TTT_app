package nl.sogyo.ttt_app.api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.hibernate.Session;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import nl.sogyo.ttt_app.domain.*;
public class TestDatabaseAccessor{
    Session hibernateSession;
    DatabaseAccessor databaseAccessor;

    @BeforeEach 
    public void databaseAccessorSetup(){
        hibernateSession = DatabaseAccessor.buildSessionFactory("hibernateTest.cfg.xml").openSession();
        databaseAccessor = new DatabaseAccessor(hibernateSession);
    }

    @AfterEach
    public void databaseAccessorTeardown(){
        if(hibernateSession.isOpen())
            hibernateSession.close();
    }

    @Test
    public void TestGetPlayerByID(){
        Player testplayerin = new Player();
        testplayerin.setRating(800);
        testplayerin.setName("testplayer1");
        databaseAccessor.persistPlayer(testplayerin);

        hibernateSession.clear();

        Player testplayer = databaseAccessor.getPlayer(testplayerin.getID());
        assertEquals("testplayer1", testplayer.getName());
        assertEquals(800, testplayer.getRating());
    }

    @Test
    public void TestManyToMany(){
        Player testplayer1 = new Player();
        testplayer1.setRating(800);
        testplayer1.setName("testplayer1");
        Player testplayer2 = new Player();
        testplayer2.setRating(1000);
        testplayer2.setName("testplayer2");
        Tournament testTournament1 = new Tournament();
        testTournament1.setName("testtournament1");
        Tournament testTournament2 = new Tournament();
        testTournament2.setName("testtournament2");

        testplayer1.signUpForTournament(testTournament1);
        testplayer1.signUpForTournament(testTournament2);
        testplayer2.signUpForTournament(testTournament1);

        databaseAccessor.persistPlayer(testplayer1);
        databaseAccessor.persistPlayer(testplayer2);

        hibernateSession.clear();

        Player testplayer1out = databaseAccessor.getPlayer(testplayer1.getID());
        Player testplayer2out = databaseAccessor.getPlayer(testplayer2.getID());
        Tournament testtTournament1out = databaseAccessor.getTournament(testTournament1.getTournamentID());
        Tournament testtTournament2out = databaseAccessor.getTournament(testTournament2.getTournamentID());

        assertEquals("testplayer1", testplayer1out.getName());
        assertEquals(800, testplayer1out.getRating());
        assertEquals("testplayer2", testplayer2out.getName());
        assertEquals(1000, testplayer2out.getRating());
        assert(testplayer1out.getTournaments().contains(testtTournament1out));
        assert(testplayer1out.getTournaments().contains(testtTournament2out));
        assert(testtTournament1out.getParticipants().contains(testplayer2out));
    
    }
}