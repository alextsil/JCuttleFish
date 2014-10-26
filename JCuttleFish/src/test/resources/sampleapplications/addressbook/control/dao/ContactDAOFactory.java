/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control.dao;

import API.IContactDAO;
import dao.ContactDAOmysql;
import ini.PersistenceType;

/**
 *
 * @author Γρηγόρης
 */
public class ContactDAOFactory 
{
    public static IContactDAO getInstance(PersistenceType type)
    {
        switch(type)
        {
            case MYSQL:
                return new ContactDAOmysql();
            default:
                return new ContactDAOmysql();
        }
    }
}
