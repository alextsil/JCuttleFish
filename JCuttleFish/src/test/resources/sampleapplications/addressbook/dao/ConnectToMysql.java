/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import ini.MysqlConnProperties;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

    /**
    * Connect to database. --> dimiourgei obj p periexei ta stoixeia sindesis.
    * @return Connection to database
    * @throws java.lang.Exception
    */
public class ConnectToMysql {
    
    public static Connection getDBConnection()
    {
        try 
        {
            MysqlConnProperties dbSettings = new MysqlConnProperties();
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver"); //fortonei sto runtime tous drivers. giati??
            String url = "jdbc:mysql://" + dbSettings.getDbIpPort() + "/" + dbSettings.getDbTable();
            return DriverManager.getConnection( url,dbSettings.getDbUser(), dbSettings.getDbPass());
        } 
        catch (Exception ex) 
        {
            JOptionPane.showMessageDialog( null, "Σφάλμα σύνδεσης με τη βάση δεδομένων", "Σφάλμα σύνδεσης", JOptionPane.ERROR_MESSAGE );
            Logger.getLogger(ConnectToMysql.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}
