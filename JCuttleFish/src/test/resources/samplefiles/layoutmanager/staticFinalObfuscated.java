package test;

public class Onthefly
{

    public String alpha;

    private String a;

    private static String b = "1";

    private final String c = "2";

    private final static String d = "3";

    public Onthefly ( String alpha, String beta, String id2 )
    {
        this.alpha = alpha;
        this.a = beta;
        this.c = id2;
    }

}