package com.LY.project.View;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.LY.basemodule.Essential.BaseTemplate.BaseActivity;
import com.LY.project.Controller.LoginController;
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
 * Created by YX_PC on 2018/4/10.
 * 登陆界面
 */

public class Login extends BaseActivity {
    private EditText phone;//电话
    private TextView nation;//国家+86
    private Button next;//下一步
    private String account;//账户
    private static String temp;//为了解决内部类必须使用final类型的问题

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initViews() {
        nation = findView(R.id.nation);
        phone = findView(R.id.login_phone);
        next = findView(R.id.login_next);
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
        switch (v.getId()) {
            case R.id.login_next:
                account = phone.getText().toString().trim();
                temp = account;
                if (!TextUtils.isEmpty(account)) {
                    vefiyAccount(account);
                } else {
                    showToast(getResources().getString(R.string.no_write_phone));
                }
                break;
            default:
                break;
        }
    }

    private void vefiyAccount(String account) {
        LoginController loginController = new LoginController(this);
        final List<String> photos = new ArrayList<>();

        List<MultipartBody.Part> parts = null;
//        parts = RetrofitUtils.filesToMultipartBodyParts("photo", photos);
        Map<String, RequestBody> params = new HashMap<>();
//        params.put("token", RetrofitUtils.convertToRequestBody(PrefManager.getToken()));
        params.put("account", RetrofitUtils.convertToRequestBody(account));
        loginController.isexist(params, parts, new InterfaceManger.OnRequestListener() {
            @Override
            public void onSuccess(Object success) {
                startActivity(LoginInputPassword.class);
            }

            @Override
            public void onError(String error) {
                showToast(error);
            }

            @Override
            public void onComplete() {
                Intent intent = new Intent(Login.this, RegistrationCode.class);
                intent.putExtra("account",temp);
                startActivity(intent);
            }
        });
    }
}
