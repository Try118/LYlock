package com.LY.project.View;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.LY.basemodule.Essential.BaseTemplate.BaseActivity;
import com.LY.project.Controller.LoginController;
import com.LY.project.CustomView.MyProgressDialog;
import com.LY.project.Manager.InterfaceManger;
import com.LY.project.R;
import com.LY.project.Utils.RetrofitUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;



/**
 * Created by jie on 2018/4/14.
 */

public class LoginInputPassword extends BaseActivity {
    private EditText input_account;//填入电话号码
    private EditText input_password;//填入密码
    private TextView input_forget_password;//忘记密码
    private Button login;//登录
    private TextView input_back;//返回控件

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0x123) {
                MyProgressDialog.remove();
            }
        }
    };
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
        String temp1 = getIntent().getStringExtra("account");
        SharedPreferences.Editor edit = getSharedPreferences("UserInformation", MODE_PRIVATE).edit();
        edit.putString("account",temp1);
        edit.apply();
        SharedPreferences book = getSharedPreferences("UserInformation", MODE_PRIVATE);
        String s = book.getString("password", null);
        input_password.setText(s);
        input_account.setText(temp1);
        input_account.setEnabled(false);
    }

    @Override
    public void processClick(View v) {
        switch (v.getId()){
            case R.id.input_forget_password:
                String account1 = input_account.getText().toString().trim();
                Intent intent = new Intent(LoginInputPassword.this, VerifyRegistration.class);
                intent.putExtra("account",account1);
                startActivity(intent);
                break;
            case R.id.login:
                Log.e("processClick:123","123123123");
                String account = input_account.getText().toString().trim();
                String password = input_password.getText().toString().trim();
                MyProgressDialog.show(this, "Loading...", false, null);
                handler.sendEmptyMessageDelayed(0x123, 8000);
                verify(account,password);
//                startActivity(AddLock.class);
                break;
            case R.id.input_back:
                finish();
                break;
            default:
                break;
        }
    }

    private void verify(final String account, final String password) {
        LoginController loginController = new LoginController(this);
        final List<String> photos = new ArrayList<>();

        List<MultipartBody.Part> parts = null;

        Map<String, RequestBody> params = new HashMap<>();

        params.put("account", RetrofitUtils.convertToRequestBody(account));
        params.put("password", RetrofitUtils.convertToRequestBody(password));
        loginController.login(params, parts, new InterfaceManger.OnRequestListener() {
            @Override
            public void onSuccess(Object success) {

                //保存用户的账号密码
                SharedPreferences.Editor editor = getSharedPreferences("UserInformation", MODE_PRIVATE).edit();
                editor.putString("account",account);
                editor.putString("password",password);
                editor.apply();

                Intent i = new Intent(LoginInputPassword.this,AddLock.class);
                i.putExtra("phone",account);
                i.putExtra("password",password);

                MyProgressDialog.remove();
                handler.removeMessages(0x123);

                startActivity(i);
                finish();
            }

            @Override
            public void onError(String error) {
                showToast(error);
            }

            @Override
            public void onComplete() {

            }
        });
    }
}
