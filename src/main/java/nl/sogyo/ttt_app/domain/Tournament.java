package nl.sogyo.ttt_app.domain;

import java.time.LocalDateTime;
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
@Entity
@Table(name = "tournaments")
public class Tournament{
    @Id
    @GeneratedValue
    @Column(name = "tournament_ID")
    private int tournamentID;
    @Column(name = "tournament_name")
    private String name;
    @Column(name = "adress")
    private String adress;
    @Column(name = "date")
    private LocalDateTime tournamentDate;
    @Column(name = "max_participants")
    private int maxParticipants;

    // @ManyToMany(mappedBy = "tournaments")
    // private Set<Player> participants = new HashSet<Player>();

    public Tournament(){

    }

    public Tournament(String name){
        this.name = name;
    }

    public int getTournamentID(){
        return tournamentID;
    }
    public void setTournamentID(int id){
        this.tournamentID = id;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getAdress(){
        return adress;
    }
    public void setAdress(String adress){
        this.adress = adress;
    }
    public LocalDateTime getDate(){
        return tournamentDate;
    }
    public void setDate(LocalDateTime tournamentDate){
        this.tournamentDate = tournamentDate;
    }
    public int getMaxParticipants(){
        return maxParticipants;
    }
    public void setMaxParticipants(int maxParticipants){
        this.maxParticipants = maxParticipants;
    }

    // public void playerSignUp(Player player){
    //     participants.add(player);
    // }
}