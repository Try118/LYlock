package com.diko.project.View;

import android.view.View;
import android.widget.Button;

import com.diko.basemodule.Essential.BaseTemplate.BaseActivity;
import com.diko.project.R;

import static com.diko.project.R.string.login;

/**
 * Created by YX_PC on 2018/4/10.
 */

public class LegalProvisions extends BaseActivity {

    private Button agree;//同意
    private Button disagree;//不同意

    @Override
    public int getLayoutId() {
        return R.layout.activity_legal_provisions;
    }

    @Override
    public void initViews() {
        agree=findView(R.id.agress);
        disagree=findView(R.id.disagress);
    }

    @Override
    public void initListener() {
        agree.setOnClickListener(this);
        disagree.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void processClick(View v) {
        switch (v.getId()){
            case R.id.agress:
                startActivity(Login.class);
                break;
            case R.id.disagress:
                showToast("点击不同意");
                break;
            default:
                break;
        }
    }
}
