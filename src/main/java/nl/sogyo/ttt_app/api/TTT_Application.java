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
		DatabaseAccessor databaseAccessor = new DatabaseAccessor();
		Player user = databaseAccessor.getOrCreatePlayerWithOutsideID(outside_ID);
		PlayerResponse response = new PlayerResponse(user);
		response.setAdress(user.getAdress());
		databaseAccessor.closeSession();
		return response;
	}

	@GetMapping("/tournaments")
	public List<TournamentResponse> tournaments(){
		DatabaseAccessor databaseAccessor = new DatabaseAccessor();
		List<Tournament> tournaments = databaseAccessor.getAllFromDB(Tournament.class);
		List<TournamentResponse> response = new ArrayList<TournamentResponse>();
		for(Tournament tournament : tournaments){
			response.add(new TournamentResponse(tournament));
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
		Player user = databaseAccessor.getOrCreatePlayerWithOutsideID(principal.getName());
		Tournament tournament = (Tournament) databaseAccessor.getFromDB(tournamentID, Tournament.class);
		user.signUpForTournament(tournament);
		databaseAccessor.updateInDB(user);
		databaseAccessor.closeSession();
		PlayerResponse response = new PlayerResponse(user);
		response.setAdress(user.getAdress());
		return response;
	}

	@PostMapping("/editprofile")
	public void editprofile(@AuthenticationPrincipal OAuth2User principal, @RequestBody PlayerResponse user){
		DatabaseAccessor databaseAccessor = new DatabaseAccessor();
		Player player = (Player) databaseAccessor.getFromDB(user.getID(), Player.class);
		player.setAdress(user.getAdress());
		player.setName(user.getName());
		player.setRating(user.getRating());
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
