package com.example.hashhacks;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    ArrayList<Double> db;
    ArrayList<Double> db2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        db = new ArrayList<>();
        db2 = new ArrayList<>();

        for(int i=0; i<DonorList.donorInfo.size(); i++)
        {
            Log.d("Donor", String.valueOf(i));


            Donor donor = DonorList.donorInfo.get(i);
            Double lat = new Double(donor.lat);
            Double lng = new Double(donor.lan);

            db.add(lat);
            db2.add(lng);
            Log.d("Donor", donor.lat);
            Log.d("Donor", donor.lan);


            /*LatLng donar = new LatLng(lat, lng);
            //String donorName = donor.name+ " " + donor.contuctNumber;
            mMap.addMarker(new MarkerOptions().position(donar));*/
        }

        mapFragment.getMapAsync(this);
    }
    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
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
        LatLng you = new LatLng(MainActivity2.lat, MainActivity2.lng);
        mMap.addMarker(new MarkerOptions().position(you).title("Your Position").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

        //mMap.addMarker(new MarkerOptions().position(new LatLng(db.get(0),db2.get(0))).title("0"));

        for (int i=0;i<db.size();i++)
        {
            Double l = db.get(i);
            Double m = db2.get(i);

//            Log.i("TAG", "Lat = " +l);
//            Log.i("TAG", "Long= " + m);

            LatLng donar = new LatLng(l,m);
            mMap.addMarker(new MarkerOptions().position(new LatLng(l,m)).title("Frnd Location"));
        }
        mMap.moveCamera( CameraUpdateFactory.newLatLngZoom(you , 15.0f) );

//        Double lat1 = 28.7501;
//        Double lng1 = 77.2096;
//        LatLng donar1 = new LatLng(lat1, lng1);
//
//        mMap.addMarker(new MarkerOptions().position(donar1));
    }
}