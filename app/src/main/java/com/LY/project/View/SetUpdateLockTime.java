package com.LY.project.View;

import android.app.AlertDialog;
import android.bluetooth.BluetoothGattCharacteristic;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
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
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;
import java.util.logging.Logger;

import cn.com.heaton.blelibrary.ble.Ble;
import cn.com.heaton.blelibrary.ble.BleDevice;
import cn.com.heaton.blelibrary.ble.callback.BleConnCallback;
import cn.com.heaton.blelibrary.ble.callback.BleWriteCallback;

/**
 * Created by jie on 2018/4/11.
 */

public class SetUpdateLockTime extends BluetoothActivity {
    private TextView back;//返回控件
    private Button update;//更新时间
    private Ble<BleDevice> mBle;
    BleDevice bleDevice = new BleDevice();
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
            } else if (message.what == 0x226) {
                if (gatt != null) {
                    bluetoothGattCallback.setMessage(sendMessage);
                    gatt.discoverServices();
                }
            } else if (message.what == 0x234) {
                if (gatt != null) {
                    gatt.disconnect();
                    gatt = null;
                }
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

//                refreshDeviceCache();

                Update();

                break;
            default:
                break;
        }
    }

    /**
     * 校准时间
     */
    private void Update() {
        String message = GetDate.getDate();
        sendMessage = "(" + message + ")";
        Log.e("sendMessage:", sendMessage);
        MyProgressDialog.show(SetUpdateLockTime.this, "Updating...", false, null);
        bleDevice.setBleAddress("36:67:31:3A:A3:51");
                    bleDevice.setBleName("WL96B385AB67C1");
                    mBle = Ble.getInstance();
                    initBle();
        mBle.connect(bleDevice, connectCallback);
//        gatt.setCharacteristicNotification(characteristic, enable);

//        getBluetooth(bluetoothaddress, manager);
//        con();
        //发送消息延迟 20秒后移除弹框
//        handler.sendEmptyMessageDelayed(0x125, 20000);
    }
    private BleConnCallback<BleDevice> connectCallback = new BleConnCallback<BleDevice>() {
        @Override
        public void onConnectionChanged(BleDevice device) {
            if (device.isConnected()) {

            } else {
                mBle = null;
            }
        }

        @Override
        public void onConnectException(BleDevice device, int errorCode) {
            super.onConnectException(device, errorCode);
            mBle = null;
            if (gatt != null) {
                gatt.disconnect();
                gatt.close();
                gatt = null;
            }
//            if(errorCode==2510){
//                showToast("连接异常，请重新连接");
//            }
//            Toast.makeText(BluetoothActivity.this, "连接异常，异常状态码:" + errorCode, Toast.LENGTH_SHORT).show();
        }
    };
    /*发送数据*/
    public void sendData(BleDevice device) {
        boolean result = mBle.write(device, changeLevelInner(1),
                new BleWriteCallback<BleDevice>() {
                    @Override
                    public void onWriteSuccess(BluetoothGattCharacteristic characteristic) {
                        Log.e("bluetoothGatt", String.valueOf(characteristic));
                        showToast("更新时间成功");
                    }
                });
        if (!result) {
            showToast("更新时间失败，请重新更新时间");
        }
    }
    public byte[] changeLevelInner(int play) {
        byte[] data = new byte[Command.qppDataSend.length];
        System.arraycopy(Command.qppDataSend, 0, data, 0, data.length);
        data[6] = 0x03;
        data[7] = (byte) play;
        Logger.e("data:" + Arrays.toString(data));
        return data;
    }
    private void initBle() {
        Ble.Options options = new Ble.Options();
        options.logBleExceptions = true;//设置是否输出打印蓝牙日志
        options.throwBleException = true;//设置是否抛出蓝牙异常
        options.autoConnect = false;//设置是否自动连接
        options.scanPeriod = 12 * 1000;//设置扫描时长
        options.connectTimeout = 10 * 1000;//设置连接超时时长
        options.uuid_service = UUID.fromString("0000fff0-0000-1000-8000-00805f9b34fb");//设置主服务的uuid
        //options.uuid_services_extra = new UUID[]{UUID.fromString("0000180f-0000-1000-8000-00805f9b34fb")};//添加额外的服务（如电量服务，心跳服务等）
        options.uuid_write_cha = UUID.fromString("0000fff2-0000-1000-8000-00805f9b34fb");//设置可写特征的uuid
        mBle.init(getApplicationContext(), options);
    }



    public synchronized void con() {
        getBluetooth(bluetoothaddress, manager);
    }

    BluetoothCallbackManager manager = new BluetoothCallbackManager() {
        @Override
        public void readCallback(String result) {
            MyProgressDialog.remove();
            Log.e("result", result);
            if (result.contains("333333") || result != null) {
                showToast("校准成功");
                handler.removeMessages(0x125);

                handler.sendEmptyMessage(0x234);
//                    gatt.disconnect();
//                    refreshDeviceCache();
//                    try {
//                        Thread.sleep(400);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    gatt = null;

//                refreshDeviceCache();
                finish();

//                gatt = null;
            }
//            if (gatt != null) {
//                gatt.disconnect();
//                gatt.close();
//            }
        }

        @Override
        public void writeCallback(String result) {
            Log.e("writeCallback: ", result);
        }

        @Override
        public void connectCallback() {
            Log.e("bluetoothGatt:", "意外意外");
//            handler.sendEmptyMessageDelayed(0x226, 100);
            find();

        }

        @Override
        public void unConnectCallback() {
            MyProgressDialog.remove();
        }
    };

    public synchronized void find() {
//        if (gatt != null) {
            bluetoothGattCallback.setMessage(sendMessage);
            gatt.discoverServices();
//        }
    }

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

    /**
     * @param webUrl 获取网络时间
     * @return
     */
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

    /**
     * 页面死亡去除弹框
     */
    @Override
    protected void onDestroy() {
        MyProgressDialog.remove();
        super.onDestroy();
    }
}
