package com.moyuchen.mvpuser.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.moyuchen.mvpuser.API.API;
import com.moyuchen.mvpuser.Bean.GetCartsInfo;
import com.moyuchen.mvpuser.Bean.UpdateCartsInfo;
import com.moyuchen.mvpuser.LoginView.UpdataCartsView;
import com.moyuchen.mvpuser.Owner.AmountView;
import com.moyuchen.mvpuser.Presenter.UpdateCartsPresenter;
import com.moyuchen.mvpuser.R;
import com.moyuchen.mvpuser.Utils.CommonUtils;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * User: 张亚博
 * Date: 2017-10-18 20:14
 * Description：
 */
public class gwcRecRecAdapter extends RecyclerView.Adapter<gwcRecRecAdapter.gwcRecRecViewHolder> {
    private Context context;
    private  List<GetCartsInfo.DataBean.ListBean>  data;
    private int selectednum;
    private int amount;

    public gwcRecRecAdapter(Context context, List<GetCartsInfo.DataBean.ListBean> data) {

        this.context = context;
        this.data = data;
    }

    @Override
    public gwcRecRecViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.gwcrecrecviewitem, parent, false);
        return new gwcRecRecViewHolder(inflate);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onBindViewHolder(final gwcRecRecViewHolder holder, int position) {
        final GetCartsInfo.DataBean.ListBean listBean = data.get(position);
        amount=listBean.getNum();

        double bargainPrice = listBean.getBargainPrice();
        DecimalFormat df = new DecimalFormat("#.00");
        String format = df.format(bargainPrice);
        String[] split = format.split("\\.");
        String images = listBean.getImages();
        String[] imageurls = images.split("\\|");
        final int[] selected = {listBean.getSelected()};
        System.out.println("selected1:"+ selected[0]);
        holder.addsub.setCon(listBean.getNum()+"");
        if (selected[0] ==0) {
            holder.gwcproductyuanhuan.setImageResource(R.drawable.duihaoselected);
        }else {
            holder.gwcproductyuanhuan.setImageResource(R.drawable.yuanhuan);
        }
        holder.gwcproductyuanhuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selected[0] ==0) {
                    updatecarts(holder, listBean, 1,amount);
                    updataShopCartsSecond.onUpDataShopCartsSecond();
                    listBean.setSelected(1);
                    selected[0] =1;
                }else {
                    updatecarts(holder, listBean,0,amount);
                    updataShopCartsSecond.onUpDataShopCartsSecond();
                    listBean.setSelected(0);
                    selected[0] =0;
                }
                System.out.println("selected:"+ selected[0]);
                all();
            }
        });
        holder.gwcproducttitle.setText(listBean.getTitle());
        holder.yuan.setText(split[0]);
        holder.other.setText("."+split[1]);
        Glide.with(context).load(imageurls[0]).into(holder.productimageview);
        holder.addsub.setOnAmountChangeListener(new AmountView.OnAmountChangeListener() {
            @Override
            public void onAmountChange(View view, int amount) {
                updatecarts(holder, listBean, selected[0],amount);
                System.out.println("amount:"+amount);
                System.out.println("selected:"+ selected[0]);
                updataShopCartsSecond.onUpDataShopCartsSecond();
                holder.addsub.setCon(amount+"");
            }
        });

    }

    private void updatecarts(final gwcRecRecViewHolder holder, GetCartsInfo.DataBean.ListBean listBean, final int selected,int amount) {

        UpdateCartsPresenter updateCartsPresenter=new UpdateCartsPresenter(new UpdataCartsView() {
            @Override
            public void OnResponse(Call call, Response response) {
                if (response.isSuccessful()){
                    try {
                        String string = response.body().string();
                        Gson gson=new Gson();
                        UpdateCartsInfo updateCartsInfo = gson.fromJson(string, UpdateCartsInfo.class);
                        final String code = updateCartsInfo.getCode();
                        String msg = updateCartsInfo.getMsg();
                        System.out.println("msg:"+msg);
                        ((Activity)context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if ("0".equals(code)) {
                                    if (selected==0) {
                                        holder.gwcproductyuanhuan.setImageResource(R.drawable.duihaoselected);
                                    }else {
                                        holder.gwcproductyuanhuan.setImageResource(R.drawable.yuanhuan);
                                    }
                                }
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void OnFailure(Call call, Exception e) {

            }
        });
        updateCartsPresenter.UpdataCarts(API.UPDATECARTS, CommonUtils.GetString(context,"uid"),listBean.getPid()+"",listBean.getSellerid()+"",amount+"",selected+"");
    }

    private void all() {
        selectednum=0;
        for (GetCartsInfo.DataBean.ListBean listBean : data) {
            int selected = listBean.getSelected();
            if (selected==0) {
                selectednum++;
            }
        }
    }
    public class gwcRecRecViewHolder extends RecyclerView.ViewHolder{
        private ImageView productimageview;
        private ImageView gwcproductyuanhuan;
        private TextView gwcproducttitle;
        private AmountView addsub;
        private TextView yuan;
        private TextView other;
        public gwcRecRecViewHolder(View itemView) {
            super(itemView);
            productimageview= itemView.findViewById(R.id.gwcProductImageView);
            gwcproductyuanhuan = itemView.findViewById(R.id.gwcproductyuanhuan);
            gwcproducttitle = itemView.findViewById(R.id.gwcproducttitle);
            addsub = itemView.findViewById(R.id.addsub);
            addsub.setGoods_storage(50);
            yuan = itemView.findViewById(R.id.yuan);
           other= itemView.findViewById(R.id.other);
        }
    }

    public updataShopCartsSecond updataShopCartsSecond;

    public void setUpdataShopCartsSecond(gwcRecRecAdapter.updataShopCartsSecond updataShopCartsSecond) {
        this.updataShopCartsSecond = updataShopCartsSecond;
    }

    public interface updataShopCartsSecond{
        void onUpDataShopCartsSecond();
    }



}
