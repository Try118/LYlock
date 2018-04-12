package com.diko.project;

import android.content.Context;
import android.widget.ListView;

import com.diko.project.Adapter.MyLockAdapter;
import com.diko.project.Module.LockInfo;
import com.diko.project.Utils.BaseNet;

import java.util.Vector;
import java.util.concurrent.locks.Lock;

/**
 * Created by YX_PC on 2018/4/11.
 */

public class GainLockList extends BaseNet {
    private Vector<LockInfo> lockInfo;
    private Vector<String> lockName;
    private ListView listView;
    private Context context;

    public GainLockList(ListView listView, Context context) {
        this.listView = listView;
        this.context = context;
    }

    public void resultJY(String result){
        lockName=new Vector<String>();
        lockInfo=new Vector<LockInfo>();

        LockInfo lock_info = new LockInfo();
        lock_info.setLockId("lockid");
        lock_info.setLockKey("123");
        lock_info.setBluetoothAddress("111");
        lock_info.setStartTime("2018");
        lock_info.setPower("1");
        lock_info.setEndTime("2019");
        lock_info.setLockName("门锁名字");
        lock_info.setBluetoothName("22222");

        lockName.add(lock_info.getLockName());
        MyLockAdapter myAdapter = new MyLockAdapter(context, lockName, lockInfo);
        listView.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();
    }


}
