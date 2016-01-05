/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import API.IContact;


/**
 * @author Alexei
 */
public class a implements IContact
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
    public a ( String aa, String bb )
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
    public a ()
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
    public void a ( String aa )
    {
        this.a = aa;
    }

    @Override
    public String b ()
    {
        return a;
    }

    @Override
    public String c ()
    {
        return b;
    }

    @Override
    public void d ( String aa )
    {
        this.b = aa;
    }

    @Override
    public String e ()
    {
        return c;
    }

    @Override
    public void f ( String aa )
    {
        this.c = aa;
    }

    @Override
    public String g ()
    {
        return d;
    }

    @Override
    public void h ( String aa )
    {
        this.d = aa;
    }

    @Override
    public String i ()
    {
        return e;
    }

    @Override
    public void j ( String aa )
    {
        this.e = aa;
    }

    @Override
    public String k ()
    {
        return f;
    }

    @Override
    public void l ( String aa )
    {
        this.f = aa;
    }

    @Override
    public String m ()
    {
        return g;
    }

    @Override
    public void n ( String aa )
    {
        this.g = aa;
    }

    @Override
    public String o ()
    {
        return h;
    }

    @Override
    public void p ( String aa )
    {
        this.h = aa;
    }
}
