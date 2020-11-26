package com.cm_grupo18.paint;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class PaintCanvas extends View implements View.OnTouchListener{

    private static final int SIZE_LOW_LIMIT = 10;
    private static final int SIZE_HIGH_LIMIT = 100;

    private Paint paint = new Paint();
    private Path path = new Path();
    private List<List<List<Float>>> pathPoints;
    private int backGroundColor = Color.WHITE;
    private GestureDetector mGestureDetector;

    public PaintCanvas(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnTouchListener(this);
        setBackgroundColor(backGroundColor);
        initPaint();
    }

    public PaintCanvas(Context context, AttributeSet attrs, GestureDetector mGestureDetector, int bColor) {
        super(context, attrs);
        this.mGestureDetector = mGestureDetector;
        setOnTouchListener(this);
        this.backGroundColor = bColor;
        setBackgroundColor(backGroundColor);
        initPaint();
        pathPoints = new ArrayList<>();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPath(path, paint);// draws the path with the paint
    }

    @Override
    public boolean performClick(){
        return super.performClick();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        mGestureDetector.onTouchEvent(event);
        return false; // let the event go to the rest of the listeners
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float eventX = event.getX();
        float eventY = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                pathPoints.add(new ArrayList<List<Float>>());

                int mainIndex_down = pathPoints.size() - 1;

                pathPoints.get(mainIndex_down).add(new ArrayList<Float>());

                int secondIndex_down = pathPoints.get(mainIndex_down).size() - 1;

                pathPoints.get(mainIndex_down).get(secondIndex_down).add(eventX);
                pathPoints.get(mainIndex_down).get(secondIndex_down).add(eventY);

                path.moveTo(eventX, eventY);// updates the path initial point
                return true;
            case MotionEvent.ACTION_MOVE:
                int mainIndex_move = pathPoints.size() - 1;

                pathPoints.get(mainIndex_move).add(new ArrayList<Float>());

                int secondIndex_move = pathPoints.get(mainIndex_move).size() - 1;

                pathPoints.get(mainIndex_move).get(secondIndex_move).add(eventX);
                pathPoints.get(mainIndex_move).get(secondIndex_move).add(eventY);

                path.lineTo(eventX, eventY);// makes a line to the point each time this event is fired
                break;
            case MotionEvent.ACTION_UP:// when you lift your finger
                performClick();
                break;
            default:
                return false;
        }

        // Schedules a repaint.
        invalidate();
        return true;
    }

    public void changeBackground(){
        Random r = new Random();
        backGroundColor = Color.rgb(r.nextInt(256), r.nextInt(256), r.nextInt(256));
        setBackgroundColor(backGroundColor);
    }

    public void erase(){
        path.reset();
    }

    private void initPaint(){
        paint.setAntiAlias(true);
        paint.setStrokeWidth(20f);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
    }

    public void changePaintColor(int color){
        paint.setColor(color);
    }

    public void decreasePaintSize() {
        if (paint.getStrokeWidth() > SIZE_LOW_LIMIT){
            paint.setStrokeWidth(paint.getStrokeWidth() - 10f);
        }
    }

    public void increasePaintSize() {
        if (paint.getStrokeWidth() < SIZE_HIGH_LIMIT){
            paint.setStrokeWidth(paint.getStrokeWidth() + 10f);
        }
    }

    public PaintCanvasDTO toCanvasDTO() {
        PaintCanvasDTO canvasDTO = new PaintCanvasDTO();
        canvasDTO.setBackgroundColor(backGroundColor);
        canvasDTO.setPaintColor(paint.getColor());
        canvasDTO.setPaintStroke(paint.getStrokeWidth());
        canvasDTO.setPathPoints(pathPoints);

        return canvasDTO;
    }

    public void convertDTO(PaintCanvasDTO canvasDTO){
        backGroundColor = canvasDTO.getBackgroundColor();

        setBackgroundColor(backGroundColor);
        paint.setStrokeWidth(canvasDTO.getPaintStroke());
        paint.setColor(canvasDTO.getPaintColor());

        for (List<List<Float>> list : canvasDTO.getPathPoints()){

            for (int i = 0; i < list.size(); i ++){

                System.out.println(list.get(i).get(0).floatValue());

                if (i == 0){
                    float eventX = list.get(i).get(0).floatValue();
                    float eventY = list.get(i).get(1).floatValue();
                    path.moveTo(eventX, eventY);
                }
                else {
                    float eventX = list.get(i).get(0).floatValue();
                    float eventY = list.get(i).get(1).floatValue();
                    path.lineTo(eventX, eventY);
                }
            }
            performClick();
        }

        invalidate();
    }

}

