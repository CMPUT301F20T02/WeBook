package com.example.webook;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;

public class BorrowerRequestDelivery extends AppCompatActivity {
    private MapView mapView;
    private GoogleMap mMap;
    private TextView title;
    private TextView isbn;
    private TextView owner;
    private TextView borrower;
    private TextView address;
    private TextView status;
    private Button scan;
    private Marker marker;
    private LatLng locationSelected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent1 = getIntent();
        BookRequest request = (BookRequest) intent1.getSerializableExtra("request");
        setContentView(R.layout.request_profile_borrower_deliver);
        mapView = findViewById(R.id.deliver_location_map);
        title = findViewById(R.id.request_profile_book_title);
        isbn = findViewById(R.id.request_profile_ISBN);
        owner = findViewById(R.id.request_profile_owner);
        borrower = findViewById(R.id.request_profile_borrower);
        status= findViewById(R.id.request_profile_status);
        scan = findViewById(R.id.request_profile_deliver_scan);
        mapView.onCreate(savedInstanceState);
        Places.initialize(getApplicationContext(), "AIzaSyDvu69tLn3WmOwJD-mfx2OJV_DtYNUBILw");
        mapView.getMapAsync(new OnMapReadyCallback() {
            @SuppressLint("MissingPermission")
            @Override
            public void onMapReady(final GoogleMap googleMap) {
                // Add a marker in Sydney, Australia,
                // and move the map's camera to the same location.
                mMap = googleMap;
                // Check if we were successful in obtaining the map.

                // Construct a PlaceDetectionClient.
                LatLng sydney = new LatLng(-33.852, 151.211);

                googleMap.addMarker(new MarkerOptions().position(sydney)
                        .title("Marker in Sydney"));
                if (mMap != null) {
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,18));
                }
                //googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

}
