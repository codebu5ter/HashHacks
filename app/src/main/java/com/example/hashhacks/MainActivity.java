package com.example.hashhacks;

import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.fusedbulblib.GetCurrentLocation;
import com.fusedbulblib.interfaces.DialogClickListener;
import com.fusedbulblib.interfaces.GpsOnListner;
import com.google.android.youtube.player.YouTubeStandalonePlayer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements GpsOnListner{
    private static final String TAG = "MainActivity";

    private Double lattitude,longitude;
    // The following are used for the shake detection
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;
    private final int SPEECH_RECOGNITION_CODE = 1;
    private String urltest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //It's enough to remove the line
        requestWindowFeature(Window.FEATURE_NO_TITLE);

//But if you want to display  full screen (without action bar) write too

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.activity_main);

        Intent intent = new Intent(MainActivity.this, ShakeService.class);
        startService(intent);
        TextView txt = (TextView)findViewById(R.id.t1);
        Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/md2.ttf");
        txt.setTypeface(typeface);
        // ShakeDetector initialization
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector();
        mShakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {

            @Override
            public void onShake(int count) {
//                Toast.makeText(getApplicationContext(), "shake",Toast.LENGTH_SHORT).show();
                startSpeechToText();
//                Intent intent = new Intent(MainActivity.this, MicrophoneService.class);
//                startService(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        // Add the following line to register the Session Manager Listener onResume
        mSensorManager.registerListener(mShakeDetector, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onPause() {
        mSensorManager.unregisterListener(mShakeDetector);
        super.onPause();
    }

    private void startSpeechToText() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "Speak something...");
        try {
            startActivityForResult(intent, SPEECH_RECOGNITION_CODE);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    "Sorry! Speech recognition is not supported in this device.",
                    Toast.LENGTH_SHORT).show();
        }
    }
    /**
     * Callback for speech recognition activity
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SPEECH_RECOGNITION_CODE: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String text = result.get(0);
                    Toast.makeText(getApplicationContext(),
                            text,
                            Toast.LENGTH_SHORT).show();
                    if (text.matches("I want to sleep")) {
                        Intent intent = YouTubeStandalonePlayer.createPlaylistIntent(this, YoutubeActivity.GOOGLE_API_KEY, YoutubeActivity.YOUTUBE_PLAYLIST, 0, 0, true, true);
                        startActivity(intent);
                    }
                    else if (text.matches("I want to be healthy")) {
                        Intent intent = YouTubeStandalonePlayer.createPlaylistIntent(this, YoutubeActivity.GOOGLE_API_KEY, "PLpThn6Vz7qBf-xWRixnWD7ZYzGi6ctEvK", 0, 0, true, true);
                        startActivity(intent);

                    }
                    else if (text.matches("calculate my body mass index")) {
                        Intent intent = new Intent(this, FirstFragment.class);
                        startActivity(intent);
                    }
                    else if (text.matches("accident"))
                    {
                        new GetCurrentLocation(MainActivity.this).getCurrentLocation();
                    }
                    else if (text.matches("I need blood"))
                    {
                        startActivity(new Intent(this, NeedBlood.class));
                    }
                    else if (text.matches("options"))
                    {
                        startActivity(new Intent(this, MainActivity2.class));
                    }



                }
                break;
            }
        }

    }
    @Override
    public void gpsStatus(boolean _status) {
        if (_status==false){
            new CheckGPSDialog(this).showDialog();
        }else {
            new GetCurrentLocation(MainActivity.this).getCurrentLocation();
        }
    }

    @Override
    public void gpsPermissionDenied(int deviceGpsStatus) {
        if (deviceGpsStatus==1){
            permissionDeniedByUser();
        }else {
            new GetCurrentLocation(MainActivity.this).getCurrentLocation();
        }
    }

    @Override
    public void gpsLocationFetched(Location location) {
        if (location != null) {
            lattitude = location.getLatitude();
            longitude = location.getLongitude();
            String lat = Double.toString(location.getLatitude());
            String longi = Double.toString(location.getLongitude());
            Toast.makeText(MainActivity.this, lat + " " + longi, Toast.LENGTH_SHORT).show();
           /* Intent intent = new Intent(MainActivity.this, MapsActivity.class);
            /*//*intent.putExtra("lat",lat1);
            //intent.putExtra("long",longi2);*//**//*
            startActivity(intent);*/
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
                Toast.makeText(MainActivity.this, address, Toast.LENGTH_SHORT).show();
                Toast.makeText(MainActivity.this, city, Toast.LENGTH_SHORT).show();
                Toast.makeText(MainActivity.this, state, Toast.LENGTH_SHORT).show();
                Toast.makeText(MainActivity.this, country, Toast.LENGTH_SHORT).show();

                String p1 = "9818684437";
                String p2 = "7982395773";
                String message = " i am in distress. Please help me. My location is "+ address + " "+ city;
                SmsManager sms = SmsManager.getDefault();
                sms.sendTextMessage(p1,null,message,null,null);
                sms.sendTextMessage(p2,null,message,null,null);
                Toast.makeText(MainActivity.this, "SMS sent", Toast.LENGTH_SHORT).show();
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
                    new GetCurrentLocation(MainActivity.this).getCurrentLocation();
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
                new GetCurrentLocation(MainActivity.this).getCurrentLocation();
            }

            @Override
            public void negativeListener(Activity context, Dialog dialog) {
                dialog.dismiss();
            }
        });

    }
}


