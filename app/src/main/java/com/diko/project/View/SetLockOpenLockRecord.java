package com.diko.project.View;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.diko.basemodule.Essential.BaseTemplate.BaseActivity;
import com.diko.project.Adapter.OpenRecordAdapter;
import com.diko.project.Module.OpenRecord;
import com.diko.project.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jie on 2018/4/11.
 */

public class SetLockOpenLockRecord extends BaseActivity {
    private TextView back;//f返回控件
    private LinearLayout title;//标题栏
    private ListView list;//listview展示列表
    private OpenRecordAdapter adapter;//listview的适配器
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
        initlistviwe();
    }

    private void initlistviwe() {
        List<OpenRecord> lists = new ArrayList<OpenRecord>();
        OpenRecord dailyCheck = new OpenRecord("XNC1344", "20天", "项目一"," ");
        lists.add(dailyCheck);
        adapter=new OpenRecordAdapter(lists,getApplication());
        list.setAdapter(adapter);
    }

    @Override
    public void processClick(View v) {
        switch(v.getId()){
            case R.id.back:
                break;
            default:
                break;
        }
    }
}
