package com.LY.project.View;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.LY.basemodule.Essential.BaseTemplate.BaseActivity;
import com.LY.project.Controller.LoginController;
import com.LY.project.CustomView.MyPasswordView;
import com.LY.project.Manager.InterfaceManger;
import com.LY.project.R;
import com.LY.project.Utils.Callback;
import com.LY.project.Utils.RetrofitUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by YX_PC on 2018/4/12.
 * 修改密码里的输入验证码
 */

public class RegistrationCode extends BaseActivity {
    private MyPasswordView verifyCode;
    private TextView correct_back;//返回键
    private TextView resend;//发送验证码
    private TextView send_state;//验证码发送状态

    private String account
            ;//待注册账号
    Handler handler=new Handler();
    private int second=60;
    Runnable r=new Runnable() {
        @Override
        public void run() {
            second--;
            resend.setText(second+getResources().getString(R.string.compare_resend));
            if(second==0){
                resend.setText(getResources().getString(R.string.click_send));
                resend.setEnabled(true);
                send_state.setText("点击获取验证码");
                verifyCode.reInput();
            }
            else{
                handler.postDelayed(this,1000);
            }
        }
    };

    @Override
    public int getLayoutId() {
        return R.layout.activity_registration;
    }

    @Override
    public void initViews() {
        correct_back=(TextView)findView(R.id.correct_back);
        resend = findView(R.id.resend);
        send_state = findView(R.id.send_state);
        verifyCode = findView(R.id.verify_code);
    }

    @Override
    public void initListener() {
        final LoginController loginController = new LoginController(this);
        correct_back.setOnClickListener(this);
        resend.setOnClickListener(this);
        Callback mycallback = new Callback() {
            @Override
            public void excute() {
                String text = verifyCode.getText();
                final List<String> photos = new ArrayList<>();
                List<MultipartBody.Part> parts = null;
                Map<String, RequestBody> params = new HashMap<>();
                params.put("account", RetrofitUtils.convertToRequestBody(account));
                params.put("code", RetrofitUtils.convertToRequestBody(text));
                loginController.VerifyCode(params, parts, new InterfaceManger.OnRequestListener() {
                    @Override
                    public void onSuccess(Object success) {
                        showToast(String.valueOf(success));
                        Intent intent = new Intent(RegistrationCode.this, Register.class);
                        intent.putExtra("account",account);
                        startActivity(intent);
                    }

                    @Override
                    public void onError(String error) {
                        showToast(error);
                        verifyCode.reInput();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
            }
        };
        verifyCode.setCallback(mycallback);
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        account = intent.getStringExtra("account");
    }

    @Override
    public void processClick(View v) {
        switch (v.getId()){
            case R.id.correct_back:
                finish();
                break;
            case R.id.resend:
                resend_click();
                break;
            default:
                break;
        }
    }

    private void resend_click() {
        final List<String> photos = new ArrayList<>();
        List<MultipartBody.Part> parts = null;
        Map<String, RequestBody> params = new HashMap<>();
        params.put("account", RetrofitUtils.convertToRequestBody(account));
        LoginController.GetVerifyCode(params, parts, new InterfaceManger.OnRequestListener() {
            @Override
            public void onSuccess(Object success) {
                send_state.setText("验证码已经发送到"+" "+account);
                resend.setEnabled(false);
                second=60;
                handler.postDelayed(r,1000);
                verifyCode.reInput();
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
