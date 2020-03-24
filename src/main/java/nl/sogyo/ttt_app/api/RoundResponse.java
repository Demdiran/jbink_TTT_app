package nl.sogyo.ttt_app.api;

import java.util.ArrayList;
import java.util.List;

import nl.sogyo.ttt_app.domain.Match;
import nl.sogyo.ttt_app.domain.TournamentRound;

public class RoundResponse {
    
    private int tournamentRoundID;
    private List<MatchResponse> matches = new ArrayList<MatchResponse>();

    public RoundResponse(TournamentRound tournamentRound){
        this.tournamentRoundID = tournamentRound.getTournamentRoundID();
        for(Match match : tournamentRound.getMatches()){
            this.matches.add(new MatchResponse(match));
        }
    }
    public List<MatchResponse> getMatches() {
        return matches;
    }
    public int getTournamentRoundID() {
        return tournamentRoundID;
    }
    public void setMatches(List<MatchResponse> matches) {
        this.matches = matches;
    }
    public void setTournamentRoundID(int tournamentRoundID) {
        this.tournamentRoundID = tournamentRoundID;
    }

}