/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.dao;

import java.util.Iterator;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;
import modelo.vo.Players;
import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 *
 * @author brais
 */
public class PlayersDAO {

    public void getAllPlayers(Session session, DefaultTableModel modelTable, JLabel lblNumPlayers) {
        modelTable.setRowCount(0);
        Query q = session.createQuery("from Players p order by p.playerId");
        List<Players> listPlayers = q.list();
        for (Players p : listPlayers) {
            modelTable.addRow(new Object[]{
                p.getPlayerId(),
                p.getName(),
                p.getSurnames(),
                p.getEmail(),
                p.getPhone(),
                p.getDni(),
                p.getUserName(),
                p.getPassword()
            });
        }
        if (listPlayers.isEmpty()) {
            lblNumPlayers.setText("There are no players");
        } else {
            lblNumPlayers.setText("Number of Players: " + listPlayers.size());
        }

    }

    public Players getPlayer(Session session, String dni, String userName) {
        Players p = null;
        Query q = session.createQuery("from Players p where p.dni = :dni or p.userName = :username");
        q.setParameter("dni", dni);
        q.setParameter("username", userName);
        p = (Players) q.uniqueResult();
        return p;
    }

    public void insert(Session session, int playerId, String name, String surnames, String email, int phone, String dni, String userName, String password) {
        Players p = new Players(playerId, name, surnames, email, phone, dni, userName, password);
        session.save(p);
    }

    public void modified(Session session, Players p, String email, int phone, String password) {
        p.setEmail(email);
        p.setPhone(phone);
        p.setPassword(password);
        session.update(p);
    }

    public Players getPlayerByPlayerId(Session session, int playerId) {
        return session.get(Players.class, playerId);
    }

    public void delete(Session session, Players p) {
        session.delete(p);
    }

    public void loadcombo(Session session, DefaultComboBoxModel modelcomboPlayers) {
        modelcomboPlayers.removeAllElements();
        Players p ;
        Query q = session.createQuery("from Players p");
        
        List<Players> listPlayers = q.list();
        Iterator it = listPlayers.iterator();
        
        while(it.hasNext()){
            modelcomboPlayers.addElement(it.next());
        }
    }

    public Players getPlayerByName(Session session, String playerName) {
        Players p = null;
        Query q = session.createQuery("from Players p where p.name = :name");
        q.setParameter("name", playerName);
        
        p = (Players) q.uniqueResult();
        return p;
    }

}
