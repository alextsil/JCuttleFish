/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import API.IContact;

/**
 * @author Alexei
 */
public class Contact implements IContact {
    
    protected String a;
    protected String b;
    protected String c;
    protected String d;
    protected String e;
    protected String f;
    protected String g;
    protected String h;
    
    /**
     * Contact type objects constructor
     * that needs at least two parameters to allow
     * initialisation of it's objects
     * @param firstname
     * @param telephone 
     */
    public Contact(String firstname, String telephone)
    {
        this.b = firstname;
        this.e = telephone;
        this.a = "";
        this.c = "";
        this.d = "";
        this.f = "";
        this.g = "";
        this.h = "";
    }
    
    /**
     * simple constructor
     */
    public Contact()
    {
        this.a = "";
        this.b = "";
        this.c = "";
        this.d = "";
        this.e = "";
        this.f = "";
        this.g = "";
        this.h = "";
    }

    @Override
    public void setId(String id) {
        this.a = id;
    }
   
    @Override
    public String getId() {
        return this.a;
    }
    @Override
    public String getFirstname() {
        return this.b;
    }

    @Override
    public void setFirstname(String firstname) {
        this.b = firstname;
    }

    @Override
    public String getLastname() {
        return this.c;
    }

    @Override
    public void setLastname(String lastname) {
        this.c = lastname;
    }

    @Override
    public String getEmail() {
        return this.d;
    }

    @Override
    public void setEmail(String email) {
        this.d = email;
    }

    @Override
    public String getTelephone() {
        return this.e;
    }

    @Override
    public void setTelephone(String telephone) {
        this.e = telephone;
    }

    @Override
    public String getMtelephone() {
        return this.f;
    }

    @Override
    public void setMtelephone(String mtelephone) {
        this.f = mtelephone;
    }

    @Override
    public String getAddress() {
        return this.g;
    }

    @Override
    public void setAddress(String address) {
        this.g = address;
    }

    @Override
    public String getCity() {
        return this.h;
    }

    @Override
    public void setCity(String city) {
        this.h = city;
    }
}
