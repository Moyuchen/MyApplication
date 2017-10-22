package com.moyuchen.mvpuser.LoginView;

import okhttp3.Response;

/**
 * User: 张亚博
 * Date: 2017-10-13 21:05
 * Description：
 */
public interface GetShangPinChildListView {
    void onFailure(String msg);
    void onResponse(Response response);
    void OnNULLUrl(String msg);
    void OnNULLMap(String msg);
}
