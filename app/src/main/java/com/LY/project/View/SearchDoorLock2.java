package com.LY.project.View;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
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
import com.LY.basemodule.Manager.BluetoothCallbackManager;
import com.LY.basemodule.Utils.BluetoothGattCallBackUtils;
import com.LY.project.Adapter.DeviceListAdapter;
import com.LY.project.Controller.LockController;
import com.LY.project.Controller.LoginController;
import com.LY.project.CustomView.MyProgressDialog;
import com.LY.project.Manager.InterfaceManger;
import com.LY.project.Module.LockList;
import com.LY.project.Module.activeLock;
import com.LY.project.Module.addLock;
import com.LY.project.R;
import com.LY.project.Utils.GattAppService;
import com.LY.project.Utils.GetDate;
import com.LY.project.Utils.RetrofitUtils;
import com.LY.project.Utils.isEmpty;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.jar.Manifest;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

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
    private String account;//账户
    private String name;//蓝牙设备名称
    private String address;//mac地址
    private boolean lockstate = false;
    private String lockKey = null;
    private boolean oneTime = false;
    private boolean twoTime = false;
    private String password;//密码
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message message) {
            if (message.what == 0x123) {
                MyProgressDialog.remove();
            }
        }
    };
    private BluetoothAdapter MybluetoothAdapter;
    private String lockid;//服务器传回的

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
        Toast.makeText(this, getString(R.string.trying_to_search), Toast.LENGTH_LONG).show();
        getUserInfo();
        MybluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
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

        final BluetoothCallbackManager manager = new BluetoothCallbackManager() {
            @Override
            public void readCallback(String result) {
                Log.e("YXreadCallback: ", result);
                //门锁无雇主
                if (result.contains("Noneuser") && !lockstate) {
                    lockstate = true;
                    //激活门锁
                    LockController lockController = new LockController(SearchDoorLock2.this);
                    List<MultipartBody.Part> parts = null;
                    Map<String, RequestBody> params = new HashMap<>();
                    params.put("BTAdress", RetrofitUtils.convertToRequestBody(address));//mac地址
                    Log.e("YXBTAdress: ", address);
                    lockController.activeLock(params, parts, new InterfaceManger.OnRequestListener() {
                        @Override
                        public void onSuccess(Object success) {
                            activeLock ac = (activeLock) success;
                            //传mac到服务器,取回秘钥
                            lockKey = ac.getLockKey();
                            Log.e("YXonSuccess: ", lockKey);
                            bluetoothGattCallback.setMessage("(!Key" + lockKey + "*)");
                            gatt.discoverServices();
                        }

                        @Override
                        public void onError(String error) {
                            showToast(error);
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
                } else if (!lockstate) {
                    showToast("门锁已有雇主");
                }

                //激活门锁后,lockKey被赋值,lockstate被赋值true,这里进行添加门锁(物理地址暂时是mac地址,门锁名称暂时是蓝牙名称)
                if (lockKey != null && result.contains(lockKey) && lockstate) {
                    showToast("开始进行添加门锁");
//                    handler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            String message = GetDate.getDate();
//                            bluetoothGattCallback.setMessage("(" + message + ")");
//                            gatt.discoverServices();
//                        }
//                    });
//                    try {
//                        Thread.sleep(100);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                    LockController lockController = new LockController(SearchDoorLock2.this);
                    List<MultipartBody.Part> parts = null;
                    Map<String, RequestBody> params = new HashMap<>();
                    params.put("phone", RetrofitUtils.convertToRequestBody(account));//账户
                    params.put("lockKey", RetrofitUtils.convertToRequestBody(lockKey));//秘钥
                    params.put("name", RetrofitUtils.convertToRequestBody(name));//现在是蓝牙设备名称,后期会被修改
                    params.put("BTAdress", RetrofitUtils.convertToRequestBody(address));//mac地址
                    lockController.addLock(params, parts, new InterfaceManger.OnRequestListener() {
                        @Override
                        public void onSuccess(Object success) {
                            //success:{"code":1,"lockid":"513"}
                            addLock al = (addLock) success;
                            lockid = al.getLockid();
                            MyProgressDialog.remove();
                            Intent i = new Intent(SearchDoorLock2.this, Reset.class);
                            i.putExtra("lockKey", lockKey);//服务器取回的秘钥
                            i.putExtra("address", address);//mac地址
                            i.putExtra("lockid", lockid);//添加门锁时候,服务器传回来的lockid
                            startActivity(i);
                        }

                        @Override
                        public void onError(String error) {
                            showToast(error);
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
                }
            }

            @Override
            public void writeCallback(String result) {

            }

            @Override
            public void connectCallback() {
                MyProgressDialog.remove();
                MyProgressDialog.show(SearchDoorLock2.this, "adding...", false, null);
                handler.sendEmptyMessageDelayed(0x123, 10000);
                bluetoothGattCallback.setMessage("(!S*)");
                boolean b = gatt.discoverServices();
                //Log.e("gatt.discoverServices", String.valueOf(b));//true
            }

            @Override
            public void unConnectCallback() {

            }
        };

        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final LockList lockList = lockLists.get(position);
                address = lockList.device.getAddress();//mac
                name = lockList.device.getName();//蓝牙设备名称
                Log.e("YXdevice", lockList.device.getAddress());
                Log.e("YXdevice", lockList.device.getName());
                getBluetooth(lockList.device, manager);
                MyProgressDialog.show(SearchDoorLock2.this, "connecting...", false, null);
                handler.sendEmptyMessageDelayed(0x123, 10000);
                Log.e("device", lockList.device.getAddress());
            }
        });
    }

    @Override
    public void processClick(View v) {
        switch (v.getId()) {
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
                final int rssi = intent.getIntExtra(GattAppService.EXTRA_RSSI, 0);
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


    @Override
    protected void onPause() {
        lockLists.clear();
        mDeviceAdapter.notifyDataSetChanged();
        super.onPause();
    }

    public void getUserInfo() {
        SharedPreferences info = getSharedPreferences("UserInformation", MODE_PRIVATE);
        account = info.getString("account", null);
        password = info.getString("password", null);
        if (isEmpty.StringIsEmpty(account) || isEmpty.StringIsEmpty(password)) {
            finish();
        }
    }
}
