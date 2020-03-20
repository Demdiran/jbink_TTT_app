package nl.sogyo.ttt_app.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

public class TestMatch{

    @Test
    public void TestGetWinnerPlayer1(){
        Match match = new Match(new Player(800), new Player(1000));
        match.addGame(new Game(11, 8));
        match.addGame(new Game(11, 8));
        match.addGame(new Game(11, 8));
        assertEquals(match.getWinner(3).getRating(), 800);
    }

    @Test
    public void TestGetWinnerPlayer2(){
        Match match = new Match(new Player(800), new Player(1000));
        match.addGame(new Game(11, 8));
        match.addGame(new Game(11, 8));
        match.addGame(new Game(3, 11));
        match.addGame(new Game(10, 12));
        match.addGame(new Game(0, 11));
        assertEquals(match.getWinner(3).getRating(), 1000);
    }

    @Test
    public void TestGetWinnerNone(){
        Match match = new Match(new Player(800), new Player(1000));
        match.addGame(new Game(11, 8));
        match.addGame(new Game(11, 8));
        match.addGame(new Game(3, 11));
        match.addGame(new Game(10, 12));
        assertEquals(match.getWinner(3), null);

    }
}