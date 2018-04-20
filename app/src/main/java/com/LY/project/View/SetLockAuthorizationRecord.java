package com.LY.project.View;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.LY.basemodule.Essential.BaseTemplate.BaseActivity;
import com.LY.project.Adapter.SetLockAuthorizationRecordAdapter;
import com.LY.project.Controller.LockSetController;
import com.LY.project.Manager.InterfaceManger;
import com.LY.project.Module.AuthorizationRecords;
import com.LY.project.R;
import com.LY.project.Utils.RetrofitUtils;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by jie on 2018/4/12.
 */

public class SetLockAuthorizationRecord extends BaseActivity {
    private TextView back;//返回控件
    private ListView giver_record;//授权记录列表
    private SetLockAuthorizationRecordAdapter adapter;//listview 的设配器

    private String phone;//登录账号
    private String password;//登录密码
    private String lockKey;//门锁密钥
    private String customer;//被授权客户的信息
    private List<AuthorizationRecords> lists = new ArrayList<AuthorizationRecords>();//数据集


    @Override
    public int getLayoutId() {
        return R.layout.activity_authorization_records;
    }

    @Override
    public void initViews() {
        back = findView(R.id.back);
        giver_record = findView(R.id.giver_record);
    }

    @Override
    public void initListener() {
        back.setOnClickListener(this);
    }

    @Override
    public void initData() {
        Intent i = getIntent();
        lockKey = i.getStringExtra("lockKey");
        SharedPreferences userInformation = getSharedPreferences("UserInformation", MODE_PRIVATE);
        phone = userInformation.getString("account", null);
        password = userInformation.getString("password", null);
        initAuthorization();
    }

    private void initAuthorization() {
        LockSetController lockSetController = new LockSetController(this);
        List<String> photos = new ArrayList<>();

        List<MultipartBody.Part> parts = null;
//        parts = RetrofitUtils.filesToMultipartBodyParts("photo", photos);

        Map<String, RequestBody> params = new HashMap<>();
        params.put("phone", RetrofitUtils.convertToRequestBody(phone));
        params.put("password", RetrofitUtils.convertToRequestBody(password));
        params.put("lockKey", RetrofitUtils.convertToRequestBody(lockKey));
        lockSetController.ReadGive(params, parts, new InterfaceManger.OnRequestListener() {
            @Override
            public void onSuccess(Object success) {
                JsonParser parser = new JsonParser();
                //将JSON的String 转成一个JsonArray对象
                JsonArray jsonArray = parser.parse(String.valueOf(success)).getAsJsonArray();
                Gson gson = new Gson();
                //加强for循环遍历JsonArray
                for (JsonElement user : jsonArray) {
                    //使用GSON，直接转成Bean对象
                    AuthorizationRecords userBean = gson.fromJson(user, AuthorizationRecords.class);
                    customer = userBean.getCustomer();
                    lists.add(userBean);
                    giver_record.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(final AdapterView<?> parent, View view, final int position, long id) {
                            AlertDialog.Builder builder=new AlertDialog.Builder(SetLockAuthorizationRecord.this);
                            builder.setTitle("系统提示");
                            builder.setMessage("是否删除此授权？\n删除后对方不可使用此门锁！");
                            builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    initdelete();
                                }

                                private void initdelete() {
                                    List<String> photos = new ArrayList<>();
                                    List<MultipartBody.Part> parts = null;
                                    Map<String, RequestBody> params = new HashMap<>();
                                    params.put("phone", RetrofitUtils.convertToRequestBody(phone));
                                    params.put("password", RetrofitUtils.convertToRequestBody(password));
                                    params.put("customer", RetrofitUtils.convertToRequestBody(customer));
                                    params.put("lockKey", RetrofitUtils.convertToRequestBody(lockKey));
                                    LockSetController.cancelGive(params, parts, new InterfaceManger.OnRequestListener() {
                                        @Override
                                        public void onSuccess(Object success) {
                                            showToast(String.valueOf(success));
                                            lists.clear();
                                            initAuthorization();
                                        }

                                        @Override
                                        public void onError(String error) {
                                            Toast.makeText(SetLockAuthorizationRecord.this, error, Toast.LENGTH_SHORT).show();
                                        }

                                        @Override
                                        public void onComplete() {
                                        }
                                    });
                                }
                            });
                            builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            AlertDialog alertDialog=builder.create();
                            alertDialog.show();

                        }
                    });
                }
                adapter = new SetLockAuthorizationRecordAdapter(lists,SetLockAuthorizationRecord.this);
                giver_record.setAdapter(adapter);
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
            case R.id.back:
                finish();
                break;
            default:
                break;
        }
    }
}
