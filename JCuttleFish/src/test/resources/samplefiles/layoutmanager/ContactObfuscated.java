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
    public Contact(String b, String e)
    {
        this.b = b;
        this.e = e;
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
    public void setId(String a) {
        this.a = a;
    }
   
    @Override
    public String getId() {
        return a;
    }
    @Override
    public String getFirstname() {
        return b;
    }

    @Override
    public void setFirstname(String b) {
        this.b = b;
    }

    @Override
    public String getLastname() {
        return c;
    }

    @Override
    public void setLastname(String c) {
        this.c = c;
    }

    @Override
    public String getEmail() {
        return d;
    }

    @Override
    public void setEmail(String d) {
        this.d = d;
    }

    @Override
    public String getTelephone() {
        return e;
    }

    @Override
    public void setTelephone(String e) {
        this.e = e;
    }

    @Override
    public String getMtelephone() {
        return f;
    }

    @Override
    public void setMtelephone(String f) {
        this.f = f;
    }

    @Override
    public String getAddress() {
        return g;
    }

    @Override
    public void setAddress(String g) {
        this.g = g;
    }

    @Override
    public String getCity() {
        return h;
    }

    @Override
    public void setCity(String h) {
        this.h = h;
    }
}
