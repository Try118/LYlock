package com.LY.project.Module;

import android.bluetooth.BluetoothDevice;

/**
 * Created by lcj yx on 2017/10/31.
 */

public class LockList {
    public BluetoothDevice device;
    public int state;
    public LockList(BluetoothDevice device, int s){
        this.device=device;
        state=s;
    }
}
