package com.LY.project.View;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.LY.basemodule.Essential.BaseTemplate.BluetoothActivity;
import com.LY.basemodule.Manager.BluetoothCallbackManager;
import com.LY.project.Controller.LockController;
import com.LY.project.CustomView.MyProgressDialog;
import com.LY.project.CustomView.SetSecret;
import com.LY.project.Manager.InterfaceManger;
import com.LY.project.R;
import com.LY.project.Utils.RetrofitUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by YX on 2018/5/2.
 */

public class Reset extends BluetoothActivity {
    private SetSecret secret;//出厂密码
    private TextView finish;//完成
    private String address;//mac地址
    private String lockKey;//服务器取回的秘钥
    private String lockid;//服务器传回的lockid

    @Override
    public int getLayoutId() {
        return R.layout.activity_reset;
    }

    @Override
    public void initViews() {
        secret = findView(R.id.password);
        finish = findView(R.id.finish);
    }

    @Override
    public void initListener() {
        finish.setOnClickListener(this);
    }

    @Override
    public void initData() {
        address = getIntent().getStringExtra("address");//mac地址
        lockKey = getIntent().getStringExtra("lockKey");//服务器取回的秘钥
        lockid = getIntent().getStringExtra("lockid");//服务器传回的lockid
        finish.setClickable(false);
        secret.setCallback(new SetSecret.myCallback() {
            public void execute() {
                finish.setClickable(true);
            }
        });
    }

    @Override
    public void processClick(View v) {
        switch (v.getId()) {
            case R.id.finish:
                String s = secret.getText();
                if (s != null && s.length() >= 8) {
                    secret.noInput();
                    MyProgressDialog.show(Reset.this, "Updating...", false, null);
                    getBluetooth(address, manager);
                } else {
                    showToast("未设置恢复出厂密码");
                }
                break;
            default:
                break;
        }
    }

    BluetoothCallbackManager manager = new BluetoothCallbackManager() {
        @Override
        public void readCallback(String result) {
            if (result.contains("RESETOK")) {
                LockController lockController = new LockController(Reset.this);
                List<MultipartBody.Part> parts = null;
                Map<String, RequestBody> params = new HashMap<>();
                params.put("lockId", RetrofitUtils.convertToRequestBody(lockid));
                params.put("restore", RetrofitUtils.convertToRequestBody(secret.getText()));
                lockController.SetRestore(params, parts, new InterfaceManger.OnRequestListener() {
                    @Override
                    public void onSuccess(Object success) {
                        if (gatt != null) {
                            gatt.disconnect();
                            gatt.close();
                            gatt = null;
                        }
                        Intent i = new Intent(Reset.this, ResetSetLockName.class);
                        i.putExtra("lockKey", lockKey);
                        startActivity(i);
                        finish();
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

        @Override
        public void writeCallback(String result) {

        }

        @Override
        public void connectCallback() {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            bluetoothGattCallback.setMessage("(!R" + secret.getText() + "*)");
            gatt.discoverServices();
            MyProgressDialog.remove();
        }

        @Override
        public void unConnectCallback() {

        }
    };

    @Override
    protected void onPause() {
        super.onPause();
//        if (gatt != null) {
//            gatt.disconnect();
//            gatt.close();
//        }
    }
}
