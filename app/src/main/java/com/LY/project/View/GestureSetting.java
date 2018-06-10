package com.LY.project.View;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.LY.basemodule.Essential.BaseTemplate.BaseActivity;
import com.LY.project.R;

/**
 * Created by YX_PC on 2018/4/12.
 */

public class GestureSetting extends BaseActivity {

    private TextView back;//返回键
    private Switch switchGesture;//打开关闭手势密码
    private LinearLayout set_gesture;//修改手势密码
    private SharedPreferences userInfo;
    private boolean gestureState;//手势密码的状态（开启或者关闭）

    @Override
    public int getLayoutId() {
        return R.layout.activity_gesture_setting;
    }

    @Override
    public void initViews() {
        back = (TextView) findView(R.id.back);
        switchGesture = (Switch) findView(R.id.gesture);
        set_gesture = (LinearLayout) findView(R.id.set_gesture);
    }

    @Override
    public void initListener() {
        back.setOnClickListener(this);
        switchGesture.setOnClickListener(this);
        set_gesture.setOnClickListener(this);
    }

    @Override
    public void initData() {
        userInfo = getSharedPreferences("userInfo", MODE_PRIVATE);
        gestureState = userInfo.getBoolean("gesture", false);
        switchGesture.setChecked(gestureState);
        set_gesture.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    v.setBackgroundColor(Color.LTGRAY);
                }else if(event.getAction() == MotionEvent.ACTION_MOVE){
                    v.setBackgroundColor(Color.LTGRAY);
                }else if(event.getAction() == MotionEvent.ACTION_UP){
                    v.setBackgroundColor(Color.WHITE);
                }
                return false;
            }
        });
    }

    @Override
    public void processClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.gesture:
                Intent i=new Intent(GestureSetting.this, GestureDraw.class);
                i.putExtra("state",1);
                startActivity(i);
                break;
            case R.id.set_gesture:
                Intent i2=new Intent(GestureSetting.this, GestureDraw.class);
                i2.putExtra("state",2);
                startActivity(i2);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onResume() {
        if(userInfo.getBoolean("gesture",false)){
            switchGesture.setChecked(true);
        }else{
            switchGesture.setChecked(false);
        }
        super.onResume();
    }
}
