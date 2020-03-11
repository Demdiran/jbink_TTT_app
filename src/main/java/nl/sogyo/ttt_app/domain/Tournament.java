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
    private int toernooi_ID;
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

    // public void playerSignUp(Player player){
    //     participants.add(player);
    // }
}