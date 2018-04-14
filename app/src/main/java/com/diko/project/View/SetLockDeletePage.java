package com.diko.project.View;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.diko.basemodule.Essential.BaseTemplate.BaseActivity;
import com.diko.project.R;

/**
 * Created by jie on 2018/4/12.
 */

public class SetLockDeletePage extends BaseActivity {
    private Button delete;//删除门锁控件
    private TextView back;//返回控件

    @Override
    public int getLayoutId() {
        return R.layout.activity_delete_page;
    }

    @Override
    public void initViews() {
        delete = findView(R.id.delete);
        back = findView(R.id.back);
    }

    @Override
    public void initListener() {
        back.setOnClickListener(this);
        delete.setOnClickListener(this);
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
            case R.id.delete:
                finish();
                break;
            default:
                break;
        }
    }
}
