package nl.sogyo.ttt_app.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
public class TestTournamentRound{

    @Test
    public void TestGetNextMatch(){
        Player player1 = new Player();
        player1.setRating(800);
        Player player2 = new Player();
        player2.setRating(1000);
        TournamentRound tournamentRound = new TournamentRound();
        tournamentRound.planMatch(player1, player2);
        Match match = tournamentRound.getMatches().get(0);
        assert(match.getPlayers()[0].equals(player1));
        assert(match.getPlayers()[1].equals(player2));
    }

}