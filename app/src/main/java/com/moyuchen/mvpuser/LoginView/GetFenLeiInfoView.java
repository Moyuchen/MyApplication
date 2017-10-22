package com.moyuchen.mvpuser.LoginView;

import com.moyuchen.mvpuser.Bean.GetFeiLeiInfo;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;

/**
 * User: 张亚博
 * Date: 2017-10-10 15:24
 * Description：
 */
public interface GetFenLeiInfoView {
    void GetFenLeiInfoSuccess(String code,List<GetFeiLeiInfo.DataBean> data);
    void GetFenLeiInfoFailure(String code,String message);
    void OnGetFenLeiInfoFailure(Call call, IOException e);
}
