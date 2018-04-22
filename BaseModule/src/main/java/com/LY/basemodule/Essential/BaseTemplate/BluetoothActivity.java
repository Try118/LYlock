package com.LY.basemodule.Essential.BaseTemplate;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.widget.Toast;

import com.LY.basemodule.Manager.BluetoothCallbackManager;
import com.LY.basemodule.Utils.BluetoothGattCallBackUtils;

/**
 * ====== 作者 ======
 * lcj yx
 * ====== 时间 ======
 * 2018-03-09.
 */

public abstract class BluetoothActivity extends BaseActivity {
    //获取远程蓝牙设备
    private BluetoothDevice device;
    private BluetoothGattCallBackUtils bluetoothGattCallback;
    public BluetoothGatt gatt;
    public void getBluetooth(String bluetoothAddress, BluetoothCallbackManager manager)
    {
        //检查蓝牙地址
        if (BluetoothAdapter.getDefaultAdapter().checkBluetoothAddress(bluetoothAddress))
        {
            device=BluetoothAdapter.getDefaultAdapter().getRemoteDevice(bluetoothAddress);
            if(device==null)
            {
                showToast("Can not find this Bluetooth");
                finish();
            }
            else{
                bluetoothGattCallback=new BluetoothGattCallBackUtils(this,manager);
                gatt = device.connectGatt(this,false,bluetoothGattCallback);
                if(gatt==null)
                {
                    Toast.makeText(this, "bluetoothGatt is null", Toast.LENGTH_SHORT).show();
                }
                else {
                    gatt.connect();
                }          
            }
        }
        else{
            showToast("The bluetooth address was illegal");
            finish();
        }
    }
}
