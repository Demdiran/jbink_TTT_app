package nl.sogyo.ttt_app.domain;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
public class TestTournamentPlanning{

    @Test
    public void TestPlanningTwoPlayers(){
        Player player1 = new Player();
        player1.setRating(800);
        Player player2 = new Player();
        player2.setRating(1000);
        TournamentPlanning tournamentPlanning = new TournamentPlanning(player1, player2);
        TournamentRound tournamentRound = tournamentPlanning.getCurrentRound();
        Match nextMatch = tournamentRound.getMatches().get(0);
        assertEquals(nextMatch.getPlayers()[0], player2);
        assertEquals(nextMatch.getPlayers()[1], player1);
    }

    @Test
    public void TestPlanRoundWithNonPowerOf2PlayerAmount(){
        Player[] players = new Player[6];
        players[0] = new Player(100);
        players[3] = new Player(150);
        players[2] = new Player(200);
        players[5] = new Player(250);
        players[1] = new Player(300);
        players[4] = new Player(350);

        TournamentPlanning tournamentPlanning = new TournamentPlanning(players);
        TournamentRound tournamentRound = tournamentPlanning.getCurrentRound();
        Match match1 = tournamentRound.getMatches().get(0);
        Match match2 = tournamentRound.getMatches().get(1);
        Match match3 = tournamentRound.getMatches().get(2);
        Match match4 = tournamentRound.getMatches().get(3);

        assertEquals(players[4], match1.getPlayer1());
        assertEquals(null, match1.getPlayer2());
        assertEquals(players[2], match2.getPlayer1());
        assertEquals(players[3], match2.getPlayer2());
        assertEquals(players[1], match3.getPlayer1());
        assertEquals(null, match3.getPlayer2());
        assertEquals(players[5], match4.getPlayer1());
        assertEquals(players[0], match4.getPlayer2());
    }

    @Test
    public void TestGetSecondRound(){
        Player[] players = new Player[6];
        players[0] = new Player(100);
        players[3] = new Player(150);
        players[2] = new Player(200);
        players[5] = new Player(250);
        players[1] = new Player(300);
        players[4] = new Player(350);
        TournamentPlanning tournamentPlanning = new TournamentPlanning(players);
        TournamentRound tournamentRound = tournamentPlanning.getCurrentRound();
        for(Match match : tournamentRound.getMatches()){
            match.addGame(new Game(11, 9));
            match.addGame(new Game(11, 9));
            match.addGame(new Game(11, 9));
        }
        assert(tournamentRound.isFinished());
        TournamentRound tournamentRound2 = tournamentPlanning.planNextRound();
        Match match1 = tournamentRound2.getMatches().get(0);
        Match match2 = tournamentRound2.getMatches().get(1);
        assertEquals(2, tournamentRound2.getMatches().size());
        assertEquals(players[4], match1.getPlayer1(), "" + match1.getPlayer1().getRating());
        assertEquals(players[2], match1.getPlayer2(), "" + match1.getPlayer2().getRating());
        assertEquals(players[1], match2.getPlayer1(), "" + match2.getPlayer1().getRating());
        assertEquals(players[5], match2.getPlayer2(), "" + match2.getPlayer2().getRating());
    }

    @Test
    public void TestGetThirdRound(){
        Player[] players = new Player[8];
        for(int i = 0; i < players.length; i++){
            players[i] = new Player(800 - i);
            players[i].setName("" + i);
        }
        TournamentPlanning tournamentPlanning = new TournamentPlanning(players);
        TournamentRound tournamentRound1 = tournamentPlanning.getCurrentRound();
        for(Match match : tournamentRound1.getMatches()){
            match.addGame(new Game(11,9));
            match.addGame(new Game(11,9));
            match.addGame(new Game(11,9));
        }
        TournamentRound tournamentRound2 = tournamentPlanning.planNextRound();
        for(Match match : tournamentRound2.getMatches()){
            match.addGame(new Game(11,0));
            match.addGame(new Game(11,0));
            match.addGame(new Game(11,0));
        }
        TournamentRound tournamentRound3 = tournamentPlanning.planNextRound();
        Match finals = tournamentRound3.getMatches().get(0);
        assertEquals(1, tournamentRound3.getMatches().size());
        assertEquals("0", finals.getPlayer1().getName());
        assertEquals("1", finals.getPlayer2().getName());
    }

    @Test
    public void TestGetSecondRoundWithOtherWinPattern(){
        Player[] players = new Player[8];
        for(int i = 0; i < players.length; i++){
            players[i] = new Player(800 - i);
            players[i].setName("" + i);
        }
        TournamentPlanning tournamentPlanning = new TournamentPlanning(players);
        TournamentRound tournamentRound1 = tournamentPlanning.getCurrentRound();
        for(Match match : tournamentRound1.getMatches()){
            match.addGame(new Game(11,9));
            match.addGame(new Game(11,9));
            match.addGame(new Game(11,9));
        }
        Match player0lost = tournamentRound1.getMatches().get(0);
        for(Game game : player0lost.getGames()){
            game.setScore(9, 11);
        }
        TournamentRound tournamentRound2 = tournamentPlanning.planNextRound();
        Match match1 = tournamentRound2.getMatches().get(0);
        Match match2 = tournamentRound2.getMatches().get(1);
        assertEquals("7", match1.getPlayer1().getName());
        assertEquals("3", match1.getPlayer2().getName());
        assertEquals("1", match2.getPlayer1().getName());
        assertEquals("2", match2.getPlayer2().getName());
    }
    @Test
    public void TestGetSecondRoundWithOtherWinPatternAndByePlayers(){
        Player[] players = new Player[6];
        for(int i = 0; i < players.length; i++){
            players[i] = new Player(800 - i);
            players[i].setName("" + i);
        }
        TournamentPlanning tournamentPlanning = new TournamentPlanning(players);
        TournamentRound tournamentRound1 = tournamentPlanning.getCurrentRound();
        Match match34 = tournamentRound1.getMatches().get(1);
        Match match25 = tournamentRound1.getMatches().get(3);
        for(int i = 0; i < 3; i++){
            match34.addGame(new Game(4,11));
            match25.addGame(new Game(4,11));
        }
        assert(tournamentRound1.isFinished());
        TournamentRound tournamentRound2 = tournamentPlanning.planNextRound();
        Match match04 = tournamentRound2.getMatches().get(0);
        Match match15 = tournamentRound2.getMatches().get(1);
        assertEquals(players[0], match04.getPlayer1(), match04.getPlayer1().getName());
        assertEquals(players[4], match04.getPlayer2(), match04.getPlayer2().getName());
        assertEquals(players[1], match15.getPlayer1(), match15.getPlayer1().getName());
        assertEquals(players[5], match15.getPlayer2(), match15.getPlayer2().getName());
    }

    @Test
    public void TestGetSecondRoundWithoutFirstCompleted(){
        Player[] players = new Player[6];
        players[0] = new Player(100);
        players[3] = new Player(150);
        players[2] = new Player(200);
        players[5] = new Player(250);
        players[1] = new Player(300);
        players[4] = new Player(350);
        TournamentPlanning tournamentPlanning = new TournamentPlanning(players);
        try {
            tournamentPlanning.planNextRound();
            fail("Requesting the next round while the current round has not finished should result in an exception.");            
        } catch (CurrentRoundNotFinished e){}
    }

    @Test
    public void TestPlanRoundWithoutPlayers(){
        try{
            new TournamentPlanning();
            fail("Tournamentplanning should throw an exception if no players are signed up.");
        }
        catch(NoPlayersSignedUpForTournament e){}
    }

    @Test
    public void TestGetNextMatch(){
        Player[] players = new Player[8];
        for(int i = 0; i < players.length; i++){
            players[i] = new Player(800 - i);
            players[i].setName("" + i);
        }
        TournamentPlanning tournamentPlanning = new TournamentPlanning(players);
        TournamentRound tournamentRound1 = tournamentPlanning.getCurrentRound();
        Match match1 = tournamentPlanning.getNextMatch();
        assertEquals(tournamentRound1.getMatches().get(0), match1);
    }

    @Test
    public void TestGetNextMatchRoundFinished(){
        Player[] players = new Player[8];
        for(int i = 0; i < players.length; i++){
            players[i] = new Player(800 - i);
            players[i].setName("" + i);
        }
        TournamentPlanning tournamentPlanning = new TournamentPlanning(players);
        TournamentRound tournamentRound1 = tournamentPlanning.getCurrentRound();
        for(Match match : tournamentRound1.getMatches()){
            match.addGame(new Game(11,9));
            match.addGame(new Game(11,9));
            match.addGame(new Game(11,9));
        }
        try{
            tournamentPlanning.getNextMatch();
            fail("If the current round has finished, there is no next match and an exception occurs");
        }
        catch(RoundHasFinished e){}
    }

    @Test
    public void TestGetWinner(){
        Player[] players = new Player[8];
        for(int i = 0; i < players.length; i++){
            players[i] = new Player(800 - i);
            players[i].setName("" + i);
        }
        TournamentPlanning tournamentPlanning = new TournamentPlanning(players);
        TournamentRound tournamentRound1 = tournamentPlanning.getCurrentRound();
        for(Match match : tournamentRound1.getMatches()){
            match.addGame(new Game(11,9));
            match.addGame(new Game(11,9));
            match.addGame(new Game(11,9));
        }
        TournamentRound tournamentRound2 = tournamentPlanning.planNextRound();
        for(Match match : tournamentRound2.getMatches()){
            match.addGame(new Game(11,0));
            match.addGame(new Game(11,0));
            match.addGame(new Game(11,0));
        }
        TournamentRound tournamentRound3 = tournamentPlanning.planNextRound();
        Match finals = tournamentRound3.getMatches().get(0);
        finals.addGame(new Game(3, 11));
        finals.addGame(new Game(3, 11));
        finals.addGame(new Game(3, 11));
        Player winner = tournamentPlanning.getTournamentWinner();
        assertEquals(players[1], winner, winner.getName());
    }

    @Test
    public void TestGetWinnerTournamentNotFinished(){
        Player[] players = new Player[8];
        for(int i = 0; i < players.length; i++){
            players[i] = new Player(800 - i);
            players[i].setName("" + i);
        }
        TournamentPlanning tournamentPlanning = new TournamentPlanning(players);
        TournamentRound tournamentRound1 = tournamentPlanning.getCurrentRound();
        for(Match match : tournamentRound1.getMatches()){
            match.addGame(new Game(11,9));
            match.addGame(new Game(11,9));
            match.addGame(new Game(11,9));
        }
        TournamentRound tournamentRound2 = tournamentPlanning.planNextRound();
        for(Match match : tournamentRound2.getMatches()){
            match.addGame(new Game(11,0));
            match.addGame(new Game(11,0));
            match.addGame(new Game(11,0));
        }
        Player winner = tournamentPlanning.getTournamentWinner();
        assertEquals(null, winner);
    }

    @Test
    public void TestHasWinner(){
        Player[] players = new Player[8];
        for(int i = 0; i < players.length; i++){
            players[i] = new Player(800 - i);
            players[i].setName("" + i);
        }
        TournamentPlanning tournamentPlanning = new TournamentPlanning(players);
        TournamentRound tournamentRound1 = tournamentPlanning.getCurrentRound();
        for(Match match : tournamentRound1.getMatches()){
            match.addGame(new Game(11,9));
            match.addGame(new Game(11,9));
            match.addGame(new Game(11,9));
        }
        TournamentRound tournamentRound2 = tournamentPlanning.planNextRound();
        for(Match match : tournamentRound2.getMatches()){
            match.addGame(new Game(11,0));
            match.addGame(new Game(11,0));
            match.addGame(new Game(11,0));
        }
        TournamentRound tournamentRound3 = tournamentPlanning.planNextRound();
        Match finals = tournamentRound3.getMatches().get(0);
        finals.addGame(new Game(3, 11));
        finals.addGame(new Game(3, 11));
        finals.addGame(new Game(3, 11));

        boolean haswinner = tournamentPlanning.hasWinner();
        assert(haswinner);

    }
}