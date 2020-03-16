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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Table;

import nl.sogyo.ttt_app.api.IStorable;
@Entity(name = "match")
@Table(name = "matches")
public class Match implements IStorable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "match_ID")
    private int match_ID;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
        name = "match_player_join",
        joinColumns = {@JoinColumn(name = "match_ID")},
        inverseJoinColumns = {@JoinColumn(name = "player_ID")}
    )
    @OrderColumn
    private Player[] players = new Player[2];

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "match_game_join")
    private List<Game> games = new ArrayList<Game>();

    public Match(){
    }

    public Match(Player player1, Player player2){
        this.players[0] = player1;
        this.players[1] = player2;
    }

    public int getID(){
        return match_ID;
    }

    public Player getPlayer1() {
        return players[0];
    }
    public void setPlayer1(Player player1){
        players[0] = player1;
    }
    public Player getPlayer2() {
        return players[1];
    }
    public void setPlayer2(Player player2){
        players[1] = player2;
    }
    public List<Game> getGames() {
        return games;
    }

    public void gamePlayed(Game game){
        games.add(game);
    }

    public void removeGame(int index){
        games.remove(index);
    }

    public Player getWinner(int gamesToWin){
        int scorePlayer1 = 0;
        int scorePlayer2 = 0;
        for(Game game : games){
            if(game.getWinner().equals("player1"))
                scorePlayer1++;
            if(game.getWinner().equals("player2"))
                scorePlayer2++;
        }
        if(scorePlayer1 > gamesToWin)
            return players[0];
        if(scorePlayer2 > gamesToWin)
            return players[1];
        return null;
    }
}