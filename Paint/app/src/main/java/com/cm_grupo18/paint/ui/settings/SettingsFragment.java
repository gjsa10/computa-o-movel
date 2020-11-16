package com.cm_grupo18.paint.ui.settings;

import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Point;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.cm_grupo18.paint.PaintActivityDrawer;
import com.cm_grupo18.paint.R;
import com.google.android.material.snackbar.Snackbar;

public class SettingsFragment extends Fragment implements View.OnClickListener, SeekBar.OnSeekBarChangeListener{

    private static float OFFSET = 8.0f * 10; //10 just seems to work

    int new_background_color;

    private View rootView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        Button saveBtn = rootView.findViewById(R.id.settings_save_button);
        saveBtn.setOnClickListener(this);

        setGradientSeekBar(R.id.seekbar_color);

        SeekBar seekBar = rootView.findViewById(R.id.seekbar_color);
        seekBar.setOnSeekBarChangeListener(this);

        PaintActivityDrawer pad = (PaintActivityDrawer)getActivity();
        if (pad != null){
            pad.hideOptionsMenu();
        }

        return rootView;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.settings_save_button) {
            ((PaintActivityDrawer) getActivity()).setBackgroundColor(new_background_color);
            Snackbar.make(rootView.findViewById(R.id.settings_frag), R.string.back_saved, Snackbar.LENGTH_SHORT)
                    .show();
        }
    }

    private void setGradientSeekBar(int id){
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point displaySize = new Point();
        display.getSize(displaySize);
        int width = displaySize.x;
        //int heigth = mdispSize.y;

        LinearGradient gradient = new LinearGradient(0.0f, 0.0f, width - OFFSET, 0.0f,
                new int[] { 0xFF000000, 0xFF0000FF, 0xFF8000FF, 0xFFFF0080, 0xFFFF0000, 0xFFFF8000, 0xFFFFFF00, 0xFF00FF00, 0xFF00FFFF, 0xFFFFFFFF },
                null, Shader.TileMode.CLAMP);
        ShapeDrawable shape = new ShapeDrawable(new RectShape());
        shape.getPaint().setShader(gradient);

        SeekBar seekBar = rootView.findViewById(id);
        seekBar.setProgressDrawable(shape);
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

            new_background_color = Color.argb(255, r, g, b);

            ImageView imageViewIcon = (ImageView) rootView.findViewById(R.id.back_color_preview);
            imageViewIcon.setBackgroundColor(new_background_color);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {}

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {}

}