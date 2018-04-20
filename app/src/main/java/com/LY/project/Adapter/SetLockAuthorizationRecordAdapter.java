package com.LY.project.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.LY.project.Module.AuthorizationRecords;
import com.LY.project.R;
import com.LY.project.Utils.StringToDate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jie on 2018/4/11.
 */

public class SetLockAuthorizationRecordAdapter extends BaseAdapter {
    private List<AuthorizationRecords> lists = new ArrayList<>();
    private Context context;

    public SetLockAuthorizationRecordAdapter(List<AuthorizationRecords> lists, Context context) {
        this.lists = lists;
        this.context = context;
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
        SetLockAuthorizationRecordAdapter.ViewHolder holder = null;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.view_authorization_record,null,false);
            holder = new SetLockAuthorizationRecordAdapter.ViewHolder();
            holder.tv_customer = (TextView) convertView.findViewById(R.id.device_name);
            holder.tv_endtime=(TextView)convertView.findViewById(R.id.device_addr);
            holder.power=(TextView)convertView.findViewById(R.id.power);
            convertView.setTag(holder);
        }else{
            holder = (SetLockAuthorizationRecordAdapter.ViewHolder) convertView.getTag();
        }
        holder.tv_endtime.setText("有效时间至：" + StringToDate.times(String.valueOf(Long.valueOf(lists.get(position).getEndtime())*1000)));
        holder.tv_customer.setText(lists.get(position).getCustomer()+lists.get(position).getUsername());
        if (lists.get(position).getPower().contains("2")){
            holder.power.setText("高级权限");
        }else{
            holder.power.setText("普通权限");
        }
        return convertView;
    }
    static class ViewHolder{
        TextView tv_endtime;//结束时间
        TextView tv_customer;//被授权人信息
        TextView power;//被授权者权限
    }
}
