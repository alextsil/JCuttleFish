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
public interface IGroup {

    /**
     * Adds a single contact to this contactList
     * @param newContact
     */
    void addContact(IContact newContact);

    /**
     * Inserts a single Contact into a specified position shifts other (if they
     * exist) to the right of the list
     *
     * @param index where to place new Contact
     * @param newContact the new Contact element
     */
    void addContactToIndex(int index, IContact newContact);

    /**
     * Empties the contact list by removing all group contacts
     */
    void clearContactList();

    /**
     * Deletes a specified contact
     *
     * @param index
     */
    void deleteContact(int index);

    /**
     * Returns a single specified contact
     * @param index
     * @return contact
     */
    IContact getContactFromIndex(int index);

    /**
     * Returns an ArrayList Contact type, that contains the contacts stored in
     * this object
     *
     * @return ArrayList<Contact>
     * @throws Exception
     */
    ArrayList<IContact> getContacts();

    /**
     * returns the name of tbe group
     * @return
     */
    String getName();

    /**
     * Returns int number of the contacts stored in the ContactList object
     * @return allContacts.size();
     */
    int getSize();

    /**
     * Removes a contact from group and then returns it
     * @param contact
     * @return
     */
    IContact removeContact(IContact contact);

    /**
     * removes a contact from group index and the returns it
     * @param index
     * @return
     */
    IContact removeContact(int index);

    /**
     * Replaces a single Contact into a specified position
     *
     * @param newContact the new Contact element
     * @param index
     * @throws Exception
     */
    void setContactToIndex(int index, IContact newContact);

    /**
     * Takes an ArrayList<Contact> that contains contacts
     *
     * @param allContacts
     */
    void setGroupContacts(ArrayList<IContact> contactsArList);

    /**
     * gives a name to the group
     * @param name
     */
    void setName(String name);
    
}
