/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import static controlador.controladorPlayer.insDAO;
import static controlador.controladorPlayer.modelTable;
import static controlador.controladorPlayer.plaDAO;
import static controlador.controladorPlayer.session;
import static controlador.controladorPlayer.ventana;
import controlador.factory.HibernateUtil;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.PersistenceException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.dao.InscriptionsDAO;
import modelo.dao.PlayersDAO;
import modelo.dao.TournamentsDAO;
import modelo.vo.Tournaments;
import org.hibernate.Session;
import vista.PlayerManager;
import vista.TournamentManager;

/**
 *
 * @author brais
 */
public class controladorTournament {

    public static Session session;
    public static TournamentsDAO tourDAO;
    public static PlayersDAO plaDAO;
    public static InscriptionsDAO insDAO;
    public static TournamentManager ventana = new TournamentManager();
    static DefaultTableModel modelTable = new DefaultTableModel();

    public static void iniciar() {
        iniciaSession();
        ventana.setVisible(true);
        ventana.setLocationRelativeTo(null);
        modelTable.setRowCount(0);
        modelTable = (DefaultTableModel) ventana.getTblTournament().getModel();
    }

    public static void iniciaSession() {
        session = HibernateUtil.getSessionFactory().openSession();
        tourDAO = HibernateUtil.getTournamentsDAO();
        insDAO = HibernateUtil.getInscriptionsDAO();
    }

    public static void cerrarSession() {
        session.close();
    }

    public static void listTennisPlayers() {
        try {
            HibernateUtil.beginTx(session);
            tourDAO.getAllTournament(session, modelTable, ventana.getLblTournament());

            HibernateUtil.commitTx(session);
        } catch (Exception ex) {
            Logger.getLogger(controladorTournament.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void insert() {
        if (ventana.getTxtTournamentId().getText().isEmpty() || ventana.getTxtName().getText().isEmpty()
                || ventana.getTxtDescription().getText().isEmpty() || ventana.getTxtClassification().getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Data missing");
            return;
        }

        try {
            HibernateUtil.beginTx(session);

            String name = ventana.getTxtName().getText();

            Tournaments t = tourDAO.getTournament(session, name);

            if (t != null) {
                JOptionPane.showMessageDialog(null, "A tournament with that name and date already exists");
                return;
            } else {
                tourDAO.insert(session, Integer.parseInt(ventana.getTxtTournamentId().getText()),
                        name,
                        ventana.getTxtDescription().getText(),
                        ventana.getTxtClassification().getText());

                JOptionPane.showMessageDialog(null, "Tournament inserted");

            }

            session.getTransaction().commit();
            tourDAO.getAllTournament(session, modelTable, ventana.getLblTournament());
        } catch (NumberFormatException ex) {
            session.getTransaction().rollback();
            JOptionPane.showMessageDialog(null, "Data entry error");
        } catch (Exception ex1) {
            session.getTransaction().rollback();
            Logger.getLogger(controladorTournament.class.getName()).log(Level.SEVERE, null, ex1);
        }
    }

    public static void modify() {
        if (ventana.getTxtName().getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Data missing");
            return;
        }

        try {
            HibernateUtil.beginTx(session);
            int tournamentId = Integer.parseInt(ventana.getTxtTournamentId().getText());

            Tournaments t = tourDAO.getTournamentById(session, tournamentId);
            if (t != null) {
                String description = ventana.getTxtDescription().getText();
                String classification = ventana.getTxtClassification().getText();

                tourDAO.modified(session, t, description, classification);

            } else {
                JOptionPane.showMessageDialog(null, "Tournament don't exists");
                return;
            }

            session.getTransaction().commit();
            tourDAO.getAllTournament(session, modelTable, ventana.getLblTournament());
            JOptionPane.showMessageDialog(null, "Modified tournament");
        } catch (NumberFormatException ex1) {
            session.getTransaction().rollback();
            JOptionPane.showMessageDialog(null, "Data entry error");
        } catch (Exception ex2) {
            session.getTransaction().rollback();
            Logger.getLogger(controladorTournament.class.getName()).log(Level.SEVERE, null, ex2);
        }
    }

    public static void delete() {
        if (ventana.getTxtName().getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Data missing");
            return;
        }

        try {
            HibernateUtil.beginTx(session);
            int tournamentId = Integer.parseInt(ventana.getTxtTournamentId().getText());

            Tournaments t = tourDAO.getTournamentById(session, tournamentId);

            if (t != null) {

                if (t.getInscriptionsList() != null) {
                    insDAO.deleteInscriptions(session, t.getInscriptionsList());
                }

                tourDAO.delete(session, t);
                JOptionPane.showMessageDialog(null, "tournament and registration successfully removed");
            } else {
                JOptionPane.showMessageDialog(null, "Tournament don't exists");
            }
            session.getTransaction().commit();
            tourDAO.getAllTournament(session, modelTable, ventana.getLblTournament());
        } catch (PersistenceException ex) {
            JOptionPane.showMessageDialog(null, "The player cannot be deleted");
            session.getTransaction().rollback();
        } catch (NumberFormatException ex1) {
            session.getTransaction().rollback();
            JOptionPane.showMessageDialog(null, "Data entry error");
        } catch (Exception ex2) {
            session.getTransaction().rollback();
            Logger.getLogger(controladorTournament.class.getName()).log(Level.SEVERE, null, ex2);
        }
    }

    public static void loaddata() {
        if (ventana.getTxtTournamentId().getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Data missing");
            return;
        }

        try {
            HibernateUtil.beginTx(session);

            Tournaments t = tourDAO.getTournamentById(session, Integer.parseInt(ventana.getTxtTournamentId().getText()));

            if (t != null) {
                ventana.getTxtName().setText(t.getName());
                ventana.getTxtDescription().setText(t.getDescription());
                ventana.getTxtClassification().setText(t.getClassification());
            } else {
                cleandata();
            }
            HibernateUtil.commitTx(session);
        } catch (NumberFormatException ex1) {
            session.getTransaction().rollback();
            JOptionPane.showMessageDialog(null, "Error en la entrada de datos");
        } catch (Exception ex2) {
            session.getTransaction().rollback();
            Logger.getLogger(controladorPlayer.class.getName()).log(Level.SEVERE, null, ex2);
        }
    }

    private static void cleandata() {
        ventana.getTxtName().setText("");
        ventana.getTxtDescription().setText("");
        ventana.getTxtClassification().setText("");
    }

}
