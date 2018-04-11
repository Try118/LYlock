package com.diko.project.View;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.diko.basemodule.Essential.BaseTemplate.BaseActivity;
import com.diko.project.R;

/**
 * Created by jie on 2018/4/10.
 */

public class Select_look extends BaseActivity {
    private TextView back;//返回
    private TextView lock_name;//锁的名字
    private TextView lock_setting;//设置门锁信息
    private TextView start_time;//开始时间
    private TextView end_time;//结束时间
    private LinearLayout open_lock;//开锁
    private LinearLayout send_password;//发送密码
    private LinearLayout giver;//用户授权
    @Override
    public int getLayoutId() {
        return R.layout.activity_select__lock;
    }

    @Override
    public void initViews() {
        back = findView(R.id.back);
        lock_name = findView(R.id.lock_name);
        lock_setting = findView(R.id.lock_setting);
        start_time = findView(R.id.start_time);
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


    }

    @Override
    public void processClick(View v) {
        switch (v.getId()){
            case R.id.back:
//                startActivity();
                break;
            case R.id.lock_setting:
                //  startActivity();
                break;
            case R.id.open_lock:
                //  startActivity();
                break;
            case R.id.send_password:
                //  startActivity();
                break;
            case R.id.giver:
                //  startActivity();
                break;
            default:
                break;
        }
    }
}
