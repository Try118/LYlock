package com.LY.project.View;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.LY.basemodule.Essential.BaseTemplate.BaseActivity;
import com.LY.project.CustomView.MyFastMenuBar;
import com.LY.project.R;

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
    private String lockId;//相对应的门锁ID
    private String bluetoothaddress;//门锁蓝牙地址
    private String power;//权限

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
        lockId=i.getStringExtra("lockId");
        bluetoothaddress=i.getStringExtra("bluetoothaddress");
        power=i.getStringExtra("power");

        SharedPreferences.Editor editor = getSharedPreferences("UserInformation", MODE_PRIVATE).edit();
        editor.putString("lock_name",lock_name);
        editor.putString("starttime",starttime);
        editor.putString("endtime",endtime);
        editor.putString("lockKey",lockKey);
        editor.putString("address",address);
        editor.putString("lockId",lockId);
        editor.putString("bluetoothaddress",bluetoothaddress);
        editor.putString("power",power);
        editor.apply();

        if (power.contains("3")){
            set_open_password.setVisibility(View.GONE);
            open_record.setVisibility(View.GONE);
            giver_record.setVisibility(View.GONE);
//            set_open_password.setVisibility(View.INVISIBLE);
        }
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
                Intent i = new Intent(SetLock.this, SetLockName.class);
                i.putExtra("lockKey",lockKey);
                startActivity(i);
                break;
            case R.id.set_open_password:
                Intent i3 = new Intent(SetLock.this, SetLockPasswordPage.class);
                i3.putExtra("lockKey",lockKey);
                i3.putExtra("starttime",starttime);
                i3.putExtra("endtime",endtime);
                i3.putExtra("address",address);
                i3.putExtra("bluetoothaddress",bluetoothaddress);
                startActivity(i3);

                break;
            case R.id.correct_lock_address:
                Intent intent = new Intent(SetLock.this, SetLockAddress.class);
                intent.putExtra("lockKey",lockKey);
                startActivity(intent);
                break;
            case R.id.updated_lock_time:
                Intent i4 = new Intent(SetLock.this, SetUpdateLockTime.class);
                i4.putExtra("power",power);
                i4.putExtra("bluetoothaddress",bluetoothaddress);
                startActivity(i4);
                break;
            case R.id.open_record:
                Intent i2 = new Intent(this, SetLockOpenLockRecord.class);
                i2.putExtra("lockId",lockId);
                startActivity(i2);
                break;
            case R.id.giver_record:
                Intent intent1 = new Intent(SetLock.this, SetLockAuthorizationRecord.class);
                intent1.putExtra("lockKey",lockKey);
                startActivity(intent1);
                break;
            case R.id.delete_all_password:
                startActivity(SetLockDeleteAllPassword.class);
                break;
            case R.id.delete_lock:
                Intent i5 = new Intent(SetLock.this, SetLockDeletePage.class);
                i5.putExtra("power",power);
                i5.putExtra("bluetoothaddress",bluetoothaddress);
                i5.putExtra("lockId",lockId);
                i5.putExtra("lockKey",lockKey);
                startActivity(i5);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onStart() {
        SharedPreferences information = getSharedPreferences("UserInformation", MODE_PRIVATE);
        lock_name = information.getString("lock_name",null);
        starttime = information.getString("starttime",null);
        endtime = information.getString("endtime",null);
        lockKey = information.getString("lockKey",null);
        address = information.getString("address",null);
        lockId = information.getString("lockId",null);
        bluetoothaddress = information.getString("bluetoothaddress",null);
        Log.e("onResumeendtime: ",endtime );
        super.onStart();
    }
}
