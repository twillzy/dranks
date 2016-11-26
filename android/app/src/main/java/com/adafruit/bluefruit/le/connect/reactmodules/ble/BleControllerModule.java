package com.adafruit.bluefruit.le.connect.reactmodules.ble;

import android.app.Activity;
import android.content.Intent;

import com.adafruit.bluefruit.le.connect.service.ColourChangingService;
import com.adafruit.bluefruit.le.connect.ui.GemmaColour;
import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

public class BleControllerModule extends ReactContextBaseJavaModule implements ActivityEventListener, LifecycleEventListener {

    private ColourChangingService colourChangingService = null;

    public BleControllerModule(ReactApplicationContext reactContext, ColourChangingService colourChangingService) {
        super(reactContext);
        this.colourChangingService = colourChangingService;
        reactContext.addLifecycleEventListener(this);
        reactContext.addActivityEventListener(this);
    }

    @ReactMethod
    public void changeColourToRed(Promise promise) {
        if (colourChangingService != null) {
            colourChangingService.changeColour(GemmaColour.RED);
            promise.resolve(true);
        } else {
            promise.reject(":(", ":(");
        }
    }

    @ReactMethod
    public void changeColourToBlue(Promise promise) {
        colourChangingService.changeColour(GemmaColour.BLUE);
        promise.resolve(true);
    }

    @ReactMethod
    public void changeColourToPurple(Promise promise) {
        colourChangingService.changeColour(GemmaColour.PURPLE);
        promise.resolve(true);
    }

    @ReactMethod
    public void changeColourToYellow(Promise promise) {
        colourChangingService.changeColour(GemmaColour.YELLOW);
        promise.resolve(true);
    }

    @Override
    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void onNewIntent(Intent intent) {

    }

    @Override
    public void onHostResume() {

    }

    @Override
    public void onHostPause() {

    }

    @Override
    public void onHostDestroy() {

    }

    @Override
    public String getName() {
        return "BLEControllerModule";
    }
}
