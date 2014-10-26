/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control.dao;

import API.IContact;
import API.IContactDAO;

/**
 *
 * @author Γρηγόρης
 */
public class DataToDB {
    
    private IContactDAO contactDAO;

    public DataToDB(IContactDAO contactDAO) {
        this.contactDAO = contactDAO;
    }
    
    /**
     * 
     * @param saveContact
     * @throws Exception 
     */
    public void saveContact(IContact saveContact) throws Exception
    {
        contactDAO.addContact(saveContact);
    }
    
    /**
     * 
     * @param updateContact
     * @throws Exception 
     */
    public void updateContact(IContact updateContact) throws Exception
    {
        contactDAO.updateContact(updateContact);
    }
 
}
