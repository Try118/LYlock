package com.diko.project.View;

import android.content.Intent;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
 * Created by YX_PC on 2018/4/10.
 * 登陆界面
 */

public class Login extends BaseActivity {
    private EditText phone;//电话
    private TextView nation;//国家+86
    private Button next;//下一步
    private String account;//账户
    private String language;//语言

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
        language="zh";
        if (language.equals("zh")) {
            nation.setText("CN > +86");
            phone.setInputType(InputType.TYPE_CLASS_PHONE);
            phone.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (phone.length() == 11) {
                        account = phone.getText().toString();
                    }
                    if (phone.length() > 11) {
                        phone.setText(account);
                    }
                }
            });
        }
    }

    @Override
    public void processClick(View v) {
        switch (v.getId()){
            case R.id.login_next:
                account=phone.getText().toString();
                if(!TextUtils.isEmpty(account)){
                    vefiyAccount(account);
                }else{
                    showToast(getResources().getString(R.string.no_write_phone));
                }
                break;
            default:
                break;
        }
    }

    private void vefiyAccount(String account) {
        List<MultipartBody.Part> parts = null;
        Map<String, RequestBody> params = new HashMap<>();
        params.put("account", RetrofitUtils.convertToRequestBody(account));
        LoginController.isexist(params, parts, new InterfaceManger.OnRequestListener() {
            @Override
            public void onSuccess(Object success) {
                Intent i = new Intent(Login.this, LoginInputPassword.class);
                startActivity(i);
                finish();
            }

            @Override
            public void onError(String error) {
                if(error.equals("账号不存在")){
                    Intent i = new Intent(Login.this, VerifyRegistration.class);
                    startActivity(i);
                    finish();
                }else{
                    showToast(error);
                }

            }

            @Override
            public void onComplete() {

            }
        });
    }
}
