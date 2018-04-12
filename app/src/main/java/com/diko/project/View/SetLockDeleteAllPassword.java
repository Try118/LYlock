package com.diko.project.View;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.diko.basemodule.Essential.BaseTemplate.BaseActivity;
import com.diko.project.R;

/**
 * Created by jie on 2018/4/12.
 */

public class SetLockDeleteAllPassword extends BaseActivity {
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

    }

    @Override
    public void initData() {

    }

    @Override
    public void processClick(View v) {
        switch (v.getId()){
            case R.id.back:
                break;
            case R.id.delete_all_password:
                break;
            default:
                break;
        }
    }
}
