package com.moyuchen.mvpuser.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.moyuchen.mvpuser.Bean.getordersInfo;
import com.moyuchen.mvpuser.R;

import java.util.List;

/**
 * User: 张亚博
 * Date: 2017-10-22 15:42
 * Description：
 */
public class myOrderAdapter extends RecyclerView.Adapter<myOrderAdapter.MyOrderViewHolder> {
    private List<getordersInfo.DataBean> data;
    private Context context;

    public myOrderAdapter(List<getordersInfo.DataBean> data, Context context) {
        this.data = data;
        this.context = context;
    }
    public void Repalce(List<getordersInfo.DataBean> data){
        this.data.addAll(data);
        notifyDataSetChanged();

    }

    @Override
    public MyOrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.myorder, parent, false);
        inflate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onMyOrderClickListener.onMyOrderItem();
            }
        });
        return new MyOrderViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(MyOrderViewHolder holder, int position) {
        getordersInfo.DataBean dataBean = data.get(position);
        holder.price.setText("￥："+dataBean.getPrice());
        holder.bianhao.setText(dataBean.getOrderid()+"");
        int status = dataBean.getStatus();
        if (status==0) {
            holder.payStatic.setText("未支付");
        }else if (status==1){
            holder.payStatic.setText("已支付");
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyOrderViewHolder extends RecyclerView.ViewHolder{
        private TextView bianhao;
        private TextView price;
        private TextView payStatic;
        public MyOrderViewHolder(View itemView) {
            super(itemView);
            price=itemView.findViewById(R.id.price);
            bianhao=itemView.findViewById(R.id.bianhao);
            payStatic = itemView.findViewById(R.id.paystatic);
        }
    }
    private onMyOrderClickListener onMyOrderClickListener;

    public void setOnMyOrderClickListener(myOrderAdapter.onMyOrderClickListener onMyOrderClickListener) {
        this.onMyOrderClickListener = onMyOrderClickListener;
    }

    public interface onMyOrderClickListener{
        void onMyOrderItem();
    }
}
