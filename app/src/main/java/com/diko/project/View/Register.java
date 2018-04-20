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

public class Register extends BaseActivity {
    private EditText input_account;//填入电话号码
    private EditText input_password;//填入密码
    private Button login;//登录
    private TextView input_back;//返回控件

    private String account;//待注册的电话号码
    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
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
        account = intent.getStringExtra("account");
        input_account.setText(account);
    }

    @Override
    public void processClick(View v) {
        switch (v.getId()){
            case R.id.input_forget_password:
                startActivity(VerifyRegistration.class);
                break;
            case R.id.login:
                login_click();
//                String account = input_account.getText().toString().trim();
//                String password = input_password.getText().toString().trim();
//                verify(account,password);
//                startActivity(AddLock.class);
                break;
            case R.id.input_back:
                finish();
                break;
            default:
                break;
        }
    }

    private void login_click() {
        final List<String> photos = new ArrayList<>();
        List<MultipartBody.Part> parts = null;
        Map<String, RequestBody> params = new HashMap<>();
        params.put("account", RetrofitUtils.convertToRequestBody(account));
        params.put("password", RetrofitUtils.convertToRequestBody(input_password.getText().toString().trim()));
        params.put("name", RetrofitUtils.convertToRequestBody("<NULL>"));
        LoginController.signUp(params, parts, new InterfaceManger.OnRequestListener() {
            @Override
            public void onSuccess(Object success) {
                showToast(String.valueOf(success));
                startActivity(AddLock.class);
            }

            @Override
            public void onError(String error) {
                Toast.makeText(Register.this, error, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete() {

            }
        });

    }
}
