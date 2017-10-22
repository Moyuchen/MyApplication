package com.moyuchen.mvpuser.Adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.moyuchen.mvpuser.Bean.SearchProductInfo;
import com.moyuchen.mvpuser.R;
import com.moyuchen.mvpuser.Utils.CommonUtils;

import java.util.List;

/**
 * User: 张亚博
 * Date: 2017-10-14 11:15
 * Description：
 */
public class ProSearRecViewAdapter extends RecyclerView.Adapter <RecyclerView.ViewHolder>{
    private static final int ITEM_TYPE_H=0;
    private static final int ITEM_TYPE_V=1;
    private Context context;
    private  List<SearchProductInfo.DataBean> data;
    private static onitemlistener onitemlistener;

    public ProSearRecViewAdapter(Context context, List<SearchProductInfo.DataBean> data) {
        this.context = context;
        this.data=data;
    }

    public void addRefreshitem(List<SearchProductInfo.DataBean> data){
            this.data.clear();
            this.data.addAll(data);
            notifyDataSetChanged();
    }

    public void addmoreItem(List<SearchProductInfo.DataBean> data){
        this.data.addAll(data);
        notifyDataSetChanged();
    }


    public void Replace(List<SearchProductInfo.DataBean> data){
        this.data.clear();
        this.data=data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType==ITEM_TYPE_H) {
            View horview = LayoutInflater.from(context).inflate(R.layout.myhorrecview, parent, false);
            final MyHorRecView myHorRecView = new MyHorRecView(horview);
            horview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int tag = (int) myHorRecView.itemView.getTag();
                    com.moyuchen.mvpuser.Adapter.ProSearRecViewAdapter.onitemlistener.itemlistener(tag+"");
                }
            });
            return myHorRecView;
        }else if (viewType==ITEM_TYPE_V){
            View verview = LayoutInflater.from(context).inflate(R.layout.myverrecview, parent, false);
            final MyVerRecView myVerRecView = new MyVerRecView(verview);
            verview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int tag = (int) myVerRecView.itemView.getTag();
                    com.moyuchen.mvpuser.Adapter.ProSearRecViewAdapter.onitemlistener.itemlistener(tag+"");
                }
            });
            return myVerRecView;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        SearchProductInfo.DataBean dataBean = data.get(position);
        if (dataBean!=null) {
            int itemViewType = getItemViewType(position);
            String title=dataBean.getTitle();
            String s = CommonUtils.FenGeString(dataBean.getImages(), 0);
            double bargainPrice = dataBean.getBargainPrice();
           holder.itemView.setTag(dataBean.getPid());
            if (itemViewType==ITEM_TYPE_H) {
                ((MyHorRecView)holder).horTextview.setMaxLines(2);
                ((MyHorRecView)holder).horTextview.setEllipsize(TextUtils.TruncateAt.END);
                ((MyHorRecView)holder).horTextview.setText(title);
                ((MyHorRecView)holder).horproductprice.setTextColor(ContextCompat.getColor(context,R.color.red));
                ((MyHorRecView)holder).horproductprice.setText("￥"+bargainPrice);
                Glide.with(context).load(s).into(((MyHorRecView)holder).horimageview);

            }
            if (itemViewType==ITEM_TYPE_V) {
                ((MyVerRecView)holder).verTextview.setMaxLines(2);
                ((MyVerRecView)holder).verTextview.setEllipsize(TextUtils.TruncateAt.END);
                ((MyVerRecView)holder).verTextview.setText(title);
                ((MyVerRecView)holder).verproductprice.setTextColor(ContextCompat.getColor(context,R.color.red));
                ((MyVerRecView)holder).verproductprice.setText("￥"+bargainPrice);
                Glide.with(context).load(s).into( ((MyVerRecView)holder).verimageview);
            }
        }


    }

    @Override
    public int getItemViewType(int position) {
        if (position%2==0) {
            return ITEM_TYPE_H;
        }else {
         return ITEM_TYPE_V;
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyHorRecView extends RecyclerView.ViewHolder{

        private final ImageView horimageview;
        private final TextView horTextview;
        private final TextView horproductprice;


        public MyHorRecView(View itemView) {
            super(itemView);
           horimageview= itemView.findViewById(R.id.HorImageView);
            horTextview=itemView.findViewById(R.id.HorTextView);
            horproductprice=itemView.findViewById(R.id.productprice);

        }
    }
    public class MyVerRecView extends RecyclerView.ViewHolder{

        private final ImageView verimageview;
        private final TextView verTextview;
        private final TextView verproductprice;

        public MyVerRecView(View itemView) {
            super(itemView);
           verimageview= itemView.findViewById(R.id.VerImageView);
           verTextview=itemView.findViewById(R.id.VerTextView);
            verproductprice=  itemView.findViewById(R.id.productpriceh);
        }
    }


    public void setOnitemlistener(ProSearRecViewAdapter.onitemlistener onitemlistener) {
        this.onitemlistener = onitemlistener;
    }

    public interface onitemlistener{
        void itemlistener(String pid);
    }
}
