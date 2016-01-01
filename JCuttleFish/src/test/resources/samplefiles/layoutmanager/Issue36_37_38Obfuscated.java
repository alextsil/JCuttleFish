package su.levenetc.android.textsurface;

import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

/**
 * Created by Eugene Levenetc.
 */
public class a implements Comparable<a> {

    private Paint a;
    private RectF b;
    private float c;
    private float d;


    private void initBounds(String aa) {

        Rect aaa = new Rect();
        a.getTextBounds(aa, 0, aa.length(), aaa);

        c = a.getFontMetrics().descent;
        b = new RectF(aaa);
        //a little workaround because getTextBounds returns smaller width than it is
        d = a.measureText(aa) - aaa.width();
        b.left = 0;
        b.right = aaa.width() + d;
        b.top = -a.getFontSpacing();
        b.bottom = 0;
    }
}