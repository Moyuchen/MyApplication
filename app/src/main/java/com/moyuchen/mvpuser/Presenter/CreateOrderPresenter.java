package com.moyuchen.mvpuser.Presenter;

import android.text.TextUtils;

import com.moyuchen.mvpuser.LoginView.CommonModulView;
import com.moyuchen.mvpuser.LoginView.CreateOrderView;
import com.moyuchen.mvpuser.Modul.CreateOrderModul;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

/**
 * User: 张亚博
 * Date: 2017-10-21 14:31
 * Description：
 */
public class CreateOrderPresenter implements CommonModulView {
    private CreateOrderView createOrderView;
    private CreateOrderModul createOrderModul;

    public CreateOrderPresenter(CreateOrderView createOrderView) {
        this.createOrderView = createOrderView;
        createOrderModul=new CreateOrderModul();
        createOrderModul.setCommonModulView(this);
    }

    public void CreateOrderPresenter(String url,String uid,String Price){
        Map<String,Object> map=new HashMap<>();
        if (!TextUtils.isEmpty(uid)) {
            map.put("uid",uid);
        }
        if (!TextUtils.isEmpty(Price)) {
            map.put("price",Price);
        }

        createOrderModul.CreateOrder(url,map);
    }
    @Override
    public void OnResponse(Call call, Response response) {
        createOrderView.onCreateReponse(call, response);
    }

    @Override
    public void OnFailure(Call call, Exception e) {
        createOrderView.onCreateFailure(call, e);
    }
}
