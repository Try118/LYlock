package com.diko.project.View;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.diko.basemodule.Essential.BaseTemplate.BaseActivity;
import com.diko.project.R;

import java.util.Timer;
import java.util.TimerTask;

public class MainPage extends BaseActivity {

    private TextView tv;//2秒后跳转
    private TimerTask tast;
    private TimerTask tast2;
    private Handler handler = new Handler();
    final Timer timer = new Timer();

    @Override
    public int getLayoutId() {
        return R.layout.activity_main_page;
    }

    @Override
    public void initViews() {
        tv = (TextView) findView(R.id.textView);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

        //改变文字的
        tast = new TimerTask() {
            @Override
            public void run() {
                //更新UI
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        tv.setText(getResources().getString(R.string.jump_after_one_second));
                    }
                });
            }
        };
        //跳转页面的
        tast2 = new TimerTask() {
            @Override
            public void run() {
                startActivity(LegalProvisions.class);
                finish();
            }
        };

        timer.schedule(tast, 1000);//2秒后跳转页面
        timer.schedule(tast2, 2000);//1秒后改变文字
    }

    @Override
    public void processClick(View v) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();//将任务队列里的tast和tast2清空
        handler = null;
    }
}
