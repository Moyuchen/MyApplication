package com.moyuchen.mvpuser.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.moyuchen.mvpuser.Bean.GetChildFenLeiInfo;
import com.moyuchen.mvpuser.ChildFenLeiProductListActivity;
import com.moyuchen.mvpuser.R;
import com.moyuchen.mvpuser.Utils.CommonUtils;

import java.util.List;

/**
 * User: 张亚博
 * Date: 2017-10-11 14:13
 * Description：
 */
public class FragSecAllRecViewAdapter extends RecyclerView.Adapter<FragSecAllRecViewAdapter.FragSecAllViewHolder> {

    private List<GetChildFenLeiInfo.DataBean> data;
    private Context context;

    public FragSecAllRecViewAdapter(List<GetChildFenLeiInfo.DataBean> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public FragSecAllViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.fragsecallrecv_item, parent, false);
        return new FragSecAllViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(FragSecAllViewHolder holder, int position) {
        GetChildFenLeiInfo.DataBean dataBean = data.get(position);
        holder.tv.setText(dataBean.getName());
        List<GetChildFenLeiInfo.DataBean.ListBean> list = dataBean.getList();
        final GetChildFenLeiInfo.DataBean.ListBean listBean = list.get(position);
        int moddle = CommonUtils.GetModdle(list.size());
        holder.rec.setLayoutManager(new GridLayoutManager(context,4));
        FragRecRecAdapter fragRecRecAdapter = new FragRecRecAdapter(list, context);
        fragRecRecAdapter.setOnClickListener(new FragRecRecAdapter.onClickItemListener() {
            @Override
            public void clickitemlistener(View view) {
                Intent intent=new Intent(context, ChildFenLeiProductListActivity.class);
                intent.putExtra("pscid",listBean.getPscid()+"");
                context.startActivity(intent);

            }
        });
        holder.rec.setAdapter(fragRecRecAdapter);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class FragSecAllViewHolder extends RecyclerView.ViewHolder{

        private final TextView tv;
        private final RecyclerView rec;

        public FragSecAllViewHolder(View itemView) {
            super(itemView);
           tv = itemView.findViewById(R.id.FragSecAllRecViewItem_tv);
           rec= itemView.findViewById(R.id.FragSecAllRecViewItem_RecView);

        }
    }
}
