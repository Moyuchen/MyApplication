package com.moyuchen.mvpuser.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.moyuchen.mvpuser.Bean.GetFeiLeiInfo;
import com.moyuchen.mvpuser.R;

import java.util.List;

/**
 * User: 张亚博
 * Date: 2017-10-09 16:49
 * Description：
 */
public class VP_Rcvadapter extends RecyclerView.Adapter<VP_Rcvadapter.ViewHolder> {
    private List<GetFeiLeiInfo.DataBean> dataBeen;
    private Context context;
    public VP_Rcvadapter(List<GetFeiLeiInfo.DataBean> dataBeen, Context context) {
        this.dataBeen=dataBeen;
        this.context=context;
    }

    @Override
    public VP_Rcvadapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.rcv_vp_fragment1_rcv_item, null);
        ViewHolder viewHolder=new ViewHolder(inflate);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        GetFeiLeiInfo.DataBean dataBean = dataBeen.get(position);
        Glide.with(context).load(dataBean.getIcon()).into(holder.rcv_vp_fragment1_rcv_item_iv);
        holder.rcv_vp_fragment1_rcv_item_tv.setText(dataBean.getName());
    }

    @Override
    public int getItemCount() {
        return dataBeen.size();
    }
   public class ViewHolder extends RecyclerView.ViewHolder{

       private final ImageView rcv_vp_fragment1_rcv_item_iv;
       private final TextView rcv_vp_fragment1_rcv_item_tv;

       public ViewHolder(View itemView) {
           super(itemView);
           rcv_vp_fragment1_rcv_item_iv = itemView.findViewById(R.id.RCV_VP_Fragment1_rcv_item_iv);
          rcv_vp_fragment1_rcv_item_tv = itemView.findViewById(R.id.RCV_VP_Fragment1_rcv_item_tv);

       }
   }
}
