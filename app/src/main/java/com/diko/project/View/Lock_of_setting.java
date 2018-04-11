package com.diko.project.View;

import android.view.View;
import android.widget.TextView;

import com.diko.basemodule.Essential.BaseTemplate.BaseActivity;
import com.diko.project.CustomView.MyFastMenuBar;

/**
 * Created by jie on 2018/4/11.
 */

public class Lock_of_setting extends BaseActivity {
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
        return 0;
    }

    @Override
    public void initViews() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void processClick(View v) {

    }
}
