package com.LY.project.View;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.LY.basemodule.Essential.BaseTemplate.BaseActivity;
import com.LY.project.CustomView.SetLockPasswordView;
import com.LY.project.R;

/**
 * Created by jie on 2018/4/11.
 */

public class SetLockPassword extends BaseActivity {
    private TextView back;//返回控件
    private TextView textView6;//设置门锁密码的文字
    private SetLockPasswordView my_password;//六个密码框
    private TextView next;//下一步
    private String lockKey;//相对应门锁的密钥;
    private String starttime;
    private String endtime;
    private String bluetoothaddress;//蓝牙地址

    @Override
    public int getLayoutId() {
        return R.layout.activity_set_lock_pw;
    }

    @Override
    public void initViews() {
        back = findView(R.id.back);
        textView6 = findView(R.id.textView6);
        my_password = findView(R.id.my_password);
        next = findView(R.id.next);
    }

    @Override
    public void initListener() {
        back.setOnClickListener(this);
        next.setOnClickListener(this);
    }

    @Override
    public void initData() {
        SetLockPasswordView.myCallback myCallback = new SetLockPasswordView.myCallback() {
            @Override
            public void execute() {
                my_password.reInput();
            }
        };
        my_password.setCallback(myCallback);
        lockKey=getIntent().getStringExtra("lockKey");
        starttime=getIntent().getStringExtra("starttime");
        endtime=getIntent().getStringExtra("endtime");
        bluetoothaddress=getIntent().getStringExtra("bluetoothaddress");
    }

    @Override
    public void processClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.next:
                nextStep();
                break;
            default:
                break;
        }
    }

    private void nextStep() {
        if (my_password.isNotEmpty()) {
            Intent i = new Intent(SetLockPassword.this, SetLockPasswordTime.class);
            i.putExtra("password",my_password.getText());//设置的开门密码
            i.putExtra("lockKey",lockKey);
            i.putExtra("starttime",starttime);
            i.putExtra("endtime",endtime);
            i.putExtra("bluetoothaddress",bluetoothaddress);
            startActivity(i);
        } else {
            showToast(getString(R.string.password_not_write));
        }
    }
}
