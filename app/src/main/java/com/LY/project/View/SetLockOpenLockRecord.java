package com.LY.project.View;

import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.LY.basemodule.Essential.BaseTemplate.BaseActivity;
import com.LY.project.Adapter.MyOpenRecordAdapter;
import com.LY.project.Controller.LockSetController;
import com.LY.project.Manager.InterfaceManger;
import com.LY.project.Module.SetLockOpenLockRecordModule;
import com.LY.project.R;
import com.LY.project.Utils.RetrofitUtils;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;


/**
 * Created by jie on 2018/4/11.
 */

public class SetLockOpenLockRecord extends BaseActivity {
    private TextView back;//f返回控件
    private LinearLayout title;//标题栏
    private ListView list;//listview展示列表
    private MyOpenRecordAdapter adapter;//listview的适配器
    private String lockId;
    private String account;
    List<SetLockOpenLockRecordModule> lists = new ArrayList<>();
    Comparator comparator = new Comparator<SetLockOpenLockRecordModule>() {
        //       返回正数：o2在前，o1在后
//        返回 0：两个数相等
//        返回负数：o1在前，o2在后
        @Override
        public int compare(SetLockOpenLockRecordModule O1, SetLockOpenLockRecordModule O2) {
            String o1 = O1.time;
            String o2 = O2.time;
            if (o1.equals(getString(R.string.today))) {
                return -1;
            } else if (o2.equals(getString(R.string.today))) {
                return 1;
            }
            long a = 0;
            long b = 0;
            if (o1.equals(getString(R.string.In_the_last_three_days))) {
                a = Time(new Date().getTime());
            } else if (o1.equals(getString(R.string.In_the_latest_week))) {
                a = Time(new Date().getTime() - 259200000);
            } else if (o1.equals(getString(R.string.other))) {
                a = Time(new Date().getTime() - 604800000);
            } else {
                a = Long.valueOf(o1);
            }
            if (o2.equals(getString(R.string.In_the_last_three_days))) {
                b = Time(new Date().getTime());
            } else if (o2.equals(getString(R.string.In_the_latest_week))) {
                b = Time(new Date().getTime() - 259200000);
            } else if (o2.equals(getString(R.string.other))) {
                b = Time(new Date().getTime() - 604800000);
            } else {
                b = Long.valueOf(o2);
            }
            return a >= b ? -1 : 1;
        }
    };

    @Override
    public int getLayoutId() {
        return R.layout.activity_open__records;
    }

    @Override
    public void initViews() {
        back = findView(R.id.back);
        title = findView(R.id.title);
        list = findView(R.id.list);
    }

    @Override
    public void initListener() {
        back.setOnClickListener(this);
    }

    @Override
    public void initData() {
        lockId = getIntent().getStringExtra("lockId");
        SharedPreferences userInformation = getSharedPreferences("UserInformation", MODE_PRIVATE);
        account = userInformation.getString("account", null);
        initlistviwe();
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

    private void initlistviwe() {
        LockSetController lockSetController = new LockSetController(this);
        List<MultipartBody.Part> parts = null;
        Map<String, RequestBody> params = new HashMap<>();
        params.put("account", RetrofitUtils.convertToRequestBody(account));
        params.put("lockId", RetrofitUtils.convertToRequestBody(lockId));
        Log.e("abcd: ", account + " " + lockId);
        final Vector<SetLockOpenLockRecordModule> v = new Vector<>();
        lockSetController.GetOpenLog(params, parts, new InterfaceManger.OnRequestListener() {
            @Override
            public void onSuccess(Object success) {
                Log.e("Objectsuccess: ", String.valueOf(success));
                JsonParser parser = new JsonParser();
                JsonArray jsonArray = parser.parse(String.valueOf(success)).getAsJsonArray();
                Gson gson = new Gson();
                for (JsonElement record : jsonArray) {
                    SetLockOpenLockRecordModule userBean = gson.fromJson(record, SetLockOpenLockRecordModule.class);
                    v.add(userBean);
                }
                v.add(new SetLockOpenLockRecordModule(getString(R.string.today)));
                v.add(new SetLockOpenLockRecordModule(getString(R.string.In_the_last_three_days)));
                v.add(new SetLockOpenLockRecordModule(getString(R.string.In_the_latest_week)));
                v.add(new SetLockOpenLockRecordModule(getString(R.string.other)));
                Collections.sort(v, comparator);
                final MyOpenRecordAdapter arrayAdapter = new MyOpenRecordAdapter(SetLockOpenLockRecord.this, v);
                list.setAdapter(arrayAdapter);
                list.setDivider(null);

                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        MyOpenRecordAdapter.Holder holder = (MyOpenRecordAdapter.Holder) view.getTag();
                        String s = holder.textView3.getText().toString();
                        if (s.equals(getString(R.string.today))) {
                            arrayAdapter.today = !arrayAdapter.today;
                        } else if (s.equals(getString(R.string.In_the_last_three_days))) {
                            arrayAdapter.threeDay = !arrayAdapter.threeDay;
                        } else if (s.equals(getString(R.string.In_the_latest_week))) {
                            arrayAdapter.weekend = !arrayAdapter.weekend;
                        } else if (s.equals(getString(R.string.other))) {
                            arrayAdapter.other = !arrayAdapter.other;
                        }
                        arrayAdapter.notifyDataSetChanged();
                    }
                });
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

    public long Time(long time) {
        String result = times(time) + " 00:00:00";
        return TransferDate(result);
    }

    public long TransferDate(String time) //throws ParseException
    {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = format.parse(time);
            return date.getTime();
        } catch (ParseException pe) {
            return 0;
        }
    }

    public String times(long time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd");
        String times = sdr.format(new Date(time));
        return times;
    }
}
