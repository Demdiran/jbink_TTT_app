package nl.sogyo.ttt_app.domain;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;


import org.junit.jupiter.api.Test;
public class TestTournamentPlanning{

    @Test
    public void TestPlanningTwoPlayers(){
        Player player1 = new Player();
        player1.setRating(800);
        Player player2 = new Player();
        player2.setRating(1000);
        TournamentPlanning tournamentPlanning = new TournamentPlanning(player1, player2);
        TournamentRound tournamentRound = tournamentPlanning.getRounds().get(0);
        Match nextMatch = tournamentRound.getMatches().get(0);
        assertEquals(nextMatch.getPlayer1(), player2);
        assertEquals(nextMatch.getPlayer2(), player1);
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
        TournamentRound tournamentRound = tournamentPlanning.getRounds().get(0);
        Match match1 = tournamentRound.getMatches().get(0);
        Match match2 = tournamentRound.getMatches().get(1);
        Match match3 = tournamentRound.getMatches().get(2);
        Match match4 = tournamentRound.getMatches().get(3);

        assertEquals(players[4], match1.getPlayer1());
        assertEquals(players[4], match1.getPlayer2());
        assertEquals(players[2], match2.getPlayer1());
        assertEquals(players[3], match2.getPlayer2());
        assertEquals(players[1], match3.getPlayer1());
        assertEquals(players[1], match3.getPlayer2());
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
        TournamentRound tournamentRound = tournamentPlanning.getRounds().get(0);
        for(Match match : tournamentRound.getMatches()){
            match.setGame(11, 9, 0);
            match.setGame(11, 9, 1);
            match.setGame(11, 9, 2);
        }
        assert(tournamentRound.isFinished());
        tournamentPlanning.updateRounds();
        TournamentRound tournamentRound2 = tournamentPlanning.getRounds().get(1);
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
        TournamentRound tournamentRound1 = tournamentPlanning.getRounds().get(0);
        for(Match match : tournamentRound1.getMatches()){
            match.setGame(11, 9, 0);
            match.setGame(11, 9, 1);
            match.setGame(11, 9, 2);
        }
        tournamentPlanning.updateRounds();
        TournamentRound tournamentRound2 = tournamentPlanning.getRounds().get(1);
        for(Match match : tournamentRound2.getMatches()){
            match.setGame(11, 0, 0);
            match.setGame(11, 0, 1);
            match.setGame(11, 0, 2);
        }
        tournamentPlanning.updateRounds();
        TournamentRound tournamentRound3 = tournamentPlanning.getRounds().get(2);
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
        TournamentRound tournamentRound1 = tournamentPlanning.getRounds().get(0);
        for(Match match : tournamentRound1.getMatches()){
            match.setGame(11, 9, 0);
            match.setGame(11, 9, 1);
            match.setGame(11, 9, 2);
        }
        Match player0lost = tournamentRound1.getMatches().get(0);
        for(Game game : player0lost.getGames()){
            game.setScore(9, 11);
        }
        tournamentPlanning.updateRounds();
        TournamentRound tournamentRound2 = tournamentPlanning.getRounds().get(1);
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
        TournamentRound tournamentRound1 = tournamentPlanning.getRounds().get(0);
        Match match34 = tournamentRound1.getMatches().get(1);
        Match match25 = tournamentRound1.getMatches().get(3);
        for(int i = 0; i < 3; i++){
            match34.setGame(4, 11, i);
            match25.setGame(4, 11, i);
        }
        assert(tournamentRound1.isFinished());
        tournamentPlanning.updateRounds();
        TournamentRound tournamentRound2 = tournamentPlanning.getRounds().get(1);
        Match match04 = tournamentRound2.getMatches().get(0);
        Match match15 = tournamentRound2.getMatches().get(1);
        assertEquals(players[0], match04.getPlayer1(), match04.getPlayer1().getName());
        assertEquals(players[4], match04.getPlayer2(), match04.getPlayer2().getName());
        assertEquals(players[1], match15.getPlayer1(), match15.getPlayer1().getName());
        assertEquals(players[5], match15.getPlayer2(), match15.getPlayer2().getName());
    }

    @Test
    public void TestPlanRoundWithoutPlayers(){
        try{
            new TournamentPlanning(new Player[] {});
            fail("Tournamentplanning should throw an exception if no players are signed up.");
        }
        catch(NotEnoughPlayersSignedUpForTournament e){}
    }

    @Test
    public void TestGetNextMatch(){
        Player[] players = new Player[8];
        for(int i = 0; i < players.length; i++){
            players[i] = new Player(800 - i);
            players[i].setName("" + i);
        }
        TournamentPlanning tournamentPlanning = new TournamentPlanning(players);
        TournamentRound tournamentRound1 = tournamentPlanning.getRounds().get(0);
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
        TournamentRound tournamentRound1 = tournamentPlanning.getRounds().get(0);
        for(Match match : tournamentRound1.getMatches()){
            match.setGame(11, 9, 0);
            match.setGame(11, 9, 1);
            match.setGame(11, 9, 2);
        }
        tournamentPlanning.updateRounds();

        Match match = tournamentPlanning.getNextMatch();
        assertEquals(players[0], match.getPlayer1(), ""+match.getPlayer1().getRating());
        assertEquals(players[3], match.getPlayer2(), ""+match.getPlayer2().getRating());
    }

    @Test
    public void TestGetWinner(){
        Player[] players = new Player[8];
        for(int i = 0; i < players.length; i++){
            players[i] = new Player(800 - i);
            players[i].setName("" + i);
        }
        TournamentPlanning tournamentPlanning = new TournamentPlanning(players);
        TournamentRound tournamentRound1 = tournamentPlanning.getRounds().get(0);
        for(Match match : tournamentRound1.getMatches()){
            match.setGame(11, 9, 0);
            match.setGame(11, 9, 1);
            match.setGame(11, 9, 2);
        }
        tournamentPlanning.updateRounds();
        TournamentRound tournamentRound2 = tournamentPlanning.getRounds().get(1);
        for(Match match : tournamentRound2.getMatches()){
            match.setGame(11, 0, 0);
            match.setGame(11, 0, 1);
            match.setGame(11, 0, 2);
        }
        tournamentPlanning.updateRounds();
        TournamentRound tournamentRound3 = tournamentPlanning.getRounds().get(2);
        Match finals = tournamentRound3.getMatches().get(0);
        finals.setGame(3, 11, 0);
        finals.setGame(3, 11, 1);
        finals.setGame(3, 11, 2);
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
        TournamentRound tournamentRound1 = tournamentPlanning.getRounds().get(0);
        for(Match match : tournamentRound1.getMatches()){
            match.setGame(11, 9, 0);
            match.setGame(11, 9, 1);
            match.setGame(11, 9, 2);
        }
        tournamentPlanning.updateRounds();
        TournamentRound tournamentRound2 = tournamentPlanning.getRounds().get(1);
        for(Match match : tournamentRound2.getMatches()){
            match.setGame(11, 0, 0);
            match.setGame(11, 0, 1);
            match.setGame(11, 0, 2);
        }
        Player winner = tournamentPlanning.getTournamentWinner();
        assertEquals(null, winner);
    }

    @Test
    public void TestHasWinnerTrue(){
        Player[] players = new Player[8];
        for(int i = 0; i < players.length; i++){
            players[i] = new Player(800 - i);
            players[i].setName("" + i);
        }
        TournamentPlanning tournamentPlanning = new TournamentPlanning(players);
        TournamentRound tournamentRound1 = tournamentPlanning.getRounds().get(0);
        for(Match match : tournamentRound1.getMatches()){
            match.setGame(11, 9, 0);
            match.setGame(11, 9, 1);
            match.setGame(11, 9, 2);
        }
        tournamentPlanning.updateRounds();
        TournamentRound tournamentRound2 = tournamentPlanning.getRounds().get(1);
        for(Match match : tournamentRound2.getMatches()){
            match.setGame(11, 0, 0);
            match.setGame(11, 0, 1);
            match.setGame(11, 0, 2);
        }
        tournamentPlanning.updateRounds();
        TournamentRound tournamentRound3 = tournamentPlanning.getRounds().get(2);
        Match finals = tournamentRound3.getMatches().get(0);
        finals.setGame(3, 11, 0);
        finals.setGame(3, 11, 1);
        finals.setGame(3, 11, 2);

        boolean haswinner = tournamentPlanning.hasWinner();
        assert(haswinner);
    }

    @Test
    public void TestHasWinnerFalse(){
        Player[] players = new Player[8];
        for(int i = 0; i < players.length; i++){
            players[i] = new Player(800 - i);
            players[i].setName("" + i);
        }
        TournamentPlanning tournamentPlanning = new TournamentPlanning(players);
        TournamentRound tournamentRound1 = tournamentPlanning.getRounds().get(0);
        for(Match match : tournamentRound1.getMatches()){
            match.setGame(11, 9, 0);
            match.setGame(11, 9, 1);
            match.setGame(11, 9, 2);
        }
        tournamentPlanning.updateRounds();
        TournamentRound tournamentRound2 = tournamentPlanning.getRounds().get(1);
        for(Match match : tournamentRound2.getMatches()){
            match.setGame(11, 0, 0);
            match.setGame(11, 0, 1);
            match.setGame(11, 0, 2);
        }

        boolean haswinner = tournamentPlanning.hasWinner();
        assert(!haswinner);
    }

    @Test
    public void TestMakePlanningOnePlayer(){
        try{
            Player player = new Player(600);
            player.setName("testplayer");
            new TournamentPlanning(player);
            fail("One player is not enough for a tournament.");
        }
        catch(NotEnoughPlayersSignedUpForTournament e){}
    }
}