package com.diko.project.View;

import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.diko.basemodule.Essential.BaseTemplate.BaseActivity;
import com.diko.project.CustomView.RefreshableView;
import com.diko.project.GainLockList;
import com.diko.project.R;

/**
 * Created by YX_PC on 2018/4/10.
 * 添加门锁
 */

public class AddLock extends BaseActivity {
    private RelativeLayout addLock;//添加门锁
    private TextView setting;//右上角三点
    private ListView lockList;//门锁列表
    private GainLockList gainLockList;
    private RefreshableView refreshableView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_lock;
    }

    @Override
    public void initViews() {
        addLock = (RelativeLayout) findView(R.id.add_lock);
        setting = (TextView) findView(R.id.setting);
        lockList=(ListView)findView(R.id.lock_list);
        refreshableView=findView(R.id.refreshable_view);
    }

    @Override
    public void initListener() {
        addLock.setOnClickListener(this);
        setting.setOnClickListener(this);
    }

    @Override
    public void initData() {
        gainLockList=new GainLockList(lockList,AddLock.this);
        refreshableView.setOnRefreshListener(new RefreshableView.PullToRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    new Thread(gainLockList).start();
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                refreshableView.finishRefreshing();
            }
        },0);
        new Thread(gainLockList).start();
    }

    @Override
    public void processClick(View v) {
        switch (v.getId()) {
            case R.id.add_lock:
                startActivity(SearchDoorLock2.class);
                break;
            case R.id.setting:
                startActivity(Setting.class);
                break;
            default:
                break;
        }
    }
}
