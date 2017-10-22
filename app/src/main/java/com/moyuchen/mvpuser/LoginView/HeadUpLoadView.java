package com.moyuchen.mvpuser.LoginView;

import java.io.IOException;

import okhttp3.Call;

/**
 * User: 张亚博
 * Date: 2017-09-29 16:20
 * Description：
 */
public interface HeadUpLoadView {
    void HeadUpLoadSuccess(String code,String message);
    void HeadUpLoadFailure(String code,String message);
    void OnHeadUpLoadFailure(Call call, IOException e);
}
