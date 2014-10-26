/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import API.IContactList;
import API.IContact;
import API.IGroup;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Γρηγόρης
 */
public class ContactList implements IContactList {
    
    protected ArrayList<IGroup> groupArList;

    public ContactList() {
        this.groupArList = new ArrayList<IGroup>();
    }

    public ContactList(ArrayList<IGroup> groupArList) {
        this.groupArList = groupArList;
    }

    @Override
    public ArrayList<IGroup> getGroupArList() {
        return groupArList;
    }

    @Override
    public void setGroupArList(ArrayList<IGroup> groupArList) {
        this.groupArList = groupArList;
    }
    
    @Override
    public ArrayList<IContact> getContacts() {
        ArrayList<IContact> contactArList = new ArrayList<IContact>();
        Iterator<IGroup> groupItr = this.groupArList.iterator();
        while( groupItr.hasNext() )
        {
            IGroup group = groupItr.next();
            Iterator<IContact> contactItr = group.getContacts().iterator();
            while(contactItr.hasNext())
            {
                IContact contact = contactItr.next();
                contactArList.add(contact);
            }
        }
        return contactArList;
    }
    
    @Override
    public ArrayList<IContact> getGroupContacts(String groupName)
    {
        ArrayList<IContact> contactArList = null;
        if(groupName == "none")
        {
            contactArList = getContacts();
        }
        else
        {
            Iterator<IGroup> groupItr = this.groupArList.iterator();
            while(groupItr.hasNext())
            {
                IGroup group = groupItr.next();
                if( group.getName().equals(groupName) )
                {
                    contactArList = group.getContacts();
                }
            }    
        }
        return contactArList;
    }
    
    @Override
    public IGroup getGroup(String groupName)
    {
        IGroup group = null;
        Iterator<IGroup> groupItr = this.groupArList.iterator();
        while(groupItr.hasNext())
        {
            group = groupItr.next();
            if(group.getName().equals(groupName))
            {
                break;
            }
        }
        return group;
    }
    
    @Override
    public void addGroup(IGroup newGroup)
    {
        boolean isNewGroup = true;
        Iterator<IGroup> groupItr = this.groupArList.iterator();
        while(groupItr.hasNext())
        {
            IGroup group = groupItr.next();
            if( group.getName().equals(newGroup.getName()) )
            {
                isNewGroup = false;
                break;
            }
        }
        
        if(isNewGroup)
        {
            this.groupArList.add(newGroup);    
        }
    }
    
    @Override
    public void deleteGroup(IGroup group)
    {
        Iterator<IGroup> groupItr = this.groupArList.iterator();
        while(groupItr.hasNext())
        {
            if( groupItr.next().equals(group) )
            {
                groupItr.remove();
            }
        }
    }
    
    @Override
    public ArrayList<IContact> removeGroup(IGroup group)
    {
        ArrayList<IContact> contactArList = getGroupContacts(group.getName());
        deleteGroup(group);
        return contactArList;
    }
 
}
