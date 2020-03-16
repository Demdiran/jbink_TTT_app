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
    TournamentResponse(Tournament tournament){
        this.tournamentID = tournament.getID();
        this.name = tournament.getName();
        this.club = tournament.getClub();
        this.adress = tournament.getAdress();
        this.tournamentDate = tournament.getTournamentDate();
        this.maxParticipants = tournament.getMaxParticipants();
        this.amountOfParticipants = tournament.getParticipants().size();
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
}