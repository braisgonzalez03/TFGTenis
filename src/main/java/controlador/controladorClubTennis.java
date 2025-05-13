/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import controlador.factory.HibernateUtil;
import org.hibernate.Session;
import vista.ClubTennis;

/**
 *
 * @author brais
 */
public class controladorClubTennis {
    public static Session session;
    public static ClubTennis ventana = new ClubTennis();

    public static void iniciar() {
        ventana.setVisible(true);
        ventana.setLocationRelativeTo(null);
    }

    public static void iniciaSession() {
        session = HibernateUtil.getSessionFactory().openSession();
    }

    public static void cerrarSession() {
        session.close();
    }
}
