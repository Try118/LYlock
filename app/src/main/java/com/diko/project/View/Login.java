package com.diko.project.View;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.diko.basemodule.Essential.BaseTemplate.BaseActivity;
import com.diko.project.R;

/**
 * Created by YX_PC on 2018/4/10.
 * 登陆界面
 */

public class Login extends BaseActivity {
    private EditText phone;//电话
    private TextView nation;//国家+86
    private Button next;//下一步

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initViews() {
        nation = (TextView) findView(R.id.nation);
        phone = (EditText) findView(R.id.login_phone);
        next = (Button) findView(R.id.login_next);
    }

    @Override
    public void initListener() {
        next.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void processClick(View v) {
        switch (v.getId()){
            case R.id.login_next:
                startActivity(LoginInputPassword.class);
                break;
            default:
                break;
        }
    }
}
