/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.controls.tableModifiers;

import API.IContact;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 * Class that manages Table Contact data
 * @author Γρηγόρης
 */
public class ContactTableModel extends AbstractTableModel {
    
    private ArrayList<String> header;
    private ArrayList<IContact> contactArList;

    /**
     * Constructor of class object
     * @param contactList
     * @param header 
     */
    public ContactTableModel(ArrayList<IContact> contactList,ArrayList<String> header) 
    {
        this.header = header;
        this.contactArList = contactList;
    }

    public ArrayList<String> getHeader() 
    {
        return header;
    }

    public void setHeader(ArrayList<String> header) 
    {
        this.header = header;
    }

    public ArrayList<IContact> getContactArList() 
    {
        return contactArList;
    }

    public void setContactArList(ArrayList<IContact> contactArList) 
    {
        this.contactArList = contactArList;
        super.fireTableDataChanged();
    }
    
    @Override
    public int getRowCount() 
    {
        return this.contactArList.size();
    }

    @Override
    public int getColumnCount() 
    {
        return this.header.size();
    }
   
    @Override
    public String getColumnName(int column) 
    {
        return this.header.get(column);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) 
    {
        IContact contactData;
        contactData = contactArList.get(rowIndex);
        String value=null;
        
        switch (columnIndex)
        {
            case 0: 
                value=contactData.getFirstname();
                break;
            
            case 1:
                value=contactData.getLastname();
                break;
            
            case 2:
                value=contactData.getEmail();
                break;
            case 3:
                value=contactData.getTelephone();
                break;
                
            case 4:
                value=contactData.getMtelephone();
                break;
            
            case 5:
                value=contactData.getAddress();
                break;
                      
            case 6:
                value=contactData.getCity();
        }
        return (String)value;
    }
    
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) 
    {
        IContact contactData;
        contactData=contactArList.get(rowIndex);
        String value=(String)aValue;
        
        switch(columnIndex)
        {
            case 0: 
                contactData.setFirstname(value);
                break;
            
            case 1:
                contactData.setLastname(value);
                break;
            
            case 2:
                contactData.setEmail(value);
                break;
            case 3:
                contactData.setTelephone(value);
                break;
                
            case 4:
                contactData.setMtelephone(value);
                break;
            
            case 5:
                contactData.setAddress(value);
                break;
                      
            case 6:
                contactData.setCity(value);
        }
        
        contactArList.set(rowIndex, contactData);
    }
    
    /**
     * 
     * @param row 
     */
    public void removeRow(int row) {
        this.contactArList.remove(row);
        super.fireTableRowsDeleted(row, row);
    }
    
    /**
     * 
     * @param row
     * @param newContact 
     */
    public void insertRow(int row, IContact newContact)
    {
        this.contactArList.add(row, newContact);
        super.fireTableRowsInserted(row, row);
    }
    
    /**
     * 
     * @param row
     * @param updatedContact 
     */
    public void updateRow(int row, IContact updatedContact)
    {
        this.contactArList.set(row, updatedContact);
        super.fireTableRowsUpdated(row, row);
    }
    
    public IContact getSelectedContact(int row)
    {
        return this.contactArList.get(row);
    }
}
