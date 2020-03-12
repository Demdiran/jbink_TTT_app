package nl.sogyo.ttt_app.domain;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.HashSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import nl.sogyo.ttt_app.api.IStorable;
@Entity
@Table(name = "tournaments")
public class Tournament implements IStorable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @ManyToMany(mappedBy = "tournaments")
    private Set<Player> participants = new HashSet<Player>();

    public Tournament(){

    }

    public Tournament(String name){
        this.name = name;
    }

    public int getID(){
        return tournamentID;
    }
    public void setID(int ID){
        this.tournamentID = ID;
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
    public Set<Player> getParticipants(){
        return participants;
    }

    public void playerSignUp(Player player){
        participants.add(player);
    }
}