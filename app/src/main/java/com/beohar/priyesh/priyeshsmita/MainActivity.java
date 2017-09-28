package com.beohar.priyesh.priyeshsmita;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
/**
 * Created by priyesh on 28/9/17.
 */

/*
 private View.OnClickListener onLoca = new View.OnClickListener() {
        public void onClick(View v) {
            buttonStart.setEnabled(false);
            buttonStop.setEnabled(true);

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 10, this);
        }
    };

    private View.OnClickListener offLoca = new View.OnClickListener() {
        public void onClick(View v) {
            buttonStart.setEnabled(true);
            buttonStop.setEnabled(false);

            locationManager.removeUpdates(this);
            locationManager = null;
        }
    };
*/

public class MainActivity extends Activity implements LocationListener {

    private LocationManager locationManager;
    Button buttonStart;
    Button buttonStop;
    Button buttonBuddy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /********** get Gps location service LocationManager object ***********/
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        buttonStart = (Button) findViewById(R.id.buttonStart);
        buttonStop = (Button) findViewById(R.id.buttonStop);
        buttonBuddy = (Button) findViewById(R.id.buttonBuddy);

        // set button on click listeners
        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonStart.setEnabled(false);
                buttonStop.setEnabled(true);
                Toast.makeText(getApplicationContext(), "Location on", Toast.LENGTH_LONG).show();
            }
        });
        buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonStart.setEnabled(true);
                buttonStop.setEnabled(false);
                Toast.makeText(getApplicationContext(), "Location off", Toast.LENGTH_LONG).show();
            }
        });
        buttonBuddy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.beohar.priyesh.priyeshsmita.MapsActivity");
                startActivity(intent);

            }
        });

		/*
          Parameters :
		     First(provider)    :  the name of the provider with which to register
		     Second(minTime)    :  the minimum time interval for notifications, in milliseconds. This field is only used as a hint to conserve power, and actual time between location updates may be greater or lesser than this value.
		     Third(minDistance) :  the minimum distance interval for notifications, in meters
		     Fourth(listener)   :  a {#link LocationListener} whose onLocationChanged(Location) method will be called for each location update
        */

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }


        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                3000,   // 3 sec
                10, this);

        /********* After registration onLocationChanged method called periodically after each 3 sec ***********/
    }

    private boolean updateLocation(String lat, String lng) {
        //getting the specified artist reference
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("location").child("priyesh");

        //updating artist
        BuddyLocation busupdate = new BuddyLocation(lat, lng);
        dR.setValue(busupdate);
        //Toast.makeText(getApplicationContext(), "Location Updated", Toast.LENGTH_LONG).show();
        return true;
    }

    /************* Called after each 3 sec **********/
    @Override
    public void onLocationChanged(Location location) {

        //String str = "Latitude: "+location.getLatitude()+" \nLongitude: "+location.getLongitude();
        //Toast.makeText(getBaseContext(), str, Toast.LENGTH_LONG).show();


        String lat = String.valueOf(location.getLatitude());
        String lng = String.valueOf(location.getLongitude());

        updateLocation(lat, lng);
    }


    @Override
    public void onProviderDisabled(String provider) {

        /******** Called when User off Gps *********/

        Toast.makeText(getBaseContext(), "Gps turned off ", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onProviderEnabled(String provider) {

        /******** Called when User on Gps  *********/

        Toast.makeText(getBaseContext(), "Gps turned on ", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub

    }
}

