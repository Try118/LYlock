package com.diko.project.View;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.diko.basemodule.Essential.BaseTemplate.BaseActivity;
import com.diko.project.R;

/**
 * Created by jie on 2018/4/12.
 */

public class LockSentPasswordOne extends BaseActivity {
    private TextView back;//返回控件
    private EditText phone;//填入电话号码
    private TextView get_phone;//从通讯录获取联系人信息
    private TextView next;//下一步
    private TextView Wechat;//wechat联系人
    @Override
    public int getLayoutId() {
        return R.layout.activity_sent_lock_password_one;
    }

    @Override
    public void initViews() {
        back = findView(R.id.back);
        phone = findView(R.id.phone);
        get_phone = findView(R.id.get_phone);
        next = findView(R.id.next);
        Wechat = findView(R.id.WeChat);
    }

    @Override
    public void initListener() {
        back.setOnClickListener(this);
        get_phone.setOnClickListener(this);
        next.setOnClickListener(this);
        Wechat.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void processClick(View v) {
        switch(v.getId()){
            case R.id.back:
//                startActivity();
                break;
            case R.id.get_phone:
//                startActivity();
                break;
            case R.id.next:
//                startActivity();
                break;
            case R.id.WeChat:
//                startActivity();
                break;
            default:
                break;
        }
    }
}
