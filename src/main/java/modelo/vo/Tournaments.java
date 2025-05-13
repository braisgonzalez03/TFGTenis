/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.vo;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author brais
 */
@Entity
@Table(name = "tournaments")
@NamedQueries({
    @NamedQuery(name = "Tournaments.findAll", query = "SELECT t FROM Tournaments t"),
    @NamedQuery(name = "Tournaments.findByTournamentId", query = "SELECT t FROM Tournaments t WHERE t.tournamentId = :tournamentId"),
    @NamedQuery(name = "Tournaments.findByName", query = "SELECT t FROM Tournaments t WHERE t.name = :name"),
    @NamedQuery(name = "Tournaments.findByDescription", query = "SELECT t FROM Tournaments t WHERE t.description = :description"),
    @NamedQuery(name = "Tournaments.findByClassification", query = "SELECT t FROM Tournaments t WHERE t.classification = :classification")})
public class Tournaments implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "tournament_id")
    private Integer tournamentId;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "classification")
    private String classification;
    @OneToMany(mappedBy = "tournamentsId")
    private List<Inscriptions> inscriptionsList;

    public Tournaments() {
    }

    public Tournaments(Integer tournamentId) {
        this.tournamentId = tournamentId;
    }

    public Tournaments(Integer tournamentId, String name, String description, String classification) {
        this.tournamentId = tournamentId;
        this.name = name;
        this.description = description;
        this.classification = classification;
    }
    

    public Integer getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(Integer tournamentId) {
        this.tournamentId = tournamentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public List<Inscriptions> getInscriptionsList() {
        return inscriptionsList;
    }

    public void setInscriptionsList(List<Inscriptions> inscriptionsList) {
        this.inscriptionsList = inscriptionsList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tournamentId != null ? tournamentId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tournaments)) {
            return false;
        }
        Tournaments other = (Tournaments) object;
        if ((this.tournamentId == null && other.tournamentId != null) || (this.tournamentId != null && !this.tournamentId.equals(other.tournamentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return name;
    }
    
}
