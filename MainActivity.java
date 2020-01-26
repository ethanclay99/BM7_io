package com.example.geoio;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback {
    //The google map
    private GoogleMap mMap;

    //All login UI is stored in the relativeLayout
    private RelativeLayout relativeLayout;
    private Button playButton;
    private TextView username;
    private MyLocationManager locationManager;

    //String to be sent up as the player's username
    private String uName;
//
//    private Timer timer;
//    private TimerTask timerTask = new TimerTask() {
//
//        @Override
//        public void run() {
//            final Random random = new Random();
//            int i = random.nextInt(2 - 0 + 1) + 0;
//        }
//    };
//
//    public void start() {
//        if(timer != null) {
//            return;
//        }
//        timer = new Timer();
//        timer.scheduleAtFixedRate(timerTask, 0, 2000);
//    }
//
//    public void stop() {
//        timer.cancel();
//        timer = null;
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    // Moves map camera center to whatever coordinates are entered. (Player)
    public void updateMapLocation() {
        double lat = locationManager.getLatitude();
        double lon = locationManager.getLongitude();
        System.out.println("Latitude:" + lat);
        System.out.println("Longitude:" + lon);
        LatLng newLocation = new LatLng(lat, lon);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(newLocation));
    }
}
