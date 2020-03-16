package nl.sogyo.ttt_app.api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

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
        Player testplayer = databaseAccessor.getOrCreatePlayerWithOutsideID("testID");
        assert(testplayer != null);
    }

    @Test
    public void TestObtainexistingPlayerFromOutsideKey(){
        Player testplayer = databaseAccessor.getOrCreatePlayerWithOutsideID("testID");
        hibernateSession.clear();
        Player testplayer2 = databaseAccessor.getOrCreatePlayerWithOutsideID("testID");
        assertEquals(testplayer.getID(), testplayer2.getID());
    }

    @Test
    public void TestGetAllPlayersFromDB(){
        Player player1 = databaseAccessor.getOrCreatePlayerWithOutsideID("player1");
        Player player2 = databaseAccessor.getOrCreatePlayerWithOutsideID("player2");
        Player player3 = databaseAccessor.getOrCreatePlayerWithOutsideID("player3");
        List<Player> players = databaseAccessor.getAllFromDB(Player.class);
        assertEquals(player1.getID(), players.get(0).getID());
        assertEquals(player2.getID(), players.get(1).getID());
        assertEquals(player3.getID(), players.get(2).getID());
    }

    @Test
    public void TestGetAllTournamentsFromDB(){
        Tournament tournament1 = new Tournament();
        tournament1.setName("tournament1");
        databaseAccessor.createInDB(tournament1);
        Tournament tournament2 = new Tournament();
        tournament2.setName("tournament2");
        databaseAccessor.createInDB(tournament2);
        Tournament tournament3 = new Tournament();
        tournament3.setName("tournament3");
        databaseAccessor.createInDB(tournament3);
        List<Tournament> tournaments = databaseAccessor.getAllFromDB(Tournament.class);
        assertEquals(tournament1.getID(), tournaments.get(0).getID());
        assertEquals(tournament2.getID(), tournaments.get(1).getID());
        assertEquals(tournament3.getID(), tournaments.get(2).getID());
    }

    @Test
    public void TestSignUpForTournament(){
        Tournament tournament1 = new Tournament();
        tournament1.setName("tournament1");
        tournament1.setMaxParticipants(50);
        tournament1.setClub("club1");
        Player player1 = new Player();
        player1.setName("player1");
        player1.setRating(800);
        databaseAccessor.createInDB(tournament1);
        databaseAccessor.createInDB(player1);

        player1.signUpForTournament(tournament1);
        databaseAccessor.updateInDB(player1);

        hibernateSession.clear();

        assert(player1.getTournaments().contains(tournament1));
        assert(tournament1.getParticipants().contains(player1));
    }

    @Test
    public void TestCreateMatch(){
        Player player1 = new Player();
        player1.setName("player1");
        Player player2 = new Player();
        player2.setName("player2");
        Match match = new Match(player1, player2);
        databaseAccessor.createInDB(match);

        hibernateSession.clear();

        Match match2 = (Match) databaseAccessor.getFromDB(match.getID(), Match.class);

        assertEquals(match.getPlayer1().getID(), match2.getPlayer1().getID());
    }

    @Test
    public void TestAddGameToMatch(){
        Match match = new Match();

        Game game1 = new Game(11, 12);
        match.gamePlayed(game1);
        databaseAccessor.createInDB(match);

        hibernateSession.clear();

        Match match2 = (Match) databaseAccessor.getFromDB(match.getID(), Match.class);
        Game game2 = (Game) databaseAccessor.getFromDB(game1.getID(), Game.class);
        assertEquals(match2.getGames().get(0).getID(), game2.getID());
    }

    @Test
    public void TestRemoveGameFromMatchRemovesFromDB(){
        Match match = new Match();
        Game game1 = new Game(11, 12);
        match.gamePlayed(game1);
        databaseAccessor.createInDB(match);

        hibernateSession.clear();

        Match match2 = (Match) databaseAccessor.getFromDB(match.getID(), Match.class);
        match2.removeGame(0);
        databaseAccessor.updateInDB(match2);

        hibernateSession.clear();

        Game game2 = (Game) databaseAccessor.getFromDB(game1.getID(), Game.class);

        assert(game2 == null);
    }
}