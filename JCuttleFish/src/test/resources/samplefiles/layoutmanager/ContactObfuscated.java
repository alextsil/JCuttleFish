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

    private String a;
    private String b;
    private String c;
    private String d;
    private String e;
    private String f;
    private String g;
    private String h;

    /**
     * Contact type objects constructor
     * that needs at least two parameters to allow
     * initialisation of it's objects
     *
     */
    public Contact ( String aa, String bb )
    {
        this.b = aa;
        this.e = bb;
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
    public Contact ()
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
    public void setId ( String aa )
    {
        this.a = aa;
    }

    @Override
    public String getId ()
    {
        return a;
    }

    @Override
    public String getFirstname ()
    {
        return b;
    }

    @Override
    public void setFirstname ( String aa )
    {
        this.b = aa;
    }

    @Override
    public String getLastname ()
    {
        return c;
    }

    @Override
    public void setLastname ( String aa )
    {
        this.c = aa;
    }

    @Override
    public String getEmail ()
    {
        return d;
    }

    @Override
    public void setEmail ( String aa )
    {
        this.d = aa;
    }

    @Override
    public String getTelephone ()
    {
        return e;
    }

    @Override
    public void setTelephone ( String aa )
    {
        this.e = aa;
    }

    @Override
    public String getMtelephone ()
    {
        return f;
    }

    @Override
    public void setMtelephone ( String aa )
    {
        this.f = aa;
    }

    @Override
    public String getAddress ()
    {
        return g;
    }

    @Override
    public void setAddress ( String aa )
    {
        this.g = aa;
    }

    @Override
    public String getCity ()
    {
        return h;
    }

    @Override
    public void setCity ( String aa )
    {
        this.h = aa;
    }
}
