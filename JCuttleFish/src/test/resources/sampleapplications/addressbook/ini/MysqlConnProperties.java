/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ini;
import java.io.*;
import java.net.URLDecoder;
import java.util.*;

/*
 * travaei apo to ini ta stoixeia sindesis kai ta kataxwrei stis metavlites tis.
 * get sinartiseis gia na pernei to connection tis mysql tis parametrous.
 * @throws java.lang.Exception
 */

public class MysqlConnProperties 
{
    private String dbIpPort;
    private String dbUser;
    private String dbPass;
    private String dbTable;

    public MysqlConnProperties() throws Exception
    {
        String path = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
        String decodedPath = URLDecoder.decode(path, "UTF-8"); //buggarei me .jar sto path (??) - ask
        Properties p = new Properties();
        FileInputStream fp = new FileInputStream( decodedPath + "\\ini\\dbinfoLocal.ini");
        
        try
        {       
            p.load(fp);
            this.dbIpPort = p.getProperty("dbIpPort");
            this.dbUser = p.getProperty("dbUser");
            this.dbPass = p.getProperty("dbPass");
            this.dbTable = p.getProperty("dbTable");         
        }
        catch (Exception e) 
        {
            System.out.println(e);
        }
        finally
        {
            if (fp!=null)
            {
                fp.close();
            }
        }
    }


    public String getDbIpPort() 
    {
        return dbIpPort;
    }
    
    public String getDbUser() 
    {
        return dbUser;
    }

    public String getDbPass() 
    {
        return dbPass;
    }

    public String getDbTable() 
    {
        return dbTable;
    }
    
}
