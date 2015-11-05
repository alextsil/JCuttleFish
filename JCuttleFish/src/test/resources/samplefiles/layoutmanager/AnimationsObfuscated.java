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
        this.j.add(effect);
    }

    public float getFontDescent() {
        return this.l;
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
        this.a.getTextBounds(text, 0, text.length(), tmp);

        this.l = this.a.getFontMetrics().descent;
        this.c = new RectF(tmp);
        //a little workaround because getTextBounds returns smaller width than it is
        this.k = this.a.measureText(text) - tmp.width();
        this.c.left = 0;
        this.c.right = tmp.width() + this.k;
        this.c.top = -this.a.getFontSpacing();
        this.c.bottom = 0;

        this.c.set(
                this.c.left,
                this.c.top,
                this.c.right,
                this.c.bottom
        );

        this.d.set(
                this.c.left,
                this.c.top,
                this.c.right,
                this.c.bottom
        );
    }

    public void setIndex(int index) {
        this.g = index;
    }

    public int getIndex() {
        return this.g;
    }

    public void onDraw(Canvas canvas, TextSurface textSurface) {

        layout(textSurface);

        canvas.save();
        canvas.concat( this.i );

        final float finalX = this.f.left;

        if ( this.j.isEmpty()) {
            canvas.drawText( this.b, finalX, -this.f.bottom - this.l, this.a );
        } else {
            for (ITextEffect effect : this.j ) {
                canvas.save();
                effect.apply(canvas, this.b, finalX, -this.f.bottom, this.a );
                canvas.drawText( this.b, finalX, -this.f.bottom, this.a );
                canvas.restore();
            }

        }

        canvas.restore();

        if (Debug.ENABLED) {
            canvas.drawRect(
                    this.d.left,
                    this.d.top - this.f.bottom - this.f.top,
                    this.d.right + this.f.left + this.f.right,
                    this.d.bottom,
                    Debug.RED_STROKE
            );
        }
    }

    void layout(TextSurface textSurface) {

        this. d.set( this.c.left, this.c.top, this.c.right, this.c.bottom);

        final float sx = this.h.getScaleX();
        final float sy = this.h.getScaleY();
        final float sPivotX = this.e.getRelativeX((int) this.h.getPivot().x, this, false);
        final float sPivotY = this.e.getRelativeY((int) this.h.getPivot().y, this, false);
        final float x = this.e.getX(textSurface, getWidth() * sx);
        final float y = this.e.getY(textSurface, getHeight() * sy);

        this.i.reset();
        this.i.preTranslate(x, y);
        this.i.preScale(sx, sy, sPivotX, sPivotY);
        this.i.mapRect( this.d );
    }

    public float getY(TextSurface textSurface) {
        return this.e.getY(textSurface, getHeight());
    }

    public float getX(TextSurface textSurface) {
        return this.e.getX(textSurface, getWidth());
    }

    public void setAlpha(int alpha) {
        this.a.setAlpha(alpha);
    }

    public RectF bounds() {
        return this.c;
    }

    public void setPosition(Position position) {
        this.e = position;
    }

    public void setScaleX(float value) {
        this.h.setValueX(value);
    }

    public void setScaleY(float value) {
        this.h.setValueY(value);
    }

    public void setScalePivot(float x, float y) {
        this.h.getPivot().set(x, y);
    }

    public float getScaleY() {
        return this.h.getScaleY();
    }

    public void setTranslationX(float value) {
        this.e.setTranslationX(value);
    }

    public void setTranslationY(float value) {
        this.e.setTranslationY(value);
    }

    @Override public int compareTo(@NonNull Text another) {
        return this.b.compareTo(another.b );
    }

    public float getWidth() {
        return ( this.d.width() + this.f.left + this.f.right);
    }

    public float getHeight() {
        return ( this.d.height() + this.f.top + this.f.bottom);
    }

    public Position getPosition() {
        return this.e;
    }

    public void onAnimationEnd() {
        this.e.onAnimationEnd();
    }

    public float getScaleX() {
        return this.h.getScaleX();
    }

    @Override public String toString() {
        return "Text{" +
                "b='" + this.b + '\'' +
                '}';
    }

    public void removeEffect(ITextEffect effect) {
        this.j.remove(effect);
    }

    public String getValue() {
        return this.b;
    }

    public Paint getPaint() {
        return this.a;
    }
}