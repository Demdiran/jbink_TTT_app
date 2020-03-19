package nl.sogyo.ttt_app.domain;

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
        assertEquals(match1.getPlayers()[0].getRating(), 250);
        assertEquals(match1.getPlayers()[1].getRating(), 100);
        assertEquals(match2.getPlayers()[0].getRating(), 200);
        assertEquals(match2.getPlayers()[1].getRating(), 150);
    }

}