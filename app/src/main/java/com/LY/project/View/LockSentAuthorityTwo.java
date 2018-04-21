package com.LY.project.View;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.LY.basemodule.Essential.BaseTemplate.BaseActivity;
import com.LY.project.R;

/**
 * Created by jie on 2018/4/12.
 */

public class LockSentAuthorityTwo extends BaseActivity {
    private TextView back;//返回控件按钮
    private TextView times;//发送高级权限
    private TextView Once;//发送普通权限
    private TextView send;//发送密码
    private TextView tip;//发送权限解释

    private String lockKey;//门锁密钥
    private String power;//当前用户权限
    private int customerPower = 3;//即将授权的用户权限
    private String starttime;//门锁的开始时间
    private String endtime;//门锁的结束时间
    private String customer;//授权用户的联系方式
    private String customerName;//授权客户的备注名

    private int choice = 1;//判断我们选择是哪一个权限  1 为高级权限 2 为普通权限
    @Override
    public int getLayoutId() {
        return R.layout.activity_lock_sent_authority;
    }

    @Override
    public void initViews() {
        back = findView(R.id.back);
        times = findView(R.id.times);
        Once = findView(R.id.once);
        send = findView(R.id.send);
        tip = findView(R.id.tip);
        times.setTextColor(Color.RED);
        tip.setText("高级权限指的是拥有发送密码/开锁/授权/设置密码这几个权限");
    }

    @Override
    public void initListener() {
        back.setOnClickListener(this);
        times.setOnClickListener(this);
        Once.setOnClickListener(this);
        send.setOnClickListener(this);
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        lockKey = intent.getStringExtra("lockKey");
        power = intent.getStringExtra("power");
        starttime = intent.getStringExtra("starttime");
        endtime = intent.getStringExtra("endtime");
        customer = intent.getStringExtra("customer");
        customerName = intent.getStringExtra("customerName");
    }

    @Override
    public void processClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.times:
                times.setTextColor(Color.RED);
                tip.setText("高级权限指的是拥有发送密码/开锁/授权/设置密码这几个权限");
                Once.setTextColor(getResources().getColor(R.color.Text_color));
                choice = 1;
                break;
            case R.id.once:
                Once.setTextColor(Color.RED);
                tip.setText("普通权限指的是只拥有开锁这个权限");
                times.setTextColor(getResources().getColor(R.color.Text_color));
                choice = 2;
                break;
            case R.id.send:
                if (choice == 1){
                    Intent intent = new Intent(this, LockSentAuthorityThree.class);
                    intent.putExtra("customer",customer);
                    intent.putExtra("customerName",customerName);
                    intent.putExtra("power", power);
                    intent.putExtra("lockKey", lockKey);
                    intent.putExtra("starttime", starttime);
                    intent.putExtra("endtime", endtime);
                    intent.putExtra("customerPower","2");
                    startActivity(intent);
                }
                if(choice == 2){
                    Intent intent = new Intent(this, LockSentAuthorityThree.class);
                    intent.putExtra("customer",customer);
                    intent.putExtra("customerName",customerName);
                    intent.putExtra("power", power);
                    intent.putExtra("lockKey", lockKey);
                    intent.putExtra("starttime", starttime);
                    intent.putExtra("endtime", endtime);
                    intent.putExtra("customerPower","3");
                    startActivity(intent);
                }
                break;
            default:
                break;
        }
    }
}
