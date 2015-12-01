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
        a.getTextBounds(text, 0, text.length(), tmp);

        c = a.getFontMetrics().descent;
        b = new RectF(tmp);
        //a little workaround because getTextBounds returns smaller width than it is
        d = a.measureText(text) - tmp.width();
        b.left = 0;
        b.right = tmp.width() + d;
        b.top = -a.getFontSpacing();
        b.bottom = 0;
    }
}