package com.example.hashhacks;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeStandalonePlayer;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    // The following are used for the shake detection
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;
    private final int SPEECH_RECOGNITION_CODE = 1;
    private String urltest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(MainActivity.this, ShakeService.class);
        startService(intent);

        // ShakeDetector initialization
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector();
        mShakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {

            @Override
            public void onShake(int count) {
                Toast.makeText(getApplicationContext(), "shake",Toast.LENGTH_SHORT).show();
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
                    if (text.matches("I cannot sleep")) {
                        Intent intent = YouTubeStandalonePlayer.createPlaylistIntent(this, YoutubeActivity.GOOGLE_API_KEY, YoutubeActivity.YOUTUBE_PLAYLIST, 0, 0, true, true);
                        startActivity(intent);
                    }
                    else if (text.matches("I want to be healthy")) {

//                        JSONParse jsonParse = new JSONParse();
//                        urltest=jsonParse.createUri();
//                        jsonParse.getJSONObjectFromURL(urltest);

//                        HttpURLConnection urlConnection = null;
//                        try {
//                            URL url = new URL("https://api.waqi.info/feed/geo:28.6947212;77.2146029/?token=2ab788dd6b5d69073cb1e26b973d8b35f2d45d3a");
//                            urlConnection = (HttpURLConnection) url.openConnection();
//                            urlConnection.setRequestMethod("GET");
//                            urlConnection.setReadTimeout(10000 /* milliseconds */);
//                            urlConnection.setConnectTimeout(15000 /* milliseconds */);
//                            urlConnection.setDoOutput(true);
//                            urlConnection.connect();
//
//                            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
//                            StringBuilder sb = new StringBuilder();
//                            String line;
//                            while ((line = br.readLine()) != null) {
//                                sb.append(line + "\n");
//                            }
//
//                            Log.d(TAG, "onActivityResult: " + sb.toString());
//                            br.close();
//                        } catch (MalformedURLException e) {
//                            e.printStackTrace();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        } finally {
//                            urlConnection.disconnect();
//
                        Intent intent = YouTubeStandalonePlayer.createPlaylistIntent(this, YoutubeActivity.GOOGLE_API_KEY, "PLpThn6Vz7qBf-xWRixnWD7ZYzGi6ctEvK", 0, 0, true, true);
                        startActivity(intent);

                    }
                    else if (text.matches("calculate my body mass index")) {
                        Intent intent = new Intent(this, FirstFragment.class);
                        startActivity(intent);
                    }



                }
                break;
            }
        }

    }

}
