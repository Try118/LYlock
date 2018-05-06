package com.LY.project.View;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.LY.basemodule.Essential.BaseTemplate.BaseActivity;
import com.LY.project.R;

/**
 * Created by YX on 2018/5/6.
 */

public class SetLockPasswordPage extends BaseActivity {
    private TextView back;
    private Button next;


    @Override
    public int getLayoutId() {
        return R.layout.activity_setopenlockpassword_page;
    }

    @Override
    public void initViews() {
        back = findView(R.id.back);
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
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.next:
                startActivity(SetLockPassword.class);
                break;
        }
    }
}
