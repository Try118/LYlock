package com.LY.project.View;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.LY.basemodule.Essential.BaseTemplate.BaseActivity;
import com.LY.project.Controller.SentPasswordController;
import com.LY.project.Manager.InterfaceManger;
import com.LY.project.Module.GetLockPassword;
import com.LY.project.R;
import com.LY.project.Utils.RetrofitUtils;
import com.LY.project.Utils.StringToDate;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by jie on 2018/4/12.
 */

public class LockSentPasswordThree extends BaseActivity {
    private TextView back;//返回控件按钮
    private TextView self;//自定义时间按键
    private TextView one_day;//选择时间为一天
    private TextView one_week;//选择时间为一周
    private TextView one_month;//选择时间为一月
    private TextView one_year;//选择时间为一年
    private TextView start_time;//设置开始时间
    private TextView end_time;//设置结束时间
    private TextView next;//下一步按钮
    private String endtime;
    private TextView mytextView;
    private String lock_name;
    private String lockKey;
    private String account;

    @Override
    public int getLayoutId() {
        return R.layout.activity_sent_lock_password_three;
    }

    @Override
    public void initViews() {
        back = (TextView) findView(R.id.back);
        self = (TextView) findView(R.id.self);
        one_day = (TextView) findView(R.id.one_day);
        one_week = (TextView) findView(R.id.one_week);
        one_month = (TextView) findView(R.id.one_month);
        one_year = (TextView) findView(R.id.one_year);
        start_time = (TextView) findView(R.id.start_time);
        end_time = (TextView) findView(R.id.end_time);
        next = (TextView) findView(R.id.next);
        mytextView = self;
    }

    @Override
    public void initListener() {
        back.setOnClickListener(this);
        self.setOnClickListener(this);
        one_day.setOnClickListener(this);
        one_week.setOnClickListener(this);
        one_month.setOnClickListener(this);
        one_year.setOnClickListener(this);
        next.setOnClickListener(this);
    }

    @Override
    public void initData() {
        Intent i = getIntent();
        endtime = i.getStringExtra("endtime");
        lock_name = i.getStringExtra("lock_name");
        lockKey = i.getStringExtra("lockKey");
        account = i.getStringExtra("account");
        start_time.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                showDate(start_time);
                return false;
            }
        });
        end_time.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                showDate(end_time);
                return false;
            }
        });
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
                start_time.setText("从现在开始");
                end_time.setText("截止时间");
                mytextView = self;
                break;
            case R.id.one_day:
                mytextView.setTextColor(Color.parseColor("#333333"));
                one_day.setTextColor(Color.RED);
                if (powerEnd == 0 || powerEnd >= StringToDate.TransferDate(times(new Date().getTime() + day))) {
                    start_time.setText(times(new Date().getTime()));
                    end_time.setText(times(new Date().getTime() + day));
                } else {
                    showToast("该时间超过权限范围");
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
                    showToast("该时间超过权限范围");
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
                    showToast("该时间超过权限范围");
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
                    showToast("该时间超过权限范围");
                }
                mytextView = one_year;
                break;
            case R.id.next:
                nextOne();
                break;
            default:
                break;
        }
    }


    private void nextOne() {
        SentPasswordController sentPasswordController = new SentPasswordController(this);
        String starttime = String.valueOf(StringToDate.TransferDate(start_time.getText().toString()) / 1000);
        String endtime = String.valueOf(StringToDate.TransferDate(end_time.getText().toString()) / 1000);
        List<MultipartBody.Part> parts = null;
        Map<String, RequestBody> params = new HashMap<>();
        if (!end_time.getText().toString().equals(getString(R.string.deadline))) {
            if (start_time.getText().toString().equals(getString(R.string.from_now_start))) {

                params.put("startTime", RetrofitUtils.convertToRequestBody(times(String.valueOf(Long.valueOf(endtime) * 10001))));//第一代的代码这样设置+_+
                params.put("endTime", RetrofitUtils.convertToRequestBody("0"));
                params.put("key", RetrofitUtils.convertToRequestBody(lockKey));
                params.put("type", RetrofitUtils.convertToRequestBody("0"));
                sentPasswordController.GetLockPassword(params, parts, new InterfaceManger.OnRequestListener() {
                    @Override
                    public void onSuccess(Object success) {
                        GetLockPassword data = (GetLockPassword) success;
                        String password = data.getPassword();

                        //生成消息
                        String str = "欢迎使用物勒智能门锁，您的开锁密码为：" + password.substring(0, 4) + "-" + password.substring(4, 8) + "-" + password.substring(8) + "门锁名称为:" + lock_name + ",有效时间至" + end_time.getText() + "。输入密码后按 # 号键即可开门";
                        if (account.equals("wechat")){
                            Intent intent = new Intent(Intent.ACTION_SEND);
                            intent.setType("text/plain");//  intent.setPackage("com.tencent.mm");
                            intent.putExtra(Intent.EXTRA_TEXT, str);
                            startActivity(Intent.createChooser(intent, "请选择"));
                        }else{
                            Intent sendIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + account));
                            sendIntent.putExtra("sms_body", str);
                            startActivity(sendIntent);
                        }
                    }

                    @Override
                    public void onError(String error) {
                        showToast("从服务器获取密码失败，请重新尝试！");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
            }else {
                Log.e("nextOne: ",starttime+" "+endtime );
                params.put("startTime", RetrofitUtils.convertToRequestBody(times(String.valueOf(Long.valueOf(starttime) * 10001))));
                params.put("endTime", RetrofitUtils.convertToRequestBody(times(String.valueOf(Long.valueOf(endtime) * 10001))));
                params.put("key", RetrofitUtils.convertToRequestBody(lockKey));
                params.put("type", RetrofitUtils.convertToRequestBody("0"));
                sentPasswordController.GetLockPassword(params, parts, new InterfaceManger.OnRequestListener() {
                    @Override
                    public void onSuccess(Object success) {
                        GetLockPassword data = (GetLockPassword) success;
                        String password = data.getPassword();

                        //生成消息
                        String str = "欢迎使用物勒智能门锁，您的开锁密码为：" + password.substring(0, 4) + "-" + password.substring(4, 8) + "-" + password.substring(8) + "门锁名称为:" + lock_name + ",有效时间至" + end_time.getText() + "。输入密码后按 # 号键即可开门";
                        if (account.equals("wechat")){
                            Intent intent = new Intent(Intent.ACTION_SEND);
                            intent.setType("text/plain");//  intent.setPackage("com.tencent.mm");
                            intent.putExtra(Intent.EXTRA_TEXT, str);
                            startActivity(Intent.createChooser(intent, "请选择"));
                        }else{
                            Intent sendIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + account));
                            sendIntent.putExtra("sms_body", str);
                            startActivity(sendIntent);
                        }
                    }

                    @Override
                    public void onError(String error) {
                        showToast("从服务器获取密码失败，请重新尝试！");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
            }
        } else {
            showToast(getString(R.string.unset_time));
        }
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
                        Toast.makeText(LockSentPasswordThree.this, getString(R.string.set_time_error), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    long powerEndTime = Long.valueOf(endtime + "000");
                    if (setTime >= powerStartTime && setTime <= powerEndTime) {
                        textView.setTextColor(Color.parseColor("#000000"));
                        textView.setText(time);
                    } else {
                        Toast.makeText(LockSentPasswordThree.this, getString(R.string.set_time_error), Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(LockSentPasswordThree.this, getString(R.string.endTime_error), Toast.LENGTH_SHORT).show();
                        end_time.setText(getString(R.string.deadline));
                    }
                    time1 = time1 + 24l * 60l * 60l * 1000l * 365l * 2l;
                    Log.e("time1", String.valueOf(time1));
                    Log.e("time2", String.valueOf(time2));
                    if (time1 < time2) {
                        Toast.makeText(LockSentPasswordThree.this, "有效时间不能超过两年", Toast.LENGTH_SHORT).show();
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

    public static String times(long time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        @SuppressWarnings("unused")
        //   int i = Integer.parseInt(time);
                String times = sdr.format(time);
        return times;
    }

    public static String times(String time) {
        SimpleDateFormat sdr;
        sdr = new SimpleDateFormat("yyMMddHHmm");
        long lcc = Long.valueOf(time);
        String times = sdr.format(new Date(lcc));
        return times;
    }
}
