package com.moyuchen.mvpuser.LoginView;

import okhttp3.Call;
import okhttp3.Response;

/**
 * User: 张亚博
 * Date: 2017-10-18 15:51
 * Description：
 */
public interface CommonModulView {
    void OnResponse(Call call, Response response);
    void OnFailure(Call call,Exception e);
}
