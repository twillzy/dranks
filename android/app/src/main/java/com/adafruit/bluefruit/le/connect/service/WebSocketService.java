package com.adafruit.bluefruit.le.connect.service;

import android.util.Log;

import com.adafruit.bluefruit.le.connect.ui.GemmaColour;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONObject;

import java.net.URISyntaxException;

public class WebSocketService {

    private static final String TAG = "WEBSOCKET";
    public static final String URL = "https://fathomless-peak-84606.herokuapp.com";
    private static final Integer BUDGET = 200;
    private final ColourChangingService colourChangingService;
    private Socket mSocket = null;
    private int runningTotal = 0;

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
        mSocket.disconnect();
    }

    private void onDrinkBought() {
        if (mSocket == null) {
            return;
        }
        try {
            mSocket.on("drinkBought", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    try {
                        JSONObject jsonObject = (JSONObject) args[0];
                        Integer totalPrice = (Integer) jsonObject.get("totalPrice");
                        runningTotal += totalPrice / 2;
                        if (totalPrice != null && runningTotal < BUDGET) {
                            colourChangingService.momentOfDelight();
                        } else {
                            colourChangingService.changeGemmaColour(GemmaColour.RED);
                            mSocket.disconnect();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {

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
                    Float drunkenness = null;
                    try {
                        String str = (String) jsonObject.get("drunkenness");
                        drunkenness = Float.parseFloat(str);
                    } catch (Exception e) {

                    }
                    try {
                        if (drunkenness != null && drunkenness < 3.0f) {
                            Log.d(TAG, "CHANGING " + drunkenness);
                            colourChangingService.changeGemmaColour(GemmaColour.PINK);
                        } else {
                            colourChangingService.changeGemmaColour(GemmaColour.BLACK);
                            mSocket.disconnect();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {

        }
    }
}


