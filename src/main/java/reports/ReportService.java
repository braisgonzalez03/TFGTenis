/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package reports;

import controlador.factory.HibernateUtil;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Map;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import org.hibernate.Session;
import vista.MainJasper;

/**
 *
 * @author brais
 */
public class ReportService {

    private final String url;
    private final String username;
    private final String password;

    public ReportService(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public Report get(String reportPath) throws Exception {
        return get(reportPath, null);
    }

    public Report get(String reportPath, Map<String, Object> parameters) throws Exception {
    // Cargar el driver de MySQL
    Class.forName("com.mysql.cj.jdbc.Driver");

    // Cargar el informe .jrxml como InputStream
    try (InputStream is = MainJasper.class.getResourceAsStream(reportPath)) {
        // Asegúrate de que el InputStream no sea null
        if (is == null) {
            throw new IllegalArgumentException("No se pudo encontrar el archivo del informe: " + reportPath);
        }

        // Abrir conexión con la base de datos
        try (Connection con = DriverManager.getConnection(url, username, password)) {
            // Compilar el informe
            JasperReport jr = JasperCompileManager.compileReport(is);

            // Rellenar el informe con la conexión y los parámetros
            JasperPrint jp = JasperFillManager.fillReport(jr, parameters, con);

            // Devolver el reporte
            return new Report(jp);
        }
    }
}
}
