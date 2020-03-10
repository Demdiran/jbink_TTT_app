package nl.sogyo.ttt_app.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity(name = "speler")
@Table(name = "spelers")
class Speler{
    @Id@GeneratedValue
    @Column(name = "speler_ID")
    private int speler_ID;
    @Column(name = "speler_rating")
    private int rating;
    @Column(name = "speler_naam")
    private String naam;
    @Column(name = "speler_adres")
    private String adres;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
        name = "speler_toernooi",
        joinColumns = { @JoinColumn(name = "speler_ID")},
        inverseJoinColumns = { @JoinColumn(name = "toernooi_ID")}
    )
    private List<Toernooi> toernooien;

    public int getRating(){
        return rating;
    }
}