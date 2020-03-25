package nl.sogyo.ttt_app.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import nl.sogyo.ttt_app.api.IStorable;
@Entity(name = "match")
@Table(name = "matches")
public class Match implements IStorable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "match_ID")
    private int match_ID;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "match_ID")
    private List<Game> games;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "matchPlayers_ID")
    private MatchPlayers players = new MatchPlayers();;
    public Match(){
        initialiseGames();
    }

    public Match(Player byePlayer){
        initialiseGames();
        this.players.setPlayer1(byePlayer);
    }

    public Match(Player player1, Player player2){
        initialiseGames();
        this.players.setPlayer1(player1);
        this.players.setPlayer2(player2);
    }

    private void initialiseGames(){
        this.games = new ArrayList<Game>();
        for(int i = 0; i < 5; i++){
            games.add(new Game());
        }
    }

    public int getID(){
        return match_ID;
    }

    public Player getPlayer1() {
        return players.getPlayer1();
    }
    public void setPlayer1(Player player1){
        players.setPlayer1(player1);
    }
    public Player getPlayer2() {
        return players.getPlayer2();
    }
    public void setPlayer2(Player player2){
        players.setPlayer2(player2);
    }
    public List<Game> getGames() {
        return games;
    }

    public void setGame(int pointsPlayer1, int pointsPlayer2, int gamenr){
        this.games.get(gamenr).setScore(pointsPlayer1, pointsPlayer2);
    }

    public boolean isFinished(){
        return getWinner() != null;
    }

    public Player getWinner(){
        if(players.getPlayer2() == null)
            return players.getPlayer1();
        int scorePlayer1 = 0;
        int scorePlayer2 = 0;
        for(Game game : games){
            if(game.getWinner().equals("player1"))
                scorePlayer1++;
            if(game.getWinner().equals("player2"))
                scorePlayer2++;
        }
        if(scorePlayer1 >= 3)
            return players.getPlayer1();
        if(scorePlayer2 >= 3)
            return players.getPlayer2();
        return null;
    }
}