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
@Table(name = "players")
@NamedQueries({
    @NamedQuery(name = "Players.findAll", query = "SELECT p FROM Players p"),
    @NamedQuery(name = "Players.findByPlayerId", query = "SELECT p FROM Players p WHERE p.playerId = :playerId"),
    @NamedQuery(name = "Players.findByName", query = "SELECT p FROM Players p WHERE p.name = :name"),
    @NamedQuery(name = "Players.findBySurnames", query = "SELECT p FROM Players p WHERE p.surnames = :surnames"),
    @NamedQuery(name = "Players.findByEmail", query = "SELECT p FROM Players p WHERE p.email = :email"),
    @NamedQuery(name = "Players.findByPhone", query = "SELECT p FROM Players p WHERE p.phone = :phone"),
    @NamedQuery(name = "Players.findByDni", query = "SELECT p FROM Players p WHERE p.dni = :dni"),
    @NamedQuery(name = "Players.findByUserName", query = "SELECT p FROM Players p WHERE p.userName = :userName"),
    @NamedQuery(name = "Players.findByPassword", query = "SELECT p FROM Players p WHERE p.password = :password")})
public class Players implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "player_id")
    private Integer playerId;
    @Column(name = "name")
    private String name;
    @Column(name = "surnames")
    private String surnames;
    @Column(name = "email")
    private String email;
    @Column(name = "phone")
    private Integer phone;
    @Column(name = "Dni")
    private String dni;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "password")
    private String password;
    @OneToMany(mappedBy = "playersId")
    private List<Inscriptions> inscriptionsList;

    public Players() {
    }

    public Players(Integer playerId) {
        this.playerId = playerId;
    }

    public Players(Integer playerId, String name, String surnames, String email, Integer phone, String dni, String userName, String password) {
        this.playerId = playerId;
        this.name = name;
        this.surnames = surnames;
        this.email = email;
        this.phone = phone;
        this.dni = dni;
        this.userName = userName;
        this.password = password;
    }
    

    public Integer getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Integer playerId) {
        this.playerId = playerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurnames() {
        return surnames;
    }

    public void setSurnames(String surnames) {
        this.surnames = surnames;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getPhone() {
        return phone;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
        hash += (playerId != null ? playerId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Players)) {
            return false;
        }
        Players other = (Players) object;
        if ((this.playerId == null && other.playerId != null) || (this.playerId != null && !this.playerId.equals(other.playerId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return name;
    }
    
}
