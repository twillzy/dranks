package com.adafruit.bluefruit.le.connect.service;

import android.util.Log;

import com.adafruit.bluefruit.le.connect.ui.GemmaColour;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

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
                    JSONObject jsonObject = (JSONObject) args[0];
                    String drunkenness = null;
                    try {
                        drunkenness = (String)jsonObject.get("drunkenness");
                    } catch (JSONException e) {

                    }
                    Log.d(TAG, "DRUNKENNESS = " + drunkenness);
                    GemmaColour gemmaColour = GemmaColour.PURPLE;
                    if (drunkenness != null && drunkenness.compareToIgnoreCase("3.0") < 0) {
                        gemmaColour = GemmaColour.RED;
                    } else if (drunkenness != null){
                        gemmaColour = GemmaColour.GREEN;
                    }
                    colourChangingService.changeColour(gemmaColour);
                }
            });
        } catch(Exception e) {

        }
    }
}


