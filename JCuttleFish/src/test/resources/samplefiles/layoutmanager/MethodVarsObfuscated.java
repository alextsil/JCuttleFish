public class Test
{

    public void test ()
    {
        int aaa = 5;
        String bbb = "test";
        String ccc = "test2322";

        ccc = bbb + aaa;
        ccc = null;
    }

    public void test2 ( String aa, String bb )
    {
        test( aa, bb );
    }
}