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
        databaseAccessor.closeSession();
    }

    @Test
    public void TestGetPlayerByID(){
        Player testplayerin = new Player();
        testplayerin.setRating(800);
        testplayerin.setName("testplayer1");
        databaseAccessor.createInDB(testplayerin);

        hibernateSession.clear();

        Player testplayer = (Player) databaseAccessor.getFromDB(testplayerin.getID(), Player.class);
        assertEquals("testplayer1", testplayer.getName());
        assertEquals(800, testplayer.getRating());
    }

    @Test
    public void TestUpdatePlayer(){
        Player testplayerin = new Player();
        testplayerin.setName("testplayerin");
        testplayerin.setRating(0);
        databaseAccessor.createInDB(testplayerin);

        hibernateSession.clear();
        
        Player testplayerout = (Player) databaseAccessor.getFromDB(testplayerin.getID(), Player.class);

        testplayerout.setName("testplayerout");
        databaseAccessor.updateInDB(testplayerout);

        hibernateSession.clear();

        Player testplayerFinal = (Player) databaseAccessor.getFromDB(testplayerin.getID(), Player.class);
        assertEquals("testplayerout", testplayerFinal.getName());
    }

    @Test
    public void TestManyToManyOfPlayer_Tournament(){
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

        databaseAccessor.createInDB(testplayer1);
        databaseAccessor.createInDB(testplayer2);

        hibernateSession.clear();

        Player testplayer1out = (Player) databaseAccessor.getFromDB(testplayer1.getID(), Player.class);
        Player testplayer2out = (Player) databaseAccessor.getFromDB(testplayer2.getID(), Player.class);
        Tournament testtTournament1out = (Tournament) databaseAccessor.getFromDB(testTournament1.getID(), Tournament.class);
        Tournament testtTournament2out = (Tournament) databaseAccessor.getFromDB(testTournament2.getID(), Tournament.class);

        assertEquals("testplayer1", testplayer1out.getName());
        assertEquals(800, testplayer1out.getRating());
        assertEquals("testplayer2", testplayer2out.getName());
        assertEquals(1000, testplayer2out.getRating());
        assert(testplayer1out.getTournaments().contains(testtTournament1out));
        assert(testplayer1out.getTournaments().contains(testtTournament2out));
        assert(testtTournament1out.getParticipants().contains(testplayer2out));    
    }

    @Test
    public void TestCreateNewPlayer(){
        Player testplayer = databaseAccessor.getOrCreatePlayer("testID");
        assert(testplayer != null);
    }

    @Test
    public void TestObtainexistingPlayerFromOutsideKey(){
        Player testplayer = databaseAccessor.getOrCreatePlayer("testID");
        hibernateSession.clear();
        Player testplayer2 = databaseAccessor.getOrCreatePlayer("testID");
        assertEquals(testplayer.getID(), testplayer2.getID());
    }
}