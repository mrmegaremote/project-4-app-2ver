package com.example.robin.project_4_group_5_app;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.util.Pair;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private MainActivity mainActivity;

    ArrayList<String> latContainers;
    ArrayList<String> lngContainers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        //get current location
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        mMap.setMyLocationEnabled(true);
        String currentLat =  Double.toString(mMap.getMyLocation().getLatitude());
        String currentLng =  Double.toString(mMap.getMyLocation().getLongitude());

        //make jsonString for parsing
        String jsonString = "10&val=0.1&lat="+currentLat+"&lng="+currentLng;

        //get list of coordinates of bike container
        ArrayList<ArrayList<android.util.Pair<String, String>>> lnglatContainers;

        lnglatContainers = JSONAdapter.initializeJSON(jsonString);

        //make string lists for lat and lng
        ArrayList<String> latContainers = new ArrayList<>();
        ArrayList<String> lngContainers = new ArrayList<>();

        //fill string lists
        for (ArrayList<android.util.Pair<String, String>> row:lnglatContainers) {
            latContainers.add(row.get(1).second);
            lngContainers.add(row.get(2).second);
        }

        //place markers
        for (int i = 0; i < lngContainers.size(); i++) {
            mMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(latContainers.get(i)), Double.parseDouble(lngContainers.get(i)))).title("Bike Container"));
        }
    }
}
