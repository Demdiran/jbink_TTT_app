package nl.sogyo.ttt_app.domain;

import java.util.Set;
import java.util.HashSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity(name = "player")
@Table(name = "players")
public class Player{
    @Id@GeneratedValue
    @Column(name = "player_ID")
    private int player_ID;
    @Column(name = "player_rating")
    private int rating;
    @Column(name = "player_name")
    private String name;
    @Column(name = "player_adress")
    private String adress;

    // @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    // @JoinTable(
    //     name = "player_tournament",
    //     joinColumns = { @JoinColumn(name = "player_ID")},
    //     inverseJoinColumns = { @JoinColumn(name = "tournament_ID")}
    // )
    // private Set<Tournament> tournaments = new HashSet<Tournament>();

    public Player(){

    }

    public Player(String name){
        this.name = name;
    }
    public int getRating(){
        return rating;
    }
    public int getID(){
        return this.player_ID;
    }
    public void setID(int id){
        this.player_ID = id;
    }

    // public void signUpForTournament(Tournament tournament){
    //     this.tournaments.add(tournament);
    //     tournament.playerSignUp(this);
    // }
}