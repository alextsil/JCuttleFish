package pack1;

import pack2.One;

import java.util.ArrayList;
import java.util.List;


public class Two
{

    private One one;
    private List<List<One>> ones;
    private List<List<Map<String, One>>> onesmap;
    private List<? extends One> extendone;
    private One[] onearray;
    private List<One[]> listarrayone;

    public Two ()
    {
        One newOne = new One();
        List<One> newOnes = new ArrayList<One>();
        newOne.funct1();
        One.class.getClass();
        String static1Str = One.static1;
        One.staticFunct();
        pack2.One.staticFunct();
    }

    public Two both ( One one, Two two )
    {
        //
    }
}
