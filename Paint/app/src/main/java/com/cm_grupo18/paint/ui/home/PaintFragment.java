package com.cm_grupo18.paint.ui.home;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.Settings;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.cm_grupo18.paint.GestureListener;
import com.cm_grupo18.paint.PaintActivityDrawer;
import com.cm_grupo18.paint.PaintCanvas;
import com.cm_grupo18.paint.R;

import static android.content.Context.SENSOR_SERVICE;

public class PaintFragment extends Fragment implements SensorEventListener {

    private static final int SHAKE_THRESHOLD = 200;
    private static final float LUX_THRESHOLD = 10.0f;

    private PaintCanvas paintCanvas;
    private SensorManager sensorManager;
    private Sensor accelerationSensor;
    private Sensor lightSensor;

    private float last_x = -1.0f, last_y = -1.0f, last_z = -1.0f;
    private long lastUpdate = 0;

    public PaintFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_paint, container, false);

        // Create sensors
        sensorManager = (SensorManager)getActivity().getSystemService(SENSOR_SERVICE);
        accelerationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        sensorManager.registerListener(this, accelerationSensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);

        // Create gesture listener
        GestureListener mGestureListener = new GestureListener();
        GestureDetector mGestureDetector = new GestureDetector(getActivity().getApplicationContext(), mGestureListener);
        mGestureDetector.setIsLongpressEnabled(true);
        mGestureDetector.setOnDoubleTapListener(mGestureListener);

        int bColor = ((PaintActivityDrawer)getActivity()).getBackgroundColor();

        // Create the paint canvas
        paintCanvas = new PaintCanvas(getActivity().getBaseContext(), null, mGestureDetector, bColor);
        mGestureListener.setCanvas(paintCanvas);

        RelativeLayout relativeLayout = rootView.findViewById(R.id.paint_frag_layout);
        relativeLayout.addView(paintCanvas);

        return rootView;
    }

    public void changePaintColor(int color){
        paintCanvas.changePaintColor(color);
        paintCanvas.invalidate();
    }

    public void erasePaint() {
        paintCanvas.erase();
        paintCanvas.invalidate();
    }

    public void decreasePainSize() {
        paintCanvas.decreasePaintSize();
        paintCanvas.invalidate();
    }

    public void increasePainSize() {
        paintCanvas.increasePaintSize();
        paintCanvas.invalidate();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            long curTime = System.currentTimeMillis();
            // only allow one update every 100ms.
            if ((curTime - lastUpdate) > 100) {
                long diffTime = (curTime - lastUpdate);
                lastUpdate = curTime;

                float x = event.values[SensorManager.DATA_X];
                float y = event.values[SensorManager.DATA_Y];
                float z = event.values[SensorManager.DATA_Z];

                float speed = Math.abs(x + y + z - last_x - last_y - last_z) / diffTime * 10000;

                if (speed > SHAKE_THRESHOLD) {
                    erasePaint();
                }
                last_x = x;
                last_y = y;
                last_z = z;
            }
        }

        if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
            float currentReading = event.values[0];

            int brightnessMode = 0;
            try {
                brightnessMode = Settings.System.getInt(getActivity().getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE);
            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
            }
            if (brightnessMode == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC) {
                Settings.System.putInt(getActivity().getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
            }

            WindowManager.LayoutParams layoutParams = getActivity().getWindow().getAttributes();
            layoutParams.screenBrightness = currentReading / LUX_THRESHOLD;
            getActivity().getWindow().setAttributes(layoutParams);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}