package nl.sogyo.ttt_app.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity(name = "tournamentplanning")
@Table(name = "tournamentplannings")
public class TournamentPlanning{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tournamentPlanning_ID")
    private int tournamentPlanningID;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "tournamentPlanning_ID")
    private List<TournamentRound> rounds = new ArrayList<TournamentRound>();

    public TournamentPlanning(){
    }

    public TournamentPlanning(Player...players){
        List<Player> tournamentPlayers = new ArrayList<Player>(Arrays.asList(players));
        if(tournamentPlayers.size() == 0){
            throw new NoPlayersSignedUpForTournament();
        }
        tournamentPlayers.sort(Comparator.comparing(Player::getRating).reversed());
        TournamentRound round = new TournamentRound();
        int playerByeCount = (int) Math.pow(2,(int) Math.ceil((Math.log(tournamentPlayers.size()) / Math.log(2)))) - tournamentPlayers.size();
        System.out.println("playerByeCount: " + playerByeCount);
        for(int i = 0; i < playerByeCount; i++){
            tournamentPlayers.add(null);
        }
        List<Player> orderedTournamentPlayers = orderTournamentPlayers(tournamentPlayers);
        round.planMatches(orderedTournamentPlayers);
        rounds.add(round);
        for(int i = tournamentPlayers.size() / 4; i > 0; i = i/2){
            rounds.add(new TournamentRound(i));
        }
    }

    public void updateRounds(){
        for(int i = 1; i < rounds.size(); i++){
            List<Match> currentMatches = rounds.get(i).getMatches();
            List<Match> previousMatches = rounds.get(i-1).getMatches();
            for(int j = 0; j < currentMatches.size(); j++){
                boolean parentMatchesPlayed = previousMatches.get(j*2).isFinished() && previousMatches.get(j*2+1).isFinished();
                if(parentMatchesPlayed){
                    currentMatches.get(j).setPlayer1(previousMatches.get(j*2).getWinner(3));
                    currentMatches.get(j).setPlayer2(previousMatches.get(j*2+1).getWinner(3));
                }
            }
        }
    }

    private List<Player> orderTournamentPlayers(List<Player> players){
        if(players.size() == 2){
            return players;
        }
        List<Player> firstHalf = new ArrayList<Player>();
        List<Player> secondHalf = new ArrayList<Player>();
        firstHalf.add(players.get(0));
        for(int i = 4; i < players.size(); i += 4){
            firstHalf.add(players.get(i - 1));
            firstHalf.add(players.get(i));
        }
        for(int i = 2; i < players.size(); i += 4){
            secondHalf.add(players.get(i - 1));
            secondHalf.add(players.get(i));
        }
        firstHalf.add(players.get(players.size() - 1));
        List<Player> orderedTournamentPlayers = orderTournamentPlayers(firstHalf);
        orderedTournamentPlayers.addAll(orderTournamentPlayers(secondHalf));
        return orderedTournamentPlayers;
    }
    public List<TournamentRound> getRounds() {
        return rounds;
    }
    public int getTournamentPlanningID() {
        return tournamentPlanningID;
    }
    public void setRounds(List<TournamentRound> rounds) {
        this.rounds = rounds;
    }
    public void setTournamentPlanningID(int tournamentPlanningID) {
        this.tournamentPlanningID = tournamentPlanningID;
    }

    public Match getNextMatch(){
        for(TournamentRound round : rounds){
            for(Match match : round.getMatches()){
                if(!match.isFinished())
                    return match;
            }
        }
        return null;
    }

    public Player getTournamentWinner(){
        return rounds.get(rounds.size() - 1).getTournamentWinner();
    }

    public boolean hasWinner(){
        return rounds.get(rounds.size() - 1).hasWinner();
    }
}