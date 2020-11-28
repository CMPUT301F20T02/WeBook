package com.example.webook;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class BorrowerReturn extends AppCompatActivity {
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
    private Button confirm;
    private Marker marker;
    private TextView date;
    private String isbn_base;
    private TextView time;
    private ArrayList<Double> latlong;
    private ArrayList<Integer> dateSelected;
    private String timeChosen;
    private  LatLng locationSelected;
    private Boolean scaned;
    private Calendar myCalendar; //initialize a calender object
    private DataBaseManager dataBaseManager;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.request_profile_borrower_return);
        mapView = findViewById(R.id.return_location_map);
        title = findViewById(R.id.request_profile_book_title);
        isbn  = findViewById(R.id.request_profile_ISBN);
        owner = findViewById(R.id.request_profile_owner);
        borrower = findViewById(R.id.request_profile_borrower);
        address = findViewById(R.id.location_instruction);
        status = findViewById(R.id.request_profile_status);
        scan = findViewById(R.id.request_profile_return_scan);
        date = findViewById(R.id.return_date);
        time = findViewById(R.id.return_time);
        confirm = findViewById(R.id.request_profile_confirm_button);
        myCalendar = Calendar.getInstance();
        dateSelected = new ArrayList<Integer>();
        latlong = new ArrayList<Double>();
        scaned = false;

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(BorrowerReturn.this, date_c, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BorrowerReturn.this,TimePickerActivity.class);
                startActivityForResult(intent,3);
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timeChosen != null) {
                    if(!latlong.isEmpty()) {
                        if(!dateSelected.isEmpty()) {
                            final FirebaseFirestore db = FirebaseFirestore.getInstance();
                            db.collection("requests").document(isbn_base).update("time", timeChosen);
                            db.collection("requests").document(isbn_base).update("geoLocation", latlong);
                            db.collection("requests").document(isbn_base).update("date", dateSelected);
                            finish();
                        }
                    }
                }
            }
        });
        mapView.onCreate(savedInstanceState);
        Places.initialize(getApplicationContext(), "AIzaSyDvu69tLn3WmOwJD-mfx2OJV_DtYNUBILw");
        Intent intent1 = getIntent();
        final BookRequest bookRequest = (BookRequest) intent1.getSerializableExtra("request");
        String owner_name = "Owner: " + bookRequest.getRequestee();
        String borrower_name = "Borrower: " + bookRequest.getRequester().get(0);
        isbn_base = bookRequest.getBook().getISBN();
        final String book_isbn = "ISBN: " + isbn_base;
        String book_title = "Title: " + bookRequest.getBook().getTitle();
        String book_status = "Status: " + bookRequest.getBook().getStatus();
        owner.setText(owner_name);
        borrower.setText(borrower_name);
        isbn.setText(book_isbn);
        title.setText(book_title);
        status.setText(book_status);
        address.setText("Please click the map to select the return location.");

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
                        Intent intent = new Intent(BorrowerReturn.this,SimpleMapViewActivity.class);
                        startActivityForResult(intent,1);
                    }
                });
                Places.isInitialized();
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
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(xiamen));
                }
            }
        });

        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(BorrowerReturn.this,CodeScanner.class);
                startActivityForResult(intent,2);
            }
        });

        dataBaseManager = new DataBaseManager();
        owner.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                dataBaseManager.getUserBorrowerReturn(BorrowerReturn.this, bookRequest.getBook().getOwner());
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

            dateSelected.add(0,year);
            dateSelected.add(1,monthOfYear);
            dateSelected.add(2,dayOfMonth);

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
                    latlong.add(0,latitude);
                    latlong.add(1,longitude);

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
                                if(bookRequest.getBook().getStatus().equals("borrowed")){
                                    if(bookRequest.getwaiting()){
                                        //if (isbn_base.equals(bookRequest.getBook().getISBN())){
                                        if (timeChosen != null) {
                                            if(!latlong.isEmpty()) {
                                                if(!dateSelected.isEmpty()) {
                                                    db.collection("requests").document(isbn_base).update("waiting", true);
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
        if (requestCode == 3){
            if(resultCode == RESULT_OK){
                timeChosen = (String) data.getSerializableExtra("time");
                time.setText(timeChosen);
            }
        }
    }
}
