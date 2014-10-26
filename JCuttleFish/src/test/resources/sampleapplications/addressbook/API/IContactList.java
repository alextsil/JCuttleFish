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
public interface IContactList {

    void addGroup(IGroup newGroup);

    void deleteGroup(IGroup group);

    ArrayList<IContact> getContacts();

    IGroup getGroup(String groupName);

    ArrayList<IGroup> getGroupArList();

    ArrayList<IContact> getGroupContacts(String groupName);

    ArrayList<IContact> removeGroup(IGroup group);

    void setGroupArList(ArrayList<IGroup> groupArList);
    
}
