/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.controls;

import API.IGroup;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;
import javax.swing.event.ListDataListener;

/**
 *
 * @author Γρηγόρης
 */
public class ComboBoxGroupModel extends AbstractListModel implements ComboBoxModel {

    private ArrayList<String> groupNames;
    private String selectedGroupName;

    public ComboBoxGroupModel(ArrayList<IGroup> groupArList) {
        groupNames = getGroupNames(groupArList);
        if ( this.getSize() > 0 ) {
            selectedGroupName = this.groupNames.get(0);
        }
    }
    
    private ArrayList<String> getGroupNames(ArrayList<IGroup> groupArList)
    {
        ArrayList<String> groupNamesArList = new ArrayList<String>();
        groupNamesArList.add("none");
        Iterator<IGroup> groupItr = groupArList.iterator();
        while( groupItr.hasNext() )
        {
            IGroup group = groupItr.next();
            groupNamesArList.add(group.getName());
        }
        return groupNamesArList;
    }

    
    @Override
    public void setSelectedItem(Object anItem) {
        String anGroupName = (String) anItem;
        if ((selectedGroupName != null && !selectedGroupName.equals( anGroupName )) ||
            selectedGroupName == null && anGroupName != null) {
            selectedGroupName = anGroupName;
            fireContentsChanged(this, -1, -1);
        }
    }

    @Override
    public Object getSelectedItem() {
        return selectedGroupName;
    }

    @Override
    public int getSize() {
        return this.groupNames.size();
    }

    @Override
    public Object getElementAt(int index) {
        return this.groupNames.get(index);
    }

    @Override
    public void addListDataListener(ListDataListener l) {
        super.addListDataListener(l);
    }

    @Override
    public void removeListDataListener(ListDataListener l) {
        super.removeListDataListener(l);
    }
}
