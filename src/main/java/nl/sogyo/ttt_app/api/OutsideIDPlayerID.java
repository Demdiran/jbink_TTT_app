package nl.sogyo.ttt_app.api;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "OutsideIDPlayerID")
@Table(name = "outsideID_PlayerID")
public class OutsideIDPlayerID{
    @Id
    @Column(name = "outside_ID")
    private String outside_ID;
    @Column(name = "player_ID")
    private int player_ID;

    public String getOutside_ID() {
        return outside_ID;
    }
    public int getPlayer_ID() {
        return player_ID;
    }
    public void setOutside_ID(String outside_ID) {
        this.outside_ID = outside_ID;
    }
    public void setPlayer_ID(int player_ID) {
        this.player_ID = player_ID;
    }
}