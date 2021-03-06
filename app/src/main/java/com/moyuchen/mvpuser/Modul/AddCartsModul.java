package com.moyuchen.mvpuser.Modul;

import com.moyuchen.mvpuser.LoginView.CallBackResult;
import com.moyuchen.mvpuser.LoginView.CommonModulView;
import com.moyuchen.mvpuser.Utils.NetWorkRequestUtils;

import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

/**
 * User: 张亚博
 * Date: 2017-10-18 19:26
 * Description：
 */
public class AddCartsModul {
    private CommonModulView commonModulView;

    public void setCommonModulView(CommonModulView commonModulView) {
        this.commonModulView = commonModulView;
    }

    public AddCartsModul() {
    }

    public void AddCartsModul(String url, Map<String,Object> map){
        NetWorkRequestUtils.getSingletonInstance().GetData(url, map, new CallBackResult() {
            @Override
            public void onFailure(Call call, Exception e) {
                commonModulView.OnFailure(call, e);
            }

            @Override
            public void onResponse(Call call, Response response) {
                commonModulView.OnResponse(call, response);
            }
        });
    }


}
