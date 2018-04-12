package com.diko.project.View;

import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.diko.basemodule.Essential.BaseTemplate.BaseActivity;
import com.diko.project.R;

/**
 * Created by jie on 2018/4/12.
 */

public class SetLockAuthorizationRecord extends BaseActivity {
    private TextView back;//返回控件
    private ListView giver_record;//授权记录列表

    @Override
    public int getLayoutId() {
        return R.layout.activity_authorization_records;
    }

    @Override
    public void initViews() {
        back = findView(R.id.back);
        giver_record = findView(R.id.giver_record);
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
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            default:
                break;
        }
    }
}
