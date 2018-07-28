package com.LY.project.View;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.VisibleForTesting;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.LY.basemodule.Essential.BaseTemplate.BaseActivity;
import com.LY.basemodule.Essential.BaseTemplate.BluetoothActivity;
import com.LY.basemodule.Manager.BluetoothCallbackManager;
import com.LY.project.Controller.LockController;
import com.LY.project.CustomView.MyProgressDialog;
import com.LY.project.Manager.InterfaceManger;
import com.LY.project.R;
import com.LY.project.Utils.RetrofitUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by jie on 2018/4/12.
 */

public class SetLockDeleteAllPassword extends BluetoothActivity {
    private TextView back;//返回控件
    private Button delete_all_password;//确定删除所有密码控件

    private String bluetoothaddress;//蓝牙mac地址

    String sendMessage;//删除门锁指令
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
                showToast("删除所有密码失败");
            } else if (message.what == 0x126) {
                bluetoothGattCallback.setMessage(sendMessage);
                gatt.discoverServices();
                Log.e("------", sendMessage);
            }
        }
    };

    @Override
    public int getLayoutId() {
        return R.layout.activity_delete_all_password;
    }

    @Override
    public void initViews() {
        back = findView(R.id.back);
        delete_all_password = findView(R.id.delete_all_password);
    }

    @Override
    public void initListener() {
        back.setOnClickListener(this);
        delete_all_password.setOnClickListener(this);
    }

    @Override
    public void initData() {
        Intent i = getIntent();
        bluetoothaddress = i.getStringExtra("bluetoothaddress");
        Log.e("initData:",bluetoothaddress);
    }

    @Override
    public void processClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.delete_all_password:
                certainDelete();
                break;
            default:
                break;
        }
    }

    private void certainDelete() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.lock_tip));
        builder.setMessage("是否删除门锁所有密码");
        builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                sendMessage = "(!CL*)";
                Log.e("sendMessage: ", sendMessage);
                getBluetooth(bluetoothaddress, manager);
                MyProgressDialog.show(SetLockDeleteAllPassword.this, "Deleting...", false, null);
                handler.sendEmptyMessageDelayed(0x124, 10000);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    BluetoothCallbackManager manager = new BluetoothCallbackManager() {
        @Override
        public void readCallback(String result) {
            MyProgressDialog.remove();
            Log.e("readCallback: ", result);
            if (result.contains("CLOK")) {
                if (gatt!=null){
                    gatt.disconnect();
                    gatt.close();
                    gatt = null;
                }
                handler.removeMessages(0x124);
            }else{
                if (gatt!=null){
                    gatt.disconnect();
                    gatt.close();
                    gatt = null;
                }
            }
            showToast("删除成功");
        }

        @Override
        public void writeCallback(String result) {

        }

        @Override
        public void connectCallback() {
            handler.sendEmptyMessageDelayed(0x126, 500);
        }

        @Override
        public void unConnectCallback() {

        }
    };
}
