package nl.sogyo.ttt_app.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

public class TestMatch{

    @Test
    public void TestGetWinnerPlayer1(){
        Match match = new Match(new Player(800), new Player(1000));
        match.setGame(11, 8, 0);
        match.setGame(11, 8, 1);
        match.setGame(11, 8, 2);
        assertEquals(match.getWinner().getRating(), 800);
    }

    @Test
    public void TestGetWinnerPlayer2(){
        Match match = new Match(new Player(800), new Player(1000));
        match.setGame(11, 8, 0);
        match.setGame(11, 8, 1);
        match.setGame(3, 11, 2);
        match.setGame(10, 12, 3);
        match.setGame(0, 11, 4);
        assertEquals(match.getWinner().getRating(), 1000);
    }

    @Test
    public void TestGetWinnerNone(){
        Match match = new Match(new Player(800), new Player(1000));
        match.setGame(11, 8, 0);
        match.setGame(11, 8, 1);
        match.setGame(3, 11, 2);
        match.setGame(10, 12, 3);
        assertEquals(match.getWinner(), null);

    }
}