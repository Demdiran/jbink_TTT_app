package nl.sogyo.ttt_app.api;

import java.util.ArrayList;
import java.util.List;

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

import nl.sogyo.ttt_app.domain.*;

@SpringBootApplication
@RestController
public class TTT_Application extends WebSecurityConfigurerAdapter{

	@GetMapping("/user")
	public PlayerResponse user(@AuthenticationPrincipal OAuth2User principal) {
		String outside_ID = principal.getName();
		System.out.println(outside_ID);
		System.out.println(outside_ID.length());
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
				System.out.println("Principal name: " + principal.getName());
				OutsideIDPlayerID outsideIDPlayerID = databaseAccessor.getFromDB(principal.getName(), OutsideIDPlayerID.class);
				System.out.println(outsideIDPlayerID);
				int userID = outsideIDPlayerID.getPlayer_ID();
				System.out.println("UserID: " + userID);
				Player player = databaseAccessor.getFromDB(userID, Player.class);
				Adresshandler distanceCalculator = new Adresshandler();
				System.out.println("User adress: " + player.getAdress() + " Tournament adress: " + tournament.getAdress());
				double distance = distanceCalculator.calculateDistance(player.getAdress(), tournament.getAdress());
				tournamentResponse.setDistanceToUser(distance);
			}
			response.add(tournamentResponse);
		}
		databaseAccessor.closeSession();
		return response;
	}

	@PostMapping("/createTournament")
	public TournamentResponse createTournament(@AuthenticationPrincipal OAuth2User principal, @RequestBody Tournament tournament){
		DatabaseAccessor databaseAccessor = new DatabaseAccessor();
		databaseAccessor.createInDB(tournament);
		databaseAccessor.closeSession();
		return new TournamentResponse(tournament);
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

	@PostMapping("/editprofile")
	public void editprofile(@AuthenticationPrincipal OAuth2User principal, @RequestBody PlayerResponse user){
		DatabaseAccessor databaseAccessor = new DatabaseAccessor();
		Player player = databaseAccessor.getFromDB(user.getID(), Player.class);
		player.setAdress(user.getAdress());
		//Check adress---------------------------------------------------------------------------------
		player.setName(user.getName());
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
