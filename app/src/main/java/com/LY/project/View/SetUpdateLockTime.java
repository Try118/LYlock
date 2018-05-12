package com.LY.project.View;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.LY.basemodule.Essential.BaseTemplate.BaseActivity;
import com.LY.basemodule.Essential.BaseTemplate.BluetoothActivity;
import com.LY.basemodule.Manager.BluetoothCallbackManager;
import com.LY.project.CustomView.MyProgressDialog;
import com.LY.project.R;
import com.LY.project.Utils.BluetoothReceiver;
import com.LY.project.Utils.GetDate;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

/**
 * Created by jie on 2018/4/11.
 */

public class SetUpdateLockTime extends BluetoothActivity {
    private TextView back;//返回控件
    private Button update;//更新时间
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message message) {
            if (message.what == 0x123) {
                if (gatt != null) {
                    gatt.disconnect();
                    gatt.close();
                }
            } else if (message.what == 0x124) {
                MyProgressDialog.remove();
                showToast(getString(R.string.delete_fault));
            } else if (message.what == 0x125) {
                MyProgressDialog.remove();
                showToast("更新失败，请重新更新");
            } else if (message.what == 0x126) {
                bluetoothGattCallback.setMessage(sendMessage);
                gatt.discoverServices();
            }
        }
    };
    public String sendMessage;//发给硬件的消息
    private String power;//权限
    private String bluetoothaddress;//蓝牙地址

    @Override
    public int getLayoutId() {
        return R.layout.activity_set_update_locktime_page;
    }

    @Override
    public void initViews() {
        back = findView(R.id.back);
        update = findView(R.id.update);
    }

    @Override
    public void initListener() {
        back.setOnClickListener(this);
        update.setOnClickListener(this);
    }

    @Override
    public void initData() {
        power = getIntent().getStringExtra("power");
        bluetoothaddress = getIntent().getStringExtra("bluetoothaddress");
    }

    @Override
    public void processClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.update:
                Update();
                break;
            default:
                break;
        }
    }

    private void Update() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                updateTheTime();
            }
        }).start();
        MyProgressDialog.show(SetUpdateLockTime.this, "Updating...", false, null);
        handler.sendEmptyMessageDelayed(0x125, 20000);
    }

    BluetoothCallbackManager manager = new BluetoothCallbackManager() {
        @Override
        public void readCallback(String result) {
            MyProgressDialog.remove();
            Log.e("UpdateTimeread", result);
            if(result.contains("33333")){
                showToast("校准成功");
                gatt.disconnect();
                gatt.close();
                gatt=null;
                finish();
            }
            handler.removeMessages(0x125);
        }

        @Override
        public void writeCallback(String result) {

        }

        @Override
        public void connectCallback() {
            handler.sendEmptyMessageDelayed(0x126, 800);
        }

        @Override
        public void unConnectCallback() {

        }
    };

    public void updateTheTime() {
        Intent i = new Intent(this, BluetoothReceiver.class);
        i.setAction("woolock.bluetooth.result");
        long result = getWebsiteDatetime(webUrl2) - new Date().getTime();
        Log.e("time1", String.valueOf(getWebsiteDatetime(webUrl4)));
        Log.e("time2", String.valueOf(new Date().getTime()));
        if (power.equals("1")) {
            String message = GetDate.getDate();
            sendMessage = "(" + message + ")";
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    getBluetooth(bluetoothaddress, manager);
                }
            });
        } else {
            if (result > -12 * 60 * 60 * 1000 && result < 12 * 60 * 60 * 1000) {
                String message = GetDate.getDate();
                sendMessage = "(" + message + ")";
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        getBluetooth(bluetoothaddress, manager);
                    }
                });
            } else {
                i.putExtra("result", 0x127);
                sendBroadcast(i);
            }
        }
    }

    String webUrl1 = "http://www.bjtime.cn";//bjTime
    String webUrl2 = "http://202.108.22.5";//百度
    String webUrl3 = "http://www.taobao.com";//淘宝
    String webUrl4 = "http://www.ntsc.ac.cn";//中国科学院国家授时中心
    String webUrl5 = "http://www.360.cn";//360
    String webUrl6 = "http://www.beijing-time.org";//beijing-time

    private static long getWebsiteDatetime(String webUrl) {
        try {
            URL url = new URL(webUrl);// 取得资源对象
            URLConnection uc = url.openConnection();// 生成连接对象
            uc.connect();// 发出连接
            long ld = uc.getDate();// 读取网站日期时间
            Date date = new Date(ld);// 转换为标准时间对象
            return date.getTime();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
