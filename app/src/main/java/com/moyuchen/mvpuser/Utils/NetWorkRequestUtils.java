package com.moyuchen.mvpuser.Utils;

import com.moyuchen.mvpuser.LoginView.CallBackResult;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * User: 张亚博
 * Date: 2017-10-13 20:02
 * Description：
 */
public class NetWorkRequestUtils {
    private static NetWorkRequestUtils netWorkRequestUtils;

    private NetWorkRequestUtils() {

    }
    public static NetWorkRequestUtils getSingletonInstance(){
        if (netWorkRequestUtils==null) {
            synchronized (NetWorkRequestUtils.class){
                if (netWorkRequestUtils==null){
                    netWorkRequestUtils=new NetWorkRequestUtils();
                }
            }
        }
        return netWorkRequestUtils;
    }

    public  void  GetRequestData(String url, Map<String,Object> map, final CallBackResult callBackResult){

        OkHttpClient.Builder client=new OkHttpClient.Builder();
        OkHttpClient build = client.build();

        FormBody.Builder formBody = new FormBody.Builder();
        for (Map.Entry<String, Object> stringObjectsEntry : map.entrySet()) {
            formBody.add(stringObjectsEntry.getKey(),stringObjectsEntry.getValue().toString());
        }
        RequestBody requestBody = formBody.build();

        Request request=new Request.Builder().url(url).post(requestBody).build();
        build.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callBackResult.onFailure(call,e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                callBackResult.onResponse(call,response);

            }
        });

    }

    public  void GetData(String url, Map<String ,Object> map, final CallBackResult callBackResult){
        OkHttpClient client=new OkHttpClient.Builder()
                .addInterceptor(new LogInterceptor()).build();
        FormBody.Builder formbuild = new FormBody.Builder();
        for (Map.Entry<String, Object> stringObjectsEntry : map.entrySet()) {
            formbuild.add(stringObjectsEntry.getKey(),stringObjectsEntry.getValue().toString());
        }
        RequestBody requestbody = formbuild.build();

        Request request=new Request.Builder().post(requestbody).url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callBackResult.onFailure(call,e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                callBackResult.onResponse(call,response);
            }
        });

    }

}
