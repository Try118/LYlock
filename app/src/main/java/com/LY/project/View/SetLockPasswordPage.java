package com.LY.project.View;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.LY.basemodule.Essential.BaseTemplate.BaseActivity;
import com.LY.project.R;

/**
 * Created by YX on 2018/5/6.
 */

public class SetLockPasswordPage extends BaseActivity {
    private TextView back;
    private Button next;
    private String lockKey;//相对应门锁的密钥
    private String starttime;
    private String endtime;
    private String bluetoothaddress;//蓝牙地址

    @Override
    public int getLayoutId() {
        return R.layout.activity_setopenlockpassword_page;
    }

    @Override
    public void initViews() {
        back = findView(R.id.back);
        next = findView(R.id.next);
    }

    @Override
    public void initListener() {
        back.setOnClickListener(this);
        next.setOnClickListener(this);
    }

    @Override
    public void initData() {
        lockKey=getIntent().getStringExtra("lockKey");
        starttime=getIntent().getStringExtra("starttime");
        endtime=getIntent().getStringExtra("endtime");
        bluetoothaddress=getIntent().getStringExtra("bluetoothaddress");
    }

    @Override
    public void processClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.next:
                Intent i = new Intent(SetLockPasswordPage.this, SetLockPassword.class);
                i.putExtra("lockKey",lockKey);
                i.putExtra("starttime",starttime);
                i.putExtra("endtime",endtime);
                i.putExtra("bluetoothaddress",bluetoothaddress);
                startActivity(i);
                finish();
                break;
        }
    }
}
