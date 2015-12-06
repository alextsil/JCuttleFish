/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import API.IContact;


/**
 * @author Alexei
 */
public class Contact implements IContact
{

    private String id;
    private String firstname;
    private String lastname;
    private String email;
    private String telephone;
    private String mtelephone;
    private String address;
    private String city;

    /**
     * Contact type objects constructor
     * that needs at least two parameters to allow
     * initialisation of it's objects
     *
     */
    public Contact ( String firstname, String telephone )
    {
        this.firstname = firstname;
        this.telephone = telephone;
        this.id = "";
        this.lastname = "";
        this.email = "";
        this.mtelephone = "";
        this.address = "";
        this.city = "";
    }

    /**
     * simple constructor
     */
    public Contact ()
    {
        this.id = "";
        this.firstname = "";
        this.lastname = "";
        this.email = "";
        this.telephone = "";
        this.mtelephone = "";
        this.address = "";
        this.city = "";
    }

    @Override
    public void setId ( String id )
    {
        this.id = id;
    }

    @Override
    public String getId ()
    {
        return id;
    }

    @Override
    public String getFirstname ()
    {
        return firstname;
    }

    @Override
    public void setFirstname ( String firstname )
    {
        this.firstname = firstname;
    }

    @Override
    public String getLastname ()
    {
        return lastname;
    }

    @Override
    public void setLastname ( String lastname )
    {
        this.lastname = lastname;
    }

    @Override
    public String getEmail ()
    {
        return email;
    }

    @Override
    public void setEmail ( String email )
    {
        this.email = email;
    }

    @Override
    public String getTelephone ()
    {
        return telephone;
    }

    @Override
    public void setTelephone ( String telephone )
    {
        this.telephone = telephone;
    }

    @Override
    public String getMtelephone ()
    {
        return mtelephone;
    }

    @Override
    public void setMtelephone ( String mtelephone )
    {
        this.mtelephone = mtelephone;
    }

    @Override
    public String getAddress ()
    {
        return address;
    }

    @Override
    public void setAddress ( String address )
    {
        this.address = address;
    }

    @Override
    public String getCity ()
    {
        return city;
    }

    @Override
    public void setCity ( String city )
    {
        this.city = city;
    }
}
