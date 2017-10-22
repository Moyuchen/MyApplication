package com.moyuchen.mvpuser.Modul;

import com.moyuchen.mvpuser.LoginView.CallBackResult;
import com.moyuchen.mvpuser.Utils.NetWorkRequestUtils;

import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

/**
 * User: 张亚博
 * Date: 2017-10-17 16:18
 * Description：
 */
public class productinfoModul {
    private  productInfoModul productInfoModul;

    public  void  productinfo(String url, Map<String,Object> map){
        NetWorkRequestUtils.getSingletonInstance().GetData(url, map, new CallBackResult() {
            @Override
            public void onFailure(Call call, Exception e) {
                productInfoModul.onFailure(call, e);
            }

            @Override
            public void onResponse(Call call, Response response) {
                productInfoModul.onResponse(call, response);
            }
        });

    }

    public void setProductInfoModul(productinfoModul.productInfoModul productInfoModul) {
        this.productInfoModul = productInfoModul;
    }

    public interface productInfoModul{
        void onFailure(Call call, Exception e);
        void onResponse(Call call, Response response);
    }
}
