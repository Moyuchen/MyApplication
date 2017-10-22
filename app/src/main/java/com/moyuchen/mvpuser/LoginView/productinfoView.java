package com.moyuchen.mvpuser.LoginView;

import okhttp3.Call;
import okhttp3.Response;

/**
 * User: 张亚博
 * Date: 2017-10-17 16:13
 * Description：
 */
public interface productinfoView {
    void onFailure(Call call, Exception e);
    void onResponse(Call call, Response response);
}
