package com.LY.project.View;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.LY.basemodule.Essential.BaseTemplate.BaseActivity;
import com.LY.basemodule.Essential.BaseTemplate.BluetoothActivity;
import com.LY.project.Adapter.DeviceListAdapter;
import com.LY.project.CustomView.MyProgressDialog;
import com.LY.project.Module.LockList;
import com.LY.project.R;
import com.LY.project.Utils.GattAppService;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.jar.Manifest;

/**
 * Created by YX_PC on 2018/4/10.
 * 搜索门锁
 */

public class SearchDoorLock2 extends BluetoothActivity implements BluetoothAdapter.LeScanCallback {
    private ListView myList;//展示数据列表
    private TextView back;//返回控件

    DeviceListAdapter mDeviceAdapter;
    BluetoothAdapter mBluetoothAdapter;
    private boolean mScanning = false;
    private List<LockList> lockLists = new ArrayList<LockList>();
    BluetoothGatt bluetoothGatt;
    private String account;
    private String name;
    private String address;
    private boolean lockstate = false;
    private String lockKey = null;
    private boolean oneTime = false;
    private boolean twoTime = false;
    private String password;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message message) {
            if (message.what == 0x123) {
                MyProgressDialog.remove();
            }
        }
    };
    private BluetoothAdapter MybluetoothAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_search_door_lock2;
    }

    @Override
    public void initViews() {
        myList = findView(R.id.myList);
        back = findView(R.id.back);
    }

    @Override
    public void initListener() {
        back.setOnClickListener(this);
    }

    @Override
    public void initData() {
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            requestPermissions(android.Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            requestPermissions(android.Manifest.permission.ACCESS_FINE_LOCATION);
        }
        MybluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        Log.e("initData: ", String.valueOf(MybluetoothAdapter));
        if (MybluetoothAdapter == null) {
            showToast("当前设备不支持蓝牙");
        } else {
            if (!MybluetoothAdapter.isEnabled()) {
                Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivity(intent);
            }
        }
        if (MybluetoothAdapter.isEnabled()) {
            //开始搜索
            MybluetoothAdapter.startDiscovery();
            IntentFilter filter = new IntentFilter();
            filter.addAction(BluetoothDevice.ACTION_FOUND);
            registerReceiver(receiver, filter);
        }
        MybluetoothAdapter.startLeScan(this);
        mDeviceAdapter = new DeviceListAdapter(this, lockLists);
        myList.setAdapter(mDeviceAdapter);

    }

    @Override
    public void processClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            default:
                break;
        }
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                final BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                final int rssi = intent.getParcelableExtra(BluetoothDevice.EXTRA_RSSI);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (device.getName() != null) {
                            boolean changed = true;
                            for (int i = 0; i < lockLists.size(); i++) {
                                if (lockLists.get(i).device.getName().equals(device.getName())) {
                                    lockLists.get(i).state = rssi;
                                    changed = false;
                                    break;
                                }
                            }
                            if (changed) {
                                LockList lockList = new LockList(device, rssi);
                                lockLists.add(lockList);
                            }
                            mDeviceAdapter.notifyDataSetChanged();
                        }
                    }
                });
                Log.e("onReceiveDevice: ", name + " " + address);
            }
        }
    };

    @Override
    public void onLeScan(final BluetoothDevice device, final int rssi, byte[] scanRecord) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (device.getName() != null) {
                    boolean changed = true;
                    for (int i = 0; i < lockLists.size(); i++) {
                        if (lockLists.get(i).device.getName().equals(device.getName())) {
                            lockLists.get(i).state = rssi;
                            changed = false;
                            break;
                        }
                    }
                    if (changed) {
                        LockList lockList = new LockList(device, rssi);
                        lockLists.add(lockList);
                    }
                    mDeviceAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(receiver);
        MybluetoothAdapter.cancelDiscovery();
        MybluetoothAdapter.stopLeScan(this);
        super.onDestroy();
    }
}
