package com.example.webook;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;

public class RequestProfile extends AppCompatActivity {
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 4;
    private MapView mapView;
    private GoogleMap mMap;
    private TextView title;
    private TextView isbn;
    private TextView owner;
    private TextView borrower;
    private TextView address;
    private TextView status;
    private Button scan;
    private  LatLng locationSelected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acticity_request_profile_owner);
        mapView = findViewById(R.id.mapView2);
        title = findViewById(R.id.Title);
        isbn  = findViewById(R.id.ISBN);
        owner = findViewById(R.id.Owner);
        borrower = findViewById(R.id.Borrower);
        address = findViewById(R.id.Address);
        status = findViewById(R.id.Status);
        scan = findViewById(R.id.Scan);
        mapView.onCreate(savedInstanceState);
        Places.initialize(getApplicationContext(), "AIzaSyDvu69tLn3WmOwJD-mfx2OJV_DtYNUBILw");
        mapView.getMapAsync(new OnMapReadyCallback() {
            @SuppressLint("MissingPermission")
            @Override
            public void onMapReady(final GoogleMap googleMap) {
                // Add a marker in Sydney, Australia,
                // and move the map's camera to the same location.
                mMap = googleMap;
                mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        Intent intent = new Intent(RequestProfile.this,SimpleMapViewActivity.class);
                        startActivityForResult(intent,1);
                    }
                });
                Places.isInitialized();
                LatLng xiamen = new LatLng(24.58505466160215,118.09337005019188);
                googleMap.addMarker(new MarkerOptions().position(xiamen)
                        .title("Marker in Xiamen"));
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(xiamen));
            }
        });

        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }





    // Add the mapView lifecycle to the activity's lifecycle methods
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


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Double latitude =  (Double) data.getSerializableExtra("latitude");
        Double longitude =  (Double) data.getSerializableExtra("longitude");
        if(resultCode == RESULT_OK) {
            if(latitude != null) {
                LatLng latLng =  new LatLng(latitude,longitude);
                mMap.addMarker(new MarkerOptions().position(latLng)
                        .title("the chosen place"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));
                String text = "latitude: " + Double.toString(latitude) + "longitude: " + Double.toString(longitude);
                address.setText(text);
            }
        }
    }
}
