package com.moyuchen.mvpuser.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.moyuchen.mvpuser.Bean.GetFeiLeiInfo;
import com.moyuchen.mvpuser.R;

import java.util.List;

/**
 * User: 张亚博
 * Date: 2017-10-10 21:10
 * Description：
 */
public class SecondRecycleViewAdapter extends RecyclerView.Adapter<SecondRecycleViewAdapter.SecondRecycleViewHolder> implements View.OnClickListener {
    private List<GetFeiLeiInfo.DataBean> data;
    private Context context;
    private onRecItemListener onRecItemListener;

    public void setOnRecItemListener(onRecItemListener onRecItemListener) {
        this.onRecItemListener = onRecItemListener;
    }

    public SecondRecycleViewAdapter(List<GetFeiLeiInfo.DataBean> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public SecondRecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View inflate = LayoutInflater.from(context).inflate(R.layout.secondrecycleview_item, parent, false);

        SecondRecycleViewHolder viewHolder = new SecondRecycleViewHolder(inflate);

        inflate.setOnClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SecondRecycleViewHolder holder, int position) {

        String name = data.get(position).getName();

        holder.SecondRecycleView_item_tv.setText(name);
       holder.itemView.setTag(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onClick(View view) {
        onRecItemListener.RecItemListener(view, (GetFeiLeiInfo.DataBean) view.getTag());
    }

    public class SecondRecycleViewHolder extends RecyclerView.ViewHolder{
        public TextView SecondRecycleView_item_tv;
        public SecondRecycleViewHolder(View itemView) {
            super(itemView);
           SecondRecycleView_item_tv = itemView.findViewById(R.id.SecondRecycleView_item_tv);
        }
    }

    public interface onRecItemListener{
        void RecItemListener(View view,GetFeiLeiInfo.DataBean databean);
    }
}
