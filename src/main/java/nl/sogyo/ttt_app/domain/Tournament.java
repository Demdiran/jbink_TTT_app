package nl.sogyo.ttt_app.domain;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.HashSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import nl.sogyo.ttt_app.api.IStorable;
import nl.sogyo.ttt_app.api.TournamentResponse;
@Entity(name = "tournament")
@Table(name = "tournaments")
public class Tournament implements IStorable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tournament_ID")
    private int tournamentID;
    @Column(name = "tournament_name")
    private String name;
    @Column(name = "club")
    private String club;
    @Column(name = "open")
    private boolean canSignUp = true;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "adress_ID")
    private Adress adress;

    @Column(name = "date")
    private LocalDateTime tournamentDate;
    @Column(name = "max_participants")
    private int maxParticipants;

    @ManyToMany(mappedBy = "tournaments", fetch = FetchType.LAZY)
    private Set<Player> participants = new HashSet<Player>();

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "tournamentPlanning_ID")
    private TournamentPlanning tournamentPlanning;

    public Tournament(){
    }

    public Tournament(String name){
        this.name = name;
    }

    public void createPlanning(){
        this.canSignUp = false;
        this.tournamentPlanning = new TournamentPlanning(this.participants.toArray(new Player[0]));
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
    public String getClub() {
        return club;
    }
    public void setClub(String club) {
        this.club = club;
    }
    public Adress getAdress(){
        return adress;
    }
    public void setAdress(Adress adress){
        this.adress = adress;
    }
    public LocalDateTime getTournamentDate(){
        return tournamentDate;
    }
    public void setTournamentDate(LocalDateTime tournamentDate){
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
    public boolean getCanSignUp(){
        return canSignUp;
    }
    public void setCanSignup(boolean canSignUp){
        this.canSignUp = canSignUp;
    }
    public TournamentPlanning getTournamentPlanning() {
        return tournamentPlanning;
    }
    public void setTournamentPlanning(TournamentPlanning tournamentPlanning) {
        this.tournamentPlanning = tournamentPlanning;
    }
    public void closeForSignup(){
        canSignUp = false;
    }

    public void playerSignUp(Player player){
        participants.add(player);
    }

    public void copyGeneralInfo(TournamentResponse tournamentResponse){
        this.name = tournamentResponse.getName();
        this.adress = tournamentResponse.getAdress();
        this.club = tournamentResponse.getClub();
        this.maxParticipants = tournamentResponse.getMaxParticipants();
        this.tournamentDate = tournamentResponse.getTournamentDate();
    }
}