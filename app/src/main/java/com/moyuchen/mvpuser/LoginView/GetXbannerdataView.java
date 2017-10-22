package com.moyuchen.mvpuser.LoginView;

import com.moyuchen.mvpuser.Bean.jdmainData;

import okhttp3.Call;

/**
 * User: 张亚博
 * Date: 2017-10-07 21:14
 * Description：
 */
public interface GetXbannerdataView {
    void GetXbannerImageUrlsFailure(String code,String message);
    void GetXbannerImageUrlsSuccess(String code, jdmainData jdmainData);
    void OnGetXbannerImageUrlsFailure(Call call,Exception e);
}
