package com.diko.project.Adapter;

import android.content.Context;
import android.content.Intent;
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

    private List<ReadAllLock> lists = new ArrayList<>();
    private Context context;
    private ViewHolder holder;

    public MyLockAdapter(Context context,List<ReadAllLock> lists){
        this.context=context;
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
        holder = new ViewHolder();
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.my_lock_view, null);
            holder.lockAddress=(TextView)convertView.findViewById(R.id.lock_address);
            holder.lockName = (TextView) convertView.findViewById(R.id.lock_name);
            holder.linearLayout=(LinearLayout)convertView.findViewById(R.id.linear) ;
            convertView.setTag(holder);
        }else {
            holder=(ViewHolder)convertView.getTag();
        }
        holder.lockAddress.setText("地址地址地址");
//        holder.lockAddress.setText(lockName.get(position));
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context, Select_look.class);
                context.startActivity(i);
            }
        });
        return convertView;
    }

    class ViewHolder{
        TextView lockAddress;
        TextView lockName;
        LinearLayout linearLayout;
    }

}
