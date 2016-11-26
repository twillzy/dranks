package com.adafruit.bluefruit.le.connect.service;

import android.util.Log;

import com.adafruit.bluefruit.le.connect.ui.GemmaColour;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;

public class WebSocketService {

    private static final String TAG = "WEBSOCKET";
    public static final String URL = "https://fathomless-peak-84606.herokuapp.com";
    private final ColourChangingService colourChangingService;
    private Socket mSocket = null;

    public WebSocketService(ColourChangingService colourChangingService) {
        this.colourChangingService = colourChangingService;
    }

    public void connectWebSocket() {
        try {
            mSocket = IO.socket(URL);
            Socket connect = mSocket.connect();
            onDrinkBought();
            onBreathalyzer();
        } catch (URISyntaxException e) {
            Log.d(TAG, "Error: " + e.getMessage());
        }
    }

    public void disconnect() {
//        mSocket.disconnect();
    }

    private void onDrinkBought() {
        if (mSocket == null) {
            return;
        }
        try {
            mSocket.on("drinkBought", new Emitter.Listener() {
                @Override
                public void call(Object... args) {

                }
            });
        } catch(Exception e) {

        }
    }

    private void onBreathalyzer() {
        if (mSocket == null) {
            return;
        }
        try {
            mSocket.on("breathalyzer", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    colourChangingService.changeColour(GemmaColour.GREEN);
                }
            });
        } catch(Exception e) {

        }
    }
}


