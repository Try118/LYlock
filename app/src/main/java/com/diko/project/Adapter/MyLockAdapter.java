package com.diko.project.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.diko.project.Module.ReadAllLock;
import com.diko.project.R;
import com.diko.project.View.Select_look;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created by YX_PC on 2018/4/11.
 */

public class MyLockAdapter extends BaseAdapter {

    private List<ReadAllLock> lists;
    private Context context;
    private ViewHolder holder;

    public MyLockAdapter(Context context, List<ReadAllLock> lists) {
        this.context = context;
        this.lists = lists;
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int position) {
        return lists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int p=position;
        holder = new ViewHolder();
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.my_lock_view, null);
            holder.lockAddress = (TextView) convertView.findViewById(R.id.lock_address);
            holder.lockName = (TextView) convertView.findViewById(R.id.lock_name);
            holder.linearLayout = (LinearLayout) convertView.findViewById(R.id.linear);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.lockAddress.setText(lists.get(position).getAddress());
        holder.lockName.setText(lists.get(position).getLockname());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, Select_look.class);
                i.putExtra("lock_name",lists.get(p).getLockname());
                i.putExtra("starttime",lists.get(p).getStarttime());
                i.putExtra("endtime",lists.get(p).getEndtime());
                i.putExtra("lockKey",lists.get(p).getKey());
                i.putExtra("address",lists.get(p).getAddress());
                i.putExtra("power",lists.get(p).getPower());
                i.putExtra("lockId",lists.get(p).getId());
                i.putExtra("age","18");
                Log.e("onClick:----- ",lists.get(p).getAddress()+lists.get(p).getKey()+"  "+lists.get(p).getEndtime()+"  "+lists.get(p).getStarttime()+"  "+lists.get(p).getLockname());
                context.startActivity(i);
            }
        });
        return convertView;
    }

    class ViewHolder {
        TextView lockAddress;
        TextView lockName;
        LinearLayout linearLayout;
    }

}
