package nl.sogyo.ttt_app.domain;

import java.util.ArrayList;
import java.util.List;

public class TournamentRound{
    private List<Match> matches = new ArrayList<Match>();

    public void planMatch(Player player1, Player player2){
        matches.add(new Match(player1, player2));
    }

    public void planMatches(List<Player> players){
        for(int i = 0; i < players.size(); i+=2){
            matches.add(new Match(players.get(i), players.get(i+1)));
        }
    }

    public List<Match> getMatches(){
        return matches;
    }

    public List<Player> getWinners(){
        List<Player> players = new ArrayList<Player>();
        for(Match match : matches){
            players.add(match.getWinner(3));
        }
        return players;
    }

    public boolean isFinished(){
        boolean finished = true;
        for(Match match : matches){
            finished = finished && match.isFinished();
        }
        return finished;
    }
}