package com.cm_grupo18.paint;

import android.util.Pair;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PaintCanvasDTO {
    private int backgroundColor;
    private float paintStroke;
    private int paintColor;
    private List<List<List<Float>>> pathPoints;

    public PaintCanvasDTO() {}

    public int getBackgroundColor(){
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor){
        this.backgroundColor = backgroundColor;
    }

    public float getPaintStroke() {
        return paintStroke;
    }

    public void setPaintStroke(float paintStroke) {
        this.paintStroke = paintStroke;
    }

    public int getPaintColor() {
        return paintColor;
    }

    public void setPaintColor(int paintColor) {
        this.paintColor = paintColor;
    }

    public List<List<List<Float>>> getPathPoints() {
        return pathPoints;
    }

    public void setPathPoints(List<List<List<Float>>> pathPoints) {
        this.pathPoints = pathPoints;
    }


    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        //result.put("uid", uid);
        result.put("background", backgroundColor);
        result.put("paintStroke", paintStroke);
        result.put("paintColor", paintColor);
        result.put("pathPoints", pathPoints);

        return result;
    }

}
