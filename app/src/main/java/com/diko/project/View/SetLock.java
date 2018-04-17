package com.diko.project.View;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.diko.basemodule.Essential.BaseTemplate.BaseActivity;
import com.diko.project.CustomView.MyFastMenuBar;
import com.diko.project.R;

/**
 * Created by jie on 2018/4/11.
 */

public class SetLock extends BaseActivity implements MyFastMenuBar.onMenuBarClickListener {
    private TextView back;//返回控件
    private MyFastMenuBar correct_lock_name;//修改门锁名称
    private MyFastMenuBar correct_lock_address;//修改门锁地址
    private MyFastMenuBar set_open_password;//设置开门密码
    private MyFastMenuBar updated_lock_time;//更新门锁时间
    private MyFastMenuBar open_record;//开锁记录
    private MyFastMenuBar giver_record;//授权记录
    private MyFastMenuBar delete_all_password;//删除所有密码
    private MyFastMenuBar delete_lock;//删除门锁
    private String lock_name;//相对应的门锁名字
    private String starttime;//相对应的开始时间
    private String endtime;//相对应的门锁结束时间
    private String lockKey;//相对应门锁的密钥
    private String address;//相对应的门锁地址

    @Override
    public int getLayoutId() {
        return R.layout.activity_lock_of__setting;
    }

    @Override
    public void initViews() {
        back = findView(R.id.back);
        correct_lock_name = findView(R.id.correct_lock_name);
        correct_lock_address = findView(R.id.correct_lock_address);
        set_open_password = findView(R.id.set_open_password);
        updated_lock_time = findView(R.id.updated_lock_time);
        open_record = findView(R.id.open_record);
        giver_record = findView(R.id.giver_record);
        delete_all_password = findView(R.id.delete_all_password);
        delete_lock = findView(R.id.delete_lock);
    }

    @Override
    public void initListener() {
        back.setOnClickListener(this);
        correct_lock_name.setOnMenuBarClickListener(this);
        set_open_password.setOnMenuBarClickListener(this);
        correct_lock_address.setOnMenuBarClickListener(this);
        updated_lock_time.setOnMenuBarClickListener(this);
        open_record.setOnMenuBarClickListener(this);
        giver_record.setOnMenuBarClickListener(this);
        delete_all_password.setOnMenuBarClickListener(this);
        delete_lock.setOnMenuBarClickListener(this);
    }

    @Override
    public void initData() {
        Intent i = getIntent();
        lock_name = i.getStringExtra("lock_name");
        starttime = i.getStringExtra("starttime");
        endtime = i.getStringExtra("endtime");
        lockKey = i.getStringExtra("lockKey");
        address = i.getStringExtra("address");
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

    @Override
    public void onMenuBarClick(MyFastMenuBar view) {
        switch (view.getId()) {
            case R.id.correct_lock_name:
                startActivity(SetLockName.class);
                break;
            case R.id.set_open_password:
                startActivity(SetLockPassword.class);
                break;
            case R.id.correct_lock_address:
                startActivity(SetLockAddress.class);
                break;
            case R.id.updated_lock_time:
                startActivity(SetUpdateLockTime.class);
                break;
            case R.id.open_record:
                startActivity(SetLockOpenLockRecord.class);
                break;
            case R.id.giver_record:
                startActivity(SetLockAuthorizationRecord.class);
                break;
            case R.id.delete_all_password:
                startActivity(SetLockDeleteAllPassword.class);
                break;
            case R.id.delete_lock:
                startActivity(SetLockDeletePage.class);
                break;
            default:
                break;
        }
    }
}
