package nl.sogyo.ttt_app.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import nl.sogyo.ttt_app.api.IStorable;

@Entity(name = "game")
@Table(name = "games")
public class Game implements IStorable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_ID")
    private int game_ID;
    @Column(name = "points_player_1")
    private int pointsPlayer1;
    @Column(name = "points_player_2")
    private int pointsPlayer2;

    public Game(){
        
    }
    public Game(int pointsPlayer1, int pointsPlayer2){
        this.pointsPlayer1 = pointsPlayer1;
        this.pointsPlayer2 = pointsPlayer2;
    }

    public int getID(){
        return game_ID;
    }

    public int getPointsPlayer1() {
        return pointsPlayer1;
    }
    public int getPointsPlayer2() {
        return pointsPlayer2;
    }

    public void pointForPlayer1(){
        pointsPlayer1++;
    }
    public void pointForPlayer2(){
        pointsPlayer2++;
    }

    public String getWinner(){
        if(Math.abs(pointsPlayer1 - pointsPlayer2) < 2){
            return null;
        }
        else if(pointsPlayer1 > Math.max(pointsPlayer2, 11)){
            return "player1";
        }
        else if(pointsPlayer2 > Math.max(pointsPlayer1, 11)){
            return "player2";
        }
        else{
            return null;
        }
    }
}