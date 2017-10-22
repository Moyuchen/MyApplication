package com.moyuchen.mvpuser.Adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.moyuchen.mvpuser.Bean.GetFeiLeiInfo;
import com.moyuchen.mvpuser.R;

import java.util.List;

/**
 * User: 张亚博
 * Date: 2017-10-09 14:39
 * Description：
 */
public class RCV1_VPadapter extends PagerAdapter {
    private List<List<GetFeiLeiInfo.DataBean>> dataBeenAll;
    private Context context;
    public RCV1_VPadapter(List<List<GetFeiLeiInfo.DataBean>> dataBeenAll, Context context) {
        this.dataBeenAll=dataBeenAll;
        this.context=context;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
       container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return dataBeenAll.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==(object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        RecyclerView view = (RecyclerView) View.inflate(context, R.layout.recycleview, null);

        List<GetFeiLeiInfo.DataBean> dataBeen = dataBeenAll.get(position);
        view.setLayoutManager(new GridLayoutManager(context,5));
        view.setAdapter(new VP_Rcvadapter(dataBeenAll.get(position),context));
        container.addView(view);
        return view;
    }
}
