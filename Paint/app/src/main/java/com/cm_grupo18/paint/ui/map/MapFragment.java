package com.cm_grupo18.paint.ui.map;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.cm_grupo18.paint.PaintActivityDrawer;
import com.cm_grupo18.paint.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.snackbar.Snackbar;

public class MapFragment extends Fragment implements View.OnClickListener, LocationListener {
    private Button drawingBtn;
    private View rootView;
    private GoogleMap map;
    private Location myLocation;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_map, container, false);

        SupportMapFragment smf = (SupportMapFragment) getChildFragmentManager().
                findFragmentById(R.id.google_map);

        smf.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(final GoogleMap googleMap) {
                //When the map is ready
                map = googleMap;
                centerOnMyLocation();
                map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        //When click on map
                        //Initialize marker options
                        MarkerOptions mo = new MarkerOptions();
                        mo.position(latLng);
                        mo.title(latLng.latitude + " : " + latLng.longitude);
                        map.clear();
                        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                        map.addMarker(mo);
                    }
                });
            }
        });

        drawingBtn = rootView.findViewById(R.id.map_draw_btn);
        drawingBtn.setTag(1);
        drawingBtn.setText("Start Drawing");
        drawingBtn.setOnClickListener(this);

        return rootView;
    }

    private void centerOnMyLocation() {

        if (ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        map.setMyLocationEnabled(true);

        LocationManager lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        }

        if (myLocation != null) {
            LatLng location = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 20));
        }

    }

    @Override
    public void onClick(View v) {
        int status =(Integer) v.getTag();
        if(status == 1) {
            //start drawing
            //TODO

            v.setTag(0);
            drawingBtn.setText("Stop Drawing");

        } else {
            //stop drawing
            //TODO

            v.setTag(1);
            drawingBtn.setText("Start Drawing");
        }
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        myLocation = location;
    }

    @Override
    public void onProviderDisabled(String provider) {}

    @Override
    public void onProviderEnabled(String provider) {}

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}
}