package com.adafruit.bluefruit.le.connect.service;

import android.util.Log;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;

public class WebSocketService {

    private static final String TAG = "WEBSOCKET";
    private Socket mSocket = null;

    public void connectWebSocket() {
        try {
            mSocket = IO.socket("https://fathomless-peak-84606.herokuapp.com");
            Socket connect = mSocket.connect();
        } catch (URISyntaxException e) {
            Log.d(TAG, "Error: " + e.getMessage());
        }
    }

    public void onDrinkBought() {
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
}


