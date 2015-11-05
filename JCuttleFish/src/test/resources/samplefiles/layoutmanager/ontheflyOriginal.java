package test;

import android.graphics.Paint;
import android.graphics.RectF;
import su.levenetc.android.textsurface.common.Position;

/**
 * Created by Eugene Levenetc.
 */
public class Text implements Comparable<Text> {

    private Paint paint;
    private final String text;
    private Position position;
    private RectF padding;


    public Text(String text, Position position, RectF padding, Paint paint) {
        this.text = text;
        this.position = position;
        this.padding = padding;
        this.paint = paint;

        initBounds(text);
    }

}