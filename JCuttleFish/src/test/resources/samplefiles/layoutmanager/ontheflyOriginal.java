package test;

import java.util.ArrayList;


public class Text implements Comparable<Text>
{

    private Paint paint;
    private final String text;
    private RectF padding;
    private ArrayList<ITextEffect> effects = new ArrayList<>();
    private float fontDescent;


    public Text ()
    {
        final float finalX = padding.left;

        if (effects.isEmpty()) {
            canvas.drawText(text, finalX, -padding.bottom - fontDescent, paint);
        } else {
            for (ITextEffect effect : effects) {
                canvas.save();
                effect.apply(canvas, text, finalX, -padding.bottom, paint);
                canvas.drawText(text, finalX, -padding.bottom, paint);
                canvas.restore();
            }

        }
    }

}