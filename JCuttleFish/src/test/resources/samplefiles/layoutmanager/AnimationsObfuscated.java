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
public class Text implements Comparable<Text> {

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

    public Text(String aa, Position bb, RectF cc, Paint dd) {
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
        String trimmed = aa.trim();
        if (trimmed.length() < aa.length()) {

            int length = aa.length();
            int start = aa.lastIndexOf(trimmed);
            int end = length - (start + trimmed.length());

            aa = Utils.genString(start) + aa + Utils.genString(end);
        }

        Rect tmp = new Rect();
        a.getTextBounds(aa, 0, aa.length(), tmp);

        l = a.getFontMetrics().descent;
        c = new RectF(tmp);
        //a little workaround because getTextBounds returns smaller width than it is
        k = a.measureText(aa) - tmp.width();
        c.left = 0;
        c.right = tmp.width() + k;
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

        final float finalX = f.left;

        if (j.isEmpty()) {
            aa.drawText(b, finalX, -f.bottom - l, a);
        } else {
            for (ITextEffect effect : j) {
                aa.save();
                effect.apply(aa, b, finalX, -f.bottom, a);
                aa.drawText(b, finalX, -f.bottom, a);
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

        final float sx = h.getScaleX();
        final float sy = h.getScaleY();
        final float sPivotX = e.getRelativeX((int) h.getPivot().x, this, false);
        final float sPivotY = e.getRelativeY((int) h.getPivot().y, this, false);
        final float x = e.getX(aa, getWidth() * sx);
        final float y = e.getY(aa, getHeight() * sy);

        i.reset();
        i.preTranslate(x, y);
        i.preScale(sx, sy, sPivotX, sPivotY);
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