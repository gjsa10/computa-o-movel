
package com.cm_grupo18.paint;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

public class PaintView extends View {

    public static int brushcolor = Color.argb(255, 255, 255, 255);

    public ViewGroup.LayoutParams params;
    private Path path = new Path();
    private Paint brush = new Paint();

    public PaintView(Context context) {
        super(context);

        brush.setAntiAlias(true);
        brush.setColor(brushcolor);
        brush.setStyle(Paint.Style.STROKE);
        brush.setStrokeJoin(Paint.Join.ROUND);
        brush.setStrokeWidth(8f);

        params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);


    }


    public boolean onTouchEvent(MotionEvent event) {

        float pointX = event.getX();
        float pontY = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(pointX, pontY);
                return true;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(pointX, pontY);
                break;
            default:
                return true;
        }
        postInvalidate();
        return false;
    }

    public void onDraw(Canvas canvas){
        canvas.drawPath(path, brush);
    }
}