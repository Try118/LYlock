package com.diko.project.View;

import android.view.View;
import android.widget.TextView;

import com.diko.basemodule.Essential.BaseTemplate.BaseActivity;
import com.diko.project.R;

/**
 * Created by jie on 2018/4/12.
 */

public class LockSentAuthorityThree extends BaseActivity {
    private TextView back;//返回控件按钮
    private TextView self;//自定义时间按键
    private TextView one_day;//选择时间为一天
    private TextView one_week;//选择时间为一周
    private TextView one_month;//选择时间为一月
    private TextView one_year;//选择时间为一年
    private TextView start_time;//设置开始时间
    private TextView end_time;//设置结束时间
    private TextView next;//下一步按钮

    @Override
    public int getLayoutId() {
        return R.layout.activity_sent_lock_authority_three;
    }

    @Override
    public void initViews() {
        back = findView(R.id.back);
        self = findView(R.id.self);
        one_day = findView(R.id.one_day);
        one_week = findView(R.id.one_week);
        one_month = findView(R.id.one_month);
        one_year = findView(R.id.one_year);
        start_time = findView(R.id.start_time);
        end_time = findView(R.id.end_time);
        next = findView(R.id.next);
    }

    @Override
    public void initListener() {
        back.setOnClickListener(this);
        self.setOnClickListener(this);
        one_day.setOnClickListener(this);
        one_week.setOnClickListener(this);
        one_month.setOnClickListener(this);
        one_year.setOnClickListener(this);
        start_time.setOnClickListener(this);
        end_time.setOnClickListener(this);
        next.setOnClickListener(this);
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
            case R.id.self:
//                startActivity();
                break;
            case R.id.one_day:
//                startActivity();
                break;
            case R.id.one_week:
//                startActivity();
                break;
            case R.id.one_month:
//                startActivity();
                break;
            case R.id.one_year:
//                startActivity();
                break;
            case R.id.start_time:
//                startActivity();
                break;
            case R.id.end_time:
//                startActivity();
                break;
            case R.id.next:
//                startActivity();
                break;
            default:
                break;
        }
    }
}
