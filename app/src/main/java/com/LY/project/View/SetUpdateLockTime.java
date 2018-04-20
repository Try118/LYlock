package com.LY.project.View;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.LY.basemodule.Essential.BaseTemplate.BaseActivity;
import com.LY.project.R;

/**
 * Created by jie on 2018/4/11.
 */

public class SetUpdateLockTime extends BaseActivity {
    private TextView back;//返回控件
    private Button update;//更新时间

    @Override
    public int getLayoutId() {
        return R.layout.activity_set_update_locktime_page;
    }

    @Override
    public void initViews() {
        back = findView(R.id.back);
        update = findView(R.id.update);
    }

    @Override
    public void initListener() {
        back.setOnClickListener(this);
        update.setOnClickListener(this);
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
            case R.id.update:
                break;
            default:
                break;
        }
    }
}
