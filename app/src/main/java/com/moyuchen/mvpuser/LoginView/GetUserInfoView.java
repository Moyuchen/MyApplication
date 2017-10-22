package com.moyuchen.mvpuser.LoginView;

import com.moyuchen.mvpuser.Bean.Resultinfo;

import java.io.IOException;

import okhttp3.Call;

/**
 * User: 张亚博
 * Date: 2017-09-29 15:35
 * Description：
 */
public interface GetUserInfoView {
    void GetUserInfoSuccess(String code, String message, Resultinfo.DataBean dataBean);
    void GetUserInfoFailure(String code,String message);
    void OnGetUserInfoFailure(Call call, IOException e);

}
