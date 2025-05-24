/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Encriptacion;

import org.mindrot.jbcrypt.BCrypt;


/**
 *
 * @author brais
 */
public class HashContraseñas {
    
    // Genera hash bcrypt a partir de un texto plano
    public static String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    // Verifica si la contraseña es correcta comparando con el hash ya almacenado
    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}
