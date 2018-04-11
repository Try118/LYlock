package com.diko.project.View;

import android.view.View;
import android.widget.TextView;

import com.diko.basemodule.Essential.BaseTemplate.BaseActivity;
import com.diko.project.CustomView.SetLockPasswordView;
import com.diko.project.R;

/**
 * Created by jie on 2018/4/11.
 */

public class Set_lock_password extends BaseActivity {
    private TextView back;
    private TextView textView6;
    private SetLockPasswordView my_password;
    private TextView next;

    @Override
    public int getLayoutId() {
        return R.layout.activity_set_lock_pw;
    }

    @Override
    public void initViews() {
        back = findView(R.id.back);
        textView6 = findView(R.id.textView6);
        my_password = findView(R.id.my_password);
        next = findView(R.id.next);
    }

    @Override
    public void initListener() {
        back.setOnClickListener(this);
        next.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void processClick(View v) {
        switch (v.getId()) {
            case R.id.back:
//                startActivity();
                break;
            case R.id.next:
//                startActivity();
                break;
            default:
                break;
        }
    }
}
