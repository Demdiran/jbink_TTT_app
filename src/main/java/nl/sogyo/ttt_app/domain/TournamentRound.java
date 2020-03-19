package nl.sogyo.ttt_app.domain;

import java.util.ArrayList;
import java.util.List;

public class TournamentRound{
    private List<Match> matches = new ArrayList<Match>();

    public void planMatch(Player player1, Player player2){
        matches.add(new Match(player1, player2));
    }

    public List<Match> getMatches(){
        return matches;
    }
}