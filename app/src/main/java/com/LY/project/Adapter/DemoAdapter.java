package com.LY.project.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.LY.project.Module.ReadAllLock;
import com.LY.project.R;

import java.util.List;

/**
 * Created by jie on 2018/8/17.
 */

public class DemoAdapter extends RecyclerView.Adapter<DemoAdapter.ViewHolder> {

    private List<ReadAllLock> list;

    private OnItemClickListener mOnItemClickListener;

    static public class ViewHolder extends RecyclerView.ViewHolder {
        TextView lockname;
        TextView lockaddress;
        public ViewHolder(View itemView) {
            super(itemView);
            lockname = itemView.findViewById(R.id.lock_name);
            lockaddress = itemView.findViewById(R.id.lock_address);
        }
    }

    public DemoAdapter(List<ReadAllLock> list) {
        this.list = list ;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_lock_view,null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        ReadAllLock lock = list.get(position);
        holder.lockname.setText(lock.getLockname());
        holder.lockaddress.setText(lock.getAddress());
        if( mOnItemClickListener!= null){
            holder. itemView.setOnClickListener( new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onClick(position);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mOnItemClickListener.onLongClick(position);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface OnItemClickListener{
        void onClick( int position);
        void onLongClick( int position);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener ){
        this. mOnItemClickListener=onItemClickListener;
    }
}
