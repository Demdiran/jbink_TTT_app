package nl.sogyo.ttt_app.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

public class TestGame{

    @Test
    public void TestGetWinnerPlayer1(){
        Game game = new Game(11, 9);
        assertEquals("player1", game.getWinner());
    }
    @Test
    public void TestGetWinnerPlayer1_2(){
        Game game = new Game(12, 10);
        assertEquals("player1", game.getWinner());
    }
    @Test
    public void TestGetWinnerPlayer1_3(){
        Game game = new Game(12, 0);
        assertEquals("player1", game.getWinner());
    }
    @Test
    public void TestGetWinnerPlayer2(){
        Game game = new Game(9, 11);
        assertEquals("player2", game.getWinner());
    }
    @Test
    public void TestGetWinnerNone(){
        Game game = new Game(9, 10);
        assertEquals("neither", game.getWinner());
    }
}