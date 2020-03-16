package nl.sogyo.ttt_app.api;

import java.util.ArrayList;
import java.util.List;

import nl.sogyo.ttt_app.domain.Player;
import nl.sogyo.ttt_app.domain.Tournament;

class PlayerResponse {
    private int player_ID;
    private int rating;
    private String name;
    private List<TournamentResponse> tournaments = new ArrayList<TournamentResponse>();
    private String adress;
    PlayerResponse(Player player){
        this.player_ID = player.getID();
        this.rating = player.getRating();
        this.name = player.getName();
        for(Tournament tournament : player.getTournaments()){
            tournaments.add(new TournamentResponse(tournament));
        }
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }
    public String getAdress() {
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