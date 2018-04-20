package com.LY.project.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.LY.project.Module.OpenRecord;
import com.LY.project.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jie on 2018/4/11.
 */

public class OpenRecordAdapter extends BaseAdapter {
    private List<OpenRecord> lists = new ArrayList<>();
    private Context context;

    public OpenRecordAdapter(List<OpenRecord> lists, Context context) {
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
        OpenRecordAdapter.ViewHolder holder = null;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.view_set_open_record,null,false);
            holder = new OpenRecordAdapter.ViewHolder();
            holder.tv_name = (TextView) convertView.findViewById(R.id.device_name);
            holder.tv_address=(TextView)convertView.findViewById(R.id.device_addr);
            convertView.setTag(holder);   //将Holder存储到convertView中
        }else{
            holder = (OpenRecordAdapter.ViewHolder) convertView.getTag();
        }
        holder.tv_name.setText(lists.get(position).getName());
        holder.tv_address.setText(lists.get(position).getTime());
        return convertView;
    }
    static class ViewHolder{
        TextView tv_address;//标题
        TextView tv_name;//时间
    }
}
