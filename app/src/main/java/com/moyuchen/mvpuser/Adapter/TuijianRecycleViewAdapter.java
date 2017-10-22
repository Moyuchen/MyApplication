package com.moyuchen.mvpuser.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.moyuchen.mvpuser.Bean.jdmainData;
import com.moyuchen.mvpuser.R;

import java.util.List;

/**
 * User: 张亚博
 * Date: 2017-10-10 19:56
 * Description：
 */
public class TuijianRecycleViewAdapter extends RecyclerView.Adapter<TuijianRecycleViewAdapter.TuiJianViewHolder> {
    private List<jdmainData.TuijianBean.ListBean> tuijianglist;
    private Context context;

    public TuijianRecycleViewAdapter(List<jdmainData.TuijianBean.ListBean> tuijianglist, Context context) {
        this.tuijianglist = tuijianglist;
        this.context = context;
    }

    @Override
    public TuiJianViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.tuijian_item, parent, false);

        return new TuiJianViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(TuiJianViewHolder holder, int position) {
        jdmainData.TuijianBean.ListBean listBean = tuijianglist.get(position);
        holder.tuijian_item_tv_title.setMaxLines(2);
        holder.tuijian_item_tv_title.setEllipsize(TextUtils.TruncateAt.END);
        holder.tuijian_item_tv_title.setText(listBean.getTitle());
        holder.tuijian_item_tv_price.setText("￥"+listBean.getPrice());
        String images = listBean.getImages();
        String[] split = images.split("\\|");
        String s = split[0];
        Glide.with(context).load(s).into(holder.tuijiang_item_imageview);
    }

    @Override
    public int getItemCount() {
        return tuijianglist.size();
    }

    public class TuiJianViewHolder extends RecyclerView.ViewHolder{
        private ImageView tuijiang_item_imageview;
        private TextView tuijian_item_tv_title;
        private TextView tuijian_item_tv_price;
        public TuiJianViewHolder(View itemView) {
            super(itemView);
             tuijiang_item_imageview = itemView.findViewById(R.id.tuijian_item_imageview);
            tuijian_item_tv_title=itemView.findViewById(R.id.tuijian_item_tv_title);
            tuijian_item_tv_price=itemView.findViewById(R.id.tuijian_item_tv_price);

        }
    }

}
