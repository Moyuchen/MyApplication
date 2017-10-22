package com.moyuchen.mvpuser;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.moyuchen.mvpuser.API.API;
import com.moyuchen.mvpuser.Adapter.ProductViewPagerAdapter;
import com.moyuchen.mvpuser.Bean.ProductInfo;
import com.moyuchen.mvpuser.Bean.addCartInfo;
import com.moyuchen.mvpuser.Fragments.PingjiaFragment;
import com.moyuchen.mvpuser.Fragments.detailFragment;
import com.moyuchen.mvpuser.Fragments.productFragment;
import com.moyuchen.mvpuser.LoginView.AddCartsView;
import com.moyuchen.mvpuser.LoginView.productinfoView;
import com.moyuchen.mvpuser.Presenter.AddCartsPresenter;
import com.moyuchen.mvpuser.Presenter.productInfoPresenter;
import com.moyuchen.mvpuser.Utils.CommonUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class ProductInfoActivity extends AppCompatActivity implements productinfoView, View.OnClickListener, AddCartsView {

    private ImageView productimageview;
    private TabLayout producttablayout;
    private ViewPager productViewPager;
    private productInfoPresenter presenter;
    private List<Fragment> fragments;
    private List<String> titls;
    private ProductViewPagerAdapter adapter;
    private Button addGouWuCar;
    private String pid;
    private String uid;
    private AddCartsPresenter addpresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productinfo);
        initView();
        initData();
    }

    private void initData() {
        uid = CommonUtils.GetString(this, "uid");
        fragments = new ArrayList<>();
        titls = new ArrayList<>();
        pid = getIntent().getStringExtra("pid");
        presenter = new productInfoPresenter(this);
        presenter.productinfo(API.PRODUCTINFO, pid);
        addpresenter = new AddCartsPresenter(this);
    }

    private void initView() {
        productimageview = (ImageView) findViewById(R.id.productimageivew);
        producttablayout = (TabLayout) findViewById(R.id.producttablayout);
        productViewPager = (ViewPager) findViewById(R.id.productviewpager);
        addGouWuCar = (Button) findViewById(R.id.addGouWuCar);
        addGouWuCar.setOnClickListener(this);


    }

    @Override
    public void onFailure(Call call, Exception e) {
        CommonUtils.SHOWTOAST(this,"请求失败");
    }

    @Override
    public void onResponse(Call call, Response response) {
        if (response.isSuccessful()) {
            try {
                String string = response.body().string();
                Gson gson=new Gson();
                ProductInfo productInfo = gson.fromJson(string, ProductInfo.class);
                String code = productInfo.getCode();
                String msg = productInfo.getMsg();
                ProductInfo.DataBean data = productInfo.getData();
                productFragment product = (productFragment) new productFragment().productFragment(data);

                if ("0".equals(code)) {
                    fragments.add(product);
                    titls.add("商品");
                    detailFragment detail= (detailFragment) new detailFragment().detailFragment(data.getDetailUrl());
                    fragments.add(detail);
                    titls.add("详情");
                    PingjiaFragment pingjia=new PingjiaFragment();
                    fragments.add(pingjia);
                    titls.add("评价");
                    adapter = new ProductViewPagerAdapter(getSupportFragmentManager(),fragments,titls);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            productViewPager.setAdapter(adapter);

                            producttablayout.setupWithViewPager(productViewPager);
                        }
                    });
                }else {
                    CommonUtils.SHOWTOAST(ProductInfoActivity.this,msg);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.addGouWuCar:
                addpresenter.AddCartsPresenter(API.ADDCART,uid,pid);
                break;

        }
    }

    @Override
    public void UidError(String msg) {
        CommonUtils.SHOWTOAST(this,msg);
    }

    @Override
    public void PidError(String msg) {
        CommonUtils.SHOWTOAST(this,msg);
    }

    @Override
    public void OnResponse(Call call, Response response) {
        if (response.isSuccessful()) {
            try {
                String string = response.body().string();
                Gson gson=new Gson();
                addCartInfo addCartInfo = gson.fromJson(string, addCartInfo.class);
                String code = addCartInfo.getCode();
                String msg = addCartInfo.getMsg();
                if ("0".equals(code)) {
                    CommonUtils.SHOWTOAST(ProductInfoActivity.this,msg);
                }else {
                    CommonUtils.SHOWTOAST(ProductInfoActivity.this,msg);
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
}
