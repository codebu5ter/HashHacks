package com.example.hashhacks;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Nikhil on 28-10-2017.
 */

public class JSONParse {
    private static final String TAG = "JSONParse";
    private String mBaseURL;
    private String test;

//    public String createUri() {
//        Log.d(TAG, "createUri: starts");
//        mBaseURL = "https://api.waqi.info/feed/geo:28.6947212;77.2146029/";
//
//
//        return Uri.parse(mBaseURL).buildUpon()
//                .appendQueryParameter("token", "2ab788dd6b5d69073cb1e26b973d8b35f2d45d3a")
////                .appendQueryParameter("tagsmode", matchAll ? "ALL" : "ANY")
////                .appendQueryParameter("lang", lang)
////                .appendQueryParameter("format", "json")
////                .appendQueryParameter("nojsoncallback", "1")
//                .build().toString();
//    }

    public JSONObject getJSONObjectFromURL(String urlString) throws IOException, JSONException {

        try {
            HttpURLConnection urlConnection = null;
            URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setDoOutput(true);
            urlConnection.connect();

            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuilder sb = new StringBuilder();

            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            br.close();

            String jsonString = sb.toString();
            System.out.println("JSON: " + jsonString);

            return new JSONObject(jsonString);
        } catch (IOException e) {
             e.printStackTrace();;
        } catch (JSONException e) {
            e.printStackTrace();
        }
            return new JSONObject();
    }
    
}
