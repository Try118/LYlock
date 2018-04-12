package com.diko.project.View;

import android.view.View;
import android.widget.TextView;

import com.diko.basemodule.Essential.BaseTemplate.BaseActivity;
import com.diko.project.R;

/**
 * Created by YX_PC on 2018/4/12.
 * 修改密码里的输入验证码
 */

public class VerifyRegistration extends BaseActivity {

    private TextView correct_back;//返回键

    @Override
    public int getLayoutId() {
        return R.layout.activity_verify_registration;
    }

    @Override
    public void initViews() {
        correct_back=(TextView)findView(R.id.correct_back);
    }

    @Override
    public void initListener() {
        correct_back.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void processClick(View v) {
        switch (v.getId()){
            case R.id.correct_back:
                finish();
                break;
            default:
                break;
        }
    }
}
