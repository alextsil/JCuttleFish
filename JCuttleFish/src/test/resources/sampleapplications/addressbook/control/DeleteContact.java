/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import API.IContact;
import API.IContactDAO;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Γρηγόρης
 */
public class DeleteContact {

    private IContactDAO dbContact;
    
    public DeleteContact(IContactDAO dbContact) {
        this.dbContact = dbContact;
    }
    
    /**
     * Delete a specific contact, given as parameter, from database
     * @param delContact
     * @exception 
     */
    public void delete(IContact delContact)
    {
        try 
        {         
            dbContact.deleteContact(delContact);
        } 
        catch (Exception ex) 
        {
            Logger.getLogger(DeleteContact.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
