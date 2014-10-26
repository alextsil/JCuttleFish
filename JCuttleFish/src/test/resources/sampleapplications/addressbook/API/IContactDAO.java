/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package API;

import java.util.ArrayList;

/**
 *
 * @author Γρηγόρης
 */
public interface IContactDAO {

    /*
     * Insert a single contact into the database
     * @throws java.lang.Exception
     */
    void addContact(IContact insContact) throws Exception;
    
    void addGroup(IGroup newGroup) throws Exception;

    /**
     * Deletes a Contact from the database, identified by id.
     * @param dContact
     * @throws Exception
     */
    void deleteContact(IContact delContact) throws Exception;
    
    void deleteGroup(IGroup delGroup) throws Exception;

    /**
     * Returns all contacts from the database, ordered by id
     * @return ContactList
     * @throws java.lang.Exception
     */
    ArrayList<IContact> getContactsArList() throws Exception;
    
    ArrayList<IGroup> getGroupArList() throws Exception;

    /**
     * Updates an old Contact with the new information given. identified by id.
     * @param updContact
     * @throws Exception
     */
    void updateContact(IContact updContact) throws Exception;
    
    void updateGroup(IGroup updGroup) throws Exception;
}
