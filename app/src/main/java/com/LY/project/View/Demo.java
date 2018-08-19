package com.LY.project.View;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.LY.basemodule.Essential.BaseTemplate.BaseActivity;
import com.LY.project.Adapter.DemoAdapter;
import com.LY.project.Adapter.MyLockAdapter;
import com.LY.project.Controller.LockController;
import com.LY.project.Manager.InterfaceManger;
import com.LY.project.Module.ReadAllLock;
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
 * Created by jie on 2018/8/17.
 */

public class Demo extends BaseActivity {

    List<ReadAllLock> lists = new ArrayList<>();
    private RecyclerView recyclerView;

    @Override
    public int getLayoutId() {
        return R.layout.demo;
    }

    @Override
    public void initViews() {
        recyclerView = findView(R.id.rv);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        InternextData();
    }

    private void InternextData() {
        LockController lockController = new LockController(Demo.this);
        List<String> photos = new ArrayList<>();
        List<MultipartBody.Part> parts = null;
        Map<String, RequestBody> params = new HashMap<>();
        params.put("phone", RetrofitUtils.convertToRequestBody("17875305749"));
        params.put("password", RetrofitUtils.convertToRequestBody("1234567"));
        lockController.ReadAllLock(params, parts, new InterfaceManger.OnRequestListener() {

            @Override
            public void onSuccess(Object success) {
                String body = success.toString();
                Log.e("onSuccess:", body);
                lists = ReadAllLock.arrayDemoFromData(body);
                 /*
                * 默认为垂直向下的列表格式
                */
                LinearLayoutManager layoutmanager = new LinearLayoutManager(getApplication());

                /**
                 * 设置为水平布局格式
                 */
//                layoutmanager.setOrientation(LinearLayoutManager.HORIZONTAL);

                /*
                * 瀑布格式
                * 第一个参数表示布局的列数
                * 第二个参数表示布局的方向，这里我们传入StaggeredGridLayoutManager.VERTICAL，表示布局纵向排列
                */
//                StaggeredGridLayoutManager layoutmanager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

                //设置RecyclerView 布局
                recyclerView.setLayoutManager(layoutmanager);
                //设置Adapter
                DemoAdapter adapter = new DemoAdapter(lists);
                adapter.setOnItemClickListener(new DemoAdapter.OnItemClickListener() {

                    @Override
                    public void onLongClick(int position) {
                        Toast.makeText(Demo.this, "onLongClick事件       您点击了第：" + position + "个Item", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onClick(int position) {
                        Toast.makeText(Demo.this, "onClick事件       您点击了第：" + position + "个Item", Toast.LENGTH_SHORT).show();
                    }
                });
                recyclerView.setAdapter(adapter);

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

    }
}
