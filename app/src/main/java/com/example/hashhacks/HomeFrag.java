package com.example.hashhacks;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.fusedbulblib.GetCurrentLocation;
import com.fusedbulblib.interfaces.DialogClickListener;
import com.fusedbulblib.interfaces.GpsOnListner;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by Harsh on 10/27/2017.
 */

public class HomeFrag extends AppCompatActivity implements GpsOnListner {

 //   DatabaseReference db;
    public static Double lat1;
    public static Double longi1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frag_home);
       // FirebaseApp.initializeApp(this);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        Button btn = (Button)findViewById(R.id.loc);
       // db = FirebaseDatabase.getInstance().getReference().child("Location");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new GetCurrentLocation(HomeFrag.this).getCurrentLocation();

            }
        });

    }

    @Override
    public void gpsStatus(boolean _status) {
        if (_status==false){
            new CheckGPSDialog(this).showDialog();
        }else {
            new GetCurrentLocation(HomeFrag.this).getCurrentLocation();
        }
    }

    @Override
    public void gpsPermissionDenied(int deviceGpsStatus) {
        if (deviceGpsStatus==1){
            permissionDeniedByUser();
        }else {
            new GetCurrentLocation(HomeFrag.this).getCurrentLocation();
        }
    }

    @Override
    public void gpsLocationFetched(Location location) {
        if (location != null) {
            lat1 = location.getLatitude();
            longi1 = location.getLongitude();
            String lat = Double.toString(location.getLatitude());
            String longi = Double.toString(location.getLongitude());
            Toast.makeText(HomeFrag.this, lat + " " + longi, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(HomeFrag.this, MapsActivity.class);
                /*intent.putExtra("lat",lat1);
                intent.putExtra("long",longi2);*/
            startActivity(intent);
            Geocoder geocoder;
            final List<Address> addresses;
            geocoder = new Geocoder(this, Locale.getDefault());

            try {
                addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();
                String postalCode = addresses.get(0).getPostalCode();
                String knownName = addresses.get(0).getFeatureName();
                Toast.makeText(HomeFrag.this, address, Toast.LENGTH_SHORT).show();
                Toast.makeText(HomeFrag.this, city, Toast.LENGTH_SHORT).show();
                Toast.makeText(HomeFrag.this, state, Toast.LENGTH_SHORT).show();
                Toast.makeText(HomeFrag.this, country, Toast.LENGTH_SHORT).show();

                /*db.child("lattitude").setValue(lat);
                db.child("longitude").setValue(longi);
                db.child("address").setValue(address);
                db.child("city").setValue(city);
                db.child("state").setValue(state);
                db.child("country").setValue(country);*/
                // currentLocationTxt.setText(new GetAddress(this).fetchCurrentAddress(location));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            Toast.makeText(this, "unable to find", Toast.LENGTH_SHORT).show();
        }


    }
    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {

        switch (requestCode) {
            case 1: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    new GetCurrentLocation(HomeFrag.this).getCurrentLocation();
                } else {
                    permissionDeniedByUser();
                    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                }
                return;
            }}
    }

    private void permissionDeniedByUser() {

        new PermissionDeniedDialog(this).showDialog(new DialogClickListener() {
            @Override
            public void positiveListener(Activity context, Dialog dialog) {
                new GetCurrentLocation(HomeFrag.this).getCurrentLocation();
            }

            @Override
            public void negativeListener(Activity context, Dialog dialog) {
                dialog.dismiss();
            }
        });

    }

}




