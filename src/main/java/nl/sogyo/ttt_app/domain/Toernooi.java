package nl.sogyo.ttt_app.domain;

import java.time.LocalDateTime;
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
@Entity
@Table(name = "toernooien")
class Toernooi{
    @Id
    @GeneratedValue
    @Column(name = "toernooi_ID")
    private int toernooi_ID;
    @Column(name = "toernooi_naam")
    private String naam;
    @Column(name = "adres")
    private String adres;
    @Column(name = "datum")
    private LocalDateTime toernooiDatum;
    @Column(name = "max_deelnemers")
    private int maxDeelnemers;

    @ManyToMany(mappedBy = "toernooien")
    private List<Speler> deelnemers;
}