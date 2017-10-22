package com.moyuchen.mvpuser.Presenter;

import android.text.TextUtils;

import com.moyuchen.mvpuser.LoginView.productinfoView;
import com.moyuchen.mvpuser.Modul.productinfoModul;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

/**
 * User: 张亚博
 * Date: 2017-10-17 16:12
 * Description：
 */
public class productInfoPresenter implements productinfoModul.productInfoModul {
    private productinfoView view;
    private  productinfoModul productmodul;

    public productInfoPresenter(productinfoView view) {
        this.view = view;
       productmodul=new productinfoModul();
        productmodul.setProductInfoModul(this);

    }
    public  void productinfo(String url,String pid){
        Map<String,Object> map=new HashMap<>();
        if (!TextUtils.isEmpty(pid)) {
            map.put("pid",pid);
        }
        productmodul.productinfo(url,map);
    }
    @Override
    public void onFailure(Call call, Exception e) {
        view.onFailure(call, e);
    }

    @Override
    public void onResponse(Call call, Response response) {
        view.onResponse(call, response);
    }
}
