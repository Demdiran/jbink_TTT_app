package nl.sogyo.ttt_app.api;

import java.time.LocalDateTime;

import nl.sogyo.ttt_app.domain.Tournament;

class TournamentResponse{
    private int tournamentID;
    private String name;
    private String club;
    private String adress;
    private LocalDateTime tournamentDate;
    private int maxParticipants;
    private int amountOfParticipants;
    private double distanceToUser;
    TournamentResponse(Tournament tournament){
        this.tournamentID = tournament.getID();
        this.name = tournament.getName();
        this.club = tournament.getClub();
        this.adress = tournament.getAdress();
        this.tournamentDate = tournament.getTournamentDate();
        this.maxParticipants = tournament.getMaxParticipants();
        this.amountOfParticipants = tournament.getParticipants().size();
    }

    TournamentResponse(){
    }

    public String getAdress() {
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
    public void setAdress(String adress) {
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
}