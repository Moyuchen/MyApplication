package com.moyuchen.mvpuser.Presenter;

import android.text.TextUtils;

import com.moyuchen.mvpuser.LoginView.AddCartsView;
import com.moyuchen.mvpuser.LoginView.CommonModulView;
import com.moyuchen.mvpuser.Modul.AddCartsModul;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

/**
 * User: 张亚博
 * Date: 2017-10-18 19:23
 * Description：
 */
public class AddCartsPresenter implements CommonModulView {
    private AddCartsView addCartsView;
    private AddCartsModul addCartsModul;


    public  AddCartsPresenter(AddCartsView addCartsView) {
        this.addCartsView = addCartsView;
        addCartsModul=new AddCartsModul();
        addCartsModul.setCommonModulView(this);
    }
    public void AddCartsPresenter(String url,String uid,String pid){
        Map<String,Object> map=new HashMap<>();
        if (TextUtils.isEmpty(uid)) {
            addCartsView.UidError("uid为空");
            return;
        }else {
            map.put("uid",uid);
        }
        if (TextUtils.isEmpty(pid)) {
            addCartsView.PidError("pid为空");
            return;
        }else {
            map.put("pid",pid);
        }
        addCartsModul.AddCartsModul(url, map); ;
    }
    @Override
    public void OnResponse(Call call, Response response) {

    }

    @Override
    public void OnFailure(Call call, Exception e) {

    }
}
