package nl.sogyo.ttt_app.api;


import nl.sogyo.ttt_app.domain.Adress;
import nl.sogyo.ttt_app.domain.Player;

public class PlayerResponse {
    private int player_ID;
    private int rating;
    private String name;
    private Adress adress;
    PlayerResponse(Player player){
        this.player_ID = player.getID();
        this.rating = player.getRating();
        this.name = player.getName();
    }

    PlayerResponse(){
    }

    public void setAdress(Adress adress) {
        this.adress = adress;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setID(int player_ID) {
        this.player_ID = player_ID;
    }
    public void setRating(int rating) {
        this.rating = rating;
    }
    public Adress getAdress() {
        return adress;
    }
    public int getID() {
        return player_ID;
    }
    public String getName() {
        return name;
    }
    public int getRating() {
        return rating;
    }
}