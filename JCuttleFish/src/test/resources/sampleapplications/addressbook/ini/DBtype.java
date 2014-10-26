/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ini;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Γρηγόρης
 */
public class DBtype {
    
    /**
     *
     * @return
     */
    public static final PersistenceType db_type = getType();
    
    private static PersistenceType getType()
    {
       
        Settings settings = new Settings();
        String path = settings.getPath();
            
        try 
        {
            FileInputStream fp = new FileInputStream(path);
            Properties prop = new Properties();
            prop.load(fp);
            String dbName = prop.getProperty("dbType");
            
            if(dbName.endsWith("Mysql"))
            {
                return PersistenceType.MYSQL;
            }
            else
            {
                return PersistenceType.MYSQL;
            }
        }
        catch (IOException ex) 
        {
            Logger.getLogger(DBtype.class.getName()).log(Level.SEVERE, null, ex);
        }
        return PersistenceType.XML;
    }
}
