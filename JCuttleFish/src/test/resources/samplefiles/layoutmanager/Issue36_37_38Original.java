package su.levenetc.android.textsurface;

import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

/**
 * Created by Eugene Levenetc.
 */
public class Text implements Comparable<Text> {

    private Paint paint;
    private RectF defaultSize;
    private float fontDescent;
    private float dx;


    private void initBounds(String text) {

        Rect tmp = new Rect();
        paint.getTextBounds(text, 0, text.length(), tmp);

        fontDescent = paint.getFontMetrics().descent;
        defaultSize = new RectF(tmp);
        //a little workaround because getTextBounds returns smaller width than it is
        dx = paint.measureText(text) - tmp.width();
        defaultSize.left = 0;
        defaultSize.right = tmp.width() + dx;
        defaultSize.top = -paint.getFontSpacing();
        defaultSize.bottom = 0;
    }
}