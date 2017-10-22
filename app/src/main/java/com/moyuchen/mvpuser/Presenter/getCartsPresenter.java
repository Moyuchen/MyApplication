package com.moyuchen.mvpuser.Presenter;

import android.text.TextUtils;

import com.moyuchen.mvpuser.LoginView.CommonModulView;
import com.moyuchen.mvpuser.LoginView.GetCartsView;
import com.moyuchen.mvpuser.Modul.GetCartsModul;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

/**
 * User: 张亚博
 * Date: 2017-10-18 15:43
 * Description：
 */
public class getCartsPresenter implements CommonModulView {
    private GetCartsView getCartsView;
    private GetCartsModul getCartsModul;

    public getCartsPresenter(GetCartsView getCartsView) {
        this.getCartsView = getCartsView;
        getCartsModul=new GetCartsModul();
        getCartsModul.setCommonModulView(this);

    }
        public void GetCartsView(String url,String uid){
            Map<String,Object> map=new HashMap<>();
            if (TextUtils.isEmpty(uid)) {
               getCartsView.UidError("uid为空");
                return;
            }else {
                map.put("uid",uid);
            }
        getCartsModul.getCartsModul(url, map) ;
        }


    @Override
    public void OnResponse(Call call, Response response) {
        getCartsView.OnResponse(call, response);
    }

    @Override
    public void OnFailure(Call call, Exception e) {
        getCartsView.OnFailure(call, e);
    }
}
