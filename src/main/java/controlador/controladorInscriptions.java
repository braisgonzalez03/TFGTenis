/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import controlador.factory.HibernateUtil;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.dao.InscriptionsDAO;
import modelo.dao.PlayersDAO;
import modelo.dao.TournamentsDAO;
import modelo.vo.Inscriptions;
import modelo.vo.Players;
import modelo.vo.Tournaments;
import org.hibernate.Session;
import vista.InscriptionsManager;

/**
 *
 * @author brais
 */
public class controladorInscriptions {

    public static Session session;
    public static PlayersDAO plaDAO;
    public static TournamentsDAO tourDAO;
    public static InscriptionsDAO insDAO;
    public static InscriptionsManager ventana = new InscriptionsManager();
    static DefaultComboBoxModel modelcomboPlayers = new DefaultComboBoxModel();
    static DefaultComboBoxModel modelcomboTournaments = new DefaultComboBoxModel();
    static DefaultTableModel modelTable = new DefaultTableModel();

    public static void iniciar() {
        iniciaSession();
        ventana.setVisible(true);
        ventana.setLocationRelativeTo(null);
        modelTable.setRowCount(0);
        modelTable = (DefaultTableModel) ventana.getTblInscriptions().getModel();
        ventana.getCmbPlayers().setModel(modelcomboPlayers);
        ventana.getCmbTournaments().setModel(modelcomboTournaments);
    }

    public static void iniciaSession() {
        session = HibernateUtil.getSessionFactory().openSession();
        plaDAO = HibernateUtil.getPlayersDAO();
        tourDAO = HibernateUtil.getTournamentsDAO();
        insDAO = HibernateUtil.getInscriptionsDAO();
    }

    public static void cerrarSession() {
        session.close();
    }

    public static void listInscriptions() {
        try {
            HibernateUtil.beginTx(session);
            insDAO.getAllInscriptions(session, modelTable, ventana.getLblInscriptions());
            HibernateUtil.commitTx(session);
        } catch (Exception ex) {
            Logger.getLogger(controladorTournament.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void loadCombo() {
        try {
            HibernateUtil.beginTx(session);
            plaDAO.loadcombo(session, modelcomboPlayers);
            tourDAO.loadcombo(session, modelcomboTournaments);
            HibernateUtil.commitTx(session);
        } catch (Exception ex) {
            session.getTransaction().rollback();
            Logger.getLogger(controladorInscriptions.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void insert() {
        if (ventana.getTxtInscriptionId().getText().isEmpty()
                || ventana.getCmbPlayers().getSelectedItem() == null
                || ventana.getCmbTournaments().getSelectedItem() == null
                || ventana.getJdcStartDate().getDate() == null) {
            JOptionPane.showMessageDialog(null, "Data missing");
            return;
        }

        try {
            HibernateUtil.beginTx(session);

            String playerName = ventana.getCmbPlayers().getSelectedItem().toString();
            String tournamentName = ventana.getCmbTournaments().getSelectedItem().toString();

            Players p = plaDAO.getPlayerByName(session, playerName);
            Tournaments t = tourDAO.getTournamentByName(session, tournamentName);

            if (p == null || t == null) {
                JOptionPane.showMessageDialog(null, "Player or tournament not found");
                return;
            }

            Inscriptions activeInscription = insDAO.getActiveInscription(session, p.getPlayerId(), t.getTournamentId());
            if (activeInscription != null) {
                JOptionPane.showMessageDialog(null, "This player is already actively registered in this tournament");
                return;
            }

            Date startDate = ventana.getJdcStartDate().getDate();
            Date endDate = ventana.getJdcEndDate().getDate();

            if (endDate != null && endDate.before(startDate)) {
                JOptionPane.showMessageDialog(null, "End date must be after start date");
                return;
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String formattedStartDate = sdf.format(startDate);
            String formattedEndDate = (endDate != null) ? sdf.format(endDate) : "null";

            insDAO.insert(session,
                    Integer.parseInt(ventana.getTxtInscriptionId().getText()),
                    p,
                    t,
                    startDate,
                    endDate);

            JOptionPane.showMessageDialog(null, "Inscription inserted");
            insDAO.getAllInscriptions(session, modelTable, ventana.getLblInscriptions());
            plaDAO.loadcombo(session, modelcomboPlayers);
            tourDAO.loadcombo(session, modelcomboTournaments);
            session.getTransaction().commit();

        } catch (NumberFormatException ex) {
            session.getTransaction().rollback();
            JOptionPane.showMessageDialog(null, "Data entry error");
        } catch (Exception ex1) {
            session.getTransaction().rollback();
            Logger.getLogger(controladorInscriptions.class.getName()).log(Level.SEVERE, null, ex1);
        }
    }

    public static void loadData() {
        if (ventana.getTxtInscriptionId().getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Data missing");
            return;
        }

        try {
            HibernateUtil.beginTx(session);

            Inscriptions i = insDAO.getInscriptionId(session, Integer.parseInt(ventana.getTxtInscriptionId().getText()));

            if (i != null) {
                ventana.getCmbPlayers().setSelectedItem(i.getPlayersId());
                ventana.getCmbTournaments().setSelectedItem(i.getTournamentsId());
                ventana.getJdcStartDate().setDate(i.getStartDate());
                ventana.getJdcEndDate().setDate(i.getEndDate());
            } else {
                cleandata();
            }
            HibernateUtil.commitTx(session);
        } catch (NumberFormatException ex1) {
            session.getTransaction().rollback();
            JOptionPane.showMessageDialog(null, "Error en la entrada de datos");
        } catch (Exception ex2) {
            session.getTransaction().rollback();
            Logger.getLogger(controladorInscriptions.class.getName()).log(Level.SEVERE, null, ex2);
        }
    }

    private static void cleandata() {
        ventana.getCmbPlayers().setSelectedItem(0);
        ventana.getCmbTournaments().setSelectedItem(0);
        ventana.getJdcStartDate().setDate(null);
        ventana.getJdcEndDate().setDate(null);
    }

    public static void modify() {
        if (ventana.getTxtInscriptionId().getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Data missing");
        }

        try {
            HibernateUtil.beginTx(session);
            int inscriptionId = Integer.parseInt(ventana.getTxtInscriptionId().getText());
            Inscriptions i = insDAO.getInscriptionId(session, inscriptionId);

            if (i == null) {
                JOptionPane.showMessageDialog(null, "Inscription not found");
                return;
            }

            Date starDate = i.getStartDate();
            Date newDateEnd = ventana.getJdcEndDate().getDate();
            if (newDateEnd != null && newDateEnd.before(starDate)) {
                JOptionPane.showMessageDialog(null, "End date cannot be before the start date.");
                return;
            }
            
            insDAO.modified(session, i,newDateEnd);
            JOptionPane.showMessageDialog(null, "Inscription updated successfully");
            session.getTransaction().commit();
            insDAO.getAllInscriptions(session, modelTable, ventana.getLblInscriptions());
            
        }catch (NumberFormatException ex1) {
            session.getTransaction().rollback();
            JOptionPane.showMessageDialog(null, "Data entry error");
        } catch (Exception ex2) {
            session.getTransaction().rollback();
            Logger.getLogger(controladorInscriptions.class.getName()).log(Level.SEVERE, null, ex2);
        }
    }

    public static void delete() {
        if(ventana.getTxtInscriptionId().getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Data missing");
            return;
        }
        
        try{
            HibernateUtil.beginTx(session);
            int inscriptionId = Integer.parseInt(ventana.getTxtInscriptionId().getText());
            
            Inscriptions i = insDAO.getInscriptionId(session, inscriptionId);
            
            if(i == null){
                JOptionPane.showMessageDialog(null, "Inscription not found");
            }
            insDAO.delete(session, i);
            
            JOptionPane.showMessageDialog(null, "Inscriptions successfully removed");
            
            session.getTransaction().commit();
            insDAO.getAllInscriptions(session, modelTable, ventana.getLblInscriptions());
        }catch (NumberFormatException ex1) {
            session.getTransaction().rollback();
            JOptionPane.showMessageDialog(null, "Data entry error");
        } catch (Exception ex2) {
            session.getTransaction().rollback();
            Logger.getLogger(controladorInscriptions.class.getName()).log(Level.SEVERE, null, ex2);
        }
    }
}
