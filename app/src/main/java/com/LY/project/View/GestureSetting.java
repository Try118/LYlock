package com.LY.project.View;

import android.view.View;
import android.widget.TextView;

import com.LY.basemodule.Essential.BaseTemplate.BaseActivity;
import com.LY.project.R;

/**
 * Created by YX_PC on 2018/4/12.
 */

public class GestureSetting extends BaseActivity {

    private TextView back;//返回键

    @Override
    public int getLayoutId() {
        return R.layout.activity_gesture_setting;
    }

    @Override
    public void initViews() {
        back=(TextView)findView(R.id.back);
    }

    @Override
    public void initListener() {
        back.setOnClickListener(this);
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
            default:
                break;
        }
    }
}
