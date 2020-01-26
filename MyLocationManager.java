package com.example.geoio;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;


public class MyLocationManager extends AppCompatActivity {
    private FusedLocationProviderClient fusedLocationProviderClient;
    private Location location;
    private String[] whyDoIEvenBother = new String[2];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        System.out.println("Anyone out there?");

        whyDoIEvenBother[0] = "0.2";
        whyDoIEvenBother[1] = "0.3";
        // foreground only or targeting API level 28 and lower
        ActivityCompat.requestPermissions(this, new String[]
                { Manifest.permission.ACCESS_FINE_LOCATION }, 1);

        // background and targeting API level 29 and higher
        ActivityCompat.requestPermissions(this, new String[]
                        { Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_BACKGROUND_LOCATION },
                1);

    }

    public String[] getLocation (Activity ryanGay) {

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(ryanGay);
        fusedLocationProviderClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location loc) {
                        System.out.println("Bruh");
                        if (loc != null) {
                            // Got last known location. In some rare situations this can be null.
                            location = loc;
                            whyDoIEvenBother[0] = "" + location.getLatitude();
                            whyDoIEvenBother[1] = "" + location.getLongitude();
                            System.out.println(whyDoIEvenBother[0]);
                            System.out.println(whyDoIEvenBother[1]);
                        } else {

                        }
                    }
                });
        return whyDoIEvenBother;
    }
//    //global coordinate variables
//    double lat;
//    double lon;
//
//    //sets up manager to read locationListener every 5 seconds
//    @RequiresApi(api = Build.VERSION_CODES.M)
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        LocationManager locationManager =
//                (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        LocationListener locationListener = new LocationListener() {
//            @Override
//            public void onLocationChanged(Location location) {
//                lat = location.getLatitude();
//                lon = location.getLongitude();
//            }
//
//            @Override
//            public void onStatusChanged(String provider, int status, Bundle extras) {
//
//            }
//
//            @Override
//            public void onProviderEnabled(String provider) {
//
//            }
//
//            @Override
//            public void onProviderDisabled(String provider) {
//
//            }
//        };
//        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) ==
//            PackageManager.PERMISSION_GRANTED &&
//            checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) ==
//            PackageManager.PERMISSION_GRANTED) {
//                locationManager.requestLocationUpdates(
//                    LocationManager.GPS_PROVIDER, 5000, 10, locationListener);
//        }
//    }
//
//    //returns coordinate array to the Main Activity
//    public double[] getLocation() {
//        double[] coordinates = new double[2];
//
//        coordinates[0] = lat;
//        coordinates[1] = lon;
//
//        return coordinates;
//    }
//    public double getLatitude() {
//        return lat;
//    }
//    public double getLongitude() {
//        return lon;
//    }
}