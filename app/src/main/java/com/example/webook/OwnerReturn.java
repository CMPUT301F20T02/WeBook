package com.example.webook;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class OwnerReturn extends AppCompatActivity {
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
    private ArrayList<Double> latlong;
    private TextView date;
    private String isbn_base;
    private TextView time;
    private ArrayList<Integer> dateSelected;
    private Boolean scaned;
    private String timeChosen;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent1 = getIntent();
        final BookRequest bookRequest = (BookRequest) intent1.getSerializableExtra("request");

        setContentView(R.layout.request_profile_owner_return);
        mapView = findViewById(R.id.return_location_map);
        title = findViewById(R.id.request_profile_book_title);
        isbn = findViewById(R.id.request_profile_ISBN);
        owner = findViewById(R.id.request_profile_owner);
        borrower = findViewById(R.id.request_profile_borrower);
        status= findViewById(R.id.request_profile_status);
        scan = findViewById(R.id.request_profile_return_scan);
        time = findViewById(R.id.return_time);
        date = findViewById(R.id.return_date);
        address = findViewById(R.id.location_instruction);
        dateSelected = new ArrayList<Integer>();
        latlong = new ArrayList<Double>();
        scaned = false;

        String owner_name = "Owner: " + bookRequest.getRequestee();
        String borrower_name = "Borrower: " + bookRequest.getRequester().get(0);
        isbn_base = bookRequest.getBook().getISBN();
        final String book_isbn = "ISBN: " + isbn_base;
        String book_title = "Title: " + bookRequest.getBook().getTitle();
        String book_status = "Status: " + bookRequest.getStatus();
        owner.setText(owner_name);
        borrower.setText(borrower_name);
        isbn.setText(book_isbn);
        title.setText(book_title);
        status.setText(book_status);
        address.setText("This is the return location selected by the borrower: ");

        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OwnerReturn.this,CodeScanner.class);
                startActivityForResult(intent,2);
            }
        });


        if(bookRequest.getTime() != null){
            time.setText(bookRequest.getTime());
            timeChosen = bookRequest.getTime();
            scaned = true;
        }
        if(bookRequest.getDate() != null){
            Integer year = bookRequest.getDate().get(0);
            Integer month = bookRequest.getDate().get(1);
            Integer day = bookRequest.getDate().get(2);
            dateSelected = bookRequest.getDate();
            String dateHere= Integer.toString(year) + "-" + Integer.toString(month) + "-"
                    + Integer.toString(day);
            date.setText(dateHere);
        }

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
                if(bookRequest.getGeoLocation() != null){
                    Double latitude = bookRequest.getGeoLocation().get(0);
                    Double longitude = bookRequest.getGeoLocation().get(1);
                    latlong = bookRequest.getGeoLocation();
                    LatLng selected = new LatLng(latitude,longitude);
                    marker = googleMap.addMarker(new MarkerOptions().position(selected)
                            .title("Chosen place"));
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(selected,15));
                }else {
                    LatLng xiamen = new LatLng(24.58505466160215,118.09337005019188);
                    marker = googleMap.addMarker(new MarkerOptions().position(xiamen)
                            .title("Marker in Xiamen"));
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(xiamen,15));
                }
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



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2){
            if(resultCode == RESULT_OK){
                final String code = (String) data.getSerializableExtra("code");
                final FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("requests").document(code).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            DocumentSnapshot documentSnapshot = task.getResult();
                            if(documentSnapshot.exists()){
                                BookRequest bookRequest = documentSnapshot.toObject(BookRequest.class);
                                if(bookRequest.getStatus() != null){
                                    if(bookRequest.getStatus().equals("waiting_for_return")){
                                        //if (isbn_base.equals(bookRequest.getBook().getISBN())){
                                        if (timeChosen != null) {
                                            if(!latlong.isEmpty()) {
                                                if(!dateSelected.isEmpty()) {
                                                    db.collection("requests").document(isbn_base).update("status", "available");
                                                    db.collection("requests").document(isbn_base).update("time", null);
                                                    db.collection("requests").document(isbn_base).update("geoLocation", null);
                                                    db.collection("requests").document(isbn_base).update("date", null);
                                                    scaned = true;
                                                    finish();
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                });
            }
        }
    }
}

