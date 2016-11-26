package com.adafruit.bluefruit.le.connect.service;

import android.bluetooth.BluetoothGattService;
import android.util.Log;

import com.adafruit.bluefruit.le.connect.app.UartInterfaceActivity;
import com.adafruit.bluefruit.le.connect.ble.BleManager;
import com.adafruit.bluefruit.le.connect.ble.BleUtils;
import com.adafruit.bluefruit.le.connect.ui.GemmaColour;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class ColourChangingService {

    // Service Constants
    public static final String UUID_SERVICE = "6e400001-b5a3-f393-e0a9-e50e24dcca9e";
    public static final String UUID_TX = "6e400002-b5a3-f393-e0a9-e50e24dcca9e";
    public static final int kTxMaxCharacters = 20;

    // Data
    protected BleManager mBleManager;
    protected BluetoothGattService mUartService;
    private boolean isRxNotificationEnabled = false;

    private final static String TAG = UartInterfaceActivity.class.getSimpleName();

    public ColourChangingService(BleManager bleManager) {
        this.mBleManager = bleManager;
        // Start services
        onServicesDiscovered();
    }

    public void changeGemmaColour(GemmaColour gemmaColour) throws InterruptedException {
        changeColour(gemmaColour.getColour());
    }

    public void momentOfDelight() throws Exception {
        changeColour(GemmaColour.BLUE.getColour());

        Thread.sleep(500);

        for (int i = 0; i < 10; i++) {
            turnOffNeoPixel();
            Thread.sleep(80 + i);
        }
    }

    private void turnOffNeoPixel() {
        toggleNeoPixel(8, false);
    }

    private void turnOnNeoPixel() {
        toggleNeoPixel(7, false);
    }

    private void toggleNeoPixel(int tag, boolean pressed) {
        String data = "!B" + tag + (pressed ? "1" : "0");
        ByteBuffer buffer = ByteBuffer.allocate(data.length()).order(java.nio.ByteOrder.LITTLE_ENDIAN);
        buffer.put(data.getBytes());
        sendDataWithCRC(buffer.array());
    }

    private void changeColour(int colour) throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            turnOnNeoPixel();
        }
        // Send selected color !Crgb
        byte r = (byte) ((colour >> 16) & 0xFF);
        byte g = (byte) ((colour >> 8) & 0xFF);
        byte b = (byte) ((colour >> 0) & 0xFF);

        ByteBuffer buffer = ByteBuffer.allocate(2 + 3 * 1).order(java.nio.ByteOrder.LITTLE_ENDIAN);

        // prefix
        String prefix = "!C";
        buffer.put(prefix.getBytes());

        // values
        buffer.put(r);
        buffer.put(g);
        buffer.put(b);

        byte[] result = buffer.array();
        sendDataWithCRC(result);
    }

    protected void sendDataWithCRC(byte[] data) {

        // Calculate checksum
        byte checksum = 0;
        for (byte aData : data) {
            checksum += aData;
        }
        checksum = (byte) (~checksum);       // Invert

        // Add crc to data
        byte dataCrc[] = new byte[data.length + 1];
        System.arraycopy(data, 0, dataCrc, 0, data.length);
        dataCrc[data.length] = checksum;

        // Send it
        Log.d(TAG, "Send to UART: " + BleUtils.bytesToHexWithSpaces(dataCrc));
        sendData(dataCrc);
    }

    protected void sendData(byte[] data) {
        if (mUartService != null) {
            // Split the value into chunks (UART service has a maximum number of characters that can be written )
            for (int i = 0; i < data.length; i += kTxMaxCharacters) {
                final byte[] chunk = Arrays.copyOfRange(data, i, Math.min(i + kTxMaxCharacters, data.length));
                mBleManager.writeService(mUartService, UUID_TX, chunk);
            }
        } else {
            Log.w(TAG, "Uart Service not discovered. Unable to send data");
        }
    }

    public void onServicesDiscovered() {
        mUartService = mBleManager.getGattService(UUID_SERVICE);
    }
}
