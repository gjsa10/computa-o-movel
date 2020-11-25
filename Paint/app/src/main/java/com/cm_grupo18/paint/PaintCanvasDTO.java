package com.cm_grupo18.paint;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class PaintCanvasDTO {
    private int backgroundColor;

    public PaintCanvasDTO() {}

    public void setBackgroundColor(int backgroundColor){
        this.backgroundColor = backgroundColor;
    }

    public int getBackgroundColor(){
        return backgroundColor;
    }



    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        //result.put("uid", uid);
        result.put("background", backgroundColor);

        return result;
    }
}
