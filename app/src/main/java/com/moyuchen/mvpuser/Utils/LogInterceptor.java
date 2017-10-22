package com.moyuchen.mvpuser.Utils;

import android.util.Log;

import java.io.IOException;


import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Author:kson
 * E-mail:19655910@qq.com
 * Time:2017/10/18
 * Description:
 */
public class LogInterceptor implements Interceptor {

    public static String TAG = "LogInterceptor";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        long startTime = System.currentTimeMillis();
        Response response = chain.proceed(chain.request());
        long endTime = System.currentTimeMillis();
        long duration=endTime-startTime;
        MediaType mediaType = response.body().contentType();
        String content = response.body().string();
        Log.d(TAG,"\n");
        Log.d(TAG,"----------Start----------------");
        Log.d(TAG, "| "+request.toString());
        String method=request.method();
        if("POST".equals(method)){
            StringBuilder sb = new StringBuilder();
            if (request.body() instanceof FormBody) {
                FormBody body = (FormBody) request.body();
                for (int i = 0; i < body.size(); i++) {
                    sb.append(body.encodedName(i) + "=" + body.encodedValue(i) + ",");
                }
//                sb.delete(sb.length() - 1, sb.length());
                Log.d(TAG, "| RequestParams:{"+sb.toString()+"}");
            }
        }
        Log.d(TAG, "| Response:" + content);
        Log.d(TAG,"----------End:"+duration+"毫秒----------");
        return response.newBuilder()
                .body(ResponseBody.create(mediaType, content))
                .build();
    }
}
