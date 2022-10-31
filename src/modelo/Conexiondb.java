/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.*;
import javax.swing.JOptionPane;

/**
 *
 * @author CESAR DIAZ MARADIAGA
 */
public class Conexiondb {
    String db = "cdstorered";
    String url = "jdbc:mysql://localhost/" + db;
    //String url  = "jdbc:mysql://bvbm7ywbtgjjsmoizw52-mysql.services.clever-cloud.com:3306/bvbm7ywbtgjjsmoizw52?autoReconnect=true&useSSL=false"; 
    //String user = "urms3pphdcqteljh";
    String user = "root";
    //String pass = "NMFPsrOAeYFqJU9DWdGO";
    String pass = "19199697tsoCD";

    public Conexiondb() {

    }

    public Connection Conexion() {
        Connection link = null;
        try {
            //cargamos el Driver a 
            //Class.forName("org.gjt.mm.mysql.Driver");
            Class.forName("com.mysql.jdbc.Driver");

            link = DriverManager.getConnection(this.url, this.user, this.pass);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return link;
    }
}
