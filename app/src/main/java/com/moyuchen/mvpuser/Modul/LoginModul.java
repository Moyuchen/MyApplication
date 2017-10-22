package com.moyuchen.mvpuser.Modul;

import com.google.gson.Gson;
import com.moyuchen.mvpuser.API.API;
import com.moyuchen.mvpuser.Bean.GetChildFenLeiInfo;
import com.moyuchen.mvpuser.Bean.GetFeiLeiInfo;
import com.moyuchen.mvpuser.Bean.Resultinfo;
import com.moyuchen.mvpuser.Bean.jdmainData;
import com.moyuchen.mvpuser.LoginView.CallBackResult;
import com.moyuchen.mvpuser.Utils.NetWorkRequestUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * User: 张亚博
 * Date: 2017-09-27 13:45
 * Description：处理数据
 */
public class LoginModul {
    private MyModul myModul;

    /**
     * 注册用户
     * @param mobile
     * @param password
     */
    public void Register(String mobile,String password){
        OkHttpClient okHttpClient=new OkHttpClient();
        FormBody requestbody  = new FormBody.Builder()
                .add("mobile", mobile)
                .add("password", password)
                .build();
        Request request=new Request.Builder()
                .url(API.USERREGISTER)
                .post(requestbody)
                .build();
        okHttpClient.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ilogin.onZhuceFailure(call,e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Gson gson=new Gson();
                Resultinfo resultinfo = gson.fromJson(string, Resultinfo.class);
                String code = resultinfo.getCode();
                String msg = resultinfo.getMsg();
                if ("0".equals(code)) {
                    ilogin.ZhuceSuccess(code,msg);
                }else {
                    ilogin.ZhuceFailure(code,msg);
                }
            }
        });


    }

    /**
     * 登录用户
     * @param mobile
     * @param password
     */
    public void  Login(final String mobile, String password){

        OkHttpClient okHttpClient=new OkHttpClient();
        FormBody requestBody=new FormBody.Builder()
                .add("mobile",mobile)
                .add("password",password)
                .build();
        Request request=new Request.Builder()
                .url(API.USERLOGIN)
                .post(requestBody)
                .build();
        okHttpClient.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ilogin.onLoginFailure(call,e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Gson gson=new Gson();
                Resultinfo resultinfo = gson.fromJson(string, Resultinfo.class);
                String code = resultinfo.getCode();
                String msg = resultinfo.getMsg();
                if ("0".equals(code)) {
                    ilogin.LoginSuccess(code,msg,resultinfo);
                }
                else {
                    ilogin.LoginFailure(code,msg);
                }
            }
        });
    }

    /**
     * 获取用户信息方法
     * @param uid
     */
    public void  Getinfo(String uid){
        OkHttpClient okHttpClient=new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("uid", uid)
                .build();
        final Request request=new Request.Builder().url(API.USERGETINFO)
                .post(body)
                .build();
        okHttpClient.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            ilogin.onGetUserInfoFailure(call,e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Gson gson=new Gson();
                Resultinfo resultinfo = gson.fromJson(string, Resultinfo.class);
                String code = resultinfo.getCode();
                String msg = resultinfo.getMsg();
                Resultinfo.DataBean data = resultinfo.getData();
                if ("0".equals(code)) {
                    ilogin.GetUserInfoSuccess(code,msg,data);
                }else {
                    ilogin.GetUserInfoFailure(code,msg);
                }
            }
        });
    }

    /**
     * 头像上传
     * @param uid
     * @param file
     */
    public void  HeadUpLoad(int uid,File file){
        OkHttpClient okHttpClient=new OkHttpClient();
        RequestBody  requestbody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("uid", uid+"")
                .addFormDataPart("file",file.getName(), RequestBody.create(MediaType.parse("application/octet-stream"),file))
                .build();
        Request request = new Request.Builder().url(API.USERHEADUP).post(requestbody).build();

        okHttpClient.newBuilder().writeTimeout(50, TimeUnit.SECONDS).build().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ilogin.OnHeadUpLoadFailure(call,e);
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Gson gson=new Gson();
                Resultinfo resultinfo = gson.fromJson(string, Resultinfo.class);
                String code = resultinfo.getCode();
                String msg = resultinfo.getMsg();
                if ("0".equals(code)) {
                    ilogin.HeadUpLoadSuccess(code,msg);
                }else {
                    ilogin.HeadUpLoadFailure(code,msg);
                }
            }
        });
    }

    /**
     * 获取首页信息
     */
    public void  GetXbannerImageUrls(){
//        OkHttpClient client =new OkHttpClient.Builder()
//                .addInterceptor(new LogInterceptor())
//                .build();
        OkHttpClient client=new OkHttpClient();
        FormBody requestbody= new FormBody.Builder().build();
        Request request=new Request.Builder().url(API.XBANNERINFO).post(requestbody).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ilogin.OnGetXbannerImageUrlsFailure(call,e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Gson gson=new Gson();
                jdmainData jdmainData = gson.fromJson(string, jdmainData.class);
                String code = jdmainData.getCode();
                String msg = jdmainData.getMsg();
                if ("0".equals(code)) {
                    if (jdmainData!=null){
                        ilogin.GetXbannerImageUrlsSuccess(code,jdmainData);
                    }

                }else if ("1".equals(code)){
                    ilogin.GetXbannerImageUrlsFailure(code,msg);
                }


            }
        });
    }

    public void GetFeiLeiInfo(){
        OkHttpClient client=new OkHttpClient();
        RequestBody formBody=new FormBody.Builder()
                .build();
        Request request=new Request.Builder().url(API.GETFENLEIINFO).post(formBody).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ilogin.OnGetFenLeiInfoFailure(call,e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String string = response.body().string();
                    Gson gson=new Gson();
                    GetFeiLeiInfo getFeiLeiInfo = gson.fromJson(string, GetFeiLeiInfo.class);
                    if (getFeiLeiInfo!=null) {
                        String code = getFeiLeiInfo.getCode();
                        String msg = getFeiLeiInfo.getMsg();
                        List<GetFeiLeiInfo.DataBean> data = getFeiLeiInfo.getData();
                        if ("0".equals(code)) {
                            ilogin.GetFenLeiInfoSuccess(code,data);
                        } else if ("1".equals(code)) {
                            ilogin.GetFenLeiInfoFailure(code,msg);
                        }
                    }


                }
            }
        });

    }

    public void GetChildFenLeiInfo(int cid){
        OkHttpClient client=new OkHttpClient();
        RequestBody body=new FormBody.Builder()
                .add("cid",cid+"")
                .build();
        Request request=new Request.Builder().url(API.GETCHILDFENLEIINFO).post(body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ilogin.OnGetChildFenLeiInfoFailure(call,e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String string = response.body().string();
                    Gson gson=new Gson();
                    GetChildFenLeiInfo getChildFenLeiInfo = gson.fromJson(string, GetChildFenLeiInfo.class);
                    String code = getChildFenLeiInfo.getCode();
                    List<GetChildFenLeiInfo.DataBean> data = getChildFenLeiInfo.getData();
                    String msg = getChildFenLeiInfo.getMsg();
                    if ("0".equals(code)) {
                        ilogin.GetChildFenLeiInfoSuccess(code,msg,data);
                    }else if ("1".equals(code)){
                        ilogin.GetChildFenLeiInfoFailure(code,msg);

                    }
                }
            }
        });

    }

    public void GetShangPinChildList(String url, Map<String ,Object> map){
        NetWorkRequestUtils.getSingletonInstance().GetRequestData(url, map, new CallBackResult() {
            @Override
            public void onFailure(Call call, Exception e) {
                myModul.onFailure(call,e);
            }

            @Override
            public void onResponse(Call call, Response response) {
                myModul.onResponse(call, response);
            }
        });
    }
    public  void GetData(String url, Map<String ,Object> map ){
        NetWorkRequestUtils.getSingletonInstance().GetData(url, map, new CallBackResult() {
            @Override
            public void onFailure(Call call, Exception e) {
                myModul.onFailure(call,e);
            }

            @Override
            public void onResponse(Call call, Response response) {
                myModul.onResponse(call, response);
            }
        });
    }
    public void GetChildFenLeiProductList(String url,Map<String,Object> map){
        NetWorkRequestUtils.getSingletonInstance().GetData(url, map, new CallBackResult() {
            @Override
            public void onFailure(Call call, Exception e) {
                myModul.onFailure(call, e);
            }

            @Override
            public void onResponse(Call call, Response response) {
                myModul.onResponse(call, response);
            }
        });

    }






    private ILogin ilogin;

    public void setIlogin(ILogin ilogin) {
        this.ilogin = ilogin;
    }

    public interface ILogin{
        void LoginSuccess(String code,String message,Resultinfo resultinfo);
        void LoginFailure(String code,String message);
        void onLoginFailure(Call call,IOException e);

        void ZhuceSuccess(String code,String message);
        void ZhuceFailure(String code,String message);
        void onZhuceFailure(Call call,IOException e);

        void GetUserInfoSuccess(String code, String message, Resultinfo.DataBean dataBean);
        void GetUserInfoFailure(String code,String message);
        void onGetUserInfoFailure(Call call,IOException e);

        void HeadUpLoadSuccess(String code,String message);
        void HeadUpLoadFailure(String code,String message);
        void OnHeadUpLoadFailure(Call call,IOException e);

        void GetXbannerImageUrlsSuccess(String code,jdmainData jdmainData);
        void GetXbannerImageUrlsFailure(String code,String message);
        void OnGetXbannerImageUrlsFailure(Call call,IOException e);

        void GetFenLeiInfoSuccess(String code,List<GetFeiLeiInfo.DataBean> data);
        void GetFenLeiInfoFailure(String code,String message);
        void OnGetFenLeiInfoFailure(Call call, IOException e);

        void GetChildFenLeiInfoSuccess(String code,String msg,List<GetChildFenLeiInfo.DataBean> data);
        void GetChildFenLeiInfoFailure(String code,String message);
        void OnGetChildFenLeiInfoFailure(Call call, IOException e);

    }


    public void setMyModul(MyModul myModul) {
        this.myModul = myModul;
    }

    public interface MyModul{
        void onFailure(Call call,Exception e);
        void onResponse(Call call,Response response);
    }
}
