package com.diko.project.View;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.diko.basemodule.Essential.BaseTemplate.BaseActivity;
import com.diko.project.R;

/**
 * Created by jie on 2018/4/11.
 */

public class SetLockOpenPasswordPage extends BaseActivity {
    private Button next;//确定门锁开发下一步
    @Override
    public int getLayoutId() {
        return R.layout.activity_setopenlockpassword_page;
    }
    @Override
    public void initViews() {
       next = findView(R.id.next);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        next.setOnClickListener(this);
    }

    @Override
    public void processClick(View v) {
        switch (v.getId()){
            case R.id.next:
//                startActivity();
            break;
        }
    }
}
