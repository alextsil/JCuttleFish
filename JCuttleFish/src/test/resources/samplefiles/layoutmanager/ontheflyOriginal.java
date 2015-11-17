package test;

import java.util.ArrayList;


public class Text implements Comparable<Text>
{

    private ScaleValue scale = new ScaleValue();

    public void setScalePivot ( float x, float y )
    {
        scale.getPivot().set( x, y );
    }

}