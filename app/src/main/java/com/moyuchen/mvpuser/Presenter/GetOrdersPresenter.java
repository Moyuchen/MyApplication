package com.moyuchen.mvpuser.Presenter;

import android.text.TextUtils;

import com.moyuchen.mvpuser.LoginView.CommonModulView;
import com.moyuchen.mvpuser.LoginView.GetOrdersView;
import com.moyuchen.mvpuser.Modul.GetOrdersModul;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

/**
 * User: 张亚博
 * Date: 2017-10-22 15:27
 * Description：
 */
public class GetOrdersPresenter implements CommonModulView {
    private GetOrdersView getOrdersView;
    private final GetOrdersModul getOrdersModul;

    public GetOrdersPresenter(GetOrdersView getOrdersView) {
        this.getOrdersView = getOrdersView;
        getOrdersModul = new GetOrdersModul();
        getOrdersModul.setCommonModulView(this);

    }
   public void getordermodul(String url, String uid,int page){
       Map<String,Object> map=new HashMap<>();
       if (!TextUtils.isEmpty(uid)) {
           map.put("uid",uid);
       }
       if (page>=2) {
         map.put("page",page);
       }
       getOrdersModul.getordermodul(url, map);
    }

    @Override
    public void OnResponse(Call call, Response response) {
        getOrdersView.OnResponse(call, response);
    }

    @Override
    public void OnFailure(Call call, Exception e) {
        getOrdersView.OnFailure(call, e);
    }
}
