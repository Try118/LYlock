package com.LY.project.View;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.LY.basemodule.Essential.BaseTemplate.BaseActivity;
import com.LY.basemodule.Essential.BaseTemplate.BluetoothActivity;
import com.LY.project.R;

/**
 * Created by jie on 2018/4/12.
 */

public class SetLockDeleteAllPassword extends BluetoothActivity {
    private TextView back;//返回控件
    private Button delete_all_password;//确定删除所有密码控件

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
        builder.setMessage(getString(R.string.certain_delete_lock));
        builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
    }
}
