package com.moyuchen.mvpuser.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.moyuchen.mvpuser.API.API;
import com.moyuchen.mvpuser.Bean.GetCartsInfo;
import com.moyuchen.mvpuser.Bean.UpdateCartsInfo;
import com.moyuchen.mvpuser.LoginView.UpdataCartsView;
import com.moyuchen.mvpuser.Presenter.UpdateCartsPresenter;
import com.moyuchen.mvpuser.R;
import com.moyuchen.mvpuser.Utils.CommonUtils;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * User: 张亚博
 * Date: 2017-10-18 16:13
 * Description：
 */
public class gwcRecycleAdapter extends RecyclerView.Adapter<gwcRecycleAdapter.gwcRecViewHolder> implements gwcRecRecAdapter.updataShopCartsSecond {
    private Context context;
    private List<GetCartsInfo.DataBean> data;
    private boolean shangjiaSelected;
    public gwcRecRecAdapter.updataShopCartsSecond updataShopCartsSecond;
    private List<Integer > listja;


    public gwcRecycleAdapter(Context context, List<GetCartsInfo.DataBean> data, List<Integer> listja) {
        this.context = context;
        this.data = data;
        this.listja=listja;
    }


    @Override
    public gwcRecViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.gwcrecitem, parent, false);
        return new gwcRecViewHolder(inflate);
    }
    @Override
    public void onBindViewHolder(final gwcRecViewHolder holder, int position) {
        int integer = listja.get(position);
        GetCartsInfo.DataBean dataBean = data.get(position);
        String sellerName = dataBean.getSellerName();
        final List<GetCartsInfo.DataBean.ListBean> list = dataBean.getList();

        if (integer==0) {
            holder.gwcRecImageView.setImageResource(R.drawable.duihaoselected);
        }else {
            holder.gwcRecImageView.setImageResource(R.drawable.yuanhuan);
        }


        holder.gwcRecrecview.setLayoutManager(new LinearLayoutManager(context));
        final gwcRecRecAdapter adapter=new gwcRecRecAdapter(context,list);
        holder.gwcRecrecview.setAdapter(adapter);
        adapter.setUpdataShopCartsSecond(this);
        holder.gwcRecShangPu.setText(sellerName);

        holder.gwcRecImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (shangjiaSelected) {
                    holder.gwcRecImageView.setImageResource(R.drawable.yuanhuan);
                    shangjiaSelected=false;
                    for (GetCartsInfo.DataBean.ListBean listBean : list) {
                        listBean.setSelected(1);
                        updatecarts(holder,listBean,1,listBean.getNum());
                    }
                    updateShopCarts.onUpdateShopCarts();
                    adapter.notifyDataSetChanged();
                }else {
                    holder.gwcRecImageView.setImageResource(R.drawable.duihaoselected);
                    shangjiaSelected=true;
                    for (GetCartsInfo.DataBean.ListBean listBean : list) {
                        listBean.setSelected(0);
                        updatecarts(holder,listBean,0,listBean.getNum());
                    }
                    updateShopCarts.onUpdateShopCarts();
                    adapter.notifyDataSetChanged();
                }
            }
        });

//点击全选，选中商家
        int num=0;
        for (GetCartsInfo.DataBean.ListBean listBean : list) {
            int selected = listBean.getSelected();
            if (selected==1) {
              num++;
            }
        }
        if (num>0) {
            holder.gwcRecImageView.setImageResource(R.drawable.yuanhuan);
        }else {
            holder.gwcRecImageView.setImageResource(R.drawable.duihaoselected);
        }

    }



    @Override
    public int getItemCount() {
        return data.size();
    }

    /**
     * 重新请求购物车
     */
    @Override
    public void onUpDataShopCartsSecond() {
        updateShopCarts.onUpdateShopCarts();
    }


    public class gwcRecViewHolder extends RecyclerView.ViewHolder{
        private ImageView gwcRecImageView;
        private TextView gwcRecShangPu;
        private RecyclerView gwcRecrecview;


        public gwcRecViewHolder(View itemView) {
            super(itemView);
            gwcRecImageView = itemView.findViewById(R.id.gwcRecImageView);
            gwcRecShangPu = itemView.findViewById(R.id.gwcRecShangPu);
           gwcRecrecview = itemView.findViewById(R.id.gwcRecrecview);

        }
    }

    private void updatecarts(final gwcRecViewHolder  holder, GetCartsInfo.DataBean.ListBean listBean, final int selected, int amount) {

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
                                        holder.gwcRecImageView.setImageResource(R.drawable.duihaoselected);
                                    }else {
                                        holder.gwcRecImageView.setImageResource(R.drawable.yuanhuan);
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

    private updateShopCarts updateShopCarts;

    public void setUpdateShopCarts(gwcRecycleAdapter.updateShopCarts updateShopCarts) {
        this.updateShopCarts = updateShopCarts;
    }

    public interface updateShopCarts{
        void onUpdateShopCarts();
    }


}
