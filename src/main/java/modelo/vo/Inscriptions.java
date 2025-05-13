/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.vo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author brais
 */
@Entity
@Table(name = "inscriptions")
@NamedQueries({
    @NamedQuery(name = "Inscriptions.findAll", query = "SELECT i FROM Inscriptions i"),
    @NamedQuery(name = "Inscriptions.findByInscriptionId", query = "SELECT i FROM Inscriptions i WHERE i.inscriptionId = :inscriptionId"),
    @NamedQuery(name = "Inscriptions.findByStartDate", query = "SELECT i FROM Inscriptions i WHERE i.startDate = :startDate"),
    @NamedQuery(name = "Inscriptions.findByEndDate", query = "SELECT i FROM Inscriptions i WHERE i.endDate = :endDate")})
public class Inscriptions implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "inscription_id")
    private Integer inscriptionId;
    @Basic(optional = false)
    @Column(name = "start_date")
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Column(name = "end_date")
    @Temporal(TemporalType.DATE)
    private Date endDate;
    @JoinColumn(name = "players_id", referencedColumnName = "player_id")
    @ManyToOne
    private Players playersId;
    @JoinColumn(name = "tournaments_id", referencedColumnName = "tournament_id")
    @ManyToOne
    private Tournaments tournamentsId;

    public Inscriptions() {
    }

    public Inscriptions(Integer inscriptionId) {
        this.inscriptionId = inscriptionId;
    }

    public Inscriptions(Integer inscriptionId, Date startDate) {
        this.inscriptionId = inscriptionId;
        this.startDate = startDate;
    }

    public Integer getInscriptionId() {
        return inscriptionId;
    }

    public Inscriptions(Integer inscriptionId, Date startDate, Date endDate, Players playersId, Tournaments tournamentsId) {
        this.inscriptionId = inscriptionId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.playersId = playersId;
        this.tournamentsId = tournamentsId;
    }

    
    public void setInscriptionId(Integer inscriptionId) {
        this.inscriptionId = inscriptionId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Players getPlayersId() {
        return playersId;
    }

    public void setPlayersId(Players playersId) {
        this.playersId = playersId;
    }

    public Tournaments getTournamentsId() {
        return tournamentsId;
    }

    public void setTournamentsId(Tournaments tournamentsId) {
        this.tournamentsId = tournamentsId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (inscriptionId != null ? inscriptionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Inscriptions)) {
            return false;
        }
        Inscriptions other = (Inscriptions) object;
        if ((this.inscriptionId == null && other.inscriptionId != null) || (this.inscriptionId != null && !this.inscriptionId.equals(other.inscriptionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.vo.Inscriptions[ inscriptionId=" + inscriptionId + " ]";
    }
    
}
