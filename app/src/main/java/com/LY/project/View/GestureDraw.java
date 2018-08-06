package com.LY.project.View;

import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.LY.basemodule.Essential.BaseTemplate.BaseActivity;
import com.LY.project.CustomView.Drawl;
import com.LY.project.CustomView.GuestureLockView;
import com.LY.project.CustomView.Variate;
import com.LY.project.R;
import com.LY.project.Utils.ToastUtils;

import java.util.Locale;

/**
 * Created by YX on 2018/6/10.
 */

public class GestureDraw extends BaseActivity {
    String language;

    private FrameLayout mFrameLayout;
    private SharedPreferences sp;
    private String pwd;
    private int state;
    private GuestureLockView mGuestureLockView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_gesture_pw1;
    }

    @Override
    public void initViews() {
        mFrameLayout = (FrameLayout) findViewById(R.id.framelayout);
        initlanguage();
    }

    private void initlanguage() {
        Locale aDefault = Locale.getDefault();
        language = aDefault.getLanguage();
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        sp = getSharedPreferences("userInfo", MODE_PRIVATE);
        pwd = sp.getString("gestureState", null);//从sp中获取保存的密码，判断用户是否已经设置密码
        state = getIntent().getIntExtra("state", 0);//上张页面传进来的state，判断是设置手势密码还是修改手势密码
        Start();
    }

    @Override
    public void processClick(View v) {

    }

    private void Start() {
        mGuestureLockView = new GuestureLockView(this, new Drawl.GestureCallBack() {
            @Override
            public void checkedSuccess(String password) {
                pwd = sp.getString("gestureState", null);//从sp中获取保存的密码，判断用户是否已经设置密码
                //首先判断一下用户是否已经设置密码
                if (TextUtils.isEmpty(pwd)) {
                    //如果为空，代码没有设置密码，需要设置新的密码；
                    // 设置新密码需要设置两遍，防止用户误操作；
                    // 第一遍设置的新密码保存在Variate类的一个变量中，这个变量默认为null
                    if (TextUtils.isEmpty(Variate.PASSWORD)) {
                        //如果这个变量为null，第一次将密码保存在Variate.PASSWORD提示再次输入密码
                        Variate.PASSWORD = password;
                        if (language.contains("zh")) {
                            ToastUtils.showToast(GestureDraw.this, "请再次输入密码");
                        } else {
                            ToastUtils.showToast(GestureDraw.this, "Please enter your password again");
                        }

                        // 并且刷新当前页面
                        //refresh();
                    } else {
                        //如果Variate.PASSWORD不为空代表是第二次输入新密码，判断两次输入密码是否相同
                        if (password.equals(Variate.PASSWORD)) {
                            //如果相同，将密码保存在当地sp中
                            sp.edit().putString("gestureState", password).commit();
                            if (language.contains("zh")) {
                                ToastUtils.showToast(GestureDraw.this, "密码设置成功");
                            } else {
                                ToastUtils.showToast(GestureDraw.this, "Password set successful");
                            }

                            sp.edit().putBoolean("gesture", true).commit();
//                            Intent intent = new Intent(GestureDraw.this, GestureSetting.class);
//                            startActivity(intent);
                            finish();
                        } else {
                            //如果两次输入密码不一样，将Variate.PASSWORD设为null,提示密码设置失败
                            Variate.PASSWORD = null;
                            if (language.contains("zh")) {
                                ToastUtils.showToast(GestureDraw.this, "密码设置失败");
                            } else {
                                ToastUtils.showToast(GestureDraw.this, "Password setting failed");
                            }

                            sp.edit().putBoolean("gesture", false).commit();
                            // 跳回主页面需重新设置密码
//                            Intent intent = new Intent(GestureDraw.this, GestureSetting.class);
//                            startActivity(intent);
                            finish();
                        }
                    }
                } else {
                    //如果已经设置密码，判断输入密码和保存密码是否相同
                    if (pwd.equals(password)) {
                        //如果相同，密码正确，进入主页面
                        if (state == 1) {
                            //关闭手势密码
                            sp.edit().putBoolean("gesture", false).commit();
                            sp.edit().putString("gestureState", null).commit();
//                            Intent i = new Intent(GestureDraw.this, GestureSetting.class);
//                            startActivity(i);
                            finish();
                        } else if (state == 2) {
                            sp.edit().putBoolean("gesture", false).commit();
                            sp.edit().putString("gestureState", null).commit();
                            if (language.contains("zh")) {
                                ToastUtils.showToast(GestureDraw.this, "重新设置密码");
                            } else {
                                ToastUtils.showToast(GestureDraw.this, "router enable password cisco");
                            }
                            //refresh();
                        } else {
                            Variate.state = true;
//                            Intent i = new Intent(GestureDraw.this, Login.class);
//                            startActivity(i);
                            finish();
                        }
                    } else {
                        if (state != 0) {
                            //如果不相同，密码错误，刷新当前activity，需重新输入密码
                            if (language.contains("zh")) {
                                Toast.makeText(GestureDraw.this, "密码错误", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(GestureDraw.this, "wrong password", Toast.LENGTH_SHORT).show();
                            }
//                            Intent i = new Intent(GestureDraw.this, GestureSetting.class);
//                            startActivity(i);
                            finish();
                        } else {
//                            Intent i = new Intent(GestureDraw.this, Login.class);
//                            startActivity(i);
//                            Variate.setState = true;
//                            finish();
                            if (language.contains("zh")) {
                                Toast.makeText(GestureDraw.this, "密码错误,请重新输入", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(GestureDraw.this, "Password error, please re-enter", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }

            }

            @Override
            public void checkedFail() {

            }
        });
        mGuestureLockView.setParentView(mFrameLayout);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //当前页面关闭时将Variate.PASSWORD设为null；防止用户第二次输入密码的时候退出当前activity
        Variate.PASSWORD = null;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (Login.activity != null)
            Login.activity.finish();
        this.finish();
        return false;

    }

    public void refresh() {
        onCreate(null);
    }
}
