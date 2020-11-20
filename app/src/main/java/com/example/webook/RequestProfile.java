package com.example.webook;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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
    private Marker marker;
    private TextView date;
    private String isbn_base;
    Date dateSelected = new Date();
    private TextView time;
    private  LatLng locationSelected;
    private Calendar myCalendar; //initialize a calender object
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.request_profile_borrower_deliver);
        mapView = findViewById(R.id.deliver_location_map);
        title = findViewById(R.id.request_profile_book_title);
        isbn  = findViewById(R.id.request_profile_owner);
//        owner = findViewById(R.id.Owner);
        borrower = findViewById(R.id.request_profile_borrower);
        address = findViewById(R.id.location_instruction);
        status = findViewById(R.id.request_profile_status);
        scan = findViewById(R.id.request_profile_deliver_scan);
        date = findViewById(R.id.deliver_date);
        time = findViewById(R.id.deliver_time);
        myCalendar = Calendar.getInstance();

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(RequestProfile.this, date_c, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RequestProfile.this,TimePickerActivity.class);
                startActivityForResult(intent,3);
            }
        });

        mapView.onCreate(savedInstanceState);
        Places.initialize(getApplicationContext(), "AIzaSyDvu69tLn3WmOwJD-mfx2OJV_DtYNUBILw");
        Intent intent1 = getIntent();
        String owner_name = "Owner: " + intent1.getStringExtra("owner_name");
        String borrower_name = "Borrower: " + intent1.getStringExtra("borrower_name");
        isbn_base = intent1.getStringExtra("isbn");
        String book_isbn = "ISBN: " + isbn_base;
        String book_title = "Title: " + intent1.getStringExtra("book_title");
        String book_status = "Status: accepted";
//        owner.setText(owner_name);
        borrower.setText(borrower_name);
        isbn.setText(book_isbn);
        title.setText(book_title);
        status.setText(book_status);
        address.setText("Please click the map to select the deliver location.");
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

                Intent intent = new Intent(RequestProfile.this,CodeScanner.class);
                startActivityForResult(intent,2);
            }
        });
    }




    DatePickerDialog.OnDateSetListener date_c = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            final FirebaseFirestore db = FirebaseFirestore.getInstance();
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            ArrayList<Integer> dateSelected = new ArrayList<Integer>();
            dateSelected.add(0,year);
            dateSelected.add(1,monthOfYear);
            dateSelected.add(2,dayOfMonth);
            db.collection("requests").document(isbn_base).update("date",dateSelected);
            updateLabel();
        } // set the  listener of the calender


    };

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        date.setText(sdf.format(myCalendar.getTime()));
    } // set the date format



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
        if(requestCode== 1) {
            if(resultCode == RESULT_OK){
                final FirebaseFirestore db = FirebaseFirestore.getInstance();
                Double latitude =  (Double) data.getSerializableExtra("latitude");
                Double longitude =  (Double) data.getSerializableExtra("longitude");
                if(latitude != null) {
                    Location location = new Location("tem");
                    location.setLatitude(latitude);
                    location.setLongitude(longitude);
                    LatLng latLng =  new LatLng(latitude,longitude);
                    if(marker != null) {
                        marker.remove();
                    }
                    marker = mMap.addMarker(new MarkerOptions().position(latLng)
                            .title("the chosen place"));
                    System.out.println("here is nice");
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));
                    String text = "latitude: " + Double.toString(latitude) + "\n" + "longitude: " + Double.toString(longitude);
                    address.setText(text);
                    ArrayList<Double> latlong = new ArrayList<Double>();
                    latlong.add(0,latitude);
                    latlong.add(1,longitude);
                    db.collection("requests").document(isbn_base).update("geoLocation",latlong);
                }
            }
        }
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
                                    if(bookRequest.getStatus().equals("accepted")){
                                        db.collection("requests").document(code).update("status","waiting");
                                    }
                                }
                            }
                        }
                    }
                });
            }
        }
        if (requestCode == 3){
            if(resultCode == RESULT_OK){
                final String timeChosen = (String) data.getSerializableExtra("time");
                final FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("requests").document(isbn_base).update("time",timeChosen);
                time.setText(timeChosen);
            }
        }
    }
}
