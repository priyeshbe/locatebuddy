package com.beohar.priyesh.priyeshsmita;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.beohar.priyesh.priyeshsmita.R.id.map;

//https://developers.google.com/maps/documentation/javascript/firebase
//https://developers.google.com/maps/documentation/android-api/marker
//https://developers.google.com/maps/documentation/android-api/views

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    DatabaseReference databaseArtists;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private Marker mSydney;

    private GoogleMap mMap;
    public static String live_lat;
    public static String live_lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        databaseArtists = FirebaseDatabase.getInstance().getReference("location").child("smita");
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
       LatLng sydney = new LatLng(22,22);

        mSydney = mMap.addMarker(new MarkerOptions().position(sydney).title("Smita"));
       // mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    @Override
    protected void onStart() {

        super.onStart();
        //attaching value event listener
        databaseArtists.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                BuddyLocation bloc = dataSnapshot.getValue(BuddyLocation.class);
                live_lat = bloc.getLat();
                live_lng = bloc.getLng();

                Float f1 = Float.parseFloat(live_lat);
                Float f2 = Float.parseFloat(live_lng);
                Float zoomLevel = 16.0f; //This goes up to 21

                // Add a marker in Sydney and move the camera
                LatLng sydney = new LatLng(f1,f2);


                //mMap.addMarker(new MarkerOptions().position(sydney).title("Smita"));

                //MainActivity.this.mMarker.setPosition(location);

                mSydney.setPosition(sydney);


                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, zoomLevel));


                //Toast.makeText(getApplicationContext(), bloc.getLat(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
