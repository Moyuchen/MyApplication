package com.moyuchen.mvpuser.Presenter;

import android.text.TextUtils;

import com.moyuchen.mvpuser.Bean.GetFeiLeiInfo;
import com.moyuchen.mvpuser.Bean.Resultinfo;
import com.moyuchen.mvpuser.Bean.jdmainData;
import com.moyuchen.mvpuser.LoginView.GetChildFenLeiInfo;
import com.moyuchen.mvpuser.LoginView.GetChildFenLeiProductListView;
import com.moyuchen.mvpuser.LoginView.GetFenLeiInfoView;
import com.moyuchen.mvpuser.LoginView.GetSearchProductList;
import com.moyuchen.mvpuser.LoginView.GetShangPinChildListView;
import com.moyuchen.mvpuser.LoginView.GetUserInfoView;
import com.moyuchen.mvpuser.LoginView.GetXbannerdataView;
import com.moyuchen.mvpuser.LoginView.HeadUpLoadView;
import com.moyuchen.mvpuser.LoginView.LoginView;
import com.moyuchen.mvpuser.LoginView.ZhuceView;
import com.moyuchen.mvpuser.Modul.LoginModul;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

/**
 * User: 张亚博
 * Date: 2017-09-27 14:13
 * Description：
 */
public class LoginPresenter implements LoginModul.ILogin, LoginModul.MyModul {
    private LoginView loginview;
    private LoginModul loginmodul;
    private ZhuceView zhuceView;
    private GetUserInfoView getUserInfoView;
    private HeadUpLoadView headUpLoadView;
    private GetXbannerdataView getXbannerdataView;
    private GetFenLeiInfoView getFenLeiInfoView;
    private GetChildFenLeiInfo getChildFenLeiInfo;
    private GetShangPinChildListView getShangPinChildListView;
    private GetSearchProductList getSearchProductList;
    private GetChildFenLeiProductListView getChildFenLeiProductListView;


    public LoginPresenter(GetShangPinChildListView getShangPinChildListView) {
        this.getShangPinChildListView = getShangPinChildListView;
        loginmodul=new LoginModul();
        loginmodul.setMyModul(this);
    }

    public LoginPresenter(LoginView loginview) {
        this.loginview = loginview;
        loginmodul=new LoginModul();
        loginmodul.setIlogin(this);
    }
    public LoginPresenter(ZhuceView zhuceView){
        this.zhuceView=zhuceView;
        loginmodul=new LoginModul();
        loginmodul.setIlogin(this);
    }
    public LoginPresenter(GetUserInfoView getUserInfoView){
        this.getUserInfoView=getUserInfoView;
        loginmodul=new LoginModul();
        loginmodul.setIlogin(this);
    }

    public LoginPresenter(HeadUpLoadView headUpLoadView){
        this.headUpLoadView=headUpLoadView;
        loginmodul=new LoginModul();
        loginmodul.setIlogin(this);

    }

    public LoginPresenter(GetFenLeiInfoView getFenLeiInfoView) {
        this.getFenLeiInfoView = getFenLeiInfoView;
        loginmodul=new LoginModul();
        loginmodul.setIlogin(this);
    }

    public LoginPresenter(GetFenLeiInfoView getFenLeiInfoView, GetXbannerdataView getXbannerdataView){
            this.getFenLeiInfoView = getFenLeiInfoView;
            this.getXbannerdataView=getXbannerdataView;
            loginmodul=new LoginModul();
            loginmodul.setIlogin(this);
    }

    public LoginPresenter(GetChildFenLeiInfo getChildFenLeiInfo){
        this.getChildFenLeiInfo=getChildFenLeiInfo;
        loginmodul=new LoginModul();
        loginmodul.setIlogin(this);
    }
    public LoginPresenter( GetChildFenLeiProductListView getChildFenLeiProductListView){
      this.getChildFenLeiProductListView=getChildFenLeiProductListView;
        loginmodul=new LoginModul();
        loginmodul.setMyModul(this);
    }
    public LoginPresenter( GetSearchProductList getSearchProductList){
      this.getSearchProductList=getSearchProductList;
        loginmodul=new LoginModul();
        loginmodul.setMyModul(this);
    }



    public void Register(String mobile,String password){
        if(TextUtils.isEmpty(mobile)){
            loginview.NameError("用户名为空");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            loginview.PasswordError("密码为空");
            return;
        }
        loginmodul.Register(mobile,password);
    }
    /**
     * 登录
     * @param mobile
     * @param password
     */
    public void Login(String mobile,String password){

        loginview.ShowProgressBar();
        if(TextUtils.isEmpty(mobile)){
            loginview.NameError("用户名为空");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            loginview.PasswordError("密码为空");
            return;
        }
        loginmodul.Login(mobile,password);
    }
    public void HeadupLoad(int uid,File file){
        if (uid!=0) {
            loginmodul.HeadUpLoad(uid,file);
        }
    }
    public void GetUserInfo(String uid){
        if (!TextUtils.isEmpty(uid)) {
            loginmodul.Getinfo(uid);
        }
    }
    public void GetXbannerdataView(){
        loginmodul.GetXbannerImageUrls();
    }
    public void GetFeiLeiInfo(){
        loginmodul.GetFeiLeiInfo();
    }
    public void setGetChildFenLeiInfo(int cid){
        if (cid==0) {
            getChildFenLeiInfo.CidError("cid为空或等于0");
            return;
        }
        loginmodul.GetChildFenLeiInfo(cid);
    }
    public void GetShangPinChildList(String url, Map<String,Object> map){
            if (TextUtils.isEmpty(url)) {
                getShangPinChildListView.OnNULLUrl("请求商品子分类列表Url为Null");
            }if (map==null){
                getShangPinChildListView.OnNULLMap("请求商品子分类列表参数Map为null");
            }
            loginmodul.GetShangPinChildList(url,map);

        }
    public void getGetSearchProductList(String url,String keywords,String page){
        Map<String,Object> map=new HashMap<>();
        if (TextUtils.isEmpty(keywords)) {
            getSearchProductList.OnKeyWordNullError("输入框为空，请重新输入");
            return;
        }else{
            map.put("keywords",keywords);
        }
        if (!TextUtils.isEmpty(page)) {
            map.put("page",page);
        }
        loginmodul.GetData(url,map);
    }
    public void GetChildFenLeiProductListInfo(String url,String pscid,String page){
        if (getChildFenLeiProductListView!=null) {

        Map<String,Object> map=new HashMap<>();
        if (TextUtils.isEmpty(pscid)) {
            getChildFenLeiProductListView.PscidNullError("pscid为空，请检查后在请求");
            return;
        }else {
            map.put("pscid",pscid);
        }
        if (TextUtils.isEmpty(page)) {
            getChildFenLeiProductListView.PageNullError("page为空，默认为1");
//            return;
        }else {
            map.put("page",page);
        }

        loginmodul.GetChildFenLeiProductList(url,map);
        }
    }




    @Override
    public void LoginSuccess(String code, String message, Resultinfo resultinfo) {
            loginview.LoginSuccess(code,message,resultinfo);
            loginview.HideProgressBar();
    }

    @Override
    public void LoginFailure(String code, String message) {
            loginview.LoginFailure(code,message);
            loginview.HideProgressBar();
    }

    @Override
    public void onLoginFailure(Call call, IOException e) {
            loginview.onFailure(call,e);
            loginview.HideProgressBar();
    }

    @Override
    public void ZhuceSuccess(String code, String message) {
        zhuceView.ZhuceSuccess(code,message);
    }

    @Override
    public void ZhuceFailure(String code, String message) {
        zhuceView.ZhuceFailure(code,message);
    }

    @Override
    public void onZhuceFailure(Call call, IOException e) {
        zhuceView.onZhuceFailure(call,e);
    }

    @Override
    public void GetUserInfoSuccess(String code, String message, Resultinfo.DataBean dataBean) {
            getUserInfoView.GetUserInfoSuccess(code,message,dataBean);
    }

    @Override
    public void GetUserInfoFailure(String code, String message) {
        getUserInfoView.GetUserInfoFailure(code,message);
    }

    @Override
    public void onGetUserInfoFailure(Call call, IOException e) {
        getUserInfoView.OnGetUserInfoFailure(call,e);
    }

    @Override
    public void HeadUpLoadSuccess(String code, String message) {
        headUpLoadView.HeadUpLoadSuccess(code,message);
    }

    @Override
    public void HeadUpLoadFailure(String code, String message) {
        headUpLoadView.HeadUpLoadFailure(code,message);
    }

    @Override
    public void OnHeadUpLoadFailure(Call call, IOException e) {
        headUpLoadView.OnHeadUpLoadFailure(call,e);
    }

    @Override
    public void GetXbannerImageUrlsSuccess(String code, jdmainData jdmainData) {
        getXbannerdataView.GetXbannerImageUrlsSuccess(code,jdmainData);
    }

    @Override
    public void GetXbannerImageUrlsFailure(String code, String message) {
        getXbannerdataView.GetXbannerImageUrlsFailure(code,message);
    }

    @Override
    public void OnGetXbannerImageUrlsFailure(Call call, IOException e) {
        getXbannerdataView.OnGetXbannerImageUrlsFailure(call,e);
    }

    @Override
    public void GetFenLeiInfoSuccess(String code,List<GetFeiLeiInfo.DataBean> data) {
        getFenLeiInfoView.GetFenLeiInfoSuccess(code,data);
    }

    @Override
    public void GetFenLeiInfoFailure(String code, String message) {
    getFenLeiInfoView.GetFenLeiInfoFailure(code, message);
    }

    @Override
    public void OnGetFenLeiInfoFailure(Call call, IOException e) {
        getFenLeiInfoView.OnGetFenLeiInfoFailure(call, e);
    }

    @Override
    public void GetChildFenLeiInfoSuccess(String code, String msg, List<com.moyuchen.mvpuser.Bean.GetChildFenLeiInfo.DataBean> data) {
        getChildFenLeiInfo.GetChildFenLeiInfoSuccess(code, msg, data);
    }

    @Override
    public void GetChildFenLeiInfoFailure(String code, String message) {
        getChildFenLeiInfo.GetChildFenLeiInfoFailure(code, message);
    }

    @Override
    public void OnGetChildFenLeiInfoFailure(Call call, IOException e) {
            getChildFenLeiInfo.OnGetChildFenLeiInfoFailure(call, e);
    }


    @Override
    public void onFailure(Call call, Exception e) {
        if (getShangPinChildListView!=null) {
            getShangPinChildListView.onFailure("请求商品子分类列表失败");
        }
        if (getSearchProductList!=null) {
            getSearchProductList.OnFailure(call, e);
        }
        if (getChildFenLeiProductListView!=null) {
            getChildFenLeiProductListView.OnFailure(call,e);
        }

    }

    @Override
    public void onResponse(Call call, Response response) {
        if (getShangPinChildListView!=null) {
            getShangPinChildListView.onResponse(response);
        }
        if (getSearchProductList!=null) {
            getSearchProductList.OnResponse(call, response);
        }
        if (getChildFenLeiProductListView!=null) {
            getChildFenLeiProductListView.OnResponse(call, response);
        }
    }
}
