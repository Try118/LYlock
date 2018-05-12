package com.LY.project.View;

import android.content.SharedPreferences;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.LY.basemodule.Essential.BaseTemplate.BaseActivity;
import com.LY.project.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by YX_PC on 2018/4/10.
 * 启动界面
 */

public class MainPage extends BaseActivity {

    private TextView tv;//2秒后跳转
    private TimerTask tast;
    private TimerTask tast2;
    private Handler handler = new Handler();
    final Timer timer = new Timer();

    private int judge = 0;


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
        SharedPreferences information = getSharedPreferences("UserInformation", MODE_PRIVATE);
        judge = information.getInt("judge",0);

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
                if (judge == 0){
                    startActivity(LegalProvisions.class);
                    finish();
                }else{
                    startActivity(Login.class);
                    finish();
                }
            }
        };

        timer.schedule(tast, 1000);//1秒后改变文字
        timer.schedule(tast2, 2000);//2秒后跳转页面
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
