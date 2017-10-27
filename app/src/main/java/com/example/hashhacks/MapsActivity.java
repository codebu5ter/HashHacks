package com.example.hashhacks;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import static com.example.hashhacks.R.id.map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Double a1,a2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps2);
         a1 = HomeFrag.lat1;
         a2 = HomeFrag.longi1;


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
        LatLng coordinate = new LatLng(a1, a2);
        mMap.addMarker(new MarkerOptions().position(coordinate).title("Marker"));
        CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(coordinate, 5);
        mMap.animateCamera(yourLocation);
        mMap.animateCamera( CameraUpdateFactory.zoomTo( 17.0f ) );
       // mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(a1, a2), 40.0f));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(coordinate));
    }
}
