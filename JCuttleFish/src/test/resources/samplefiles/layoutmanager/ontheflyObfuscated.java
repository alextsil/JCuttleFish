package test;

import android.graphics.Paint;
import android.graphics.RectF;
import su.levenetc.android.textsurface.common.Position;

/**
 * Created by Eugene Levenetc.
 */
public class Text implements Comparable<Text> {

    private Paint a;
    private final String b;
    private Position c;
    private RectF d;


    public Text(String text, Position position, RectF padding, Paint paint) {
        this.b = text;
        this.c = position;
        this.d = padding;
        this.a = paint;

        initBounds(text);
    }

}