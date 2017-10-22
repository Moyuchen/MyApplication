package com.moyuchen.mvpuser.LoginView;

import com.moyuchen.mvpuser.Bean.Resultinfo;

import okhttp3.Call;

/**
 * User: 张亚博
 * Date: 2017-09-27 14:08
 * Description：
 */
public interface LoginView {
    void ShowProgressBar();
    void HideProgressBar();
    void NameError(String msg);
    void PasswordError(String msg);
    void LoginSuccess(String code, String msg, Resultinfo resultinfo);
    void LoginFailure(String code,String msg);
    void onFailure(Call call, Exception e);


}
