package com.diko.project.View;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.diko.basemodule.Essential.BaseTemplate.BaseActivity;
import com.diko.project.R;

/**
 * Created by jie on 2018/4/12.
 */

public class LockSentAuthorityOne extends BaseActivity implements TextWatcher {
    private TextView back;//返回控件
    private EditText phone;//填入电话号码
    private TextView get_phone;//从通讯录获取联系人信息
    private TextView next;//下一步
    private TextView Wechat;//wechat联系人
    private LinearLayout name_line;//被隐藏起来的控件，其实我不想这么做的，希望有后来者，以后修改
    @Override
    public int getLayoutId() {
        return R.layout.activity_sent_lock_authority_one;
    }

    @Override
    public void initViews() {
        back = findView(R.id.back);
        phone = findView(R.id.phone);
        get_phone = findView(R.id.get_phone);
        next = findView(R.id.next);
        Wechat = findView(R.id.WeChat);
        name_line = findView(R.id.name_line);
    }

    @Override
    public void initListener() {
        back.setOnClickListener(this);
        get_phone.setOnClickListener(this);
        next.setOnClickListener(this);
        Wechat.setOnClickListener(this);
        phone.addTextChangedListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void processClick(View v) {
        switch(v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.get_phone:
//                startActivity();
                break;
            case R.id.next:
//                startActivity();
                break;
            case R.id.WeChat:
                startActivity(LockSentAuthorityTwo.class);
                break;
            default:
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        name_line.setVisibility(View.VISIBLE);
    }
}
