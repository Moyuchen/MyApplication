package com.moyuchen.mvpuser.Adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.moyuchen.mvpuser.Bean.jdmainData;
import com.moyuchen.mvpuser.R;
import com.moyuchen.mvpuser.Utils.CircleRoundImageView;

import java.util.List;

/**
 * User: 张亚博
 * Date: 2017-10-07 20:30
 * Description：
 */
public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ViewHolder> {

    private  Context context;
    private List<com.moyuchen.mvpuser.Bean.jdmainData.MiaoshaBean.ListBeanX> list;

    public RecycleAdapter( Context context,List<jdmainData.MiaoshaBean.ListBeanX> list) {
        this.context=context;
        this.list=list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.jdmainrcvitem, null);
        ViewHolder holder=new ViewHolder(inflate);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        jdmainData.MiaoshaBean.ListBeanX listBeanX = list.get(position);
        double price = listBeanX.getPrice();
        double bargainPrice = listBeanX.getBargainPrice();
        String images = listBeanX.getImages();

        String[] split = images.split("\\|");
        String s = split[0];
        holder.jdmainrcvitem_tv1.setText("￥"+price);
        holder.jdmainrcvitem_tv2.setText("￥"+bargainPrice);
        holder.jdmainrcvitem_tv2.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);//给TextView设置删除线
        Glide.with(context).load(split[0]).into(holder.jdmainrcvitem_criv);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView jdmainrcvitem_tv1;
        private final CircleRoundImageView jdmainrcvitem_criv;
        private final TextView jdmainrcvitem_tv2;


        public ViewHolder(View itemView) {
            super(itemView);
            jdmainrcvitem_tv1 = itemView.findViewById(R.id.jdmainrcvitem_tv1);
            jdmainrcvitem_tv2 = itemView.findViewById(R.id.jdmainrcvitem_tv2);
            jdmainrcvitem_criv = itemView.findViewById(R.id.jdmainrcvitem_CRIV);
        }

    }
}
