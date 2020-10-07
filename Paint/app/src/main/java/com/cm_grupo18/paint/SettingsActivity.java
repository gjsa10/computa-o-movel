package com.cm_grupo18.paint;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Point;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {
    private static float OFFSET = 8.0f * 10; //10 just seems to work
    private int progress_status = 100;
    int new_background_color;
    int new_brush_color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Button cancelBtn = findViewById(R.id.cancel_button);
        cancelBtn.setOnClickListener(this);

        Button saveBtn = findViewById(R.id.save_button);
        saveBtn.setOnClickListener(this);

        setGradientSeekBar(R.id.seekbar_color);
        setGradientSeekBar(R.id.pen_color);

        SeekBar seekBar = findViewById(R.id.seekbar_color);
        seekBar.setOnSeekBarChangeListener(this);
    }

    @Override
    public void onResume(){
        super.onResume();
        SeekBar seekBar_color = findViewById(R.id.seekbar_color);
        seekBar_color.setProgress(progress_status);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel_button:

                super.onBackPressed();
                break;
            case R.id.save_button:
                PaintActivity.background = new_background_color;
                PaintView.brushcolor = new_brush_color;
                super.onBackPressed();
                break;
            default:
                break;
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if(fromUser){

            //BLACK
            int r = 0;
            int g = 0;
            int b = 0;

            if (progress <= 10){
                //black to blue
                float percentage = progress / 10.0f;
                r = 0;
                g = 0;
                b = (int) (255 * percentage);
            }
            else if (progress <= 50) {
                //blue to red
                int n = progress - 10;
                float percentage = n / 40.0f;
                r = (int) (255 * percentage);
                g = 0;
                b = (int) (255 - 255 * percentage);
            }
            else if(progress <= 70) {
                //red to yellow
                int n = progress - 50;
                float percentage = n / 20.0f;
                r = 255;
                g = (int) (255 * percentage);
                b = 0;
            }
            else if(progress <= 80) {
                //yellow to green
                float percentage = (progress - 70) / 10.0f;
                r = (int) (255 - 255 * percentage);
                g = 255;
                b = 0;
            }
            else if(progress <= 90) {
                //green to cyan
                float percentage = (progress - 80) / 10.0f;
                r = 0;
                g = 255;
                b = (int) (255 * percentage);
            }
            else if(progress <= 100) {
                //cyan to white
                float percentage = (progress - 90) / 10.0f;
                r = (int) (255 * percentage);
                g = 255;
                b = 255;
            }
            if(R.id.pen_color == seekBar.getId()){
                new_brush_color = Color.argb(255,r,g,b);
                progress_status = progress;
            }else{
                new_background_color = Color.argb(255, r, g, b);
                progress_status = progress;
            }


            ImageView imageViewIcon = (ImageView) findViewById(R.id.back_color_preview);
            imageViewIcon.setBackgroundColor(new_background_color);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {}

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {}

    private void setGradientSeekBar(int id){
        Display display = getWindowManager().getDefaultDisplay();
        Point displaySize = new Point();
        display.getSize(displaySize);
        int width = displaySize.x;
        //int heigth = mdispSize.y;

        LinearGradient gradient = new LinearGradient(0.0f, 0.0f, width - OFFSET, 0.0f,
                new int[] { 0xFF000000, 0xFF0000FF, 0xFF8000FF, 0xFFFF0080, 0xFFFF0000, 0xFFFF8000, 0xFFFFFF00, 0xFF00FF00, 0xFF00FFFF, 0xFFFFFFFF },
                null, Shader.TileMode.CLAMP);
        ShapeDrawable shape = new ShapeDrawable(new RectShape());
        shape.getPaint().setShader(gradient);

        SeekBar seekBar = findViewById(id);
        seekBar.setProgressDrawable(shape);
    }
}