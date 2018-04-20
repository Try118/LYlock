package com.diko.project.View;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.diko.basemodule.Essential.BaseTemplate.BaseActivity;
import com.diko.project.Controller.LoginController;
import com.diko.project.Manager.InterfaceManger;
import com.diko.project.R;
import com.diko.project.Utils.RetrofitUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;


/**
 * Created by jie on 2018/4/14.
 */

public class RevisePassword extends BaseActivity {
    private EditText input_account;//填入电话号码
    private EditText input_password;//填入密码
    private Button login;//登录
    private TextView input_back;//返回控件

    private String code;//验证码
    private String phone;//待修改的账号
    @Override
    public int getLayoutId() {
        return R.layout.activity_revise_password;
    }

    @Override
    public void initViews() {
        login = findView(R.id.login);
        input_password = findView(R.id.input_password);
        input_account = findView(R.id.input_account);
        input_back = findView(R.id.input_back);
    }

    @Override
    public void initListener() {
        login.setOnClickListener(this);
        input_back.setOnClickListener(this);
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        code = intent.getStringExtra("code");
        phone = intent.getStringExtra("account");
    }

    @Override
    public void processClick(View v) {
        switch (v.getId()){
            case R.id.login:
                login_click();
                break;
            case R.id.input_back:
                finish();
                break;
            default:
                break;
        }
    }

    private void login_click() {
        String newPassword = input_password.getText().toString().trim();
        final List<String> photos = new ArrayList<>();
        List<MultipartBody.Part> parts = null;
        Map<String, RequestBody> params = new HashMap<>();
        params.put("phone", RetrofitUtils.convertToRequestBody(phone));
        params.put("code", RetrofitUtils.convertToRequestBody(code));
        params.put("newPassword", RetrofitUtils.convertToRequestBody(newPassword));
        LoginController.forgetPassword(params, parts, new InterfaceManger.OnRequestListener() {
            @Override
            public void onSuccess(Object success) {
                showToast(String.valueOf(success));
                startActivity(Login.class);
            }

            @Override
            public void onError(String error) {
                Toast.makeText(RevisePassword.this, error, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void verify(final String account, final String password) {

    }
}
