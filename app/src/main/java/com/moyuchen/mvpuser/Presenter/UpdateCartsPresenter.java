package com.moyuchen.mvpuser.Presenter;

import android.text.TextUtils;

import com.moyuchen.mvpuser.LoginView.CommonModulView;
import com.moyuchen.mvpuser.LoginView.UpdataCartsView;
import com.moyuchen.mvpuser.Modul.UpdateCartsModul;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

/**
 * User: 张亚博
 * Date: 2017-10-20 14:58
 * Description：
 */
public class UpdateCartsPresenter implements CommonModulView {
    private UpdataCartsView updataCartsView;
    private UpdateCartsModul updateCartsModul;


    public  UpdateCartsPresenter(UpdataCartsView updataCartsView) {
       this.updataCartsView=updataCartsView;
       updateCartsModul=new UpdateCartsModul();
        updateCartsModul.setCommonModulView(this);
    }
public void UpdataCarts(String url,String uid,String pid,String sellerid,String num,String selected){
    Map<String,Object> map=new HashMap<>();
    if (!TextUtils.isEmpty(uid)) {
        map.put("uid",uid);
    }
    if (!TextUtils.isEmpty(pid)) {
        map.put("pid",pid);
    }
    if (!TextUtils.isEmpty(sellerid)) {
        map.put("sellerid",sellerid);
    }
    if (!TextUtils.isEmpty(num)) {
        map.put("num",num);
    }
    if (!TextUtils.isEmpty(uid)) {
        map.put("uid",uid);
    }
    if (!TextUtils.isEmpty(selected)) {
        map.put("selected",selected);
    }
    updateCartsModul.UpdateCartsModul(url,map);
}
    @Override
    public void OnResponse(Call call, Response response) {
        updataCartsView.OnResponse(call, response);
    }

    @Override
    public void OnFailure(Call call, Exception e) {
        updataCartsView.OnFailure(call, e);
    }
}
