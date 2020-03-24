package nl.sogyo.ttt_app.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity(name = "tournamentround")
@Table(name = "tournamentrounds")
public class TournamentRound{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tournamentRound_ID")
    private int tournamentRoundID;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "tournamentRound_ID")
    private List<Match> matches = new ArrayList<Match>();

    public TournamentRound(){
    }
    public TournamentRound(int numberOfMatches){
        for(int i = 0; i < numberOfMatches; i++){
            matches.add(new Match());
        }
    }
    public void planMatch(Player player1, Player player2){
        matches.add(new Match(player1, player2));
    }

    public void planMatches(List<Player> players){
        for(int i = 0; i < players.size(); i+=2){
            matches.add(new Match(players.get(i), players.get(i+1)));
        }
    }
    public int getTournamentRoundID() {
        return tournamentRoundID;
    }
    public void setTournamentRoundID(int tournamentRoundID) {
        this.tournamentRoundID = tournamentRoundID;
    }
    public void setMatches(List<Match> matches) {
        this.matches = matches;
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

    public Match getNextMatch(){
        for(Match match : matches){
            if(!match.isFinished())
                return match;
        }
        throw new RoundHasFinished();
    }

    public Player getTournamentWinner(){
        if(matches.size() == 1){
            return matches.get(0).getWinner(3);
        }
        else{
            return null;
        }
    }

    public boolean hasWinner(){
        if(matches.size() == 1){
            return matches.get(0).isFinished();
        }
        else{
            return false;
        }
    }
}