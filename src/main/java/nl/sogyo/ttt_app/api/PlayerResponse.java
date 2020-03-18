package nl.sogyo.ttt_app.api;

import java.util.ArrayList;
import java.util.List;

import nl.sogyo.ttt_app.domain.Adress;
import nl.sogyo.ttt_app.domain.Player;
import nl.sogyo.ttt_app.domain.Tournament;

class PlayerResponse {
    private int player_ID;
    private int rating;
    private String name;
    private List<TournamentResponse> tournaments = new ArrayList<TournamentResponse>();
    private Adress adress;
    PlayerResponse(Player player){
        this.player_ID = player.getID();
        this.rating = player.getRating();
        this.name = player.getName();
        for(Tournament tournament : player.getTournaments()){
            tournaments.add(new TournamentResponse(tournament));
        }
    }

    PlayerResponse(){
    }

    public void setAdress(Adress adress) {
        this.adress = adress;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setID(int player_ID) {
        this.player_ID = player_ID;
    }
    public void setRating(int rating) {
        this.rating = rating;
    }
    public void setTournaments(List<TournamentResponse> tournaments) {
        this.tournaments = tournaments;
    }
    public Adress getAdress() {
        return adress;
    }
    public int getID() {
        return player_ID;
    }
    public String getName() {
        return name;
    }
    public int getRating() {
        return rating;
    }
    public List<TournamentResponse> getTournaments() {
        return tournaments;
    }
}