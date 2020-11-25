package com.cm_grupo18.paint.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.cm_grupo18.paint.PaintActivityDrawer;
import com.cm_grupo18.paint.PaintCanvasDTO;
import com.cm_grupo18.paint.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class HomeFragment extends Fragment {

    View rootView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_home, container, false);

        PaintActivityDrawer pad = (PaintActivityDrawer)getActivity();
        if (pad != null){
            pad.showOptionsMenu();
        }

        return rootView;
    }

    public PaintCanvasDTO getPaintCanvasDTO(){
        PaintFragment fragment = (PaintFragment) getChildFragmentManager().getFragments().get(0);
        if (fragment == null) {
            return null;
        }

        return fragment.getPaintCanvasDTO();
    }

}