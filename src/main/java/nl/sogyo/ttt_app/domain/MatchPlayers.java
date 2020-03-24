package nl.sogyo.ttt_app.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity(name = "MatchPlayers")
@Table(name = "MatchPlayers")
public class MatchPlayers{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "matchPlayers_ID")
    private int matchPlayerID;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "player1_ID")
    private Player player1;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "player2_ID")
    private Player player2;

    public int getMatchPlayerID() {
        return matchPlayerID;
    }
    public Player getPlayer1() {
        return player1;
    }
    public Player getPlayer2() {
        return player2;
    }
    public void setMatchPlayerID(int matchPlayerID) {
        this.matchPlayerID = matchPlayerID;
    }
    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }
    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }
}