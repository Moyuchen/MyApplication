package com.moyuchen.mvpuser.LoginView;

import okhttp3.Call;

/**
 * User: 张亚博
 * Date: 2017-10-14 13:15
 * Description：
 */
public interface GetSearchProductList extends CommonView {
    void OnKeyWordNullError(String msg);
    void OnFailure(Call call, Exception e);

}
