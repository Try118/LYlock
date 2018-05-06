package com.LY.project.View;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.LY.basemodule.Essential.BaseTemplate.BaseActivity;
import com.LY.project.CustomView.MyProgressDialog;
import com.LY.project.R;
import com.LY.project.Utils.StringToDate;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by YX on 2018/5/6.
 */

public class SetLockPasswordTime extends BaseActivity {
    private String password;//设置的开门密码
    private String lockKey;
    private String starttime;//门锁的开始时间
    private String endtime;//门锁的截止时间
    private String bluetoothaddress;//蓝牙地址
    private TextView back;
    private TextView starttime2;//这张页面所选择的开始时间
    private TextView endtime2;//这张页面所选择的截止时间
    private TextView finish;//完成
    private TextView forever;//永久
    private TextView self;//自定义
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message message){
            if(message.what==0x123){
                MyProgressDialog.remove();
            }
        }
    };

    @Override
    public int getLayoutId() {
        return R.layout.activity_setting_password_time;
    }

    @Override
    public void initViews() {
        back = findView(R.id.back);
        starttime2 = findView(R.id.start_time);
        endtime2 = findView(R.id.end_time);
        finish = findView(R.id.finish);
        forever = findView(R.id.forever);
        self = findView(R.id.self);
    }

    @Override
    public void initListener() {
        back.setOnClickListener(this);
        self.setOnClickListener(this);
        forever.setOnClickListener(this);
        finish.setOnClickListener(this);
        starttime2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                showDate(starttime2);
                return false;
            }
        });
        endtime2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                showDate(endtime2);
                return false;
            }
        });
    }

    @Override
    public void initData() {
        Intent i = getIntent();
        password = i.getStringExtra("password");//设置的开门密码
        lockKey = i.getStringExtra("lockKey");
        starttime = i.getStringExtra("starttime");
        endtime = i.getStringExtra("endtime");
        bluetoothaddress = i.getStringExtra("bluetoothaddress");
//        Log.e("abc: ",password+" "+ lockKey+" "+starttime+" "+endtime+" "+bluetoothaddress);
    }

    @Override
    public void processClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.self:
                self.setTextColor(Color.RED);
                forever.setTextColor(getResources().getColor(R.color.colorhint));
                endtime2.setText(getString(R.string.deadline));
                break;
            case R.id.forever:
                self.setTextColor(getResources().getColor(R.color.colorhint));
                forever.setTextColor(Color.RED);
                finish.setVisibility(View.VISIBLE);
                if (endtime.equals("0")) {
                    endtime2.setText(StringToDate.times("4102333200000"));
                } else {
                    endtime2.setText(StringToDate.times(endtime + "000"));
                }
                break;
            case R.id.finish:
                if(!endtime2.getText().toString().equals(getString(R.string.deadline))) {
//                    getBluetooth();
                    MyProgressDialog.show(SetLockPasswordTime.this, "setting...", true, null);
                    handler.sendEmptyMessageDelayed(0x123,10000);
                }
                else{
                    showToast(getString(R.string.unset_time));
                }
                break;
        }
    }
    public void showDate(final TextView textView)
    {
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
                finish.setVisibility(View.VISIBLE);
                StringBuffer sb = new StringBuffer();
                sb.append(String.format("%04d-%02d-%02d",
                        datePicker.getYear(),
                        datePicker.getMonth() + 1,
                        datePicker.getDayOfMonth()));
                String hour=timePicker.getCurrentHour().toString();
                if(hour.length()==1)
                    hour="0"+hour;
                String min=timePicker.getCurrentMinute().toString();
                if(min.length()==1)
                    min="0"+min;
                String time=sb+" "+hour+":"+min+":00";
                long setTime= StringToDate.TransferDate(time);
                long powerStartTime=StringToDate.StringToLong(starttime);
                if(endtime.equals("0"))
                {
                    if(setTime>=powerStartTime){

                        textView.setTextColor(Color.parseColor("#000000"));
                        textView.setText(time);
                    }
                    else{
                        showToast(getString(R.string.set_time_error));
                    }
                }
                else {
                    long powerEndTime = StringToDate.StringToLong(endtime+"000");
                    if(setTime>=powerStartTime&&setTime<=powerEndTime){
                        textView.setTextColor(Color.parseColor("#000000"));
                        textView.setText(time);
                    }
                    else{
                        showToast(getString(R.string.set_time_error));
                    }
                }
                if(!endtime2.getText().toString().equals(getString(R.string.deadline))){
                    long time1;
                    if(starttime2.getText().toString().equals(getString(R.string.from_now_start))) {
                        time1=new Date().getTime();
                    }
                    else{
                        time1=StringToDate.TransferDate(starttime2.getText().toString());
                    }
                    long time2=StringToDate.TransferDate(endtime2.getText().toString());
                    if(time1>time2){
                        showToast(getString(R.string.endTime_error));
                        endtime2.setText(getString(R.string.deadline));
                    }
                    time1=time1+24l*60l*60l*1000l*365l*2l;
                    Log.e("time1",String.valueOf(time1));
                    Log.e("time2",String.valueOf(time2));
                    if(time1<time2){
                        showToast("有效时间不能超过两年");
                        endtime2.setText(getString(R.string.deadline));
                    }
                }
            }
        });
        if(textView.getId()==starttime2.getId()){
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
