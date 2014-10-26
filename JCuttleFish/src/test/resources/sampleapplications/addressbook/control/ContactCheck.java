/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import API.IContact;

/**
 *
 * @author Γρηγόρης
 */
public class ContactCheck {
    
    private boolean isProper;
    private IContact contact;

    public ContactCheck(IContact contact) 
    {
        this.contact = contact;
        this.isProper = true;
    }
    
    /**
     * 
     * @return 
     */
    public boolean isProperContact()
    {
        String name=contact.getFirstname();
        String lastname=contact.getLastname();
        String address=contact.getAddress();
        String city=contact.getCity();
        String email=contact.getEmail();
        String tel=contact.getTelephone();
        String mtel=contact.getMtelephone();
        
        if( name.isEmpty() || ( tel.isEmpty() && mtel.isEmpty() ) || tel.length()>20 || mtel.length()>20 )
        {
            this.isProper=false;
        }
        else
        {
            this.checkletters(name);
            this.checkletters(lastname);
            this.checkletters(city);
            this.checknumbers(tel);
            this.checknumbers(mtel);
        }
        return isProper;
    }
 
    /**
     * 
     * @param text 
     */
    private void checkletters(String text){
        
        for(int i=0;i<text.length();i++){
            if(Character.isDigit(text.charAt(i)))
            {
                this.isProper=false;
                break;
            }
        }
    }
    
    /**
     * 
     * @param text 
     */
    private void checknumbers(String text){    
        for(int i=0;i<text.length();i++){
            if(Character.isLetter(text.charAt(i))) 
            {
                this.isProper=false;
                break;
            }
        }
    }
}