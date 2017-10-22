package com.moyuchen.mvpuser.LoginView;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;

/**
 * User: 张亚博
 * Date: 2017-10-11 14:27
 * Description：
 */
public interface GetChildFenLeiInfo {
    void GetChildFenLeiInfoSuccess(String code,String msg,List<com.moyuchen.mvpuser.Bean.GetChildFenLeiInfo.DataBean> data);
    void GetChildFenLeiInfoFailure(String code,String message);
    void OnGetChildFenLeiInfoFailure(Call call, IOException e);
    void CidError(String message);
}
