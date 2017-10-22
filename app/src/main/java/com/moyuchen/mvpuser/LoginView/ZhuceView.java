package com.moyuchen.mvpuser.LoginView;

import okhttp3.Call;

/**
 * User: 张亚博
 * Date: 2017-09-29 14:35
 * Description：
 */
public interface ZhuceView {
    void ZhuceSuccess(String code,String msg);
    void ZhuceFailure(String code,String msg);
    void NameError(String msg);
    void PasswordError(String msg);
    void onZhuceFailure(Call call,Exception e);

}
