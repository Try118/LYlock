package com.diko.project.View;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.diko.basemodule.Essential.BaseTemplate.BaseActivity;
import com.diko.project.Adapter.ContactsAdapter;
import com.diko.project.CustomView.LetterIndexView;
import com.diko.project.Module.phoneInfo;
import com.diko.project.R;
import com.diko.project.Utils.ComparatorUser;
import com.diko.project.Utils.getPhoneNumberFormMobile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by jie on 2018/4/12.
 */

public class LockSentPasswordOne extends BaseActivity {
    private TextView back;//返回控件
    private EditText phone;//填入电话号码
    private TextView get_phone;//从通讯录获取联系人信息
    private TextView next;//下一步
    private TextView Wechat;//wechat联系人
    private EditText name;//备注名
    private String power;
    private String lockKey;
    private String starttime;
    private String endtime;
    private Integer order;
    private LinearLayout nameLine;
    List<phoneInfo> list = new ArrayList<phoneInfo>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_sent_lock_password_one;
    }

    @Override
    public void initViews() {
        back = (TextView) findView(R.id.back);
        phone = (EditText) findView(R.id.phone);
        get_phone = (TextView) findView(R.id.get_phone);
        next = (TextView) findView(R.id.next);
        Wechat = (TextView) findView(R.id.WeChat);
        name = (EditText) findView(R.id.name);
        nameLine = (LinearLayout) findView(R.id.name_line);
    }

    @Override
    public void initListener() {
        back.setOnClickListener(this);
        get_phone.setOnClickListener(this);
        next.setOnClickListener(this);
        Wechat.setOnClickListener(this);
    }

    @Override
    public void initData() {
        //联系人输入完后，显示出备注名
        phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                    nameLine.setVisibility(View.VISIBLE);
            }
        });
        getUserInfo();
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
                nextOne();
                break;
            case R.id.WeChat:
                break;
            default:
                break;
        }
    }

    private void requirePeimission() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_CONTACTS},1);
        }else {
            showContacts();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1:
                if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    showContacts();
                }else{
                    showToast("拒绝给读取通讯录权限");
                }
        }
    }

    //显示联系人
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
        try{
            list = getPhoneNumberFormMobile.getPhoneNumberFormMobile(LockSentPasswordOne.this);
        }catch (Exception e){
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

    private void getUserInfo() {
        Intent i = getIntent();
        power = i.getStringExtra("power");
        lockKey = i.getStringExtra("lockKey");
        starttime = i.getStringExtra("starttime");
        endtime = i.getStringExtra("endtime");
//        order = i.getIntExtra("order", 0);
        if (TextUtils.isEmpty(lockKey) || TextUtils.isEmpty(endtime) || TextUtils.isEmpty(starttime)) {
            showToast(getString(R.string.lock_info_error));
            finish();
        }
    }

    private void nextOne() {
        String account = phone.getText().toString();
        if (!TextUtils.isEmpty(account)) {
            Intent i = new Intent(this, LockSentPasswordTwo.class);
            i.putExtra("account", account);
            i.putExtra("name", name.getText().toString());
            i.putExtra("type", 2);
            startActivity(i);
        } else {
            showToast(getString(R.string.no_write_phone));
        }
    }
}
