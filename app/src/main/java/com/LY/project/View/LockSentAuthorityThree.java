package com.LY.project.View;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.LY.basemodule.Essential.BaseTemplate.BaseActivity;
import com.LY.project.Controller.SentAuthorityContorller;
import com.LY.project.Manager.InterfaceManger;
import com.LY.project.R;
import com.LY.project.Utils.RetrofitUtils;
import com.LY.project.Utils.StringToDate;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by jie on 2018/4/12.
 */

public class LockSentAuthorityThree extends BaseActivity {
    private String Language;

    private TextView back;//返回控件按钮

    private TextView mytextView;//此控件只是单纯的一个 引用类型 为了下面选择控件节省代码量不存在于界面的（起辅助性作用）
    private TextView self;//自定义时间按键
    private TextView one_day;//选择时间为一天
    private TextView one_week;//选择时间为一周
    private TextView one_month;//选择时间为一月
    private TextView one_year;//选择时间为一年
    private TextView start_time;//设置开始时间
    private TextView end_time;//设置结束时间

    private TextView next;//下一步按钮
    private String lockKey;//门锁密钥
    private String power;//当前用户权限
    private int customerPower = 3;//即将授权的用户权限
    private String starttime;//门锁的开始时间
    private String endtime;//门锁的结束时间
    private String customer;//授权用户的联系方式
    private String customerName;//授权客户的备注名

    private String phone;//电话号码
    private String password;//用户账号

    @Override
    public int getLayoutId() {
        return R.layout.activity_sent_lock_authority_three;
    }

    @Override
    public void initViews() {
        back = findView(R.id.back);
        self = findView(R.id.self);
        one_day = findView(R.id.one_day);
        one_week = findView(R.id.one_week);
        one_month = findView(R.id.one_month);
        one_year = findView(R.id.one_year);
        start_time = findView(R.id.start_time);
        end_time = findView(R.id.end_time);
        next = findView(R.id.next);
        initLanguage();
    }

    private void initLanguage() {
        Locale locale = Locale.getDefault();
        Language = locale.getLanguage();
    }

    @Override
    public void initListener() {
        mytextView = self;
        back.setOnClickListener(this);
        self.setOnClickListener(this);
        one_day.setOnClickListener(this);
        one_week.setOnClickListener(this);
        one_month.setOnClickListener(this);
        one_year.setOnClickListener(this);
        start_time.setOnClickListener(this);
        end_time.setOnClickListener(this);
        next.setOnClickListener(this);

    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        lockKey = intent.getStringExtra("lockKey");
        power = intent.getStringExtra("power");
        starttime = intent.getStringExtra("starttime");
        endtime = intent.getStringExtra("endtime");
        customer = intent.getStringExtra("customer");
        customerName = intent.getStringExtra("customerName");
        String a = intent.getStringExtra("customerPower");
        a = intent.getStringExtra("customerPower");
        customerPower = Integer.valueOf(a);

        SharedPreferences userInformation = getSharedPreferences("UserInformation", MODE_PRIVATE);
        phone = userInformation.getString("account", null);
        password = userInformation.getString("password", null);
        Log.e("initData:----", phone + " " + password + " " + lockKey + " " + customer + " " + customerName + " " + customerPower);
    }

    @Override
    public void processClick(View v) {
        long day = 24 * 60 * 60 * 1000;
        long powerEnd = Long.valueOf(endtime + "000");
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.self:
                mytextView.setTextColor(Color.parseColor("#333333"));
                self.setTextColor(Color.RED);
                if (Language.contains("zh")){
                    start_time.setText("从现在开始");
                    end_time.setText("截止时间");
                }else{
                    start_time.setText("From now on");
                    end_time.setText("Deadline");
                }
                mytextView = self;
                break;
            case R.id.one_day:
                mytextView.setTextColor(Color.parseColor("#333333"));
                one_day.setTextColor(Color.RED);
                if (powerEnd == 0 || powerEnd >= StringToDate.TransferDate(times(new Date().getTime() + day))) {
                    start_time.setText(times(new Date().getTime()));
                    end_time.setText(times(new Date().getTime() + day));
                } else {
                    if (Language.contains("zh")){
                        Toast.makeText(this, "该时间超过权限范围", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(this, "Time Limited Exceed", Toast.LENGTH_SHORT).show();
                    }
                }
                mytextView = one_day;
                break;
            case R.id.one_week:
                mytextView.setTextColor(Color.parseColor("#333333"));
                one_week.setTextColor(Color.RED);
                if (powerEnd == 0 || powerEnd >= StringToDate.TransferDate(times(new Date().getTime() + 7 * day))) {
                    start_time.setText(times(new Date().getTime()));
                    end_time.setText(times(new Date().getTime() + 7 * day));
                } else {
                    if (Language.contains("zh")){
                        Toast.makeText(this, "该时间超过权限范围", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(this, "Time Limited Exceed", Toast.LENGTH_SHORT).show();
                    }
                }
                mytextView = one_week;
                break;
            case R.id.one_month:
                mytextView.setTextColor(Color.parseColor("#333333"));
                one_month.setTextColor(Color.RED);
                if (powerEnd == 0 || powerEnd >= StringToDate.TransferDate(times(new Date().getTime() + 30 * day))) {
                    start_time.setText(times(new Date().getTime()));
                    end_time.setText(times(new Date().getTime() + 30 * day));
                } else {
                    if (Language.contains("zh")){
                        Toast.makeText(this, "该时间超过权限范围", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(this, "Time Limited Exceed", Toast.LENGTH_SHORT).show();
                    }
                }
                mytextView = one_month;
                break;
            case R.id.one_year:
                mytextView.setTextColor(Color.parseColor("#333333"));
                one_year.setTextColor(Color.RED);
                if (powerEnd == 0 || powerEnd >= StringToDate.TransferDate(times(new Date().getTime() + 365 * day))) {
                    start_time.setText(times(new Date().getTime()));
                    end_time.setText(times(new Date().getTime() + 365 * day));
                } else {
                    if (Language.contains("zh")){
                        Toast.makeText(this, "该时间超过权限范围", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(this, "Time Limited Exceed", Toast.LENGTH_SHORT).show();
                    }
                }
                mytextView = one_year;
                break;
            case R.id.start_time:
                showDate(start_time);
                break;
            case R.id.end_time:
                showDate(end_time);
                break;
            case R.id.next:
                next_click();
                break;
            default:
                break;
        }
    }

    private void next_click() {
        if (start_time.getText().toString().equals("从现在开始") || end_time.getText().toString().equals("截止时间")) {
            showToast("信息不全");
        } else {
            long starttime = StringToDate.TransferDate(start_time.getText().toString())/1000;
            long endtime = StringToDate.TransferDate(end_time.getText().toString())/1000;
            deliver(starttime, endtime);
        }
    }

    // Log.e("initData:----", phone + " " + password + " " + lockKey + " " + customer + " " + customerName + " " + customerPower);
    private void deliver(long starttime, long endtime) {
        SentAuthorityContorller sentAuthorityContorller = new SentAuthorityContorller(this);
        customer = customer.replace(" ","");
        Log.e("initData:----", phone + " " + password + " " + lockKey + " " + customer + " " + customerName + " " + customerPower + " " + starttime + " " + endtime);
        List<MultipartBody.Part> parts = null;
        Map<String, RequestBody> params = new HashMap<>();
        params.put("startTime", RetrofitUtils.convertToRequestBody(String.valueOf(starttime)));
        params.put("endTime", RetrofitUtils.convertToRequestBody(String.valueOf(endtime)));
        params.put("phone", RetrofitUtils.convertToRequestBody(phone));
        params.put("password", RetrofitUtils.convertToRequestBody(password));
        params.put("lockKey", RetrofitUtils.convertToRequestBody(lockKey));
        params.put("customer", RetrofitUtils.convertToRequestBody(customer));
        params.put("customerName", RetrofitUtils.convertToRequestBody(customerName));
        params.put("customerPower", RetrofitUtils.convertToRequestBody(String.valueOf(customerPower)));
        sentAuthorityContorller.GiveLock(params, parts, new InterfaceManger.OnRequestListener() {
            @Override
            public void onSuccess(Object success) {
                if (Language.contains("zh")){
                    showToast("授权成功");
                }else{
                    showToast("Authorization success");
                }

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

    public static String times(long time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        @SuppressWarnings("unused")
        String times = sdr.format(time);
        return times;
    }

    public void showDate(final TextView textView) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = View.inflate(this, R.layout.date_time_dialog, null);
        final DatePicker datePicker = (DatePicker) view.findViewById(R.id.date_picker);
        final TimePicker timePicker = (android.widget.TimePicker) view.findViewById(R.id.time_picker);
        builder.setView(view);
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        datePicker.init(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), null);
        timePicker.setIs24HourView(true);
        builder.setPositiveButton(getString(R.string.certain), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                StringBuffer sb = new StringBuffer();
                sb.append(String.format("%04d-%02d-%02d",
                        datePicker.getYear(),
                        datePicker.getMonth() + 1,
                        datePicker.getDayOfMonth()));
                String hour = timePicker.getCurrentHour().toString();
                if (hour.length() == 1)
                    hour = "0" + hour;
                String min = timePicker.getCurrentMinute().toString();
                if (min.length() == 1)
                    min = "0" + min;
                String time = sb + " " + hour + ":" + min + ":00";
                long setTime = StringToDate.TransferDate(time);
                long powerStartTime = StringToDate.StringToLong(endtime);
                if (endtime.equals("0")) {
                    if (setTime >= powerStartTime) {
                        textView.setTextColor(Color.parseColor("#000000"));
                        textView.setText(time);
                    } else {
                        Toast.makeText(LockSentAuthorityThree.this, getString(R.string.set_time_error), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    long powerEndTime = Long.valueOf(endtime + "000");
                    if (setTime >= powerStartTime && setTime <= powerEndTime) {
                        textView.setTextColor(Color.parseColor("#000000"));
                        textView.setText(time);
                    } else {
                        Toast.makeText(LockSentAuthorityThree.this, getString(R.string.set_time_error), Toast.LENGTH_SHORT).show();
                    }
                }
                if (!end_time.getText().toString().equals(getString(R.string.deadline))) {
                    long time1;
                    if (start_time.getText().toString().equals(getString(R.string.from_now_start))) {
                        time1 = new Date().getTime();
                    } else {
                        time1 = StringToDate.TransferDate(start_time.getText().toString());
                    }
                    long time2 = StringToDate.TransferDate(end_time.getText().toString());
                    if (time1 > time2) {
                        Toast.makeText(LockSentAuthorityThree.this, getString(R.string.endTime_error), Toast.LENGTH_SHORT).show();
                        end_time.setText(getString(R.string.deadline));
                    }
                    time1 = time1 + 24l * 60l * 60l * 1000l * 365l * 2l;
                    Log.e("time1", String.valueOf(time1));
                    Log.e("time2", String.valueOf(time2));
                    if (time1 < time2) {
                        Toast.makeText(LockSentAuthorityThree.this, "有效时间不能超过两年", Toast.LENGTH_SHORT).show();
                        end_time.setText(getString(R.string.deadline));
                    }
                }
            }
        });
        if (textView.getId() == start_time.getId()) {
            builder.setNegativeButton(getString(R.string.from_now_start), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    textView.setText(getString(R.string.from_now_start));
                }
            });
        }
        final Dialog dialog = builder.create();
        dialog.show();
    }
}
