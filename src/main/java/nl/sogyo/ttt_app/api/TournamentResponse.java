package nl.sogyo.ttt_app.api;

import java.time.LocalDateTime;

import nl.sogyo.ttt_app.domain.Adress;
import nl.sogyo.ttt_app.domain.Tournament;

public class TournamentResponse{
    private int ownerID;
    private int tournamentID;
    private String name;
    private String club;
    private Adress adress;
    private LocalDateTime tournamentDate;
    private int maxParticipants;
    private int amountOfParticipants;
    private double distanceToUser;
    private boolean canSignUp;
    private PlanningResponse planning;
    TournamentResponse(Tournament tournament){
        this.tournamentID = tournament.getID();
        this.name = tournament.getName();
        this.club = tournament.getClub();
        this.adress = tournament.getAdress();
        this.tournamentDate = tournament.getTournamentDate();
        this.maxParticipants = tournament.getMaxParticipants();
        this.amountOfParticipants = tournament.getParticipants().size();
        this.canSignUp = tournament.getCanSignUp();
        this.ownerID = tournament.getOwnerID();
        if(tournament.getTournamentPlanning() != null)
            this.planning = new PlanningResponse(tournament.getTournamentPlanning());
    }

    TournamentResponse(){
    }

    public Adress getAdress() {
        return adress;
    }
    public int getAmountOfParticipants() {
        return amountOfParticipants;
    }
    public String getClub() {
        return club;
    }
    public int getMaxParticipants() {
        return maxParticipants;
    }
    public String getName() {
        return name;
    }
    public LocalDateTime getTournamentDate() {
        return tournamentDate;
    }
    public int getTournamentID() {
        return tournamentID;
    }
    public double getDistanceToUser() {
        return distanceToUser;
    }
    public boolean getCanSignUp(){
        return canSignUp;
    }
    public PlanningResponse getPlanning() {
        return planning;
    }
    public int getOwnerID() {
        return ownerID;
    }
    public void setAdress(Adress adress) {
        this.adress = adress;
    }
    public void setAmountOfParticipants(int amountOfParticipants) {
        this.amountOfParticipants = amountOfParticipants;
    }
    public void setClub(String club) {
        this.club = club;
    }
    public void setMaxParticipants(int maxParticipants) {
        this.maxParticipants = maxParticipants;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setTournamentDate(LocalDateTime tournamentDate) {
        this.tournamentDate = tournamentDate;
    }
    public void setTournamentID(int tournamentID) {
        this.tournamentID = tournamentID;
    }
    public void setDistanceToUser(double distanceToUser) {
        this.distanceToUser = distanceToUser;
    }
    public void setCanSignUp(boolean canSignUp){
        this.canSignUp = canSignUp;
    }
    public void setPlanning(PlanningResponse planning) {
        this.planning = planning;
    }
    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
    }
}