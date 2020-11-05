package com.cm_grupo18.paint.ui.home;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.cm_grupo18.paint.R;

public class PaleteFragment extends Fragment implements View.OnClickListener{

    public PaleteFragment() {
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
        View rootView = inflater.inflate(R.layout.fragment_palete, container, false);

        createButtons(rootView);

        return rootView;
    }

    private void createButtons(View rootView) {
        Button blackBtn = rootView.findViewById(R.id.black_btn);
        blackBtn.setOnClickListener(this);

        Button whiteBtn = rootView.findViewById(R.id.white_btn);
        whiteBtn.setOnClickListener(this);

        Button redBtn = rootView.findViewById(R.id.red_btn);
        redBtn.setOnClickListener(this);

        Button yellowBtn = rootView.findViewById(R.id.yellow_btn);
        yellowBtn.setOnClickListener(this);

        Button greenBtn = rootView.findViewById(R.id.green_btn);
        greenBtn.setOnClickListener(this);

        Button cyanBtn = rootView.findViewById(R.id.cyan_btn);
        cyanBtn.setOnClickListener(this);

        Button blueBtn = rootView.findViewById(R.id.blue_btn);
        blueBtn.setOnClickListener(this);

        Button purpleBtn = rootView.findViewById(R.id.purple_btn);
        purpleBtn.setOnClickListener(this);

        Button decBtn = rootView.findViewById(R.id.decrease_size_btn);
        decBtn.setOnClickListener(this);

        Button incBtn = rootView.findViewById(R.id.increase_size_btn);
        incBtn.setOnClickListener(this);

        Button eraseBtn = rootView.findViewById(R.id.erase_btn);
        eraseBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        FragmentManager fm = getFragmentManager();
        PaintFragment fragm = (PaintFragment) fm.findFragmentById(R.id.paint_fragment);

        switch (v.getId()){
            case R.id.black_btn:
                fragm.changePaintColor(Color.BLACK);
                break;
            case R.id.white_btn:
                fragm.changePaintColor(Color.WHITE);
                break;
            case R.id.red_btn:
                fragm.changePaintColor(Color.RED);
                break;
            case R.id.yellow_btn:
                fragm.changePaintColor(Color.YELLOW);
                break;
            case R.id.green_btn:
                fragm.changePaintColor(Color.GREEN);
                break;
            case R.id.cyan_btn:
                fragm.changePaintColor(Color.CYAN);
                break;
            case R.id.blue_btn:
                fragm.changePaintColor(Color.BLUE);
                break;
            case R.id.purple_btn:
                fragm.changePaintColor(Color.argb(255, 125, 0, 255));
                break;
            case R.id.decrease_size_btn:
                fragm.decreasePainSize();
                break;
            case R.id.increase_size_btn:
                fragm.increasePainSize();
                break;
            case R.id.erase_btn:
                fragm.erasePaint();
                break;
        }
    }
}