package com.diko.project.View;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.diko.basemodule.Essential.BaseTemplate.BaseActivity;
import com.diko.project.Controller.LoginController;
import com.diko.project.CustomView.MyPasswordView;
import com.diko.project.Manager.InterfaceManger;
import com.diko.project.R;
import com.diko.project.Utils.Callback;
import com.diko.project.Utils.RetrofitUtils;

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

public class VerifyRegistration extends BaseActivity {

    private TextView correct_back;//返回键
    private TextView send_state;//验证码获取状态
    private TextView resend;
    private MyPasswordView code;//验证码控件
    private String account;//待修改的电话号码

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
                code.reInput();
            }
            else{
                handler.postDelayed(this,1000);
            }
        }
    };

    @Override
    public int getLayoutId() {
        return R.layout.activity_verify_registration;
    }

    @Override
    public void initViews() {
        correct_back=(TextView)findView(R.id.correct_back);
        code = findView(R.id.verify_code);
        send_state = findView(R.id.send_state);
        resend = findView(R.id.resend);
    }

    @Override
    public void initListener() {
        correct_back.setOnClickListener(this);
        resend.setOnClickListener(this);
        Callback callback = new Callback() {
            @Override
            public void excute() {
                String text = code.getText();
                Intent intent = new Intent(VerifyRegistration.this,RevisePassword.class);
                intent.putExtra("code",text);
                intent.putExtra("account",account);
                startActivity(intent);
            }
        };
        code.setCallback(callback);
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
                code.reInput();
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
