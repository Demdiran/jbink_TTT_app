package nl.sogyo.ttt_app.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class TournamentPlanning{
    private List<TournamentRound> rounds = new ArrayList<TournamentRound>();

    public TournamentPlanning(Player...players){
        Player[] tournamentPlayers = players.clone(); //Prevents the original array from becoming useless
        Arrays.sort(tournamentPlayers, Comparator.comparing(Player::getRating).reversed());
        TournamentRound round = new TournamentRound();
        int playerByeCount = tournamentPlayers.length - (int) Math.pow(2,(int) (Math.log(tournamentPlayers.length) / Math.log(2)));
        for(int i = 0; i + playerByeCount < tournamentPlayers.length - i - 1; i++ ){
            round.planMatch(tournamentPlayers[i + playerByeCount], tournamentPlayers[tournamentPlayers.length - i - 1]);
        }
        for(int i = 0; i < playerByeCount; i++){
            round.addByePlayer(tournamentPlayers[i]);
        }
        rounds.add(round);
    }

    public TournamentRound getCurrentRound(){
        return rounds.get(0);
    }

    public TournamentRound planNextRound(){
        TournamentRound currentRound = getCurrentRound();
        if(currentRound.isFinished()){
            Player[] playersOfNextRound = currentRound.getWinners();
            Arrays.sort(playersOfNextRound, Comparator.comparing(Player::getRating).reversed());
            TournamentRound nextRound = new TournamentRound();
            for(int i = 0; i < playersOfNextRound.length - i - 1; i++){
                nextRound.planMatch(playersOfNextRound[i], playersOfNextRound[playersOfNextRound.length - i - 1]);
            }
            rounds.add(nextRound);
            return nextRound;
        }
        else{
            return null;
        }
    }
}