package com.moyuchen.mvpuser.Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.moyuchen.mvpuser.Adapter.RCV1_VPadapter;
import com.moyuchen.mvpuser.Adapter.RecycleAdapter;
import com.moyuchen.mvpuser.Adapter.TuijianRecycleViewAdapter;
import com.moyuchen.mvpuser.Bean.GetFeiLeiInfo;
import com.moyuchen.mvpuser.Bean.jdmainData;
import com.moyuchen.mvpuser.LoginView.GetFenLeiInfoView;
import com.moyuchen.mvpuser.LoginView.GetXbannerdataView;
import com.moyuchen.mvpuser.Owner.GradaScrollView;
import com.moyuchen.mvpuser.Presenter.LoginPresenter;
import com.moyuchen.mvpuser.ProductSearchActivity;
import com.moyuchen.mvpuser.R;
import com.moyuchen.mvpuser.Utils.CommonUtils;
import com.stx.xhb.xbanner.XBanner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * User: 张亚博
 * Date: 2017-10-08 18:49
 * Description：
 */
@SuppressLint("NewApi")
public class FragmentFirst extends Fragment implements GetXbannerdataView,GetFenLeiInfoView, ViewPager.OnPageChangeListener {
    private View view;
    private RecyclerView recycleView;
    private XBanner xbanner;
    private LoginPresenter presenter;
    private ViewPager rcv_vp;
    private LinearLayout linearlayout;
    private List<ImageView> imageViews=new ArrayList<>();
    private TextView mstitle;
    private List<List<GetFeiLeiInfo.DataBean>> dataBeenAll=new ArrayList<>();
    private RecyclerView tuiJianRecycleView;
    private GradaScrollView scrollView;
    private Toolbar toolBar;
    private ImageView etlinear;
    private TextView hours;
    private TextView minute;
    private TextView second;
    private int hours2;
    private int minute2;
    private int second2;
Handler handler=new Handler(){
    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        int what = msg.what;
        if (what>=0) {
            int seconds=what/1000%60;
            int minutes=what/1000/60%60;
            int hourss=what/1000/60/60%60;

                if (hourss<10) {
                    hours.setText("0"+hourss+":");
                }else {
                    hours.setText(hourss+":");
                }

            if (minutes<10) {
                minute.setText("0"+minutes+":");
            }else {
                minute.setText(minutes+":");
            }

            if (seconds<10) {
                second.setText("0"+seconds+":");
            }else {
                second.setText(seconds+"");
            }

            what=what-1000;
            handler.sendEmptyMessageDelayed(what,1000);
            }
        }

};


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view==null) {
            view=inflater.inflate(R.layout.fragmentfirst,container,false);
            InitView();
            InitData();
        }

        return view;
    }

    private void InitData() {
        presenter = new LoginPresenter(this,this);
        presenter.GetXbannerdataView();
        presenter.GetFeiLeiInfo();

    }

    @SuppressLint("NewApi")
    private void InitView() {
        etlinear = view.findViewById(R.id.jqr);
        etlinear.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (getActivity()!=null) {
                }
                Intent intent=new Intent(getActivity(), ProductSearchActivity.class);
                startActivity(intent);
                return false;
            }
        });

        recycleView = (RecyclerView) view.findViewById(R.id.recycleview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recycleView.setLayoutManager(linearLayoutManager);

        tuiJianRecycleView = view.findViewById(R.id.TuiJianRecycleView);

        tuiJianRecycleView.setLayoutManager(new GridLayoutManager(getContext(),2));

        xbanner=view.findViewById(R.id.xbanner);

        rcv_vp = view.findViewById(R.id.RCV_vp);
        rcv_vp.setOnPageChangeListener(this);

        linearlayout = view.findViewById(R.id.linearlayout);

        mstitle = view.findViewById(R.id.MsTitle);

        scrollView = view.findViewById(R.id.scrollView);

        toolBar = view.findViewById(R.id.toolbar);

        hours = view.findViewById(R.id.hours);
        minute = view.findViewById(R.id.minute);
        second = view.findViewById(R.id.second);


        ViewTreeObserver viewTreeObserver = xbanner.getViewTreeObserver();

        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                final int height = xbanner.getResources().getDisplayMetrics().heightPixels;

                scrollView.setScrollViewListener(new GradaScrollView.ScrollViewListener() {
                    @Override
                    public void onScrollChanged(GradaScrollView scrollView, int x, int y, int oldx, int oldy) {
                        if (y <= 0) {

                            toolBar.setBackgroundColor(Color.argb((int) 0, 18, 176, 242));
                        } else if (y > 0 && y <= height) {
                            float scale = (float) y / height;
                            float alpha = (255 * scale);
                            toolBar.setBackgroundColor(Color.argb((int) alpha, 18, 176, 242));
                        } else {
                            toolBar.setBackgroundColor(Color.argb((int) 255, 18, 176, 242));
                        }
                    }
                });
            }

        });
    }

    /**
     * 画圆点
     */
    private void drawCircle() {
        for (int i=0;i<dataBeenAll.size();i++){
            ImageView imageview=new ImageView(getContext());
            if (i==0) {
                imageview.setImageResource(R.drawable.round_red);
            }else {
                imageview.setImageResource(R.drawable.round_white);
            }
            imageViews.add(imageview);

            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(10,10);
            params.setMargins(5,0,5,0);
            linearlayout.addView(imageview,params);
        }
    }

    @Override
    public void GetXbannerImageUrlsFailure(String code, final String message) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getContext(),message,Toast.LENGTH_LONG).show();

            }
        });
    }

    @Override
    public void GetXbannerImageUrlsSuccess(String code, final jdmainData jdmainData) {
        if (getActivity()!=null) {

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                com.moyuchen.mvpuser.Bean.jdmainData.MiaoshaBean miaosha = jdmainData.getMiaosha();
                List<com.moyuchen.mvpuser.Bean.jdmainData.MiaoshaBean.ListBeanX> list = miaosha.getList();
                String name = miaosha.getName();
                final int time = miaosha.getTime();

                List<com.moyuchen.mvpuser.Bean.jdmainData.DataBean> data = jdmainData.getData();

                com.moyuchen.mvpuser.Bean.jdmainData.TuijianBean tuijian = jdmainData.getTuijian();
                List<com.moyuchen.mvpuser.Bean.jdmainData.TuijianBean.ListBean> tuijianglist = tuijian.getList();

                final List<String> imageurls=new ArrayList<String>();

                for (jdmainData.DataBean dataBean : data) {
                    String icon = dataBean.getIcon();
                    imageurls.add(icon);
                }

                xbanner.setData(imageurls,null);
                xbanner.setmAdapter(new XBanner.XBannerAdapter() {
                    @Override
                    public void loadBanner(XBanner banner, Object model, View view, int position) {
                        Glide.with(getContext()).load(imageurls.get(position)).into((ImageView) view);
                    }
                });
                mstitle.setText(name);
             handler.sendEmptyMessageDelayed(time,1000);

                RecycleAdapter recycleAdapter=new RecycleAdapter(getContext(),list);
                recycleView.setAdapter(recycleAdapter);

                tuiJianRecycleView.setAdapter(new TuijianRecycleViewAdapter(tuijianglist,getContext()));

            }
        });
        }
    }
    @Override
    public void OnGetXbannerImageUrlsFailure(Call call, Exception e) {
        if ( getActivity()!=null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getContext(),"广告图片请求失败",Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        for (int i=0;i<imageViews.size();i++){
            ImageView imageView = imageViews.get(i);
            if (i==position) {
                imageView.setImageResource(R.drawable.round_red);
            }else {
                imageView.setImageResource(R.drawable.round_white);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    @Override
    public void GetFenLeiInfoSuccess(String code, final List<GetFeiLeiInfo.DataBean> data) {
        if (getActivity()!=null) {

        getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int moddle = CommonUtils.GetModdle(data.size());
                        final List<GetFeiLeiInfo.DataBean> dataBeen=new ArrayList<>();
                        final List<GetFeiLeiInfo.DataBean> dataBeen2=new ArrayList<>();
                        for (int i=0;i<moddle;i++) {
                            dataBeen.add(data.get(i));
                        }
                        for (int i=moddle;i<data.size();i++) {
                            dataBeen2.add(data.get(i));
                        }
                        dataBeenAll.add(dataBeen);
                        dataBeenAll.add(dataBeen2);
                        drawCircle();
                        rcv_vp.setAdapter(new RCV1_VPadapter(dataBeenAll,getContext()));
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
        CommonUtils.SHOWTOAST(getActivity(),"1请求分类数据失败");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(handler);
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacksAndMessages(handler);

    }
}
