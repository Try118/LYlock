package com.diko.project.View;

import android.view.View;
import android.widget.TextView;

import com.diko.basemodule.Essential.BaseTemplate.BaseActivity;
import com.diko.project.CustomView.MyFastMenuBar;
import com.diko.project.R;

/**
 * Created by jie on 2018/4/11.
 */

public class SetLock extends BaseActivity {
    private TextView back;//返回控件
    private MyFastMenuBar correct_lock_name;//修改门锁名称
    private MyFastMenuBar correct_lock_address;//修改门锁地址
    private MyFastMenuBar set_open_password;//设置开门密码
    private MyFastMenuBar updated_lock_time;//更新门锁时间
    private MyFastMenuBar open_record;//开锁记录
    private MyFastMenuBar giver_record;//授权记录
    private MyFastMenuBar delete_all_password;//删除所有密码
    private MyFastMenuBar delete_lock;//删除门锁

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
        correct_lock_name.setOnClickListener(this);
        set_open_password.setOnClickListener(this);
        correct_lock_address.setOnClickListener(this);
        updated_lock_time.setOnClickListener(this);
        open_record.setOnClickListener(this);
        giver_record.setOnClickListener(this);
        delete_all_password.setOnClickListener(this);
        delete_lock.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void processClick(View v) {
        switch (v.getId()) {
            case R.id.back:
//                startActivity();
                break;
            case R.id.correct_lock_name:
                //  startActivity();
                break;
            case R.id.set_open_password:
                //  startActivity();
                break;
            case R.id.correct_lock_address:
                //  startActivity();
                break;
            case R.id.updated_lock_time:
                //  startActivity();
                break;
            case R.id.open_record:
                //  startActivity();
                break;
            case R.id.giver_record:
                //  startActivity();
                break;
            case R.id.delete_all_password:
                //  startActivity();
                break;
            case R.id.delete_lock:
                //  startActivity();
                break;
            default:
                break;
        }
    }
}
