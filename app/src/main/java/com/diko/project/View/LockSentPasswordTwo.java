package com.diko.project.View;

import android.view.View;
import android.widget.TextView;

import com.diko.basemodule.Essential.BaseTemplate.BaseActivity;
import com.diko.project.R;

/**
 * Created by jie on 2018/4/12.
 */

public class LockSentPasswordTwo extends BaseActivity {
    private TextView back;//返回控件按钮
    private TextView times;//发送多次密码
    private TextView Once;//发送一次性密码
    private TextView send;//发送密码
    @Override
    public int getLayoutId() {
        return R.layout.activity_lock_sent_pw;
    }

    @Override
    public void initViews() {

    }

    @Override
    public void initListener() {
        back.setOnClickListener(this);
        times.setOnClickListener(this);
        Once.setOnClickListener(this);
        send.setOnClickListener(this);
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
            case R.id.times:
//                startActivity();
                break;
            case R.id.once:
//                startActivity();
                break;
            case R.id.send:
//                startActivity();
                break;
            default:
                break;
        }
    }
}
