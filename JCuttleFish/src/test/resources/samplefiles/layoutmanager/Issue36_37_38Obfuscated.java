package su.levenetc.android.textsurface;

import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

/**
 * Created by Eugene Levenetc.
 */
public class Text implements Comparable<Text> {

    private Paint a;
    private RectF b;
    private float c;
    private float d;


    private void initBounds(String text) {

        Rect tmp = new Rect();
        this.a.getTextBounds(text, 0, text.length(), tmp);

        this.c = this.a.getFontMetrics().descent;
        this.b = new RectF(tmp);
        //a little workaround because getTextBounds returns smaller width than it is
        this.d = this.a.measureText(text) - tmp.width();
        this.b.left = 0;
        this.b.right = tmp.width() + this.d;
        this.b.top = -this.a.getFontSpacing();
        this.b.bottom = 0;
    }
}