package com.moyuchen.mvpuser;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.moyuchen.mvpuser.API.API;
import com.moyuchen.mvpuser.Adapter.ProSearRecViewAdapter;
import com.moyuchen.mvpuser.Bean.SearchProductInfo;
import com.moyuchen.mvpuser.LoginView.GetSearchProductList;
import com.moyuchen.mvpuser.Presenter.LoginPresenter;
import com.moyuchen.mvpuser.Utils.CommonUtils;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;


public class ProductSearchActivity extends AppCompatActivity implements GetSearchProductList {

    private RecyclerView proSearRecView;

    private EditText searchKeyWords;
    private LoginPresenter commonPresenter;
    private ProSearRecViewAdapter proSearRecViewAdapter;
    private int clicknumber=0;
    private SwipeRefreshLayout swipeRefresh;
    private LinearLayoutManager manager;
    private int lastVisibleItem;
    private String pscid;
    private ImageView search;
    private int state=0;
    private int page=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_search);
        initView();
        initData();
    }

    private void initData() {
        commonPresenter=new LoginPresenter(this);
    }


    private void initView() {
        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                state=1;
                commonPresenter.GetChildFenLeiProductListInfo(API.GETCHILDFENLEIPRODUCTLISTINFO, pscid,null);
            }
        });
        search = (ImageView) findViewById(R.id.search);
        proSearRecView = (RecyclerView) findViewById(R.id.ProSearRecView);
        searchKeyWords = (EditText) findViewById(R.id.Et_SearchKeyWords);
        manager = new LinearLayoutManager(this);
        proSearRecView.setLayoutManager(manager);
        proSearRecView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState ==RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 ==proSearRecViewAdapter.getItemCount()) {
                    String keywords = searchKeyWords.getText().toString();
                    page++;
                    commonPresenter.getGetSearchProductList(API.GETSEARCHPRODUCTLIST,keywords,page+"");
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
    public void OnSearch(View view){
        String searchKeyWord = searchKeyWords.getText().toString();
        commonPresenter.getGetSearchProductList(API.GETSEARCHPRODUCTLIST,searchKeyWord,null);

    }
    public void OnTranslate(View view){
        if (clicknumber%2==0) {
            search.setImageResource(R.drawable.qiehuan2);
            proSearRecView.setLayoutManager(new GridLayoutManager(this,2));
        }else {
            proSearRecView.setLayoutManager(new LinearLayoutManager(this));
            search.setImageResource(R.drawable.qiehuan);
        }
        clicknumber++;
    }

    @Override
    public void OnKeyWordNullError(String msg) {
        CommonUtils.SHOWTOAST(this,msg);
    }

    @Override
    public void OnFailure(Call call, Exception e) {
        CommonUtils.SHOWTOAST(this,"请求搜索商品失败");
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
                                        proSearRecViewAdapter = new ProSearRecViewAdapter(ProductSearchActivity.this,data);
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

                            proSearRecView.setAdapter(proSearRecViewAdapter);


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







}
