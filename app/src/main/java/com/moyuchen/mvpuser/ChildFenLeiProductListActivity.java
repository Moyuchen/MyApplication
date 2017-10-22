package com.moyuchen.mvpuser;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.moyuchen.mvpuser.API.API;
import com.moyuchen.mvpuser.Adapter.ProSearRecViewAdapter;
import com.moyuchen.mvpuser.Bean.SearchProductInfo;
import com.moyuchen.mvpuser.LoginView.GetChildFenLeiProductListView;
import com.moyuchen.mvpuser.Presenter.LoginPresenter;
import com.moyuchen.mvpuser.Utils.CommonUtils;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class ChildFenLeiProductListActivity extends AppCompatActivity implements GetChildFenLeiProductListView {

    private String pscid;
    private LoginPresenter commonPresenter;
    private SwipeRefreshLayout childswipeRefresh;
    public int state;
    private ImageView childsearch;
    private RecyclerView childProSearRecView;
    private LinearLayoutManager manager;
    private int lastVisibleItem;
    private int page;
    private int clicknumber;
    private ProSearRecViewAdapter proSearRecViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_fen_lei_product_list);
        initView();
        initData();
    }

    private void initView() {
        childswipeRefresh = (SwipeRefreshLayout) findViewById(R.id.childswipeRefresh);
        childswipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                state=1;
                commonPresenter.GetChildFenLeiProductListInfo(API.GETCHILDFENLEIPRODUCTLISTINFO, pscid,null);
            }
        });
        childsearch = (ImageView) findViewById(R.id.childsearch);
        childProSearRecView = (RecyclerView) findViewById(R.id.childProSearRecView);
        manager = new LinearLayoutManager(this);
        childProSearRecView.setLayoutManager(manager);
        childProSearRecView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState ==RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 ==proSearRecViewAdapter.getItemCount()) {

                    page++;
                    commonPresenter.GetChildFenLeiProductListInfo(API.GETCHILDFENLEIPRODUCTLISTINFO,pscid,page+"");
                    state=2;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem =manager.findLastVisibleItemPosition();
            }
        });
    }

    private void initData() {
        pscid = getIntent().getStringExtra("pscid");
        commonPresenter = new LoginPresenter(this);
        commonPresenter.GetChildFenLeiProductListInfo(API.GETCHILDFENLEIPRODUCTLISTINFO, pscid,null);
    }
    public void OnTranslate(View view){
        if (clicknumber%2==0) {
            childsearch.setImageResource(R.drawable.qiehuan2);
            childProSearRecView.setLayoutManager(new GridLayoutManager(this,2));
        }else {
            childProSearRecView.setLayoutManager(new LinearLayoutManager(this));
            childsearch.setImageResource(R.drawable.qiehuan);
        }
        clicknumber++;
    }

    @Override
    public void OnResponse(Call call, Response response) {
        if (response.isSuccessful()) {
            try {
                String string = response.body().string();
                Gson gson=new Gson();
                final SearchProductInfo searchProductInfo = gson.fromJson(string, SearchProductInfo.class);
                String code = searchProductInfo.getCode();
                String msg = searchProductInfo.getMsg();

                if ("0".equals(code)) {
                    CommonUtils.SHOWTOAST(this,msg);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            final List<SearchProductInfo.DataBean> data = searchProductInfo.getData();
                            switch (state){
                                case 0:
                                    if(proSearRecViewAdapter!=null){
                                        proSearRecViewAdapter.Replace(data);
                                        proSearRecViewAdapter.notifyDataSetChanged();
                                    }else {
                                        proSearRecViewAdapter = new ProSearRecViewAdapter(ChildFenLeiProductListActivity.this,data);
                                    }
                                    state=0;
                                    break;
                                case 1:
                                    if(proSearRecViewAdapter!=null){
                                        proSearRecViewAdapter.addRefreshitem(data);
                                        proSearRecViewAdapter.notifyDataSetChanged();
                                    }
                                    break;
                                case 2:
                                    if(proSearRecViewAdapter!=null){
                                        proSearRecViewAdapter.addmoreItem(data);
                                        proSearRecViewAdapter.notifyDataSetChanged();
                                    }
                                    break;
                            }
                            proSearRecViewAdapter.setOnitemlistener(new ProSearRecViewAdapter.onitemlistener() {
                                @Override
                                public void itemlistener(String pid) {
                                    Intent intent=new Intent(ChildFenLeiProductListActivity.this,ProductInfoActivity.class);
                                    intent.putExtra("pid",pid);
                                    startActivity(intent);
                                }
                            });
                            childProSearRecView.setAdapter(proSearRecViewAdapter);


                        }
                    });
                } else if ("1".equals(code)) {
                    CommonUtils.SHOWTOAST(this,msg);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void OnFailure(Call call, Exception e) {
        CommonUtils.SHOWTOAST(this,"请求失败");
    }

    @Override
    public void PscidNullError(String msg) {
        CommonUtils.SHOWTOAST(this,msg);
    }

    @Override
    public void PageNullError(String msg) {
        CommonUtils.SHOWTOAST(this,msg);
    }
}
