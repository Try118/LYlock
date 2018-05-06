package com.LY.project.View;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.LY.basemodule.Essential.BaseTemplate.BaseActivity;
import com.LY.basemodule.Essential.BaseTemplate.BluetoothActivity;
import com.LY.basemodule.Manager.BluetoothCallbackManager;
import com.LY.basemodule.Utils.BluetoothGattCallBackUtils;
import com.LY.project.Controller.LockController;
import com.LY.project.Controller.LoginController;
import com.LY.project.CustomView.MyProgressDialog;
import com.LY.project.Manager.InterfaceManger;
import com.LY.project.R;
import com.LY.project.Utils.BluetoothReceiver;
import com.LY.project.Utils.RetrofitUtils;
import com.LY.project.Utils.StringToDate;
import com.hp.hpl.sparta.Text;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by jie on 2018/4/10.
 */

public class Select_look extends BluetoothActivity {
    private TextView back;//返回
    private TextView lockname;//锁的名字
    private TextView lock_setting;//设置门锁信息
    private TextView start;//开始时间
    private TextView end_time;//结束时间
    private LinearLayout open_lock;//开锁
    private LinearLayout send_password;//发送密码
    private LinearLayout giver;//用户授权
    private TextView powernumber;//电量
    private TextView power_photo;//电量图片

    private String lock_name;//相对应的门锁名字
    private String starttime;//相对应的开始时间
    private String endtime;//相对应的门锁结束时间
    private String lockKey;//相对应门锁的密钥
    private String address;//相对应的门锁地址
    private String power;//权限
    static int time = 3;//缓冲时间
    private boolean state = false;//链接状态
    private String lockId;//锁的id
    private String bluetoothaddress;//相对应的蓝牙地址
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0x123) {
                open_lock.setBackgroundResource(R.color.Text_color);
                open_lock.setClickable(false);
            } else if (msg.what == 0x124) {
                open_lock.setBackgroundResource(R.color.line);
                open_lock.setClickable(true);
                if (gatt != null) {
                    gatt.disconnect();
                    gatt.close();
                    gatt = null;
                }
            } else if (msg.what == 0x125) {
                MyProgressDialog.remove();
            }
            super.handleMessage(msg);
        }
    };

    private String account; //账号

    @Override
    public int getLayoutId() {
        return R.layout.activity_select__lock;
    }

    @Override
    public void initViews() {
        back = findView(R.id.back);
        lockname = findView(R.id.lock_name);
        lock_setting = findView(R.id.lock_setting);
        start = findView(R.id.start_time);
        end_time = findView(R.id.end_time);
        open_lock = findView(R.id.open_lock);
        send_password = findView(R.id.send_password);
        giver = findView(R.id.giver);
        powernumber = findView(R.id.powernumber);
        power_photo = findView(R.id.power_photo);
    }

    @Override
    public void initListener() {
        back.setOnClickListener(this);
        lock_setting.setOnClickListener(this);
        open_lock.setOnClickListener(this);
        send_password.setOnClickListener(this);
        giver.setOnClickListener(this);
    }

    @Override
    public void initData() {
        SharedPreferences userInformation = getSharedPreferences("UserInformation", MODE_PRIVATE);
        account = userInformation.getString("account", null);
        Intent i = getIntent();
        lock_name = i.getStringExtra("lock_name");
        starttime = i.getStringExtra("starttime");
        endtime = i.getStringExtra("endtime");
        lockKey = i.getStringExtra("lockKey");
        address = i.getStringExtra("address");
        power = i.getStringExtra("power");
        lockId = i.getStringExtra("lockId");
        bluetoothaddress = i.getStringExtra("bluetoothaddress");
        Log.e("initData:---- ", lock_name + "00 " + starttime + " 00" + endtime + "00 " + lockKey + "00 " + address + "00");
        if (power.equals("3")) {
            send_password.setVisibility(View.INVISIBLE);
            giver.setVisibility(View.INVISIBLE);
        }
        initviewtime();
    }

    private void initviewtime() {
        lockname.setText(lock_name);
        start.setText(StringToDate.times(String.valueOf(Long.valueOf(starttime) * 1000)));
        if (endtime.equals("0")) {
            end_time.setText("永久");
        } else {
            end_time.setText(StringToDate.times(String.valueOf(Long.valueOf(endtime) * 1000)));
        }
    }

    @Override
    public void processClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.lock_setting:
                lock_setting_click();
                break;
            case R.id.open_lock:
                BluetoothCallbackManager manager = new BluetoothCallbackManager() {
                    @Override
                    public void readCallback(String result) {
                        Log.e("processClick:123123", "124123412341");
                        state = true;
                        Log.e("read-----", result);
                        final Intent i = new Intent(Select_look.this, BluetoothReceiver.class);
                        i.setAction("woolock.bluetooth.result");
                        if (result.contains("1111")) {

                        }
                        if (result.contains("POWER")) {
                            //上传操作记录
                            record();
                            String num = "POWER[0-9]{1,3}";
                            Pattern pattern = Pattern.compile(num);
                            Matcher matcher = pattern.matcher(result);
                            if (matcher.find()) {
                                String re = matcher.group(0).substring(5, matcher.group(0).length()) + "%";
                                powernumber.setText(re);
                                int s = Integer.valueOf(matcher.group(0).substring(5, matcher.group(0).length()));
                                if (s >= 90) {
                                    power_photo.setBackgroundResource(R.drawable.battery);
                                } else if (s >= 75) {
                                    power_photo.setBackgroundResource(R.drawable.battery1);
                                } else if (s >= 60) {
                                    power_photo.setBackgroundResource(R.drawable.battery2);
                                } else if (s >= 40) {
                                    power_photo.setBackgroundResource(R.drawable.battery3);
                                } else if (s >= 20) {
                                    power_photo.setBackgroundResource(R.drawable.battery4);
                                } else {
                                    power_photo.setBackgroundResource(R.drawable.battery5);
                                }
                                //电量更改
                                up_power(re);
                            }
                        }
                        if (result.contains("3333")) {
                            i.putExtra("result", 0x124);
                            sendBroadcast(i);
                        }
                    }


                    @Override
                    public void writeCallback(String result) {

                    }

                    @Override
                    public void connectCallback() {
                        Log.e("processClick:123123", "出来吧");
                        MyProgressDialog.remove();

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                handler.sendEmptyMessageDelayed(0x123, 0);
                                while (time != 0) {
                                    time--;
                                    Log.e("time", String.valueOf(time));
                                    try {
                                        Thread.sleep(1000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                                time = 3;
                                handler.sendEmptyMessageDelayed(0x124, 0);
                            }
                        }).start();

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                if (!state && gatt != null) {
                                    state = false;
                                    gatt.disconnect();
                                    gatt.close();
                                    gatt = null;
                                    Log.e("bluetoothGatt:", "disconnect");
                                }
                            }
                        }).start();

                        //发送请求门锁电量
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                bluetoothGattCallback.setMessage("(!" + lockKey + ".O*)");
                                boolean b = gatt.discoverServices();
//                                Log.e("bluetoothGatt", String.valueOf(b));
                            }
                        }).start();

                        showNotification(1);
                    }

                    @Override
                    public void unConnectCallback() {
//                        gatt.close();
//                        gatt = null;
                        showToast("链接异常,请稍后重试");
                        showNotification(2);
                    }
                };
                Log.e("processClick:123123", bluetoothaddress);
                getBluetooth(bluetoothaddress, manager);
                state = false;
                MyProgressDialog.show(this, "Opening...", false, null);
                handler.sendEmptyMessageDelayed(0x125, 8000);

                break;

            case R.id.send_password:
                Intent i = new Intent(this, LockSentPasswordOne.class);
                i.putExtra("power", power);
                i.putExtra("lockKey", lockKey);
                i.putExtra("starttime", starttime);
                i.putExtra("endtime", endtime);
                i.putExtra("lock_name", lock_name);
//                i.putExtra("order", 1);
                startActivity(i);
                break;
            case R.id.giver:
                giver_click();
                break;
            default:
                break;
        }
    }

    private void giver_click() {
        Intent intent = new Intent(this, LockSentAuthorityOne.class);
        intent.putExtra("lockKey", lockKey);
        intent.putExtra("power", power);
        intent.putExtra("starttime", starttime);
        intent.putExtra("endtime", endtime);
        startActivity(intent);
    }

    private void lock_setting_click() {
        Intent intent = new Intent(this, SetLock.class);
        intent.putExtra("lock_name", lock_name);
        intent.putExtra("starttime", starttime);
        intent.putExtra("endtime", endtime);
        intent.putExtra("lockKey", lockKey);
        intent.putExtra("address", address);
        intent.putExtra("power", power);
        intent.putExtra("lockId", lockId);
        intent.putExtra("bluetoothaddress",bluetoothaddress);
        startActivity(intent);
    }

    public void showNotification(int i) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Service.NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(this);
        if (i == 1) {
            builder.setContentText(lock_name + "智能门锁已连上!");
            builder.setTicker("Woolock智能门锁已连接上门锁" + lock_name);
        } else if (i == 2) {
            builder.setContentText(lock_name + "智能门锁已断开!");
            builder.setTicker("触摸可重新连接门锁");
        } else {
            builder.setContentText("门锁未连接");
            builder.setTicker("触摸进行连接");
        }
        builder.setContentTitle("Woolock智能门锁已连接上门锁门锁");
        builder.setLargeIcon(((BitmapDrawable) getResources().getDrawable(R.drawable.icon)).getBitmap());

        builder.setSmallIcon(R.drawable.icon);
        builder.setWhen(System.currentTimeMillis());
        //    builder.setDefaults(Notification.DEFAULT_ALL);
        Intent intent = new Intent(this, Select_look.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        builder.setContentIntent(pendingIntent);
        builder.setPriority(Notification.PRIORITY_DEFAULT);
        //    builder.setLights(Color.RED,500,500);
        Notification notification = builder.build();
        notificationManager.notify(1, notification);
    }

    private void record() {
        LockController lockController = new LockController(Select_look.this);
        final List<String> photos = new ArrayList<>();

        List<MultipartBody.Part> parts = null;
        Map<String, RequestBody> params = new HashMap<>();
        params.put("account", RetrofitUtils.convertToRequestBody(account));
        params.put("lockKey", RetrofitUtils.convertToRequestBody(lockId));
        params.put("type", RetrofitUtils.convertToRequestBody("1"));
        params.put("name", RetrofitUtils.convertToRequestBody(account));
        params.put("detial", RetrofitUtils.convertToRequestBody("<null>"));
        params.put("time", RetrofitUtils.convertToRequestBody(String.valueOf(new Date().getTime())));
        LockController.OpenLock(params, parts, new InterfaceManger.OnRequestListener() {
            @Override
            public void onSuccess(Object success) {
//
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

    private void up_power(String battery) {
        LockController lockController = new LockController(Select_look.this);
        final List<String> photos = new ArrayList<>();
        List<MultipartBody.Part> parts = null;
        Map<String, RequestBody> params = new HashMap<>();
        params.put("battery", RetrofitUtils.convertToRequestBody(battery));
        params.put("lockKey", RetrofitUtils.convertToRequestBody(lockKey));
        LockController.UpdateLockPower(params, parts, new InterfaceManger.OnRequestListener() {
            @Override
            public void onSuccess(Object success) {
//
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
