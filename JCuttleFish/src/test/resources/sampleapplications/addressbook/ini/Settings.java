/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ini;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Γρηγόρης
 */
public class Settings {
    
    private String path;
    public Settings()
    {
        this.path = "";
    }
    
    
    public String getPath()
    {   
        try 
        {
            URL location = this.getClass().getProtectionDomain().getCodeSource().getLocation();
            String folderPath = location.getPath();
            String decodedPath = URLDecoder.decode(folderPath, "UTF-8");
            path = decodedPath + "\\ini\\dbinfo.ini";
            
            return path;
        } 
        catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Settings.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}
