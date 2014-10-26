/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import API.IContact;
import API.IContactDAO;
import API.IGroup;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Contact;

/**
 *
 * @author Alexei
 */
public class ContactDAOmysql implements IContactDAO {
    
    
    /*
    * Insert a single contact into the database
    * @throws java.lang.Exception
    */
    @Override
    public void addContact(IContact insContact) throws Exception
    {
       Connection conn = ConnectToMysql.getDBConnection();   
       try
       {
            PreparedStatement pre = conn.prepareStatement("insert into contacts (firstname, lastname, email, telephone, mtelephone, address, city, group)"
                 + " values (?, ?, ?, ?, ?, ?, ?, ?)");
            pre.setString (1,insContact.getFirstname() ); 
            pre.setString (2,insContact.getLastname() );
            pre.setString (3,insContact.getEmail() );
            pre.setString (4,insContact.getTelephone() );
            pre.setString (5,insContact.getMtelephone() );
            pre.setString (6,insContact.getAddress() );
            pre.setString (7,insContact.getCity() );
            pre.execute();
       }
       catch(SQLException sqlexc)
       {
           System.out.println(sqlexc.getMessage());
       }
       finally
       {
            conn.close();
       }
    }

    /**
     * Deletes a Contact from the database, identified by id.
     * @param dContact
     * @throws Exception
     */
    @Override
    public void deleteContact(IContact delContact) throws Exception 
    {
        Connection conn = ConnectToMysql.getDBConnection();
        try
        {
            PreparedStatement pre = conn.prepareStatement("delete from contacts where id=?");
            pre.setString(1, delContact.getId());
            pre.execute();  
        }
        catch(SQLException sqlexc)
        {
            System.out.println(sqlexc.getMessage());
        }
        finally
        {
            conn.close();
        }
    }

    /**
     * Updates an old Contact with the new information given. identified by id.
     * @param updContact
     * @throws Exception
     */
    @Override
    public void updateContact(IContact updContact) throws Exception 
    {
        Connection conn = ConnectToMysql.getDBConnection();
        try
        {
            PreparedStatement pre = conn.prepareStatement("update contacts set firstname=?, lastname=?, email=?, telephone=?, mtelephone=?, address=?, city=?, group=? where id=?");
            pre.setString(1, updContact.getFirstname());
            pre.setString(2, updContact.getLastname());
            pre.setString(3, updContact.getEmail());
            pre.setString(4, updContact.getTelephone());
            pre.setString(5, updContact.getMtelephone());
            pre.setString(6, updContact.getAddress());
            pre.setString(7, updContact.getCity());
            pre.setString(8, updContact.getId());
            pre.execute();
        }
        catch(SQLException sqlexc)
        {
            System.out.println(sqlexc.getMessage());
        }
        finally
        {
        conn.close();
        }
    }

    /**
     * Returns all contacts from the database, ordered by id
     * @return ContactList
     * @throws java.lang.Exception
     */
    @Override
    public ArrayList<IContact> getContactsArList() throws Exception 
    {
        //ta orizw eksw apo to try giati alliws einai orata mono mesa sto try
        Connection conn = ConnectToMysql.getDBConnection();
        ArrayList<IContact> allContacts=new ArrayList<IContact>();
        try
        {
            PreparedStatement pre = conn.prepareStatement("select * from contacts order by id");
            ResultSet rs = pre.executeQuery();
            while (rs.next()) 
            {
                String firstname,telephone;
                firstname=rs.getString(2);
                telephone=rs.getString(5);

                Contact newContact = new Contact(firstname,telephone);
                newContact.setId(rs.getString(1));
                newContact.setLastname(rs.getString(3));
                newContact.setEmail(rs.getString(4));
                newContact.setMtelephone(rs.getString(6));
                newContact.setAddress(rs.getString(7));
                newContact.setCity(rs.getString(8));

                allContacts.add(newContact);  
            }  
        }
        catch(SQLException sqlexc)
        {
            System.out.println(sqlexc.getMessage());
        }
        finally
        {
            conn.close(); 
            return allContacts;
        }
    }

    @Override
    public void addGroup(IGroup newGroup) throws Exception {
        //TODO: code for saving new group inside db
    }

    @Override
    public void deleteGroup(IGroup delGroup) throws Exception {
        //TODO: code for deleting a group from db
    }

    @Override
    public ArrayList<IGroup> getGroupArList() throws Exception {
        //TODO: code for returning all contacts inside db (just like the old method <code>getContactsArList()</code> )
        //but this time grouped by group name
        return null;
    }

    @Override
    public void updateGroup(IGroup updGroup) throws Exception {
        //TODO: code for updating group data
    }
}