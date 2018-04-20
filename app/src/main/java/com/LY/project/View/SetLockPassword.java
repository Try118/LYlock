package com.LY.project.View;

import android.view.View;
import android.widget.TextView;

import com.LY.basemodule.Essential.BaseTemplate.BaseActivity;
import com.LY.project.CustomView.SetLockPasswordView;
import com.LY.project.R;

/**
 * Created by jie on 2018/4/11.
 */

public class SetLockPassword extends BaseActivity {
    private TextView back;//返回控件
    private TextView textView6;//设置门锁密码的文字
    private SetLockPasswordView my_password;//六个密码框
    private TextView next;//下一步

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
                finish();
                break;
            case R.id.next:
//                startActivity();
                break;
            default:
                break;
        }
    }
}
