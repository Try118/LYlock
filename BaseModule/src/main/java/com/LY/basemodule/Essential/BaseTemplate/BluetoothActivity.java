package com.LY.basemodule.Essential.BaseTemplate;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.LY.basemodule.Manager.BluetoothCallbackManager;
import com.LY.basemodule.Utils.BluetoothGattCallBackUtils;
import com.vise.baseble.ViseBle;
import com.vise.baseble.callback.IConnectCallback;
import com.vise.baseble.core.DeviceMirror;
import com.vise.baseble.exception.BleException;
//import com.vise.baseble.ViseBle;
//import com.vise.baseble.callback.IConnectCallback;
//import com.vise.baseble.core.DeviceMirror;
//import com.vise.baseble.exception.BleException;

import java.lang.reflect.Method;
import java.util.UUID;

import cn.com.heaton.blelibrary.ble.Ble;
import cn.com.heaton.blelibrary.ble.BleDevice;
import cn.com.heaton.blelibrary.ble.L;
import cn.com.heaton.blelibrary.ble.callback.BleConnCallback;

import cn.com.heaton.blelibrary.ble.Ble;
import cn.com.heaton.blelibrary.ble.BleDevice;
import cn.com.heaton.blelibrary.ble.callback.BleConnCallback;

/**
 * ====== 作者 ======
 * lcj yx
 * ====== 时间 ======
 * 2018-03-09.
 */

public abstract class BluetoothActivity extends BaseActivity {
    //获取远程蓝牙设备

    public BluetoothDevice device;
    public BluetoothGattCallBackUtils bluetoothGattCallback;
    public BluetoothGatt gatt;
    private Ble<BleDevice> mBle;

    BleDevice bleDevice = new BleDevice();

    public void getBluetooth(String bluetoothAddress, BluetoothCallbackManager manager) {
        //检查蓝牙地址
        if (BluetoothAdapter.getDefaultAdapter().checkBluetoothAddress(bluetoothAddress)) {
            device = BluetoothAdapter.getDefaultAdapter().getRemoteDevice(bluetoothAddress);
            if (device == null) {
                showToast("Can not find this Bluetooth");
                finish();
            } else {
                bluetoothGattCallback = new BluetoothGattCallBackUtils(this, manager);

                gatt = device.connectGatt(this, false, bluetoothGattCallback);

                if (gatt == null) {
                    Toast.makeText(this, "bluetoothGatt is null", Toast.LENGTH_SHORT).show();
                } else {

//                    mBle = Ble.getInstance();
//                    initBle();
//                    bleDevice.setBleAddress(bluetoothAddress);
//                    bleDevice.setBleName("WL3667313AA351");

                    Log.e("getBluetooth: ", bluetoothAddress);
                    gatt.close();
                    Boolean a = gatt.connect();

//                    mBle.connect(bleDevice, connectCallback);
                    Log.e("bluetoothGatt:connect", "1、显示连接状态"+String.valueOf(a));
                }
            }
        } else {
            showToast("The bluetooth address was illegal");
            finish();
        }
    }

    private BleConnCallback<BleDevice> connectCallback = new BleConnCallback<BleDevice>() {
        @Override
        public void onConnectionChanged(BleDevice device) {
            if (device.isConnected()) {
            } else {
            }
        }

        @Override
        public void onConnectException(BleDevice device, int errorCode) {
            super.onConnectException(device, errorCode);
            mBle = null;
        }
    };

    private void initBle() {
        Ble.Options options = new Ble.Options();
        options.logBleExceptions = true;//设置是否输出打印蓝牙日志
        options.throwBleException = true;//设置是否抛出蓝牙异常
        options.autoConnect = false;//设置是否自动连接
        options.scanPeriod = 12 * 1000;//设置扫描时长
        options.connectTimeout = 15 * 1000;//设置连接超时时长
        options.uuid_service = UUID.fromString("0000fff0-0000-1000-8000-00805f9b34fb");//设置主服务的uuid
        options.uuid_write_cha = UUID.fromString("0000fff2-0000-1000-8000-00805f9b34fb");//设置可写特征的uuid
        options.uuid_read_cha = UUID.fromString("0000fff1-0000-1000-8000-00805f9b34fb");//设置可读特征的uuid
        mBle.init(getApplicationContext(), options);
    }

    public boolean refreshDeviceCache() {
        if (gatt != null) {
            try {
                BluetoothGatt localBluetoothGatt = gatt;
                Method localMethod = localBluetoothGatt.getClass().getMethod(
                        "refresh", new Class[0]);
                if (localMethod != null) {
                    boolean bool = ((Boolean) localMethod.invoke(
                            localBluetoothGatt, new Object[0])).booleanValue();
                    return bool;
                }
            } catch (Exception localException) {
//                Log.i(TAG, "An exception occured while refreshing device");
            }
        }
        return false;
    }

    public void getBluetooth(BluetoothDevice device, BluetoothCallbackManager manager) {
        bluetoothGattCallback = new BluetoothGattCallBackUtils(this, manager);
        gatt = device.connectGatt(this, false, bluetoothGattCallback);
        if (gatt == null) {
            Toast.makeText(this, "bluetoothGatt is null", Toast.LENGTH_SHORT).show();
        } else {
            Log.e("YXgetBluetooth", "gatt不为空");

            boolean a = gatt.connect();

            Log.e("YXgetBluetooth", String.valueOf(a));
        }
    }
}
