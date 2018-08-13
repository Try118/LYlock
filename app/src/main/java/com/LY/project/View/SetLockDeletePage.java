package com.LY.project.View;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
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
import com.LY.project.Controller.LockController;
import com.LY.project.CustomView.MyProgressDialog;
import com.LY.project.Manager.InterfaceManger;
import com.LY.project.R;
import com.LY.project.Utils.RetrofitUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by jie on 2018/4/12.
 */

public class SetLockDeletePage extends BluetoothActivity {
    private String language;

    private Button delete;//删除门锁控件
    private TextView back;//返回控件
    private String power;//权限
    private String bluetoothaddress;//蓝牙地址
    public String sendMessage;//发给硬件的消息
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
                Log.e("------", sendMessage);
            }
        }
    };
    private String lockId;//锁的id
    private String account;
    private String password;
    private String lockKey;//密钥

    private TextView tv_back2;

    @Override
    public int getLayoutId() {
        return R.layout.activity_delete_page;
    }

    @Override
    public void initViews() {
        delete = findView(R.id.delete);
        back = findView(R.id.back);
        tv_back2 = findView(R.id.tv_back2);
        Locale aDefault = Locale.getDefault();
        language = aDefault.getLanguage();
        if(language.contains("zh")){

        }else{
            tv_back2.setBackground(getResources().getDrawable(R.drawable.guideen));
        }
    }

    @Override
    public void initListener() {
        back.setOnClickListener(this);
        delete.setOnClickListener(this);
    }

    @Override
    public void initData() {
        power = getIntent().getStringExtra("power");
        bluetoothaddress = getIntent().getStringExtra("bluetoothaddress");
        lockId = getIntent().getStringExtra("lockId");
        lockKey = getIntent().getStringExtra("lockKey");
        SharedPreferences userInformation = getSharedPreferences("UserInformation", MODE_PRIVATE);
        account = userInformation.getString("account", null);
        password = userInformation.getString("password", null);
    }

    @Override
    public void processClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.delete:
                centerDelete();
                break;
            default:
                break;
        }
    }

    private void centerDelete() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.lock_tip));
        builder.setMessage(getString(R.string.certain_delete_lock));
        builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setPositiveButton(getString(R.string.certain), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (power.equals("1")) {
                    sendMessage = "(!" + lockKey + ".D*)";
                    Log.e("sendMessage: ", sendMessage);
                    getBluetooth(bluetoothaddress, manager);
                    MyProgressDialog.show(SetLockDeletePage.this, "Deleting...", false, null);
                    handler.sendEmptyMessageDelayed(0x124, 10000);
                } else {
                    MyProgressDialog.show(SetLockDeletePage.this, "Deleting...", false, null);
                    handler.sendEmptyMessageDelayed(0x124, 10000);
                    LockController lockController = new LockController(SetLockDeletePage.this);
                    List<MultipartBody.Part> parts = null;
                    Map<String, RequestBody> params = new HashMap<>();
                    params.put("phone", RetrofitUtils.convertToRequestBody(account));
                    params.put("password", RetrofitUtils.convertToRequestBody(password));
                    params.put("lockKey", RetrofitUtils.convertToRequestBody(lockId));
                    lockController.deleteLock(params, parts, new InterfaceManger.OnRequestListener() {
                        @Override
                        public void onSuccess(Object success) {
                            MyProgressDialog.remove();
                            Intent i = new Intent(SetLockDeletePage.this, AddLock.class);
                            handler.removeMessages(0x124);
                            showToast("删除成功");
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
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    BluetoothCallbackManager manager = new BluetoothCallbackManager() {
        @Override
        public void readCallback(String result) {
            MyProgressDialog.remove();
            Log.e("deletereadCallback: ", result);
            if (result.contains("OK")) {
                if (gatt!=null){
                    gatt.disconnect();
                    gatt.close();
                    gatt = null;
                }
                LockController lockController = new LockController(SetLockDeletePage.this);
                List<MultipartBody.Part> parts = null;
                Map<String, RequestBody> params = new HashMap<>();
                params.put("phone", RetrofitUtils.convertToRequestBody(account));
                params.put("password", RetrofitUtils.convertToRequestBody(password));
                params.put("lockKey", RetrofitUtils.convertToRequestBody(lockKey));
                lockController.deleteLock(params, parts, new InterfaceManger.OnRequestListener() {
                    @Override
                    public void onSuccess(Object success) {
                        showToast("删除成功");
                        MyProgressDialog.remove();
                        SharedPreferences information = getSharedPreferences("UserInformation", MODE_PRIVATE);
                        String phone = information.getString("account", null);
                        String password = information.getString("password", null);

                        Intent i = new Intent(SetLockDeletePage.this, AddLock.class);
                        i.putExtra("phone",phone);
                        i.putExtra("password",password);
                        handler.removeMessages(0x124);
                        startActivity(i);
                        finish();
                    }

                    @Override
                    public void onError(String error) {
                        showToast(error);
//                        MyProgressDialog.remove();
//                        Intent i = new Intent(SetLockDeletePage.this, AddLock.class);
//                        handler.removeMessages(0x124);
//                        startActivity(i);
//                        finish();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
                handler.removeMessages(0x124);
//                gatt.disconnect();
//                gatt.close();
            }else{
                if (gatt!=null){
                    gatt.disconnect();
                    gatt.close();
                    gatt = null;
                }
            }
        }

        @Override
        public void writeCallback(String result) {

        }

        @Override
        public void connectCallback() {
//            showToast("链接上");
            handler.sendEmptyMessageDelayed(0x126, 500);
        }

        @Override
        public void unConnectCallback() {

        }
    };
}
