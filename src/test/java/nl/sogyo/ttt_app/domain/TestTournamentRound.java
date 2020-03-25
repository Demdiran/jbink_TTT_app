package nl.sogyo.ttt_app.domain;


import org.junit.jupiter.api.Test;
public class TestTournamentRound{

    @Test
    public void TestGetNextMatch(){
        Player player1 = new Player(800);
        Player player2 = new Player(1000);
        TournamentRound tournamentRound = new TournamentRound();
        tournamentRound.planMatch(player1, player2);
        Match match = tournamentRound.getMatches().get(0);
        assert(match.getPlayer1().equals(player1));
        assert(match.getPlayer2().equals(player2));
    }

    @Test
    public void TestIsFinishedTrue(){
        TournamentRound tournamentRound = new TournamentRound();
        Player player1 = new Player(800);
        Player player2 = new Player(1000);
        tournamentRound.planMatch(player1, player2);
        Match match = tournamentRound.getMatches().get(0);
        match.addGame(new Game(11, 9));
        match.addGame(new Game(11, 9));
        match.addGame(new Game(11, 9));
        assert(tournamentRound.isFinished());
    }

    @Test
    public void TestIsFinishedFalse(){
        TournamentRound tournamentRound = new TournamentRound();
        Player player1 = new Player(800);
        Player player2 = new Player(1000);
        tournamentRound.planMatch(player1, player2);
        assert(!tournamentRound.isFinished());
    }

    @Test
    public void TestIsFinishedTrueMultiple(){
        Player player1 = new Player(800);
        Player player2 = new Player(1000);
        Player player3 = new Player(1200);
        Player player4 = new Player(1400);
        TournamentRound tournamentRound = new TournamentRound();
        tournamentRound.planMatch(player1, player2);
        tournamentRound.planMatch(player3, player4);
        Match match1 = tournamentRound.getMatches().get(0);
        Match match2 = tournamentRound.getMatches().get(1);
        
        match1.addGame(new Game(11,9));
        match1.addGame(new Game(11,9));
        match1.addGame(new Game(11,9));

        match2.addGame(new Game(11,9));
        match2.addGame(new Game(11,9));
        match2.addGame(new Game(11,9));

        assert(tournamentRound.isFinished());
    }
}