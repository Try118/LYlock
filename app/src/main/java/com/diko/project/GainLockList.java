package com.diko.project;

import android.content.Context;
import android.widget.ListView;

import com.diko.project.Module.LockInfo;
import com.diko.project.Utils.BaseNet;

import java.util.Vector;

/**
 * Created by YX_PC on 2018/4/11.
 */

public class GainLockList extends BaseNet {
    private Vector<LockInfo> lockInfo;
    private ListView listView;
    private Context context;

    public GainLockList(ListView listView, Context context) {
        this.listView = listView;
        this.context = context;
    }
    LockInfo lock_info = new LockInfo();

}
