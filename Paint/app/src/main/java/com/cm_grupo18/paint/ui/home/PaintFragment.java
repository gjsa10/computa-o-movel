package com.cm_grupo18.paint.ui.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.cm_grupo18.paint.GestureListener;
import com.cm_grupo18.paint.PaintActivityDrawer;
import com.cm_grupo18.paint.PaintCanvas;
import com.cm_grupo18.paint.R;

public class PaintFragment extends Fragment {

    private PaintCanvas paintCanvas;

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

        GestureListener mGestureListener = new GestureListener();
        GestureDetector mGestureDetector = new GestureDetector(getActivity().getApplicationContext(), mGestureListener);
        mGestureDetector.setIsLongpressEnabled(true);
        mGestureDetector.setOnDoubleTapListener(mGestureListener);

        int bColor = ((PaintActivityDrawer)getActivity()).getBackgroundColor();

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

    public void undoPaint() {
        paintCanvas.undoPaint();
        paintCanvas.invalidate();
    }

}