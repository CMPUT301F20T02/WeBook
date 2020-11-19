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


/**
 * The most basic example of adding a map to an activity.
 */
public class SimpleMapViewActivity extends AppCompatActivity {

    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 4;
    private MapView mapView;
    private Boolean mLocationPermissionGranted = true;
    private GoogleMap mMap;
    private static final long MIN_TIME = 400;
    private static final float MIN_DISTANCE = 1000;
    private LocationManager locationManager;
    PlacesClient placesClient;
    private Marker marker;
    private  LatLng locationSelected;
    private  Location my;
    private Button confirm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tem);
        mapView = findViewById(R.id.mapView);
        confirm = findViewById(R.id.button5);
        mapView.onCreate(savedInstanceState);
        getLocationPermission();
        Places.initialize(getApplicationContext(), "AIzaSyDvu69tLn3WmOwJD-mfx2OJV_DtYNUBILw");
        placesClient = Places.createClient(this);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @SuppressLint("MissingPermission")
            @Override
            public void onMapReady(final GoogleMap googleMap) {
                // Add a marker in Sydney, Australia,
                // and move the map's camera to the same location.
                mMap = googleMap;
                locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                updateLocationUI();
                Places.isInitialized();
                Criteria criteria = new Criteria();
                mMap.setMyLocationEnabled(true);
                // Check if we were successful in obtaining the map.
                if (mMap != null) {
                    mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {

                        @Override
                        public void onMapLongClick(LatLng latLng) {
                            if (marker != null) {
                                marker.remove();
                            }
                            marker = googleMap.addMarker(new MarkerOptions().position(latLng)
                                    .title("chosen"));
                            locationSelected = latLng;
                            System.out.println(latLng);
                        }
                    });

                    Location location = locationManager.getLastKnownLocation(locationManager
                            .getBestProvider(criteria, false));
                    my = location;
                    LatLng now = new LatLng(location.getLatitude(), location.getLongitude());
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(now,18));
                }


                // Construct a PlaceDetectionClient.
                LatLng sydney = new LatLng(-33.852, 151.211);

                googleMap.addMarker(new MarkerOptions().position(sydney)
                        .title("Marker in Sydney"));
                //googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(locationSelected != null){
                    Intent intent = new Intent();
                    intent.putExtra
                            ("latitude", locationSelected.latitude);
                    intent.putExtra
                            ("longitude", locationSelected.longitude);
                    setResult(RESULT_OK, intent);
                    finish();
                }
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
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
        updateLocationUI();
    }
    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
             mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }
    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }
        try {
            if (mLocationPermissionGranted) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                //mLastKnownLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }


}