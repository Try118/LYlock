package com.LY.project.View;

import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

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
    private XRecyclerView recyclerView;
    private DemoAdapter adapter;

    @Override
    public int getLayoutId() {
        return R.layout.demo;
    }

    @Override
    public void initViews() {
        recyclerView = findView(R.id.rv);
        initR();
    }

    /**
     * 初始化控件
     */
    private void initR() {
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
             recyclerView.setPullRefreshEnabled(true);
             recyclerView.setLoadingMoreEnabled(false);
             recyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
             recyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
             recyclerView.setArrowImageView(R.drawable.iconfont_downgrey);
             recyclerView
                .getDefaultRefreshHeaderView()
                .setRefreshTimeVisible(true);
             View header = LayoutInflater.from(this).inflate(R.layout.recyclerview_header, (ViewGroup)findViewById(android.R.id.content),false);
             recyclerView.addHeaderView(header);

//             recyclerView.getDefaultFootView().setLoadingHint("自定义加载中提示");
             recyclerView.getDefaultFootView().setNoMoreHint("自定义加载完毕提示");
    }

    /**
     * 初始化点击事件
     */
    @Override
    public void initListener() {
        recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable(){
                    public void run() {
                        InternextData(2);
                    }
                }, 2000);
            }

            @Override
            public void onLoadMore() {

            }
        });
    }

    /**
     * 初始化数据
     */
    @Override
    public void initData() {
        InternextData(1);
    }

    /**
     * 获取数据
     * @param type    tyoe==1 初次获取数据  type == 2 刷新获取数据 type == 3 下拉更新数据
     */
    private void InternextData(final int type) {
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
                switch (type){
                    case 1:
                        lists = ReadAllLock.arrayDemoFromData(body);
                        //设置Adapter
                        adapter = new DemoAdapter(lists);
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
                        recyclerView.refresh();
                        break;
                    case 2:
                        lists.clear();
                        lists.addAll( ReadAllLock.arrayDemoFromData(body));
                        adapter.notifyDataSetChanged();
                        recyclerView.refreshComplete();
                        break;
                    default:
                        break;

                }

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
