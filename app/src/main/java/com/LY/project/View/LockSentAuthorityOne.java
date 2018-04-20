package com.LY.project.View;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.LY.basemodule.Essential.BaseTemplate.BaseActivity;
import com.LY.project.Adapter.ContactsAdapter;
import com.LY.project.CustomView.LetterIndexView;
import com.LY.project.Module.phoneInfo;
import com.LY.project.R;
import com.LY.project.Utils.ComparatorUser;
import com.LY.project.Utils.getPhoneNumberFormMobile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
    private EditText name;//备注人姓名存在于上面隐藏的控件当中

    List<phoneInfo> list = new ArrayList<phoneInfo>();

    private String lockKey;//门锁密钥
    private String power;//当前户权限
    private String customer;//即将授权的客户手机号码或是邮箱
    private String customerName;//客户的备注名
    private String starttime;//门锁信息的开始时间
    private String endtime;//门锁信息的结束时间
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
        name = findView(R.id.name);
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
        Intent intent = getIntent();
        lockKey = intent.getStringExtra("lockKey");
        power = intent.getStringExtra("power");
        starttime = intent.getStringExtra("starttime");
        endtime = intent.getStringExtra("endtime");
        customer = intent.getStringExtra("starttime");
        customerName = intent.getStringExtra("endtime");
    }

    @Override
    public void processClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.get_phone:
                requirePeimission();
                break;
            case R.id.next:
                next_click();
                break;
            case R.id.WeChat:
                startActivity(LockSentAuthorityTwo.class);
                break;
            default:
                break;
        }
    }

    private void requirePeimission() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(Manifest.permission.READ_CONTACTS);
        }else{
            showContacts();
        }
    }

    private void showContacts() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        View view = View.inflate(getApplicationContext(), R.layout.contact, null);
        builder.setView(view);
        final ListView listView = (ListView) view.findViewById(R.id.lv);
        try {
            list = getPhoneNumberFormMobile.getPhoneNumberFormMobile(LockSentAuthorityOne.this);
        } catch (Exception e) {
            showToast("无法读取通讯录权限");
            showToast("请退出重新进入并确认获取权限");
            finish();
        }
        ComparatorUser comparator = new ComparatorUser();
        Collections.sort(list, comparator);
        final ContactsAdapter adapter = new ContactsAdapter(this, list);
        listView.setAdapter(adapter);
        TextView textView = (TextView) view.findViewById(R.id.show_letter_in_center);
        final LetterIndexView letterIndexView = (LetterIndexView) view.findViewById(R.id.letter_index_view);
        letterIndexView.setTextViewDialog(textView);
        letterIndexView.setUpdateListView(new LetterIndexView.UpdateListView() {
            @Override
            public void updateListView(String currentChar) {
                int positionForSection = adapter.getPositionForSection(currentChar.charAt(0));
                listView.setSelection(positionForSection);
            }
        });
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int sectionForPosition = adapter.getSectionForPosition(firstVisibleItem);
                letterIndexView.updateLetterIndexView(sectionForPosition);
            }
        });
        final Dialog dialog = builder.create();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                phone.setText(list.get(position).getNumber());
                name.setText(list.get(position).getName());
                dialog.cancel();
            }
        });
        dialog.show();
    }

    private void next_click() {
        customer = phone.getText().toString().trim();
        customerName = name.getText().toString().trim();
        Log.e("next_click: ",lockKey+"--"+power+"--"+customer+"--"+customerName+"--"+starttime+"--"+endtime );
        if (TextUtils.isEmpty(customer)||TextUtils.isEmpty(customerName)){
            showToast("请填写信息");
        }else{
            if (power.contains("1")) {
                Intent intent = new Intent(this, LockSentAuthorityTwo.class);
                intent.putExtra("customer",customer);
                intent.putExtra("customerName",customerName);
                intent.putExtra("power", power);
                intent.putExtra("lockKey", lockKey);
                intent.putExtra("starttime", starttime);
                intent.putExtra("endtime", endtime);
                startActivity(intent);
            } else {
                String customerPower = "3";
                Intent intent = new Intent(this, LockSentAuthorityThree.class);
                intent.putExtra("customer",customer);
                intent.putExtra("customerName",customerName);
                intent.putExtra("power", power);
                intent.putExtra("lockKey", lockKey);
                intent.putExtra("starttime", starttime);
                intent.putExtra("endtime", endtime);
                intent.putExtra("customerPower", customerPower);
                startActivity(intent);
            }
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
