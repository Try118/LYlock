package com.diko.project.View;

import android.view.View;
import android.widget.TextView;

import com.diko.basemodule.Essential.BaseTemplate.BaseActivity;
import com.diko.project.R;

/**
 * Created by jie on 2018/4/11.
 */

public class SetLockAddress extends BaseActivity {
    private TextView back;//返回控件
    private TextView textView6;//设置门锁地址文字
    private TextView lock_address;//填写门锁地址
    private TextView finish;//确定门锁地址
    @Override
    public int getLayoutId() {
        return R.layout.activity_set_lock_address;
    }

    @Override
    public void initViews() {
        back = findView(R.id.back);
        textView6 = findView(R.id.textView6);
        lock_address = findView(R.id.lock_address);
        finish = findView(R.id.finish);
    }

    @Override
    public void initListener() {
        back.setOnClickListener(this);
        finish.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void processClick(View v) {
        switch (v.getId()){
            case R.id.back:
//                startActivity();
                break;
            case R.id.finish:
//                startActivity();
                break;
            default:
                break;
        }
    }
}
