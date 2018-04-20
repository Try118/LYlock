package com.diko.project.View;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.diko.basemodule.Essential.BaseTemplate.BaseActivity;
import com.diko.project.Adapter.MyLockAdapter;
import com.diko.project.Controller.LockController;
import com.diko.project.Controller.LoginController;
import com.diko.project.CustomView.RefreshableView;
import com.diko.project.Manager.InterfaceManger;
import com.diko.project.Module.OpenRecord;
import com.diko.project.Module.ReadAllLock;
import com.diko.project.R;
import com.diko.project.Utils.RetrofitUtils;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by YX_PC on 2018/4/10.
 * 添加门锁
 */

public class AddLock extends BaseActivity {
    private RelativeLayout addLock;//添加门锁
    private TextView setting;//右上角三点
    private ListView lockList;//门锁列表
    private MyLockAdapter adapter;//适配器
    private List<ReadAllLock> lists = new ArrayList<ReadAllLock>();//数据集

    private String phone;//电话号码
    private String password;//用户账号

    @Override
    protected void onRestart() {
        super.onRestart();
        lists.clear();
        intit(phone, password);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_lock;
    }

    @Override
    public void initViews() {
        addLock = (RelativeLayout) findView(R.id.add_lock);
        setting = (TextView) findView(R.id.setting);
        lockList = (ListView) findView(R.id.lock_list);
    }

    @Override
    public void initListener() {
        addLock.setOnClickListener(this);
        setting.setOnClickListener(this);
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        phone = intent.getStringExtra("phone");
        password = intent.getStringExtra("password");
        intit(phone, password);
    }

    private void intit(String phone, String password) {
        LockController lockController = new LockController(AddLock.this);
        List<String> photos = new ArrayList<>();
        List<MultipartBody.Part> parts = null;
        Map<String, RequestBody> params = new HashMap<>();
        params.put("phone", RetrofitUtils.convertToRequestBody(phone));
        params.put("password", RetrofitUtils.convertToRequestBody(password));
        lockController.ReadAllLock(params, parts, new InterfaceManger.OnRequestListener() {
            @Override
            public void onSuccess(Object success) {
                Log.e("1234123: ", String.valueOf(success));
                JsonParser parser = new JsonParser();
                //将JSON的String 转成一个JsonArray对象
                JsonArray jsonArray = parser.parse(String.valueOf(success)).getAsJsonArray();
                Gson gson = new Gson();
                //加强for循环遍历JsonArray
                for (JsonElement user : jsonArray) {
                    //使用GSON，直接转成Bean对象
                    ReadAllLock userBean = gson.fromJson(user, ReadAllLock.class);
                    lists.add(userBean);
                }
                adapter = new MyLockAdapter(AddLock.this, lists);
                lockList.setAdapter(adapter);
            }

            @Override
            public void onError(String error) {
                showToast(error);
            }

            @Override
            public void onComplete() {
            }
        });
    }

    @Override
    public void processClick(View v) {
        switch (v.getId()) {
            case R.id.add_lock:
                startActivity(SearchDoorLock2.class);
                break;
            case R.id.setting:
                startActivity(Setting.class);
                break;
            default:
                break;
        }
    }
}
