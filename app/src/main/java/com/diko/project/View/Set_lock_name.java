package com.diko.project.View;

import android.view.View;
import android.widget.TextView;

import com.diko.basemodule.Essential.BaseTemplate.BaseActivity;
import com.diko.project.R;

/**
 * Created by jie on 2018/4/11.
 */

public class Set_lock_name extends BaseActivity {
    private TextView back;
    private TextView textView6;
    private TextView lock_name;
    private TextView finish;
    @Override
    public int getLayoutId() {
        return R.layout.activity_set_lock_name;
    }

    @Override
    public void initViews() {
        back = findView(R.id.back);
        textView6 = findView(R.id.textView6);
        lock_name = findView(R.id.lock_name);
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
