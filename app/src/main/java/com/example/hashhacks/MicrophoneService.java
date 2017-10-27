package com.example.hashhacks;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Nikhil on 27-10-2017.
 */

public class MicrophoneService extends Service implements RecognitionListener {
    private SpeechRecognizer speechRecognizer;


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        Toast.makeText(this, "Microphone Service Created", Toast.LENGTH_LONG).show();
        Log.d("tag", "onCreate");
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        speechRecognizer.setRecognitionListener(this);

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "Speak something...");

        speechRecognizer.startListening(intent);
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "My Service Stopped", Toast.LENGTH_LONG).show();
        Log.d("tag", "onDestroy");
    }

    @Override
    public void onStart(Intent intent, int startid) {
        Toast.makeText(this, "My Service Started", Toast.LENGTH_LONG).show();
        Log.d("tag", "onStart");
    }

    @Override
    public void onBeginningOfSpeech() {
        Log.d("Speech", "onBeginningOfSpeech");
    }

    @Override
    public void onBufferReceived(byte[] buffer) {
        Log.d("Speech", "onBufferReceived");
    }

    @Override
    public void onEndOfSpeech() {
        Log.d("Speech", "onEndOfSpeech");
    }

    @Override
    public void onError(int error) {
        Log.d("Speech", "onError");
    }

    @Override
    public void onEvent(int eventType, Bundle params) {
        Log.d("Speech", "onEvent");
    }

    @Override
    public void onPartialResults(Bundle partialResults) {
        Log.d("Speech", "onPartialResults");
    }

    @Override
    public void onReadyForSpeech(Bundle params) {
        Log.d("Speech", "onReadyForSpeech");
    }

    @Override
    public void onResults(Bundle results) {
        ArrayList<String> speechResult = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        if(speechResult!=null){
            if(speechResult.size()>0 ){
                String command=speechResult.get(0).toString();
                Toast.makeText(getApplicationContext(), command, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onRmsChanged(float rmsdB) {
        Log.d("Speech", "onRmsChanged");
    }
}