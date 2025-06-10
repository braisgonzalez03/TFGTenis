/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import Encriptacion.HashContraseñas;
import controlador.factory.HibernateUtil;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.PersistenceException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.dao.InscriptionsDAO;
import modelo.dao.PlayersDAO;
import modelo.vo.Inscriptions;
import modelo.vo.Players;
import org.hibernate.Session;
import vista.PlayerManager;

/**
 *
 * @author acceso a datos
 */
public class controladorPlayer {

    public static Session session;
    public static PlayersDAO plaDAO;
    public static InscriptionsDAO insDAO;
    public static PlayerManager ventana = new PlayerManager();
    static DefaultTableModel modelTable = new DefaultTableModel();

    public static void iniciar() {
        iniciaSession();
        ventana.setVisible(true);
        ventana.setLocationRelativeTo(null);
        modelTable.setRowCount(0);
        modelTable = (DefaultTableModel) ventana.getTblPlayers().getModel();
    }

    public static void iniciaSession() {
        session = HibernateUtil.getSessionFactory().openSession();
        plaDAO = HibernateUtil.getPlayersDAO();
        insDAO = HibernateUtil.getInscriptionsDAO();
    }

    public static void cerrarSession() {
        session.close();
    }

    public static void listTennisPlayers() {
        try {
            HibernateUtil.beginTx(session);

            plaDAO.getAllPlayers(session, modelTable, ventana.getLblNumPlayers());

            HibernateUtil.commitTx(session);

        } catch (Exception ex) {
            Logger.getLogger(controladorPlayer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void insert() {
        if (ventana.getTxtPlayerId().getText().isEmpty() || ventana.getTxtName().getText().isEmpty()
                || ventana.getTxtSurnames().getText().isEmpty() || ventana.getTxtEmail().getText().isEmpty()
                || ventana.getTxtPhone().getText().isEmpty() || ventana.getTxtDni().getText().isEmpty()
                || ventana.getTxtUserName().getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Data missing");
            return;
        }

        try {
            HibernateUtil.beginTx(session);
            
            char[] password = ventana.getTxtPassword().getPassword();
            
            if(password.length == 0){
                JOptionPane.showMessageDialog(null, "La contraseña está vacía");
                return; 
            }
            
            String dni = ventana.getTxtDni().getText();
            String userName = ventana.getTxtName().getText();
            String passwordText = new String(password);
            
            Players p = plaDAO.getPlayer(session, dni, userName);

            if (p != null) {
                JOptionPane.showMessageDialog(null, "A player with that DNI or username already exists");
            } else {
                String passwordEncrypt = HashContraseñas.hashPassword(passwordText);
                
                plaDAO.insert(session, Integer.parseInt(ventana.getTxtPlayerId().getText()),
                        ventana.getTxtName().getText(),
                        ventana.getTxtSurnames().getText(),
                        ventana.getTxtEmail().getText(),
                        Integer.parseInt(ventana.getTxtPhone().getText()),
                        ventana.getTxtDni().getText(),
                        ventana.getTxtUserName().getText(),
                        passwordEncrypt);

                JOptionPane.showMessageDialog(null, "Player inserted");
            }
            plaDAO.getAllPlayers(session, modelTable, ventana.getLblNumPlayers());
            session.getTransaction().commit();
        } catch (NumberFormatException ex) {
            session.getTransaction().rollback();
            JOptionPane.showMessageDialog(null, "Data entry error");
        } catch (Exception ex1) {
            session.getTransaction().rollback();
            Logger.getLogger(controladorPlayer.class.getName()).log(Level.SEVERE, null, ex1);
        }
    }

    public static void modify() {
        if (ventana.getTxtPlayerId().getText().isEmpty() || ventana.getTxtName().getText().isEmpty()
                || ventana.getTxtSurnames().getText().isEmpty() || ventana.getTxtEmail().getText().isEmpty()
                || ventana.getTxtPhone().getText().isEmpty() || ventana.getTxtDni().getText().isEmpty()
                || ventana.getTxtUserName().getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Data missing");
            return;
        }
        
        String finalPassword = null;
        try {
            HibernateUtil.beginTx(session);
            
            char[] password = ventana.getTxtPassword().getPassword();
            
            String dni = ventana.getTxtDni().getText();
            String userName = ventana.getTxtUserName().getText();
            
            Players p = plaDAO.getPlayer(session, dni, userName);
            
            if(password.length > 0){
                String passwordEncrypt = new String(password);
                finalPassword = HashContraseñas.hashPassword(passwordEncrypt);
            }else{
                finalPassword = p.getPassword();
            }
            
            if (p != null) {
                plaDAO.modified(session, p, 
                        ventana.getTxtEmail().getText(), 
                        Integer.parseInt(ventana.getTxtPhone().getText()), 
                        finalPassword);
            } else {
                JOptionPane.showMessageDialog(null, "Player don't exists");
                return;
            }
            
            

            session.getTransaction().commit();
            plaDAO.getAllPlayers(session, modelTable, ventana.getLblNumPlayers());
            JOptionPane.showMessageDialog(null, "Modified player");
        } catch (NumberFormatException ex1) {
            session.getTransaction().rollback();
            JOptionPane.showMessageDialog(null, "Data entry error");
        } catch (Exception ex2) {
            session.getTransaction().rollback();
            Logger.getLogger(controladorPlayer.class.getName()).log(Level.SEVERE, null, ex2);
        }
    }

    public static void loaddata() {
        if (ventana.getTxtPlayerId().getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Data missing");
            return;
        }

        try {
            HibernateUtil.beginTx(session);
            Players p = plaDAO.getPlayerByPlayerId(session, Integer.parseInt(ventana.getTxtPlayerId().getText()));

            if (p != null) {
                ventana.getTxtName().setText(p.getName());
                ventana.getTxtSurnames().setText(p.getSurnames());
                ventana.getTxtEmail().setText(p.getEmail());
                ventana.getTxtPhone().setText(p.getPhone() + "");
                ventana.getTxtDni().setText(p.getDni());
                ventana.getTxtUserName().setText(p.getUserName());
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
        ventana.getTxtSurnames().setText("");
        ventana.getTxtEmail().setText("");
        ventana.getTxtPhone().setText("");
        ventana.getTxtDni().setText("");
        ventana.getTxtUserName().setText("");
        ventana.getTxtPassword().setText("");
    }

    public static void delete() {
        if (ventana.getTxtPlayerId().getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Data missing");
            return;
        }

        try {
            HibernateUtil.beginTx(session);
            Players p = plaDAO.getPlayerByPlayerId(session, Integer.parseInt(ventana.getTxtPlayerId().getText()));

            if (p != null) {
                boolean canDeletePlayer = true;
                if (p.getInscriptionsList() != null && !p.getInscriptionsList().isEmpty()) {
                    for (Inscriptions inscription : p.getInscriptionsList()) {
                        if (inscription.getEndDate() == null) {
                            canDeletePlayer = false;
                            JOptionPane.showMessageDialog(null, "Cannot delete player because they have active tournament registrations.");
                            break;
                        }
                    }
                    if (canDeletePlayer) {
                        insDAO.deleteInscriptions(session, p.getInscriptionsList());
                        plaDAO.delete(session, p);
                        cleandata();
                        JOptionPane.showMessageDialog(null, "Inscriptions and Player deleted");
                    }
                } else {
                    plaDAO.delete(session, p);
                    cleandata();
                    JOptionPane.showMessageDialog(null, "Player deleted");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Player doesn't exist");
            }
            session.getTransaction().commit();
            plaDAO.getAllPlayers(session, modelTable, ventana.getLblNumPlayers());

        } catch (PersistenceException ex) {
            JOptionPane.showMessageDialog(null, "The player cannot be deleted");
            session.getTransaction().rollback();
        } catch (NumberFormatException ex1) {
            session.getTransaction().rollback();
            JOptionPane.showMessageDialog(null, "Data entry error");
        } catch (Exception ex2) {
            session.getTransaction().rollback();
            Logger.getLogger(controladorPlayer.class.getName()).log(Level.SEVERE, null, ex2);
        }
    }

}
