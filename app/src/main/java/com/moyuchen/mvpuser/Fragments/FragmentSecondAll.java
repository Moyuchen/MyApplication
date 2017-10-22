package com.moyuchen.mvpuser.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moyuchen.mvpuser.Adapter.FragSecAllRecViewAdapter;
import com.moyuchen.mvpuser.LoginView.GetChildFenLeiInfo;
import com.moyuchen.mvpuser.Presenter.LoginPresenter;
import com.moyuchen.mvpuser.R;
import com.moyuchen.mvpuser.Utils.CommonUtils;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;

/**
 * User: 张亚博
 * Date: 2017-10-11 13:51
 * Description：
 */
public class FragmentSecondAll extends Fragment implements GetChildFenLeiInfo {


    private View view;
    private RecyclerView recycleview;
    private int cid;

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public Fragment FragmentSecondAll(int cid) {
        FragmentSecondAll secondall=new FragmentSecondAll();
        Bundle bundle=new Bundle();
        bundle.putInt("cid",cid);
        System.out.println("cid"+cid);
        secondall.setArguments(bundle);
        return secondall;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view==null) {
           view= inflater.inflate(R.layout.fragmentsecondall,container,false);
            initView();
            initData();
        }else {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent!=null) {
                parent.removeAllViews();
                parent.addView(view);
            }
        }
        return view;
    }

    private void initData() {
        int cid = getArguments().getInt("cid");
        LoginPresenter presenter=new LoginPresenter(this);
        presenter.setGetChildFenLeiInfo(cid);

    }

    private void initView() {
        recycleview = view.findViewById(R.id.fragmentsecondall_recycleview);
        LinearLayoutManager manager=new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleview.setLayoutManager(manager);


    }


    @Override
    public void GetChildFenLeiInfoSuccess(String code, String msg, final List<com.moyuchen.mvpuser.Bean.GetChildFenLeiInfo.DataBean> data) {
        if (getActivity()!=null) {
            getActivity().runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    FragSecAllRecViewAdapter viewAdapter = new FragSecAllRecViewAdapter(data, getContext());
                    recycleview.setAdapter(viewAdapter);

                }
            });
        }
    }

    @Override
    public void GetChildFenLeiInfoFailure(String code, String message) {
        CommonUtils.SHOWTOAST(getActivity(),message);
    }

    @Override
    public void OnGetChildFenLeiInfoFailure(Call call, IOException e) {
        CommonUtils.SHOWTOAST(getActivity(),"请求子分类失败");
    }

    @Override
    public void CidError(String message) {
        CommonUtils.SHOWTOAST(getActivity(),message);
    }
}
