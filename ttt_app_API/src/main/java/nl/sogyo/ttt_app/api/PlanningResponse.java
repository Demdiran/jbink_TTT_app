package nl.sogyo.ttt_app.api;

import java.util.ArrayList;
import java.util.List;

import nl.sogyo.ttt_app.domain.TournamentPlanning;
import nl.sogyo.ttt_app.domain.TournamentRound;

public class PlanningResponse {
    
    private int tournamentPlanningID;
    private List<RoundResponse> rounds = new ArrayList<RoundResponse>();

    public PlanningResponse(){
    }
    
    public PlanningResponse(TournamentPlanning tournamentPlanning){
        this.tournamentPlanningID = tournamentPlanning.getTournamentPlanningID();
        for(TournamentRound round : tournamentPlanning.getRounds()){
            this.rounds.add(new RoundResponse(round));
        }
    }

    public List<RoundResponse> getRounds() {
        return rounds;
    }
    public int getTournamentPlanningID() {
        return tournamentPlanningID;
    }
    public void setRounds(List<RoundResponse> rounds) {
        this.rounds = rounds;
    }
    public void setTournamentPlanningID(int tournamentPlanningID) {
        this.tournamentPlanningID = tournamentPlanningID;
    }
}