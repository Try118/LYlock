package com.diko.project.View;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.diko.basemodule.Essential.BaseTemplate.BaseActivity;
import com.diko.project.R;

/**
 * Created by jie on 2018/4/14.
 */

public class LoginInputPassword extends BaseActivity {
    private EditText input_account;//填入电话号码
    private EditText input_password;//填入密码
    private TextView input_forget_password;//忘记密码
    private Button login;//登录
    private TextView input_back;//返回控件
    @Override
    public int getLayoutId() {
        return R.layout.activity_input_password;
    }

    @Override
    public void initViews() {
        login = findView(R.id.login);
        input_password = findView(R.id.input_password);
        input_forget_password = findView(R.id.input_forget_password);
        input_account = findView(R.id.input_account);
        input_back = findView(R.id.input_back);
    }

    @Override
    public void initListener() {
        input_forget_password.setOnClickListener(this);
        login.setOnClickListener(this);
        input_back.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void processClick(View v) {
        switch (v.getId()){
            case R.id.input_forget_password:
                break;
            case R.id.login:
                startActivity(AddLock.class);
                break;
            case R.id.input_back:
                finish();
                break;
            default:
                break;
        }
    }
}
