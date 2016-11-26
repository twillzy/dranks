package com.adafruit.bluefruit.le.connect.reactmodules.nfc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.Tag;
import android.util.Log;

import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.WritableMap;

import java.util.List;

public class NFCModule extends ReactContextBaseJavaModule implements ActivityEventListener, LifecycleEventListener {
    private NFCManager nfcManager;
    private NdefMessage ndefMessage = null;
    Tag currentTag;
    private Promise promise;

    public NFCModule(ReactApplicationContext reactContext, Activity activity) {
        super(reactContext);
        nfcManager = new NFCManager(activity);
        reactContext.addLifecycleEventListener(this);
        reactContext.addActivityEventListener(this);
    }

    @Override
    public String getName() {
        return "NFCModule";
    }

    @Override
    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void onNewIntent(Intent intent) {
        Log.d("Nfc", "New intent");
        List<NdefMessage> ndefMessages = nfcManager.readTag(intent);
        Log.d("Nfc", ndefMessages.toString());
        WritableMap map = Arguments.createMap();
        promise.resolve(map);
    }

    @Override
    public void onHostResume() {
        try {
            Log.d("NFC", "verifying NFC");
            nfcManager.verifyNFC();
            Context context = getReactApplicationContext();
            String pn = context.getApplicationContext().getPackageName();
            Intent nfcIntent = context.getPackageManager().getLaunchIntentForPackage(pn);
            nfcIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        }
        catch(NFCManager.NFCNotSupported nfcnsup) {
            Log.d("Nfc", "Not supported");
        }
        catch(NFCManager.NFCNotEnabled nfcnEn) {
            Log.d("NFC", "Not enabled");
        }
    }

    @Override
    public void onHostPause() {
//        nfcManager.disableDispatch();
    }

    @Override
    public void onHostDestroy() {
//        nfcManager.disableDispatch();
    }
}