package com.moyuchen.mvpuser.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.moyuchen.mvpuser.MainActivity;
import com.moyuchen.mvpuser.MyOrderActivity;
import com.moyuchen.mvpuser.R;
import com.moyuchen.mvpuser.Utils.CommonUtils;
import com.moyuchen.mvpuser.ZhangHuSettingActivity;


/**
 * User: 张亚博
 * Date: 2017-10-08 20:10
 * Description：
 */
public class FragmentFifth extends Fragment implements View.OnClickListener {
    private View view;
    private TextView tvFifLogin;
    private TextView tvZhuce;
    private String TouXiangurl;
    private String LoginStaticTrueUserName;
    private LinearLayout zhangHuLinLay;
    private Boolean loginStatic;
    private LinearLayout myOrder;

    public String getLoginStaticTrueUserName() {
        return LoginStaticTrueUserName;
    }

    public void setLoginStaticTrueUserName(String loginStaticTrueUserName) {
        LoginStaticTrueUserName = loginStaticTrueUserName;
    }

    private RoundedImageView head_criv;
    private FragmentFifth fragmentFifth;
    private LinearLayout loginStaticFalseLinLay;
    private LinearLayout loginStaticTrueLinLay;
    private TextView fifthTvUserName;

    public String getTouXiangurl() {
        return TouXiangurl;
    }

    public void setTouXiangurl(String touXiangurl) {
        TouXiangurl = touXiangurl;
    }

    public Fragment FragmentFifth(String touXiangurl,String loginStaticTrueUserName) {
        fragmentFifth = new FragmentFifth();
        Bundle bundle=new Bundle();
        bundle.putString("TouXiangUrl",touXiangurl);
        bundle.putString("LoginStaticTrueUserName",loginStaticTrueUserName);
        fragmentFifth.setArguments(bundle);

        return fragmentFifth;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view==null) {
            view=inflater.inflate(R.layout.fragmentfifth,container,false);
            initView();
        }
        return view;
    }

    private void initView() {
        tvFifLogin = view.findViewById(R.id.tv_FifLogin);
        myOrder = view.findViewById(R.id.MyOrder);
        myOrder.setOnClickListener(this);
        tvZhuce = view.findViewById(R.id.tv_ZhuCe);
        loginStatic = CommonUtils.GetLoginStatic(getContext(),"LoginStatic");
        head_criv = view.findViewById(R.id.head_criv);
        loginStaticFalseLinLay = view.findViewById(R.id.LoginStaticFalseLinLay);
        loginStaticTrueLinLay = view.findViewById(R.id.LoginStaticTrueLinLay);
        fifthTvUserName = view.findViewById(R.id.FifthTv_UserName);
        zhangHuLinLay = view.findViewById(R.id.ZhangHuLinLay);
        if (loginStatic) {
            String loginStaticTrueUserName = getArguments().getString("LoginStaticTrueUserName");
            loginStaticTrueLinLay.setVisibility(LinearLayout.VISIBLE);
            loginStaticFalseLinLay.setVisibility(LinearLayout.GONE);
            if (TextUtils.isEmpty(loginStaticTrueUserName)) {
                fifthTvUserName.setText("17600295487");
            }else {
                fifthTvUserName.setText(LoginStaticTrueUserName);
            }
            String touXiangUrl = getArguments().getString("TouXiangUrl");
            Glide.with(getActivity()).load("http://120.27.23.105/images/1506734407186upload_6fdb1a20_bd62_48e5_9b70_c2bedc609592_00000001.tmp").into(head_criv);
        }else {
            loginStaticTrueLinLay.setVisibility(LinearLayout.GONE);
            loginStaticFalseLinLay.setVisibility(LinearLayout.VISIBLE);
            head_criv.setImageResource(R.mipmap.ic_launcher);

        }
        zhangHuLinLay.setOnClickListener(this);
    }

    /**
     * 跳转到登录页面
     */
    public void FifLogin(){

        if (getActivity()!=null){
            Intent intent=new Intent(getActivity(), MainActivity.class);
            getActivity().startActivity(intent);
        }

    }

    /**
     * 跳转到账户设置页面
     */
    public void ZhangHuSetting(){
        if (getActivity()!=null){
            Intent intent=new Intent(getActivity(), ZhangHuSettingActivity.class);
            intent.putExtra("touxiangurl","http://120.27.23.105/images/1506734407186upload_6fdb1a20_bd62_48e5_9b70_c2bedc609592_00000001.tmp");
            getActivity().startActivity(intent);
        }

    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.ZhangHuLinLay:
                if (loginStatic) {
                    ZhangHuSetting();
                }else {
                    FifLogin();
                }
                break;
            case R.id.MyOrder:
                Intent intent=new Intent(getActivity(),MyOrderActivity.class);
                startActivity(intent);
                break;

        }
    }
}
