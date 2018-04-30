package com.LY.project.View;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothGatt;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.LY.basemodule.Essential.BaseTemplate.BaseActivity;
import com.LY.basemodule.Essential.BaseTemplate.BluetoothActivity;
import com.LY.project.Adapter.DeviceListAdapter;
import com.LY.project.CustomView.MyProgressDialog;
import com.LY.project.Module.LockList;
import com.LY.project.R;

import java.util.Vector;

/**
 * Created by YX_PC on 2018/4/10.
 * 搜索门锁
 */

public class SearchDoorLock2 extends BluetoothActivity {
    private ListView myList;//展示数据列表
    private TextView back;//返回控件

    DeviceListAdapter mDeviceAdapter;
    BluetoothAdapter mBluetoothAdapter;
    private boolean mScanning = false;
    private Vector<LockList> lockLists=new Vector<>();
    BluetoothGatt bluetoothGatt;


    private String account;
    private String name;
    private String address;
    private boolean lockstate=false;
    private String lockKey=null;
    private boolean oneTime=false;
    private boolean twoTime=false;
    private String password;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message message){
            if(message.what==0x123){
                MyProgressDialog.remove();
            }
        }
    };
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

    }

    @Override
    public void processClick(View v) {

    }
}
