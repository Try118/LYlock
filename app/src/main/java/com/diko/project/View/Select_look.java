package com.diko.project.View;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.diko.basemodule.Essential.BaseTemplate.BaseActivity;
import com.diko.project.R;
import com.diko.project.Utils.StringToDate;

/**
 * Created by jie on 2018/4/10.
 */

public class Select_look extends BaseActivity {
    private TextView back;//返回
    private TextView lockname;//锁的名字
    private TextView lock_setting;//设置门锁信息
    private TextView start;//开始时间
    private TextView end_time;//结束时间
    private LinearLayout open_lock;//开锁
    private LinearLayout send_password;//发送密码
    private LinearLayout giver;//用户授权
    private String lock_name;//相对应的门锁名字
    private String starttime;//相对应的开始时间
    private String endtime;//相对应的门锁结束时间
    private String lockKey;//相对应门锁的密钥
    private String address;//相对应的门锁地址

    @Override
    public int getLayoutId() {
        return R.layout.activity_select__lock;
    }

    @Override
    public void initViews() {
        back = findView(R.id.back);
        lockname = findView(R.id.lock_name);
        lock_setting = findView(R.id.lock_setting);
        start = findView(R.id.start_time);
        end_time = findView(R.id.end_time);
        open_lock = findView(R.id.open_lock);
        send_password = findView(R.id.send_password);
        giver = findView(R.id.giver);
    }

    @Override
    public void initListener() {
        back.setOnClickListener(this);
        lock_setting.setOnClickListener(this);
        open_lock.setOnClickListener(this);
        send_password.setOnClickListener(this);
        giver.setOnClickListener(this);
    }

    @Override
    public void initData() {
        Intent i = getIntent();
        lock_name = i.getStringExtra("lock_name");
        starttime = i.getStringExtra("starttime");
        endtime = i.getStringExtra("endtime");
        lockKey = i.getStringExtra("lockKey");
        address = i.getStringExtra("address");

        lockname.setText(lock_name);
        start.setText(StringToDate.times(String.valueOf(Long.valueOf(starttime) * 1000)));
        if (endtime.equals("0")) {
            end_time.setText("永久");
        } else {
            end_time.setText(StringToDate.times(String.valueOf(Long.valueOf(endtime) * 1000)));
        }

    }

    @Override
    public void processClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.lock_setting:
                Intent intent = new Intent(this,SetLock.class);
                intent.putExtra("lock_name",lock_name);
                intent.putExtra("starttime",starttime);
                intent.putExtra("endtime",endtime);
                intent.putExtra("lockKey",lockKey);
                intent.putExtra("address",address);
                startActivity(intent);
                break;
            case R.id.open_lock:
                //  startActivity();
                break;
            case R.id.send_password:
                startActivity(LockSentPasswordOne.class);
                break;
            case R.id.giver:
                startActivity(LockSentAuthorityOne.class);
                break;
            default:
                break;
        }
    }
}
