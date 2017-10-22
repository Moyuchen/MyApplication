package com.moyuchen.mvpuser;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.moyuchen.mvpuser.API.API;
import com.moyuchen.mvpuser.Adapter.myOrderAdapter;
import com.moyuchen.mvpuser.Bean.getordersInfo;
import com.moyuchen.mvpuser.LoginView.GetOrdersView;
import com.moyuchen.mvpuser.Presenter.GetOrdersPresenter;
import com.moyuchen.mvpuser.Utils.CommonUtils;
import com.moyuchen.mvpuser.pay.PayDemoActivity;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class MyOrderActivity extends AppCompatActivity implements GetOrdersView {

    private RecyclerView mRecycleView;
    private myOrderAdapter adapter;
    private LinearLayoutManager manager;
    private int lastVisibleItem;
    private GetOrdersPresenter presenter;
    private int page=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        getSupportActionBar().hide();
        initView();
        initData();
    }

    private void initData() {
        presenter = new GetOrdersPresenter(this);
        presenter.getordermodul(API.GETORDERS, CommonUtils.GetString(this,"uid"),page);
    }

    private void initView() {
        mRecycleView = (RecyclerView) findViewById(R.id.MyOrderRecView);
        manager = new LinearLayoutManager(this);
        mRecycleView.setLayoutManager(manager);
        final String uid = CommonUtils.GetString(this, "uid");
        mRecycleView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState ==RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 ==adapter.getItemCount()) {
                    page++;
                    presenter.getordermodul(API.GETORDERS,uid,page);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = manager.findLastVisibleItemPosition();
            }
        });
    }

    @Override
    public void OnResponse(Call call, final Response response) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (response.isSuccessful()) {
                    try {
                        String string = response.body().string();
                        Gson gson=new Gson();
                        getordersInfo getordersInfo = gson.fromJson(string, getordersInfo.class);
                        List<com.moyuchen.mvpuser.Bean.getordersInfo.DataBean> data = getordersInfo.getData();
                        String msg = getordersInfo.getMsg();
                        if (data.size()==0) {
                            CommonUtils.SHOWTOAST(MyOrderActivity.this,"订单为空，请创建订单");
                        }else {
                            if (adapter==null) {
                                adapter= new myOrderAdapter(data,MyOrderActivity.this);
                            }else {
                                adapter.Repalce(data);
                                adapter.notifyDataSetChanged();
                            }
                            mRecycleView.setAdapter(adapter);
                            adapter.setOnMyOrderClickListener(new myOrderAdapter.onMyOrderClickListener() {
                                @Override
                                public void onMyOrderItem() {
                                    Intent intent=new Intent(MyOrderActivity.this,PayDemoActivity.class);
                                    startActivity(intent);
                                }
                            });
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public void OnFailure(Call call, Exception e) {
        CommonUtils.SHOWTOAST(MyOrderActivity.this,"请求失败");
    }
}
