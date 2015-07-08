package com.mobidev.listviewphpmysqlexample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by lawrence on 4/21/15.
 */
public class MapActivity extends FragmentActivity {
    public static final String ARG_UNIVERSITY_NAME = "university_name";
    public static final String ARG_LATITUDE = "lat";
    public static final String ARG_LONGITUDE = "lng";
    private Bundle extras;

    private String uniName;
    private Double latitude;
    private Double longitude;

    private LatLng OUR_LOCATION = null;
    private GoogleMap map;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        extras = getIntent().getExtras();
        if (extras != null) {
            uniName = extras.getString(ARG_UNIVERSITY_NAME);
            latitude = extras.getDouble(ARG_LATITUDE);
            longitude = extras.getDouble(ARG_LONGITUDE);
        }

        OUR_LOCATION = new LatLng(latitude, longitude);

        map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                .getMap();
        Marker hamburg = map.addMarker(new MarkerOptions().position(OUR_LOCATION)
                .title(uniName).snippet("Welcome to our university"));

        // Move the camera instantly to the university with a zoom of 25.
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(OUR_LOCATION, 5));

        map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

        // Zoom in, animating the camera.
        map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);

        map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Toast.makeText(getApplicationContext(), "hello", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
