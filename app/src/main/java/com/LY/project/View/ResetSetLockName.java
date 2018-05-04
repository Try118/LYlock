package com.LY.project.View;

import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.LY.basemodule.Essential.BaseTemplate.BaseActivity;
import com.LY.project.Controller.LockSetController;
import com.LY.project.Manager.InterfaceManger;
import com.LY.project.R;
import com.LY.project.Utils.RetrofitUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by yx on 2018/5/3.
 * 上传恢复出厂密码后,进行填写门锁名字
 */

public class ResetSetLockName extends BaseActivity {
    private TextView back;//返回控件
    private TextView textView6;//设置门锁名字的文字
    private EditText lock_name;//填写门锁名字
    private TextView finish;//确定门锁名字

    private String lockKey;//密钥
    private String account;//账号
    private String password;//密码
    private String lockName;//门锁名字

    @Override
    public int getLayoutId() {
        return R.layout.activity_set_lock_name;
    }

    @Override
    public void initViews() {
        back = findView(R.id.back);
        textView6 = findView(R.id.textView6);
        lock_name = findView(R.id.lock_name);
        finish = findView(R.id.finish);
    }

    @Override
    public void initListener() {
        back.setOnClickListener(this);
        finish.setOnClickListener(this);
    }

    @Override
    public void initData() {
        lockKey = getIntent().getStringExtra("lockKey");
        SharedPreferences preferences = getSharedPreferences("UserInformation", MODE_PRIVATE);
        account = preferences.getString("account", null);
        password = preferences.getString("password", null);
    }

    @Override
    public void processClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.finish:
                setLockName();
                break;
            default:
                break;
        }
    }

    private void setLockName() {
        lockName = lock_name.getText().toString().trim();
        if (TextUtils.isEmpty(lockName)) {
            showToast(getString(R.string.no_write_lock_name));
        } else {
            revise_name();
        }
    }

    private void revise_name() {
        LockSetController lockSetController = new LockSetController(this);
        List<MultipartBody.Part> parts = null;
        Map<String, RequestBody> params = new HashMap<>();
        params.put("phone", RetrofitUtils.convertToRequestBody(account));
        params.put("password", RetrofitUtils.convertToRequestBody(password));
        params.put("lockKey", RetrofitUtils.convertToRequestBody(lockKey));
        params.put("name", RetrofitUtils.convertToRequestBody(lockName));
        lockSetController.changeLockName(params, parts, new InterfaceManger.OnRequestListener() {
            @Override
            public void onSuccess(Object success) {
//                showToast(getResources().getString(R.string.correct_success));
                Intent i = new Intent(ResetSetLockName.this, ResetSetLockAddress.class);
                i.putExtra("lockKey",lockKey);
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
