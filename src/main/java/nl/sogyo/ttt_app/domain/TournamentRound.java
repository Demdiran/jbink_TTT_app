package nl.sogyo.ttt_app.domain;

import java.util.ArrayList;
import java.util.List;

public class TournamentRound{
    private List<Match> matches = new ArrayList<Match>();
    private List<Player> byePlayers = new ArrayList<Player>();

    public void planMatch(Player player1, Player player2){
        matches.add(new Match(player1, player2));
    }

    public List<Match> getMatches(){
        return matches;
    }

    public void addByePlayer(Player byePlayer){
        byePlayers.add(byePlayer);
    }

    public Player[] getWinners(){
        List<Player> players = new ArrayList<Player>(byePlayers);
        for(Match match : matches){
            players.add(match.getWinner(3));
        }
        return players.toArray(new Player[0]);
    }

    public boolean isFinished(){
        boolean finished = true;
        for(Match match : matches){
            finished = finished && match.isFinished();
        }
        return finished;
    }
}