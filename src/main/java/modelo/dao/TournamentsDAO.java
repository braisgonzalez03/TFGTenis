/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.dao;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import modelo.vo.Tournaments;
import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 *
 * @author brais
 */
public class TournamentsDAO {

    public void getAllTournament(Session session, DefaultTableModel modelTable, JLabel lblTournament) {
        modelTable.setRowCount(0);
        
        Query q = session.createQuery("from Tournaments t order by t.tournamentId");
        List<Tournaments> tournamentList = q.list();
        
        if(tournamentList.isEmpty()){
            lblTournament.setText("There are no tournaments");
        }else{
            for (Tournaments t : tournamentList) {
                modelTable.addRow(new Object[]{
                    t.getTournamentId(),
                    t.getName(),
                    t.getDescription(),
                    t.getClassification()
                });
                
                lblTournament.setText("Number of Tournaments: " + tournamentList.size());
            }
        }
    }

    public Tournaments getTournament(Session session, String name) {
        Tournaments t = null;
        Query q = session.createQuery("from Tournaments t where t.name = :name");
        q.setParameter("name", name);
        t = (Tournaments) q.uniqueResult();
        return t;
    }

    public void insert(Session session, int tournamentId, String name, String description, String classification) {
        Tournaments t = new Tournaments(tournamentId, name,description, classification);
        session.save(t);
    }

    public void modified(Session session, Tournaments t,String description, String classification) {
        t.setDescription(description);
        t.setClassification(classification);
        session.update(t);
    }

    public void delete(Session session, Tournaments t) {
        session.delete(t);
    }

    public Tournaments getTournamentById(Session session, int tournamentId) {
        return session.get(Tournaments.class, tournamentId);
    }

    public void loadcombo(Session session, DefaultComboBoxModel modelcomboTournaments) {
        modelcomboTournaments.removeAllElements();
        Tournaments t ;
        Query q = session.createQuery("from Tournaments t");
        List<Tournaments> listTournaments = q.list();
        Iterator it = listTournaments.iterator();
        
        while(it.hasNext()){
            modelcomboTournaments.addElement(it.next());
        }
    }

    public Tournaments getTournamentByName(Session session, String tournamentName) {
        Tournaments t = null;
        Query q = session.createQuery("from Tournaments t where t.name = :tournamentName");
        q.setParameter("tournamentName", tournamentName);
        
        t = (Tournaments) q.uniqueResult();
        return t;
    }
    
}
