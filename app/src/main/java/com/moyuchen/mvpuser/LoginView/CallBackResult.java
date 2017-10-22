package com.moyuchen.mvpuser.LoginView;

import okhttp3.Call;
import okhttp3.Response;

/**
 * User: 张亚博
 * Date: 2017-10-13 20:06
 * Description：
 */
public interface CallBackResult {
    void onFailure(Call call,Exception e);
    void onResponse(Call call, Response response);
}
