package com.diko.project.View;

import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.diko.basemodule.Essential.BaseTemplate.BaseActivity;
import com.diko.project.Adapter.MyLockAdapter;
import com.diko.project.Controller.LoginController;
import com.diko.project.CustomView.RefreshableView;
import com.diko.project.Manager.InterfaceManger;
import com.diko.project.Module.OpenRecord;
import com.diko.project.Module.ReadAllLock;
import com.diko.project.R;
import com.diko.project.Utils.RetrofitUtils;
import com.google.gson.JsonObject;

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
    private MyLockAdapter adapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_lock;
    }

    @Override
    public void initViews() {
        addLock = (RelativeLayout) findView(R.id.add_lock);
        setting = (TextView) findView(R.id.setting);
        lockList=(ListView)findView(R.id.lock_list);
    }

    @Override
    public void initListener() {
        addLock.setOnClickListener(this);
        setting.setOnClickListener(this);
    }

    @Override
    public void initData() {
        intit();
    }

    private void intit() {
        String account = "13823839265";
        String password = "123456";
        List<String> photos = new ArrayList<>();

        List<MultipartBody.Part> parts = null;
//        parts = RetrofitUtils.filesToMultipartBodyParts("photo", photos);
        Map<String, RequestBody> params = new HashMap<>();
//        params.put("token", RetrofitUtils.convertToRequestBody(PrefManager.getToken()));
        params.put("account", RetrofitUtils.convertToRequestBody(account));
        params.put("password", RetrofitUtils.convertToRequestBody(password));
        LoginController.login(params, parts, new InterfaceManger.OnRequestListener() {
            @Override
            public void onSuccess(Object success) {
                ReadAllLock announcements = (ReadAllLock) success;
                try {
                    JSONObject myJsonArray = new JSONObject(String.valueOf(announcements));
                    JSONArray lockinfo = new JSONArray(myJsonArray);
                    List<ReadAllLock> lists = new ArrayList<ReadAllLock>();
                    for (int i=0;i<lockinfo.length();i++){
                        JSONObject myjObject = lockinfo.getJSONObject(i);
                        ReadAllLock readAllLock = new ReadAllLock( );
                        readAllLock.setName(myjObject.getString("name"));
                        readAllLock.setName(myjObject.getString("name"));
                        lists.add(readAllLock);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.e("onSuccess: ", String.valueOf(announcements));
//                adapter = new MyLockAdapter(AddLock.this, announcements);
//                lockList.setAdapter(adapter);
//                com.diko.project.Module.Login login = (com.diko.project.Module.Login) success;
//                startActivity(AddLock.class);
            }

            @Override
            public void onError(String error) {
                Toast.makeText(AddLock.this, error, Toast.LENGTH_SHORT).show();
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
