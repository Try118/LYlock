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
import com.diko.project.Manager.PrefManager;
import com.diko.project.Module.*;
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
                String account = input_account.getText().toString().trim();
                String password = input_password.getText().toString().trim();
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
        final List<String> photos = new ArrayList<>();

        List<MultipartBody.Part> parts = null;
//        parts = RetrofitUtils.filesToMultipartBodyParts("photo", photos);
        Map<String, RequestBody> params = new HashMap<>();
//        params.put("token", RetrofitUtils.convertToRequestBody(PrefManager.getToken()));
        params.put("account", RetrofitUtils.convertToRequestBody(account));
        params.put("password", RetrofitUtils.convertToRequestBody(password));
        LoginController.login(params, parts, new InterfaceManger.OnRequestListener() {
            @Override
            public void onSuccess(Object success) {
//                Announcements announcements = (Announcements) success;
//                mAdapter = new AnnouncementsAdapter(AnnouncementsCompanyActivity.this, announcements.getContent().getContent());
//                lv_announcements.setAdapter(mAdapter);
//                com.diko.project.Module.Login login = (com.diko.project.Module.Login) success;

                //保存用户的账号密码
                SharedPreferences.Editor editor = getSharedPreferences("UserInformation", MODE_PRIVATE).edit();
                editor.putString("account",account);
                editor.putString("password",password);
                editor.apply();

                Intent i = new Intent(LoginInputPassword.this,AddLock.class);
                i.putExtra("phone",account);
                i.putExtra("password",password);

                startActivity(i);
            }

            @Override
            public void onError(String error) {
                Toast.makeText(LoginInputPassword.this, error, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete() {

            }
        });
    }
}
