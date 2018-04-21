package com.LY.project.View;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;

import com.LY.basemodule.Essential.BaseTemplate.BaseActivity;
import com.LY.project.Controller.SentPasswordController;
import com.LY.project.Manager.InterfaceManger;
import com.LY.project.Module.GetLockPassword;
import com.LY.project.R;
import com.LY.project.Utils.RetrofitUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by jie on 2018/4/12.
 */

public class LockSentPasswordTwo extends BaseActivity {
    private TextView back;//返回控件按钮
    private TextView times;//发送多次密码
    private TextView Once;//发送一次性密码
    private TextView send;//发送密码
    private int type;//1为多次性密码，2为一次性密码
    private String lockKey;
    private String lock_name;
    private String account;
    private String name;
    private String endtime;

    @Override
    public int getLayoutId() {
        return R.layout.activity_lock_sent_pw;
    }

    @Override
    public void initViews() {
        back = (TextView) findView(R.id.back);
        times = (TextView) findView(R.id.times);
        Once = (TextView) findView(R.id.once);
        send = (TextView) findView(R.id.send);
    }

    @Override
    public void initListener() {
        back.setOnClickListener(this);
        times.setOnClickListener(this);
        Once.setOnClickListener(this);
        send.setOnClickListener(this);
    }

    @Override
    public void initData() {
        Intent i = getIntent();
        lockKey = i.getStringExtra("lockKey");
        lock_name= i.getStringExtra("lock_name");
        account=i.getStringExtra("account");
        name=i.getStringExtra("name");
        endtime=i.getStringExtra("endtime");
    }

    @Override
    public void processClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.times:
                timesPassword();//多次性密码
                break;
            case R.id.once:
                oncePassword();//一次性密码
                break;
            case R.id.send:
                nextStep();
                break;
            default:
                break;
        }
    }

    private void timesPassword() {
        type = 1;
        times.setTextColor(Color.RED);
        Once.setTextColor(getResources().getColor(R.color.Text_color));
        send.setText(getResources().getString(R.string.next));
    }

    private void oncePassword() {
        type = 2;
        Once.setTextColor(Color.RED);
        times.setTextColor(getResources().getColor(R.color.Text_color));
        send.setText(getResources().getString(R.string.sending_password));
    }

    public static String times(String time) {
        SimpleDateFormat sdr;
        sdr = new SimpleDateFormat("yyMMddHHmm");
        long lcc = Long.valueOf(time);
        String times = sdr.format(new Date(lcc));
        return times;
    }

    private void nextStep() {
        if (type == 1) {
            //多次性密码
            Intent i = new Intent(this, LockSentPasswordThree.class);
            i.putExtra("account", account);
            i.putExtra("name", name);
            i.putExtra("lock_name", lock_name);
            i.putExtra("lockKey",lockKey);
            i.putExtra("endtime",endtime);
            startActivity(i);
        } else {
            SentPasswordController sentPasswordController = new SentPasswordController(this);
            String startTime = String.valueOf(new Date().getTime() / 1000);
//            Log.e( "nextStep: ", times(String.valueOf(Long.valueOf(startTime)*10001)));
            //一次性密码
            List<MultipartBody.Part> parts = null;
            Map<String, RequestBody> params = new HashMap<>();
            params.put("startTime", RetrofitUtils.convertToRequestBody(times(String.valueOf(Long.valueOf(startTime)*10001))));
            params.put("endTime", RetrofitUtils.convertToRequestBody("0"));
            params.put("key", RetrofitUtils.convertToRequestBody(lockKey));
            params.put("type", RetrofitUtils.convertToRequestBody("1"));
            sentPasswordController.GetLockPassword(params, parts, new InterfaceManger.OnRequestListener() {
                @Override
                public void onSuccess(Object success) {
                    GetLockPassword data =(GetLockPassword) success;
                    String password = data.getPassword();
                    //生成消息
                    String str = "欢迎使用物勒智能门锁，您的开锁密码为：" + password.substring(0, 4) + "-" + password.substring(4, 8) + "-" + password.substring(8, 11) + password.substring(11) + "门锁名称为:" + lock_name + ",2小时后失效。输入密码后按 # 号键即可开门";
                    Intent sendIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + account));
                    sendIntent.putExtra("sms_body", str);
                    startActivity(sendIntent);
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
}
