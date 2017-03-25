package com.example.tyler.labquesiton2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by tyler on 20/03/17.
 */

public class CustomView extends View {

    int shapeType = 0;

    public CustomView(Context context, AttributeSet attrs) {

        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);

        Paint paint = new Paint();
        paint.setColor(Color.RED);

        float width = canvas.getWidth();
        float height = canvas.getHeight();

        switch (shapeType) {

            case 0:
                canvas.drawCircle(width/2, height/2, width/3, paint);
                break;
            case 1:
                canvas.drawLine(0, 0, width, height, paint);
                break;
            case 2:
                canvas.drawRect(0, 0, width, height, paint);
                break;
        }
    }


}
