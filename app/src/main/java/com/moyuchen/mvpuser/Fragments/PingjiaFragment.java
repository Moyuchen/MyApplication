package com.moyuchen.mvpuser.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moyuchen.mvpuser.R;

/**
 * User: 张亚博
 * Date: 2017-10-17 16:41
 * Description：
 */
public class PingjiaFragment extends Fragment {

    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view==null) {
            view = inflater.inflate(R.layout.pingjia, container, false);
            initView();
        }
        return view;
    }

    private void initView() {
//        view.findViewById(R.id.)
    }


}
