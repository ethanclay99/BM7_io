package com.example.geoio;

import android.Manifest;
import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;


public class MainActivity extends FragmentActivity implements OnMapReadyCallback {
    //The google map
    private GoogleMap mMap;

    //All login UI is stored in the relativeLayout
    private RelativeLayout relativeLayout;
    private Button playButton;
    private TextView username;
    private MyLocationManager locationManager;
    private LatLng newLocation;

    //String to be sent up as the player's username
    private String uName;
    private double longitude;
    private double latitude;
    private String color;
    private double radius = 2;
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        count = 0;

        // foreground only or targeting API level 28 and lower
        ActivityCompat.requestPermissions(this, new String[]
                { Manifest.permission.ACCESS_FINE_LOCATION }, 1);

        // background and targeting API level 29 and higher
        ActivityCompat.requestPermissions(this, new String[]
                        { Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_BACKGROUND_LOCATION },
                1);

        //Stuff for instantiating the Map
        setContentView(R.layout.activity_main);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //Instantiating global variables
        locationManager = new MyLocationManager();
        relativeLayout = findViewById(R.id.relativeL);
        playButton = findViewById(R.id.button);
        username = findViewById(R.id.Username);

        //Button Listener calls "clickButton" method
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickButton();
            }
        });

        //Gameplay loop of 5 seconds
        final Handler handler = new Handler();
        Runnable run = new Runnable() {
            @Override
            public void run() {
                updateMapLocation();
                handler.postDelayed(this,5000);

            }
        };
        handler.post(run);
    }

    //Method sets the user's username and gets rid of the login UI
    public void clickButton() {
        uName = username.getText().toString();
        relativeLayout.setVisibility(View.GONE);
    }


    //Method that is called when the map is first set up.
    //right now it just makes a marker in Sydney, Australia
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMaxZoomPreference(20);
        mMap.setMinZoomPreference(17);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(40.427568, -86.9213988)));
        mMap.getUiSettings().setAllGesturesEnabled(false);
        mMap.getUiSettings().setZoomControlsEnabled(true);
    }


    // Moves map camera center to whatever coordinates are entered. (Player)
    public void updateMapLocation() {
        String[] coords = locationManager.getLocation(this);
        if (coords[0] != null) {
            latitude = Double.parseDouble(coords[0]);
            longitude = Double.parseDouble(coords[1]);
            System.out.println("Latitude:" + latitude);
            System.out.println("Longitude:" + longitude);
            newLocation = new LatLng(latitude, longitude);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(newLocation));
            //mMap.addMarker(new MarkerOptions().position(newLocation).title("Berat \"Big gay\" Koc"));
            count++;
            mMap.clear();
            mMap.addCircle(new CircleOptions().center(newLocation).fillColor(Color.GREEN).radius(radius));
        }
    }
}
