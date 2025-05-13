/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;
import modelo.vo.Inscriptions;
import modelo.vo.Players;
import modelo.vo.Tournaments;
import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 *
 * @author brais
 */
public class InscriptionsDAO {

    public void deleteInscriptions(Session session, List<Inscriptions> inscriptionsList) {
        for (Inscriptions inscriptions : inscriptionsList) {
            session.delete(inscriptions);
        }
    }

    public void getAllInscriptions(Session session, DefaultTableModel modelTable, JLabel lblInscriptions) {
        modelTable.setRowCount(0);
        Query q = session.createQuery("from Inscriptions i order by i.inscriptionId");
        List<Inscriptions> listInscriptions = q.list();

        for (Inscriptions i : listInscriptions) {
            Players p = i.getPlayersId();
            Tournaments t = i.getTournamentsId();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String formattedStartDate = sdf.format(i.getStartDate());
            String formattedEndDate = (i.getEndDate() != null) ? sdf.format(i.getEndDate()) : "";

            modelTable.addRow(new Object[]{
                i.getInscriptionId(),
                formattedStartDate,
                formattedEndDate,
                p.getPlayerId(),
                p.getName(),
                t.getTournamentId(),
                t.getName()
            });
        }

        if (listInscriptions.isEmpty()) {
            lblInscriptions.setText("There are no inscriptions");
        } else {
            lblInscriptions.setText("Number of Inscriptions: " + listInscriptions.size());
        }

    }

    public Inscriptions getInscription(Session session, String playerName, String tournamentName) {
        Inscriptions i = null;
        Query q = session.createQuery("from Inscriptions i where i.playersId.name = :playerName and i.tournamentsId.name = :tournamentName");
        q.setParameter("playerName", playerName);
        q.setParameter("tournamentName", tournamentName);

        i = (Inscriptions) q.uniqueResult();
        return i;
    }

    public Inscriptions getActiveInscription(Session session, Integer playerId, Integer tournamentId) {
        Inscriptions i = null;
        Query q = session.createQuery("from Inscriptions i where i.playersId.playerId = :playerId and i.tournamentsId.tournamentId = :tournamentId and i.endDate is null");
        q.setParameter("playerId", playerId);
        q.setParameter("tournamentId", tournamentId);
        i = (Inscriptions) q.uniqueResult();
        return i;
    }

    public void insert(Session session, int inscriptionId, Players p, Tournaments t, Date startDate, Date endDate) {
        Inscriptions i = new Inscriptions(inscriptionId, startDate, endDate, p, t);
        session.save(i);
    }

    public Inscriptions getInscriptionId(Session session, int inscriptionsId) {
        return session.get(Inscriptions.class, inscriptionsId);
    }

}
