/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import API.IContact;
import java.util.ArrayList;

/**
 * <title>Search Contact Class</title> 
 * @author Γρηγόρης
 */
public class SearchContact {
    
    private ArrayList<IContact> contactArList;
    
    /**
     * Constructor of class
     * takes a ContactList type object as parameter
     * @param sContactList 
     */
    public SearchContact(ArrayList<IContact> contactArList) 
    {
        this.contactArList = contactArList;
    }
    
    public ArrayList<IContact> search(String regex)
    {
        ArrayList<IContact> newList;
        regex = regex.trim();
        String[] searchRegex = regex.split(" ");
        if(searchRegex.length<2)
        {
            newList = searchContact(searchRegex[0]);
        }
        else
        {
            newList = searchContactSmart(searchRegex);
        }
        return newList;
    }
    
    /**
     * The search method pulls out an Contact object from
     * ContactList object on every loop. Then compares the
     * Firstname and Lastname of each Contact with the given parameter
     * @param name - text input of user
     * @return newList - ContactList type object
     */
    private ArrayList<IContact> searchContact(String regex)
    {
        ArrayList<IContact> newList = new ArrayList<IContact>();
        
        for(int i=0;i<this.contactArList.size();i++)
        {
            IContact contact=this.contactArList.get(i);

            String name = contact.getFirstname();
            String lastname = contact.getLastname();
            boolean isName = name.regionMatches( true, 0, regex, 0, regex.length() );
            boolean isSurname = lastname.regionMatches( true, 0, regex, 0, regex.length() );
            if( isName || isSurname )
            {
                newList.add(contact);
            }
        }
        return newList;
    }

    private ArrayList<IContact> searchContactSmart(String[] split)
    {
        ArrayList<IContact> newList = new ArrayList<IContact>();
        
        for(int i=0;i<this.contactArList.size();i++)
        {
            IContact contact=this.contactArList.get(i);

            String name=contact.getFirstname();
            String lastname = contact.getLastname();
            boolean isContactName = name.regionMatches( true, 0, split[0], 0, split[0].length() );
            boolean isContactLastname = lastname.regionMatches( true, 0, split[1], 0, split[1].length() );
            if( isContactName && isContactLastname )
            {
                newList.add(contact);
            }
        }
        return newList;
    }
    
}
