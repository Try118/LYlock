package com.LY.basemodule.Utils;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.LY.basemodule.Manager.BluetoothCallbackManager;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

/**
 * ====== 作者 ======
 *yx lcj
 * ====== 时间 ======
 * 2018-03-09.
 */

public class BluetoothGattCallBackUtils extends BluetoothGattCallback {

    private Handler handler = new Handler();
    //上下文
    private Context context;
    //蓝牙UUID
    final UUID SERVER = UUID.fromString("0000fff0-0000-1000-8000-00805f9b34fb");
    final UUID NOTIFY = UUID.fromString("0000fff1-0000-1000-8000-00805f9b34fb");
    final UUID WRITE = UUID.fromString("0000fff2-0000-1000-8000-00805f9b34fb");
    //final UUID SERVER=UUID.fromString("0783b03e-8535-b5a0-7140-a304d2495cb7");
    //final UUID NOTIFY=UUID.fromString("0783b03e-8535-b5a0-7140-a304d2495cb8");
    //final UUID WRITE=UUID.fromString("0783b03e-8535-b5a0-7140-a304d2495cba");


    //final UUID SERVER=UUID.fromString("00001000-0000-1000-8000-00805f9b34fb");
    // final UUID NOTIFY=UUID.fromString("00001002-0000-1000-8000-00805f9b34fb");
    //  final UUID WRITE=UUID.fromString("00001001-0000-1000-8000-00805f9b34fb");

    //发送默认值
    private String message = "!S*";
    //回调
    private BluetoothCallbackManager callback;

    public BluetoothGattCallBackUtils(Context context, BluetoothCallbackManager callback) {
        this.context = context;
        this.callback = callback;
    }

    //设置回调
    public void setCallback(BluetoothCallbackManager callback) {
        this.callback = callback;
    }

    //设置发送值
    public void setMessage(String message) {
        this.message = message;
        Log.e("YXbluetoothGattmessage:",message);
    }

    @Override
    public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            Log.e("onConnechange:",String.valueOf(newState) );
            if (newState == BluetoothProfile.STATE_CONNECTED) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("YXbluetoothGatt", "connection");
                        callback.connectCallback();
                    }
                });
            } else {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("YXbluetoothGatt", "disconnection");
                        callback.unConnectCallback();
                    }
                });
            }
    }

    public void onCharacteristicRead(BluetoothGatt gatt, final BluetoothGattCharacteristic characteristic, int status) {
        Log.e("YXbluetoothGatt", "read");
    }

    public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
        Log.e("bluetoothGatt", "write");
    }

    public void onCharacteristicChanged(final BluetoothGatt gatt, final BluetoothGattCharacteristic characteristic) {
        gatt.readCharacteristic(characteristic);
        String str = null;
        try {
            str = new String(characteristic.getValue(), "GB2312");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
       final String result = str;
        handler.post(new Runnable() {
            @Override
            public void run() {
                Log.e("BluetoothGatt_gainData", result);
                callback.readCallback(result);
            }
        });
    }

    public void onDescriptorRead(BluetoothGatt gatt, BluetoothGattDescriptor descriptor,
                                 int status) {
    }

    public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor,
                                  int status) {
    }

    public void onReliableWriteCompleted(BluetoothGatt gatt, int status) {
    }

    public void onServicesDiscovered(final BluetoothGatt gatt, int status) {
        BluetoothGattService Service = gatt.getService(SERVER);
        if (Service != null) {
            final BluetoothGattCharacteristic WriteCharacteristic = Service.getCharacteristic(WRITE);
            final BluetoothGattCharacteristic ReadCharacteristic = Service.getCharacteristic(NOTIFY);
            if (WriteCharacteristic != null && ReadCharacteristic != null) {
                gatt.setCharacteristicNotification(ReadCharacteristic, true);
                boolean isEnableNotification = gatt.setCharacteristicNotification(ReadCharacteristic,true);
                WriteCharacteristic.setValue(message);
                gatt.writeCharacteristic(WriteCharacteristic);
            }
        }
    }
}


/**
 * if(isEnableNotification) {
 * List<BluetoothGattDescriptor> descriptorList = ReadCharacteristic.getDescriptors();
 * if(descriptorList != null && descriptorList.size() > 0) {
 * for(BluetoothGattDescriptor descriptor : descriptorList) {
 * descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
 * gatt.writeDescriptor(descriptor);
 * }
 * }
 * }
 * }
 * }).start();
 */