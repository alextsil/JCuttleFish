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

    public Text(String text, Position position, RectF padding, Paint paint) {
        this.b = text;
        this.e = position;
        this.f = padding;
        this.a = paint;

        initBounds(text);
    }

    public void addEffect(ITextEffect effect) {
        j.add(effect);
    }

    public float getFontDescent() {
        return l;
    }

    private void initBounds(String text) {
        String trimmed = text.trim();
        if (trimmed.length() < text.length()) {

            int length = text.length();
            int start = text.lastIndexOf(trimmed);
            int end = length - (start + trimmed.length());

            text = Utils.genString(start) + text + Utils.genString(end);
        }

        Rect tmp = new Rect();
        a.getTextBounds(text, 0, text.length(), tmp);

        l = a.getFontMetrics().descent;
        c = new RectF(tmp);
        //a little workaround because getTextBounds returns smaller width than it is
        k = a.measureText(text) - tmp.width();
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

    public void setIndex(int index) {
        this.g = index;
    }

    public int getIndex() {
        return g;
    }

    public void onDraw(Canvas canvas, TextSurface textSurface) {

        layout(textSurface);

        canvas.save();
        canvas.concat( i );

        final float finalX = f.left;

        if (j.isEmpty()) {
            canvas.drawText(b, finalX, -f.bottom - l, a);
        } else {
            for (ITextEffect effect : j) {
                canvas.save();
                effect.apply(canvas, b, finalX, -f.bottom, a);
                canvas.drawText(b, finalX, -f.bottom, a);
                canvas.restore();
            }

        }

        canvas.restore();

        if (Debug.ENABLED) {
            canvas.drawRect(
                    d.left,
                    d.top - f.bottom - f.top,
                    d.right + f.left + f.right,
                    d.bottom,
                    Debug.RED_STROKE
            );
        }
    }

    void layout(TextSurface textSurface) {

        d.set(c.left, c.top, c.right, c.bottom);

        final float sx = h.getScaleX();
        final float sy = h.getScaleY();
        final float sPivotX = e.getRelativeX((int) h.getPivot().x, this, false);
        final float sPivotY = e.getRelativeY((int) h.getPivot().y, this, false);
        final float x = e.getX(textSurface, getWidth() * sx);
        final float y = e.getY(textSurface, getHeight() * sy);

        i.reset();
        i.preTranslate(x, y);
        i.preScale(sx, sy, sPivotX, sPivotY);
        i.mapRect(d);
    }

    public float getY(TextSurface textSurface) {
        return e.getY(textSurface, getHeight());
    }

    public float getX(TextSurface textSurface) {
        return e.getX(textSurface, getWidth());
    }

    public void setAlpha(int alpha) {
        a.setAlpha(alpha);
    }

    public RectF bounds() {
        return c;
    }

    public void setPosition(Position position) {
        this.e = position;
    }

    public void setScaleX(float value) {
        h.setValueX(value);
    }

    public void setScaleY(float value) {
        h.setValueY(value);
    }

    public void setScalePivot(float x, float y) {
        h.getPivot().set(x, y);
    }

    public float getScaleY() {
        return h.getScaleY();
    }

    public void setTranslationX(float value) {
        e.setTranslationX(value);
    }

    public void setTranslationY(float value) {
        e.setTranslationY(value);
    }

    @Override public int compareTo(@NonNull Text another) {
        return b.compareTo(another.text);
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

    public void removeEffect(ITextEffect effect) {
        j.remove(effect);
    }

    public String getValue() {
        return b;
    }

    public Paint getPaint() {
        return a;
    }
}