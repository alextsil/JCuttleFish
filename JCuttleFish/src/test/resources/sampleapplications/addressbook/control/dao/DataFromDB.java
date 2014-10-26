/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control.dao;

import API.IContactDAO;
import API.IContactList;
import API.IGroup;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.ContactList;
import model.Group;

/**
 *
 * @author Γρηγόρης
 */
public class DataFromDB {
    
    private IContactDAO dbContactDAO; 

    public DataFromDB(IContactDAO dbContactDAO) {
        this.dbContactDAO = dbContactDAO;
    }
    
    /**
     * Creates new ContactList and gets all
     * contacts stored in database
     * @return cList - ContactList type object
     */
    public IContactList getContactList()
    {
        ContactList contactList = new ContactList();
        try 
        {
            ArrayList<IGroup> groupArList = new ArrayList<IGroup>();
            Group group = new Group();
            group.setGroupContacts( dbContactDAO.getContactsArList() );
            group.setName("friends");
            groupArList.add(group);
            
            Group group2 = new Group();
            group2.setGroupContacts( dbContactDAO.getContactsArList() );
            group2.setName("family");
            groupArList.add(group2);
     
            contactList.setGroupArList(groupArList);
        } 
        catch (Exception ex) 
        {
            Logger.getLogger(DataFromDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally
        {
            return contactList;
        }
    }
}
