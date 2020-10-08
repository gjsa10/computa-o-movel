package com.cm_grupo18.paint;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;

public class PaintActivity extends AppCompatActivity implements View.OnClickListener {

    public static int background = Color.argb(255, 255, 255, 255);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paint);

        Button settingsBtn = findViewById(R.id.settings_button);
        settingsBtn.setOnClickListener(this);



        Button aboutBtn = findViewById(R.id.about_button);
        aboutBtn.setOnClickListener(this);



        /**
         * Manter em comentário funcionade em desenvolvimento!
         */

       // PaintView paintView = findViewById(R.id.paintView);
        //setContentView(paintView);

    }

    @Override
    public void onResume(){
        super.onResume();
        View view = getWindow().getDecorView();
        view.setBackgroundColor(background);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.settings_button:
                startActivity(new Intent(PaintActivity.this, SettingsActivity.class));
                break;
            case R.id.about_button:
                startActivity(new Intent(PaintActivity.this, AboutActivity.class));
                break;
            default:
                break;
        }
    }

}