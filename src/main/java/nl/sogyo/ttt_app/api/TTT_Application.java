package nl.sogyo.ttt_app.api;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.xml.crypto.Data;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import LocationIq.ApiException;
import nl.sogyo.ttt_app.domain.*;

@SpringBootApplication
@RestController
public class TTT_Application extends WebSecurityConfigurerAdapter{

	@GetMapping("/user")
	public PlayerResponse user(@AuthenticationPrincipal OAuth2User principal) {
		String outside_ID = principal.getName();
		DatabaseAccessor databaseAccessor = new DatabaseAccessor();
		OutsideIDPlayerID outsideIDPlayerID = databaseAccessor.getFromDB(outside_ID, OutsideIDPlayerID.class);
		PlayerResponse response;
		if(outsideIDPlayerID != null){
			int playerID = outsideIDPlayerID.getPlayer_ID();
			Player player = databaseAccessor.getFromDB(playerID, Player.class);
			response = new PlayerResponse(player);
			response.setAdress(player.getAdress());
		}
		else{
			Player player = new Player();
			databaseAccessor.createInDB(player);

			outsideIDPlayerID = new OutsideIDPlayerID();
			outsideIDPlayerID.setOutside_ID(outside_ID);
			outsideIDPlayerID.setPlayer_ID(player.getID());
			databaseAccessor.createInDB(outsideIDPlayerID);

			response = new PlayerResponse(player);
			response.setAdress(player.getAdress());
		}
		databaseAccessor.closeSession();
		return response;
	}

	@GetMapping("/tournaments")
	public List<TournamentResponse> tournaments(@AuthenticationPrincipal OAuth2User principal){
		DatabaseAccessor databaseAccessor = new DatabaseAccessor();
		List<Tournament> tournaments = databaseAccessor.getAllFromDB(Tournament.class);
		List<TournamentResponse> response = new ArrayList<TournamentResponse>();
		for(Tournament tournament : tournaments){
			TournamentResponse tournamentResponse = new TournamentResponse(tournament);
			if(principal != null){
				OutsideIDPlayerID outsideIDPlayerID = databaseAccessor.getFromDB(principal.getName(), OutsideIDPlayerID.class);
				int userID = outsideIDPlayerID.getPlayer_ID();
				Player player = databaseAccessor.getFromDB(userID, Player.class);
				Adresshandler distanceCalculator = new Adresshandler();
				double distance = distanceCalculator.calculateDistance(player.getAdress(), tournament.getAdress());
				tournamentResponse.setDistanceToUser(distance);
			}
			response.add(tournamentResponse);
		}
		databaseAccessor.closeSession();
		return response;
	}

	@PostMapping("/createTournament")
	public TournamentResponse createTournament(@AuthenticationPrincipal OAuth2User principal, @RequestBody TournamentResponse tournamentToCreate)throws ApiException{
		DatabaseAccessor databaseAccessor = new DatabaseAccessor();
		Adresshandler adresshandler = new Adresshandler();
		Tournament tournament = new Tournament();
		tournament.copyGeneralInfo(tournamentToCreate);
		tournament.setAdress(adresshandler.setLonLat(tournament.getAdress()));

		databaseAccessor.createInDB(tournament);
		int userID = databaseAccessor.getFromDB(principal.getName(), OutsideIDPlayerID.class).getPlayer_ID();
		Player player = databaseAccessor.getFromDB(userID, Player.class);

		double distance = adresshandler.calculateDistance(player.getAdress(), tournament.getAdress());
		TournamentResponse response = new TournamentResponse(tournament);
		response.setDistanceToUser(distance);

		databaseAccessor.closeSession();
		return response;
	}

	@PostMapping("/editTournament")
	public TournamentResponse editTournament(@AuthenticationPrincipal OAuth2User principal, @RequestBody TournamentResponse tournamentToEdit)throws ApiException{
		DatabaseAccessor databaseAccessor = new DatabaseAccessor();
		Adresshandler adresshandler = new Adresshandler();
		Tournament tournament = databaseAccessor.getFromDB(tournamentToEdit.getTournamentID(), Tournament.class);
		tournament.copyGeneralInfo(tournamentToEdit);
		tournament.setAdress(adresshandler.setLonLat(tournament.getAdress()));
		databaseAccessor.updateInDB(tournament);

		int userID = databaseAccessor.getFromDB(principal.getName(), OutsideIDPlayerID.class).getPlayer_ID();
		Player player = databaseAccessor.getFromDB(userID, Player.class);

		double distance = adresshandler.calculateDistance(player.getAdress(), tournament.getAdress());
		TournamentResponse response = new TournamentResponse(tournament);
		response.setDistanceToUser(distance);

		databaseAccessor.closeSession();
		return response;
	}

	@PostMapping("/signUpForTournament")
	public PlayerResponse signUpForTournament(@AuthenticationPrincipal OAuth2User principal, Integer tournamentID){
		DatabaseAccessor databaseAccessor = new DatabaseAccessor();
		int userID = databaseAccessor.getFromDB(principal.getName(), OutsideIDPlayerID.class).getPlayer_ID();
		Player player = databaseAccessor.getFromDB(userID, Player.class);
		Tournament tournament = databaseAccessor.getFromDB(tournamentID, Tournament.class);
		player.signUpForTournament(tournament);
		databaseAccessor.updateInDB(player);
		databaseAccessor.closeSession();
		PlayerResponse response = new PlayerResponse(player);
		response.setAdress(player.getAdress());
		return response;
	}

	@PostMapping("/closeTournament")
	public void closeTournament(Integer tournamentID){
		DatabaseAccessor databaseAccessor = new DatabaseAccessor();
		Tournament tournament = databaseAccessor.getFromDB(tournamentID, Tournament.class);
		tournament.closeForSignup();
		databaseAccessor.updateInDB(tournament);
		databaseAccessor.closeSession();
	}

	@PostMapping("/checkadress")
	public Boolean checkadress(@RequestBody Adress adress, HttpServletResponse response){
		Boolean adressValid = false;
		Adresshandler adresshandler = new Adresshandler();
		try {
			adressValid = adresshandler.checkAdressMatchesPostalcode(adress);
		} catch (ApiException e) {
			response.setStatus(e.getCode());
		}
		return adressValid;
	}

	@PostMapping("/createPlanning")
	public PlanningResponse createPlanning(Integer tournamentID){
		DatabaseAccessor databaseAccessor = new DatabaseAccessor();
		Tournament tournament = databaseAccessor.getFromDB(tournamentID, Tournament.class);
		tournament.createPlanning();
		TournamentPlanning planning = tournament.getTournamentPlanning();
		databaseAccessor.createInDB(planning);
		databaseAccessor.updateInDB(tournament);
		return new PlanningResponse(tournament.getTournamentPlanning());
	}

	@PostMapping("/createPlayers")
	public void createPlayers(@AuthenticationPrincipal OAuth2User principal){
		DatabaseAccessor databaseAccessor = new DatabaseAccessor();
		int userID = databaseAccessor.getFromDB(principal.getName(), OutsideIDPlayerID.class).getPlayer_ID();
		Player player = databaseAccessor.getFromDB(userID, Player.class);
		List<Tournament> tournaments = databaseAccessor.getAllFromDB(Tournament.class);
		for(int i = 0; i < 8; i++){
			Player testPlayer = new Player(i + 100);
			testPlayer.setName("testplayer" + i);
			testPlayer.setAdress(player.getAdress());
			databaseAccessor.createInDB(testPlayer);
			databaseAccessor.updateInDB(player);
			Match match = new Match(player, testPlayer);
			databaseAccessor.createInDB(match);
			for(Tournament tournament : tournaments){
				testPlayer.signUpForTournament(tournament);
				databaseAccessor.updateInDB(testPlayer);
			}
		}
		databaseAccessor.closeSession();
	}

	@PostMapping("/updateMatch")
	public void updateMatch(@RequestBody MatchResponse updatedMatch){
		DatabaseAccessor databaseAccessor = new DatabaseAccessor();
		Match match = databaseAccessor.getFromDB(updatedMatch.getMatch_ID(), Match.class);
		match.setGames(updatedMatch.getGames());
		databaseAccessor.updateInDB(match);
	}

	@PostMapping("/updatePlanning")
	public PlanningResponse updatePlanning(Integer planningID){
		DatabaseAccessor databaseAccessor = new DatabaseAccessor();
		TournamentPlanning planning = databaseAccessor.getFromDB(planningID, TournamentPlanning.class);
		planning.updateRounds();
		databaseAccessor.updateInDB(planning);
		PlanningResponse response = new PlanningResponse(planning);
		databaseAccessor.closeSession();
		return response;
	}

	@PostMapping("/editprofile")
	public void editprofile(@AuthenticationPrincipal OAuth2User principal, @RequestBody PlayerResponse user)throws ApiException{
		DatabaseAccessor databaseAccessor = new DatabaseAccessor();
		Adresshandler adresshandler = new Adresshandler();
		Player player = databaseAccessor.getFromDB(user.getID(), Player.class);
		player.copyGeneralInfo(user);
		player.setAdress(adresshandler.setLonLat(player.getAdress()));
		databaseAccessor.updateInDB(player);
		databaseAccessor.closeSession();
	}

	public static void main(String[] args) {
		SpringApplication.run(TTT_Application.class, args);
	}
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	// @formatter:off
        http
            .authorizeRequests(a -> a
                .antMatchers("/", "/error", "/indexStyle.css", "/tournaments").permitAll()
                .anyRequest().authenticated()
            )
			.logout(l -> l
				.logoutSuccessUrl("/").permitAll()
			)
			.csrf(c -> c
				.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
			)
            .exceptionHandling(e -> e
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
            )
            .oauth2Login();
        // @formatter:on
    }

}
