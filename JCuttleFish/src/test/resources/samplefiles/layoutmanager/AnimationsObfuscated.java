package su.levenetc.android.textsurface;

import android.graphics.*;
import android.support.annotation.NonNull;
import su.levenetc.android.textsurface.common.Position;
import su.levenetc.android.textsurface.common.ScaleValue;
import su.levenetc.android.textsurface.interfaces.ITextEffect;
import su.levenetc.android.textsurface.utils.Utils;

import java.util.ArrayList;


/**
 * Created by Eugene Levenetc.
 */
public class a implements Comparable<a>
{

    private Paint a;
    private final String b;
    private RectF c;
    private RectF d = new RectF();
    private Position e;
    private RectF f;
    private int g;
    private ScaleValue h = new ScaleValue();
    private Matrix i = new Matrix();
    private ArrayList<ITextEffect> j = new ArrayList<>();
    private float k;
    private float l;

    public a ( String aa, Position bb, RectF cc, Paint dd )
    {
        this.b = aa;
        this.e = bb;
        this.f = cc;
        this.a = dd;

        c( aa );
    }

    public void a ( ITextEffect aa )
    {
        j.add( aa );
    }

    public float b ()
    {
        return l;
    }

    private void c ( String aa )
    {
        String aaa = aa.trim();
        if ( aaa.length() < aa.length() )
        {

            int bbb = aa.length();
            int ccc = aa.lastIndexOf( aaa );
            int ddd = bbb - ( ccc + aaa.length() );

            aa = Utils.genString( ccc ) + aa + Utils.genString( ddd );
        }

        Rect eee = new Rect();
        a.getTextBounds( aa, 0, aa.length(), eee );

        l = a.getFontMetrics().descent;
        c = new RectF( eee );
        //a little workaround because getTextBounds returns smaller width than it is
        k = a.measureText( aa ) - eee.width();
        c.left = 0;
        c.right = eee.width() + k;
        c.top = -a.getFontSpacing();
        c.bottom = 0;

        c.set(
                c.left,
                c.top,
                c.right,
                c.bottom
        );

        d.set(
                c.left,
                c.top,
                c.right,
                c.bottom
        );
    }

    public void d ( int aa )
    {
        this.g = aa;
    }

    public int e ()
    {
        return g;
    }

    public void f ( Canvas aa, TextSurface bb )
    {

        g( bb );

        aa.save();
        aa.concat( i );

        final float aaa = f.left;

        if ( j.isEmpty() )
        {
            aa.drawText( b, aaa, -f.bottom - l, a );
        } else
        {
            for ( ITextEffect effect : j )
            {
                aa.save();
                effect.apply( aa, b, aaa, -f.bottom, a );
                aa.drawText( b, aaa, -f.bottom, a );
                aa.restore();
            }

        }

        aa.restore();

        if ( Debug.ENABLED )
        {
            aa.drawRect(
                    d.left,
                    d.top - f.bottom - f.top,
                    d.right + f.left + f.right,
                    d.bottom,
                    Debug.RED_STROKE
            );
        }
    }

    void g ( TextSurface aa )
    {

        d.set( c.left, c.top, c.right, c.bottom );

        final float aaa = h.getScaleX();
        final float bbb = h.getScaleY();
        final float ccc = e.getRelativeX( ( int )h.getPivot().x, this, false );
        final float ddd = e.getRelativeY( ( int )h.getPivot().y, this, false );
        final float eee = e.getX( aa, s() * aaa );
        final float fff = e.getY( aa, t() * bbb );

        i.reset();
        i.preTranslate( eee, fff );
        i.preScale( aaa, bbb, ccc, ddd );
        i.mapRect( d );
    }

    public float h ( TextSurface aa )
    {
        return e.getY( aa, t() );
    }

    public float i ( TextSurface aa )
    {
        return e.getX( aa, s() );
    }

    public void j ( int aa )
    {
        a.setAlpha( aa );
    }

    public RectF k ()
    {
        return c;
    }

    public void l ( Position aa )
    {
        this.e = aa;
    }

    public void m ( float aa )
    {
        h.setValueX( aa );
    }

    public void n ( float aa )
    {
        h.setValueY( aa );
    }

    public void o ( float aa, float bb )
    {
        h.getPivot().set( aa, bb );
    }

    public float p ()
    {
        return h.getScaleY();
    }

    public void q ( float aa )
    {
        e.setTranslationX( aa );
    }

    public void r ( float aa )
    {
        e.setTranslationY( aa );
    }

    @Override
    public int compareTo ( @NonNull a aa )
    {
        return b.compareTo( aa.b );
    }

    public float s ()
    {
        return ( d.width() + f.left + f.right );
    }

    public float t ()
    {
        return ( d.height() + f.top + f.bottom );
    }

    public Position u ()
    {
        return e;
    }

    public void v ()
    {
        e.onAnimationEnd();
    }

    public float w ()
    {
        return h.getScaleX();
    }

    @Override
    public String toString ()
    {
        return "Text{" +
                "text='" + b + '\'' +
                '}';
    }

    public void x ( ITextEffect aa )
    {
        j.remove( aa );
    }

    public String y ()
    {
        return b;
    }

    public Paint z ()
    {
        return a;
    }
}