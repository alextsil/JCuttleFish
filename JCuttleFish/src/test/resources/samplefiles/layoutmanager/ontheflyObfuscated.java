package test;

import java.util.ArrayList;


public class Text implements Comparable<Text>
{

    private Paint a;
    private final String b;
    private RectF c;
    private ArrayList<ITextEffect> d = new ArrayList<>();
    private float e;


    public Text ()
    {
        final float finalX = this.c.left;

        if (this.d.isEmpty()) {
            canvas.drawText(this.b, finalX, -this.c.bottom - this.e, this.a);
        } else {
            for (ITextEffect effect : this.d ) {
                canvas.save();
                effect.apply(canvas, this.b, finalX, -this.c.bottom, this.a );
                canvas.drawText( this.b, finalX, -this.c.bottom, this.a );
                canvas.restore();
            }

        }
    }

}