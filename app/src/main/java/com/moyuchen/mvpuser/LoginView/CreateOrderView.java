package com.moyuchen.mvpuser.LoginView;

import okhttp3.Call;
import okhttp3.Response;

/**
 * User: 张亚博
 * Date: 2017-10-21 14:31
 * Description：
 */
public interface CreateOrderView  {
        void onCreateReponse(Call call, Response response);
        void onCreateFailure(Call call,Exception e);
}
