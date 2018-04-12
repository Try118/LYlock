package com.diko.project.View;

import android.view.View;
import android.widget.Button;

import com.diko.basemodule.Essential.BaseTemplate.BaseActivity;
import com.diko.project.R;

/**
 * Created by jie on 2018/4/12.
 */

public class SetLockDeletePage extends BaseActivity {
    private Button delete;//删除门锁控件
    @Override
    public int getLayoutId() {
        return R.layout.activity_delete_page;
    }

    @Override
    public void initViews() {
         delete = findView(R.id.delete);
    }

    @Override
    public void initListener() {
        delete.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void processClick(View v) {
        switch (v.getId()){
            case R.id.delete:
//                startActivity();
                break;
            default:
                break;
        }
    }
}
