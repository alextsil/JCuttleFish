package su.levenetc.android.textsurface;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.NonNull;

import java.util.ArrayList;

import su.levenetc.android.textsurface.common.Position;
import su.levenetc.android.textsurface.common.ScaleValue;
import su.levenetc.android.textsurface.interfaces.ITextEffect;
import su.levenetc.android.textsurface.utils.Utils;

/**
 * Created by Eugene Levenetc.
 */
public class a implements Comparable<a> {

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

    public a(String aa, Position bb, RectF cc, Paint dd) {
        this.b = aa;
        this.e = bb;
        this.f = cc;
        this.a = dd;

        initBounds(aa);
    }

    public void addEffect(ITextEffect aa) {
        j.add(aa);
    }

    public float getFontDescent() {
        return l;
    }

    private void initBounds(String aa) {
        String aaa = aa.trim();
        if (aaa.length() < aa.length()) {

            int bbb = aa.length();
            int ccc = aa.lastIndexOf(aaa);
            int ddd = bbb - (ccc + aaa.length());

            aa = Utils.genString(ccc) + aa + Utils.genString(ddd);
        }

        Rect eee = new Rect();
        a.getTextBounds(aa, 0, aa.length(), eee);

        l = a.getFontMetrics().descent;
        c = new RectF(eee);
        //a little workaround because getTextBounds returns smaller width than it is
        k = a.measureText(aa) - eee.width();
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

    public void setIndex(int aa) {
        this.g = aa;
    }

    public int getIndex() {
        return g;
    }

    public void onDraw(Canvas aa, TextSurface bb) {

        layout(bb);

        aa.save();
        aa.concat( i );

        final float aaa = f.left;

        if (j.isEmpty()) {
            aa.drawText(b, aaa, -f.bottom - l, a);
        } else {
            for (ITextEffect effect : j) {
                aa.save();
                effect.apply(aa, b, aaa, -f.bottom, a);
                aa.drawText(b, aaa, -f.bottom, a);
                aa.restore();
            }

        }

        aa.restore();

        if (Debug.ENABLED) {
            aa.drawRect(
                    d.left,
                    d.top - f.bottom - f.top,
                    d.right + f.left + f.right,
                    d.bottom,
                    Debug.RED_STROKE
            );
        }
    }

    void layout(TextSurface aa) {

        d.set(c.left, c.top, c.right, c.bottom);

        final float aaa = h.getScaleX();
        final float bbb = h.getScaleY();
        final float ccc = e.getRelativeX((int) h.getPivot().x, this, false);
        final float ddd = e.getRelativeY((int) h.getPivot().y, this, false);
        final float eee = e.getX(aa, getWidth() * aaa);
        final float fff = e.getY(aa, getHeight() * bbb);

        i.reset();
        i.preTranslate(eee, fff);
        i.preScale(aaa, bbb, ccc, ddd);
        i.mapRect(d);
    }

    public float getY(TextSurface aa) {
        return e.getY(aa, getHeight());
    }

    public float getX(TextSurface aa) {
        return e.getX(aa, getWidth());
    }

    public void setAlpha(int aa) {
        a.setAlpha(aa);
    }

    public RectF bounds() {
        return c;
    }

    public void setPosition(Position aa) {
        this.e = aa;
    }

    public void setScaleX(float aa) {
        h.setValueX(aa);
    }

    public void setScaleY(float aa) {
        h.setValueY(aa);
    }

    public void setScalePivot(float aa, float bb) {
        h.getPivot().set(aa, bb);
    }

    public float getScaleY() {
        return h.getScaleY();
    }

    public void setTranslationX(float aa) {
        e.setTranslationX(aa);
    }

    public void setTranslationY(float aa) {
        e.setTranslationY(aa);
    }

    @Override public int compareTo(@NonNull Text aa) {
        return b.compareTo(aa.text);
    }

    public float getWidth() {
        return (d.width() + f.left + f.right);
    }

    public float getHeight() {
        return (d.height() + f.top + f.bottom);
    }

    public Position getPosition() {
        return e;
    }

    public void onAnimationEnd() {
        e.onAnimationEnd();
    }

    public float getScaleX() {
        return h.getScaleX();
    }

    @Override public String toString() {
        return "Text{" +
                "text='" + b + '\'' +
                '}';
    }

    public void removeEffect(ITextEffect aa) {
        j.remove(aa);
    }

    public String getValue() {
        return b;
    }

    public Paint getPaint() {
        return a;
    }
}