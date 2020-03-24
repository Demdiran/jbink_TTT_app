package nl.sogyo.ttt_app.api;

import java.util.List;

import nl.sogyo.ttt_app.domain.Game;
import nl.sogyo.ttt_app.domain.Match;

public class MatchResponse {
    private int match_ID;
    private PlayerResponse[] players = new PlayerResponse[2];
    private List<Game> games;


    public MatchResponse(Match match){
        this.match_ID = match.getID();
        this.games = match.getGames();
        if(match.getPlayer1() != null)
        this.players[0] = new PlayerResponse(match.getPlayer1());
        if(match.getPlayer2() != null)
            this.players[1] = new PlayerResponse(match.getPlayer2());
    }
    public List<Game> getGames() {
        return games;
    }
    public int getMatch_ID() {
        return match_ID;
    }
    public PlayerResponse[] getPlayers() {
        return players;
    }
    public void setGames(List<Game> games) {
        this.games = games;
    }
    public void setMatch_ID(int match_ID) {
        this.match_ID = match_ID;
    }
}