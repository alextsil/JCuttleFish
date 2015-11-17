package test;

import java.util.ArrayList;


public class Text implements Comparable<Text>
{

    private ScaleValue a = new ScaleValue();

    public void setScalePivot ( float x, float y )
    {
        final float sPivotX = position.getRelativeX((int) this.a.getPivot().x, this, false);
    }

}