package com.LY.project.View;

import android.view.View;
import android.widget.TextView;

import com.LY.basemodule.Essential.BaseTemplate.BaseActivity;
import com.LY.project.CustomView.MyFastMenuBar;
import com.LY.project.R;

/**
 * Created by YX_PC on 2018/4/10.
 * 门锁列表设置
 */

public class Setting extends BaseActivity implements MyFastMenuBar.onMenuBarClickListener{
    private MyFastMenuBar correct_password;//修改密码
    private MyFastMenuBar gesture;//手势密码
    private MyFastMenuBar version;//版本更新
    private MyFastMenuBar language;//语言
    private MyFastMenuBar about;//关于我们
    private MyFastMenuBar question;//常见问题
    private MyFastMenuBar logout;//退出登录
    private TextView correct_back;//返回键

    @Override
    public int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    public void initViews() {
        correct_password = (MyFastMenuBar) findView(R.id.correct_password);
        gesture = (MyFastMenuBar) findView(R.id.gesture);
        version = (MyFastMenuBar) findView(R.id.version);
        language = (MyFastMenuBar) findView(R.id.language);
        about = (MyFastMenuBar) findView(R.id.about);
        question = (MyFastMenuBar) findView(R.id.question);
        logout = (MyFastMenuBar) findView(R.id.logout);
        correct_back=(TextView)findView(R.id.correct_back);
    }

    @Override
    public void initListener() {
        correct_password.setOnMenuBarClickListener(this);
        gesture.setOnMenuBarClickListener(this);
        version.setOnMenuBarClickListener(this);
        language.setOnMenuBarClickListener(this);
        about.setOnMenuBarClickListener(this);
        question.setOnMenuBarClickListener(this);
        logout.setOnMenuBarClickListener(this);
        correct_back.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void processClick(View v) {
        switch (v.getId()){
            case R.id.correct_back:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void onMenuBarClick(MyFastMenuBar view) {
        switch (view.getId()){
            case R.id.correct_password:
                startActivity(VerifyRegistration.class);
                break;
            case R.id.gesture:
                startActivity(GestureSetting.class);
                break;
            case R.id.version:
                break;
            case R.id.language:
                break;
            case R.id.about:
                break;
            case R.id.question:
                break;
            case R.id.logout:
                startActivity(Login.class);
                break;
            default:
                break;
        }
    }
}
