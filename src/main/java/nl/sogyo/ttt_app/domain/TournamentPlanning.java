package nl.sogyo.ttt_app.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class TournamentPlanning{
    private List<TournamentRound> rounds = new ArrayList<TournamentRound>();

    public TournamentPlanning(Player...players){
        List<Player> tournamentPlayers = new ArrayList<Player>(Arrays.asList(players));
        if(tournamentPlayers.size() == 0){
            throw new NoPlayersSignedUpForTournament();
        }
        tournamentPlayers.sort(Comparator.comparing(Player::getRating).reversed());
        TournamentRound round = new TournamentRound();
        int playerByeCount = tournamentPlayers.size() - (int) Math.pow(2,(int) (Math.log(tournamentPlayers.size()) / Math.log(2)));
        for(int i = 0; i < playerByeCount; i++){
            tournamentPlayers.add(null);
        }
        List<Player> orderedTournamentPlayers = orderTournamentPlayers(tournamentPlayers);
        round.planMatches(orderedTournamentPlayers);
        rounds.add(round);
    }

    public TournamentRound getCurrentRound(){
        for(TournamentRound round : rounds){
            if(!round.isFinished())
                return round;
        }
        return rounds.get(rounds.size() - 1);
    }

    public TournamentRound planNextRound(){
        TournamentRound currentRound = getCurrentRound();

        if(currentRound.isFinished()){
            List<Player> playersOfNextRound = currentRound.getWinners();
            TournamentRound nextRound = new TournamentRound();
            nextRound.planMatches(playersOfNextRound);
            rounds.add(nextRound);
            return nextRound;
        }
        else{
            throw new CurrentRoundNotFinished();
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
}