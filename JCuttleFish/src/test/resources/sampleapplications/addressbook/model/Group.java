/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import API.IGroup;
import API.IContact;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Alexei
 */
public class Group implements IGroup {

    private String name;
    private ArrayList<IContact> contactsArList;

    /**
     * Constuctor of class
     */
    public Group() {
        this.contactsArList = new ArrayList<IContact>();
        this.name = "";
    }

    /**
     * constructor with parameter
     *
     * @param allContacts
     */
    public Group(String name, ArrayList<IContact> contactsArList) {
        this.name = name;
        this.contactsArList = contactsArList;
    }    

    /**
     * returns the name of tbe group
     * @return 
     */

    @Override
    public String getName() {
        return name;
    }

    /**
     * gives a name to the group
     * @param name 
     */

    @Override
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Takes an ArrayList<Contact> that contains contacts
     *
     * @param allContacts
     */
    @Override
    public void setGroupContacts(ArrayList<IContact> contactsArList) {
        this.contactsArList = contactsArList;
    }

    /**
     * Returns an ArrayList Contact type, that contains the contacts stored in
     * this object
     *
     * @return ArrayList<Contact>
     * @throws Exception
     */
    @Override
    public ArrayList<IContact> getContacts() {
        return this.contactsArList;
    }

    /**
     * Adds a single contact to this contactList
     * @param newContact
     */
    @Override
    public void addContact(IContact newContact) {
        this.contactsArList.add(newContact);
    }
    
    /**
     * Replaces a single Contact into a specified position
     *
     * @param newContact the new Contact element
     * @param index
     * @throws Exception
     */
    @Override
    public void setContactToIndex(int index, IContact newContact) {
        this.contactsArList.set(index, newContact);
    }

    /**
     * Inserts a single Contact into a specified position shifts other (if they
     * exist) to the right of the list
     *
     * @param index where to place new Contact
     * @param newContact the new Contact element
     */
    @Override
    public void addContactToIndex(int index, IContact newContact) {
        this.contactsArList.add(index, newContact);
    }

    /**
     * Returns a single specified contact
     * @param index
     * @return contact
     */
    @Override
    public IContact getContactFromIndex(int index) {
        return contactsArList.get(index);
    }

    /**
     * Returns int number of the contacts stored in the ContactList object
     * @return allContacts.size();
     */
    @Override
    public int getSize() {
        return this.contactsArList.size();
    }

    /**
     * Empties the contact list by removing all group contacts
     */
    @Override
    public void clearContactList() {
        this.contactsArList.clear();
    }

    /**
     * Deletes a specified contact
     *
     * @param index
     */
    @Override
    public void deleteContact(int index) {
        this.contactsArList.remove(index);
    }

    /**
     * Removes a contact from group and then returns it
     * @param contact
     * @return 
     */
    @Override
    public IContact removeContact(IContact contact) {

        Iterator<IContact> itr = this.contactsArList.iterator();
        while (itr.hasNext()) {
            IContact indexContact = itr.next();
            if (indexContact.equals(contact)) {
                itr.remove();
                return indexContact;
            }
        }
        return null;
    }

    /**
     * removes a contact from group index and the returns it
     * @param index
     * @return 
     */
    @Override
    public IContact removeContact(int index) {
        IContact contact = this.contactsArList.remove(index);
        return contact;
    }
    
}
