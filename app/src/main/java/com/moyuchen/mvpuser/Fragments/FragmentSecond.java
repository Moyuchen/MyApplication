package com.moyuchen.mvpuser.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moyuchen.mvpuser.Adapter.SecondRecycleViewAdapter;
import com.moyuchen.mvpuser.Bean.GetFeiLeiInfo;
import com.moyuchen.mvpuser.LoginView.GetFenLeiInfoView;
import com.moyuchen.mvpuser.Presenter.LoginPresenter;
import com.moyuchen.mvpuser.R;
import com.moyuchen.mvpuser.Utils.CommonUtils;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;

/**
 * User: 张亚博
 * Date: 2017-10-08 20:10
 * Description：
 */
public class FragmentSecond extends Fragment implements GetFenLeiInfoView {
    private View view;
    private LoginPresenter presentersecond;
    private RecyclerView second_recycleView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view==null) {
            view=inflater.inflate(R.layout.fragmentsecond,container,false);
            initView();
            initData();
        }
        return view;
    }

    private void initData() {
        presentersecond = new LoginPresenter(this);
        presentersecond.GetFeiLeiInfo();
    }

    private void initView() {
        second_recycleView = view.findViewById(R.id.Second_RecycleView);
        second_recycleView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void GetFenLeiInfoSuccess(String code, final List<GetFeiLeiInfo.DataBean> data) {
        if (getActivity()!=null) {

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                SecondRecycleViewAdapter RecViewAda = new SecondRecycleViewAdapter(data, getContext());
                second_recycleView.setAdapter(RecViewAda);
                int cid = data.get(0).getCid();
                FragmentSecondAll fragment = (FragmentSecondAll) new FragmentSecondAll().FragmentSecondAll(cid);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.second_framelayout,fragment).commit();

                RecViewAda.setOnRecItemListener(new SecondRecycleViewAdapter.onRecItemListener() {
                    @Override
                    public void RecItemListener(View view, GetFeiLeiInfo.DataBean databean) {
                        CommonUtils.SHOWTOAST(getActivity(),"您点了"+databean.getName());
                        int cid = databean.getCid();
                        System.out.println("CID"+cid);
                        FragmentSecondAll fragment = (FragmentSecondAll) new FragmentSecondAll().FragmentSecondAll(cid);
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.second_framelayout,fragment).commit();

                    }
                });

            }
        });
        }
    }

    @Override
    public void GetFenLeiInfoFailure(String code, String message) {
        CommonUtils.SHOWTOAST(getActivity(),message);
    }

    @Override
    public void OnGetFenLeiInfoFailure(Call call, IOException e) {
        CommonUtils.SHOWTOAST(getActivity(),"分类请求失败");
    }


}
